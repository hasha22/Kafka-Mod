package net.hasha.kafkamod;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.hasha.kafkamod.block.ModBlocks;
import net.hasha.kafkamod.config.ConfigManager;
import net.hasha.kafkamod.config.KafkaModConfig;
import net.hasha.kafkamod.items.ModItemGroups;
import net.hasha.kafkamod.items.ModItems;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class KafkaMod implements ModInitializer {
	public static final String MOD_ID = "kafka-mod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    //Hunger logic variables
    private static final Map<UUID, Integer> sprintTicks = new HashMap<>(); //map to track per player how many consecutive ticks they have been sprinting

    //Config
    public static KafkaModConfig CONFIG;

	@Override
	public void onInitialize() {
        CONFIG = ConfigManager.load();

        //Item Registration
        ModItemGroups.registerItemGroups();
        ModItems.registerModItems();
        ModBlocks.registerModBlocks();

        //Event Registration
        WindowNPCInteraction.register();

        //Increasing hunger drain for all players not in creative mode while sprinting
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for(ServerPlayerEntity player : server.getPlayerManager().getPlayerList())
            {
                if(player.isSprinting() && !player.getAbilities().creativeMode)
                {
                    //how many consecutive ticks the player has been sprinting
                    int ticks = sprintTicks.merge(player.getUuid(), 1, (old, inc) -> Math.min(old + inc, CONFIG.maxTicks));

                    //scales exponentially
                    float exhaustion = (float) (CONFIG.baseTickCost * Math.pow(CONFIG.tickCostGrowth, ticks));
                    player.getHungerManager().addExhaustion(exhaustion);
                }
                else
                {
                    sprintTicks.computeIfPresent(player.getUuid(), (uuid, old) -> {
                        int decayed = old - CONFIG.decayPerTick;
                        return decayed > 0 ? decayed : null;
                            });
                }
            }
        });

        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            sprintTicks.remove(handler.getPlayer().getUuid());
        });
	}
	public static Identifier id(String path) {
		return new Identifier(MOD_ID, path);
	}
}

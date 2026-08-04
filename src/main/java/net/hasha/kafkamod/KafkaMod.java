package net.hasha.kafkamod;

import net.fabricmc.api.ModInitializer;

import net.hasha.kafkamod.block.ModBlocks;
import net.hasha.kafkamod.items.ModItemGroups;
import net.hasha.kafkamod.items.ModItems;
import net.minecraft.util.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaMod implements ModInitializer {
	public static final String MOD_ID = "kafka-mod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
        ModItemGroups.registerItemGroups();
        ModItems.registerModItems();

        ModBlocks.registerModBlocks();
	}
	public static Identifier id(String path) {
		return new Identifier(MOD_ID, path);
	}
}

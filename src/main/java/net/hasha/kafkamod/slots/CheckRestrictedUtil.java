package net.hasha.kafkamod.slots;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameMode;

//Helper class
public class CheckRestrictedUtil
{
    public static boolean isRestricted(PlayerEntity player)
    {
        if (player instanceof ServerPlayerEntity sp)
        {
            if (sp.interactionManager == null) return false;
            GameMode mode = sp.interactionManager.getGameMode();
            return mode == GameMode.SURVIVAL || mode == GameMode.ADVENTURE;
        }
        return ClientGameModeCheck.isRestricted();
    }
}

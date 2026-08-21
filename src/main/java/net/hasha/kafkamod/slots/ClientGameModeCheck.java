package net.hasha.kafkamod.slots;

import net.minecraft.client.MinecraftClient;
import net.minecraft.world.GameMode;

//Helper class to centralize isRestricted() logic
public class ClientGameModeCheck
{
    public static boolean isRestricted()
    {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.interactionManager == null) return false;
        GameMode mode = client.interactionManager.getCurrentGameMode();
        return mode == GameMode.SURVIVAL || mode == GameMode.ADVENTURE;
    }
}

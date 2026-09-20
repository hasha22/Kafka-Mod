package net.hasha.kafkamod.vehicles.render;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.hasha.kafkamod.vehicles.ModEntities;

public class ModEntityRenderers
{
    public static void register()
    {
        EntityRendererRegistry.register(ModEntities.CAR, PlaceholderVehicleRenderer::new);
        EntityRendererRegistry.register(ModEntities.BUS, PlaceholderVehicleRenderer::new);
    }
}


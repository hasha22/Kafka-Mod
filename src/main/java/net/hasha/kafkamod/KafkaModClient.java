package net.hasha.kafkamod;

import net.fabricmc.api.ClientModInitializer;
import net.hasha.kafkamod.vehicles.networking.VehicleClientNetworking;
import net.hasha.kafkamod.vehicles.render.ModEntityRenderers;

public class KafkaModClient implements ClientModInitializer
{
    @Override
    public  void onInitializeClient()
    {
        VehicleClientNetworking.register();
        ModEntityRenderers.register();
    }
}

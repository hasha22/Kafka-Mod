package net.hasha.kafkamod.vehicles.networking;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.hasha.kafkamod.KafkaMod;
import net.hasha.kafkamod.vehicles.VehicleEntity;
import net.minecraft.util.Identifier;

public class VehicleNetworking
{
    public static final Identifier VEHICLE_INPUT = new Identifier(KafkaMod.MOD_ID, "vehicle_input");

    public static void registerServer()
    {
        ServerPlayNetworking.registerGlobalReceiver(VEHICLE_INPUT, (server, player, handler, buf, responseSender) ->
        {
            boolean forward = buf.readBoolean();
            boolean back = buf.readBoolean();
            boolean left = buf.readBoolean();
            boolean right = buf.readBoolean();

            server.execute(() ->
            {
                if (player.getVehicle() instanceof VehicleEntity vehicle && vehicle.getControllingPassenger() == player) {
                    vehicle.setInput(forward, back, left, right);
                }
            });
        });
    }
}

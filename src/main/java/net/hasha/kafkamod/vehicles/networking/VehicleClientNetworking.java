package net.hasha.kafkamod.vehicles.networking;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.PacketByteBuf;

public class VehicleClientNetworking
{
    public static void register()
    {
        ClientTickEvents.END_CLIENT_TICK.register(client ->
        {
            ClientPlayerEntity player = client.player;
            if (player == null || player.getVehicle() == null) { return; }

            boolean forward = client.options.forwardKey.isPressed();
            boolean back = client.options.backKey.isPressed();
            boolean left = client.options.leftKey.isPressed();
            boolean right = client.options.rightKey.isPressed();

            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeBoolean(forward);
            buf.writeBoolean(back);
            buf.writeBoolean(left);
            buf.writeBoolean(right);
            ClientPlayNetworking.send(VehicleNetworking.VEHICLE_INPUT, buf);
        });
    }
}

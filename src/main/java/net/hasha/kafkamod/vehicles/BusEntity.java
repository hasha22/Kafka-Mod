package net.hasha.kafkamod.vehicles;

import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class BusEntity extends VehicleEntity
{
    public static final int PASSENGER_SEAT_COUNT = 8;

    //Constructor
    public BusEntity(EntityType<? extends BusEntity> type, World world)
    {
        super(type, world);
        this.maxSpeed = 0.35f;
        this.reverseMaxSpeed = 0.12f;
        this.acceleration = 0.012f;
        this.turnSpeed = 2.5f;
        this.drag = 0.94f;
    }
}

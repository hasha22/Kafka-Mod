package net.hasha.kafkamod.vehicles;

import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class CarEntity extends VehicleEntity
{
    public static final int SEAT_COUNT = 4;

    //Constructor
    public CarEntity(EntityType<? extends CarEntity> type, World world)
    {
        super(type, world);
        this.maxSpeed = 0.5f;
        this.reverseMaxSpeed = 0.2f;
        this.acceleration = 0.02f;
        this.turnSpeed = 4.5f;
        this.drag = 0.92f;
    }
}

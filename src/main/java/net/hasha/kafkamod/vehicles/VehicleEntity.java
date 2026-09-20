package net.hasha.kafkamod.vehicles;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public abstract class VehicleEntity extends Entity
{
    //Input Variables
    private boolean inputForward;
    private boolean inputBack;
    private boolean inputLeft;
    private boolean inputRight;

    //Physics variables
    protected float maxSpeed = 0.5f;
    protected float reverseMaxSpeed = 0.2f;
    protected float acceleration = 0.02f;
    protected float turnSpeed = 4.0f;
    protected float drag = 0.92f;

    private float currentSpeed = 0.0f;

    //Constructor
    protected VehicleEntity(EntityType<?> type, World world) { super(type, world); }

    //Setter
    public void setInput(boolean forward, boolean back, boolean left, boolean right)
    {
        this.inputForward = forward;
        this.inputBack = back;
        this.inputLeft = left;
        this.inputRight = right;
    }

    //Update
    @Override
    public void tick()
    {
        super.tick();

        if (!this.getWorld().isClient) { this.applyInput(); }

        this.move(MovementType.SELF, this.getVelocity());
    }

    //Interaction
    @Override
    public ActionResult interact (PlayerEntity player, Hand hand)
    {
        if (!this.getWorld().isClient && this.getPassengerList().isEmpty()) { player.startRiding(this); }

        return ActionResult.success(this.getWorld().isClient);
    }
    @Override
    public boolean canHit() { return true; }

    //Gets the driver
    @Override
    public LivingEntity getControllingPassenger()
    {
        Entity passenger = this.getFirstPassenger();
        return passenger instanceof LivingEntity livingEntity ? livingEntity : null;
    }

    @Override
    protected void updatePassengerPosition(Entity passenger, PositionUpdater positionUpdater)
    {
        //placeholder
        positionUpdater.accept(passenger, this.getX(), this.getY() + this.getMountedHeightOffset(), this.getZ());
    }

    //Input calculations
    private void applyInput()
    {
        if (this.inputForward) { this.currentSpeed = Math.min(this.maxSpeed, this.currentSpeed + this.acceleration); }
        else if (this.inputBack) { this.currentSpeed = Math.max(-this.reverseMaxSpeed, this.currentSpeed - this.acceleration); }
        else
        {
            this.currentSpeed *= this.drag;
            if (Math.abs(this.currentSpeed) < 0.005f) { this.currentSpeed = 0f; }
        }

        float speedFactor = MathHelper.clamp(Math.abs(this.currentSpeed) / this.maxSpeed, 0f, 1f);
        if (this.inputLeft) { this.setYaw(this.getYaw() - this.turnSpeed * speedFactor); }
        if (this.inputRight) { this.setYaw(this.getYaw() + this.turnSpeed * speedFactor); }

        double yawRad = Math.toRadians(this.getYaw());
        double dx = -Math.sin(yawRad) * this.currentSpeed;
        double dz = Math.cos(yawRad) * this.currentSpeed;
        this.setVelocity(dx, this.getVelocity().y, dz);
    }

    //for later
    @Override
    protected void initDataTracker()
    {

    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt)
    {

    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt)
    {

    }


}

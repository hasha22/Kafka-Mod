package net.hasha.kafkamod.vehicles.render;

import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

public class PlaceholderVehicleRenderer<T extends Entity> extends EntityRenderer<T>
{
    public PlaceholderVehicleRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public Identifier getTexture(T entity) {
        return new Identifier("minecraft", "textures/misc/missing.png");
    }
}

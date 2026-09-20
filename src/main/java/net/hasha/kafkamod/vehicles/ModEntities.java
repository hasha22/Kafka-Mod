package net.hasha.kafkamod.vehicles;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.hasha.kafkamod.KafkaMod;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities
{
    public static final EntityType<CarEntity> CAR = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(KafkaMod.MOD_ID, "car"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, CarEntity::new)
                    .dimensions(EntityDimensions.fixed(1.8f, 1.6f))
                    .trackRangeChunks(10)
                    .build()
    );
    public static final EntityType<BusEntity> BUS = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(KafkaMod.MOD_ID, "bus"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, BusEntity::new)
                    .dimensions(EntityDimensions.fixed(2.2f, 2.4f))
                    .trackRangeChunks(10)
                    .build()
    );
    public static void register() {}

}

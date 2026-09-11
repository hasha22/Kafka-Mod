package net.hasha.kafkamod.items;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.hasha.kafkamod.KafkaMod;
import net.hasha.kafkamod.block.ModBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.text.Text;

public class ModItemGroups
{
    public static final ItemGroup KAFKA_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(KafkaMod.MOD_ID, "kafka"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.kafka"))
                    .icon(() -> new ItemStack(ModItems.KAFKA_ICON)).entries((displayContext, entries) -> {
                        entries.add(ModItems.KAFKA_ICON);
                        entries.add(ModBlocks.KAFKA_HALF_WALL);
                        entries.add(ModBlocks.KAFKA_STREET_LAMP);
                        entries.add(ModBlocks.KAFKA_LAMPOST);

                    }).build());
    public static void registerItemGroups()
    {
        KafkaMod.LOGGER.info("Registering Item Groups for " + KafkaMod.MOD_ID);
    }
}

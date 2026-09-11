package net.hasha.kafkamod.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.hasha.kafkamod.KafkaMod;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks
{
    public static final Block KAFKA_HALF_WALL = registerBlock("kafka_half_wall",
            new Block(FabricBlockSettings.create()
                    .mapColor(MapColor.STONE_GRAY)
                    .strength(2.0f)
                    .requiresTool()
                    .nonOpaque()));

    public static final Block KAFKA_STREET_LAMP = registerBlock("kafka_street_lamp",
            new Block(FabricBlockSettings.create()
                    .mapColor(MapColor.STONE_GRAY)
                    .strength(2.0f)
                    .requiresTool()
                    .nonOpaque()));

    public static final Block KAFKA_LAMPOST = registerBlock("kafka_lampost",
            new Block(FabricBlockSettings.create()
                    .mapColor(MapColor.STONE_GRAY)
                    .strength(2.0f)
                    .requiresTool()
                    .nonOpaque()));
    private static Block registerBlock(String name, Block block)
    {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(KafkaMod.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block)
    {
        return Registry.register(Registries.ITEM, new Identifier(KafkaMod.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerModBlocks()
    {
        KafkaMod.LOGGER.info("Registering mod blocks for " + KafkaMod.MOD_ID);
    }
}

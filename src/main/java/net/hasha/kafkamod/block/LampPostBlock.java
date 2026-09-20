package net.hasha.kafkamod.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

import java.util.BitSet;

public class LampPostBlock extends Block
{
    private static final VoxelShape SHAPE = VoxelShapes.union(
            VoxelShapes.cuboid(0.0, 0.0, 0.0, 1.0, 0.5, 1.0),
            VoxelShapes.cuboid(0.375, 0.5, 0.375, 0.625, 1.0, 0.625)
    );

    public LampPostBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
}

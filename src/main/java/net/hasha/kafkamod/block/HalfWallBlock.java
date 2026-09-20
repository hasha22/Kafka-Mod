package net.hasha.kafkamod.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class HalfWallBlock extends Block
{
    private static final VoxelShape SHAPE = VoxelShapes.union(
            VoxelShapes.cuboid(0.25, 0.0, 0.25, 0.75, 0.5, 0.75),
            VoxelShapes.cuboid(0.375, 0.5, 0.375, 0.625, 1.0, 0.625)
    );

    public HalfWallBlock(AbstractBlock.Settings settings)
    {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
}

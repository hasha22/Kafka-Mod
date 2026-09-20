package net.hasha.kafkamod.yuusha;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.registry.Registries;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

import java.util.List;
import java.util.Set;

public class WindowNPCInteraction
{
    //must be exact id
    private static final Set<Identifier> NPC_WINDOW_IDS = Set.of(
            new Identifier("yuushya", "cyan_windows_open_0"),
            new Identifier("yuushya", "black_windows_open_0")
    );

    public static void register()
    {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) ->
        {
            if (world.isClient() || hand != Hand.MAIN_HAND) { return ActionResult.PASS; }

            BlockPos pos = hitResult.getBlockPos();
            BlockState state = world.getBlockState(pos);
            Identifier blockId = Registries.BLOCK.getId(state.getBlock());

            if (!NPC_WINDOW_IDS.contains(blockId)) {
                return ActionResult.PASS;
            }

            Box searchBox = new Box(pos).expand(2.0);
            List<Entity> nearby = world.getOtherEntities(player, searchBox,
                    e -> e.getClass().getName().startsWith("de.markusbordihn.easynpc"));

            if (nearby.isEmpty()) { return ActionResult.PASS; }

            Entity npc = nearby.get(0);
            ActionResult result = player.interact(npc, hand);

            return result.isAccepted() ? ActionResult.SUCCESS : ActionResult.PASS;
        });
    }
}

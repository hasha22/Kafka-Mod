package net.hasha.kafkamod.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class HotbarOnlySweepMixin
{
    //Safety net for commands like /give
    @Inject(method = "tick", at = @At("TAIL"))
    private void hotbarOnly$sweep(CallbackInfo ci)
    {
        PlayerEntity self = (PlayerEntity) (Object) this;
        if (self.getWorld().isClient) return;
        if (!(self instanceof ServerPlayerEntity sp)) return;

        GameMode mode = sp.interactionManager.getGameMode();
        if (mode != GameMode.SURVIVAL && mode != GameMode.ADVENTURE) return;

        PlayerInventory inv = self.getInventory();
        boolean changed = false;

        for (int i = 9; i <= 35; i++)
        {
            ItemStack stray = inv.getStack(i);
            if (stray.isEmpty()) continue;

            inv.setStack(i, ItemStack.EMPTY);
            changed = true;
            ItemStack remainder = stray;

            for (int j = 0; j < 9 && !remainder.isEmpty(); j++)
            {
                ItemStack hotbarStack = inv.getStack(j);
                if (!hotbarStack.isEmpty() && ItemStack.areItemsEqual(hotbarStack, remainder))
                {
                    int space = hotbarStack.getMaxCount() - hotbarStack.getCount();
                    int moved = Math.min(space, remainder.getCount());
                    if (moved > 0)
                    {
                        hotbarStack.increment(moved);
                        remainder.decrement(moved);
                    }
                }
            }
            for (int l = 0; l < 9 && !remainder.isEmpty(); l++)
            {
                if (inv.getStack(l).isEmpty())
                {
                    inv.setStack(l, remainder.copy());
                    remainder.setCount(0);
                }
            }
            if (!remainder.isEmpty())
            {
                self.dropItem(remainder, false);
            }
        }

        if (changed)
        {
            inv.markDirty();
            sp.currentScreenHandler.sendContentUpdates();
        }
    }
}

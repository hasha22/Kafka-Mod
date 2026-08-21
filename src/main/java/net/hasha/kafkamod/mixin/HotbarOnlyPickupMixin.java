package net.hasha.kafkamod.mixin;

import net.hasha.kafkamod.slots.CheckRestrictedUtil;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemEntity.class)
public class HotbarOnlyPickupMixin
{
    //prevents items from getting picked up with a full hotbar
    @Redirect(method = "onPlayerCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerInventory;insertStack(Lnet/minecraft/item/ItemStack;)Z"))
    private boolean hotbarOnly$redirectInsert(PlayerInventory inventory, ItemStack stack)
    {
        PlayerEntity player = inventory.player;
        if (!CheckRestrictedUtil.isRestricted(player))
        {
            return inventory.insertStack(stack);
        }
        return hotbarOnly$insertIntoHotbarOnly(inventory, stack);
    }

    private boolean hotbarOnly$insertIntoHotbarOnly(PlayerInventory inv, ItemStack stack) {
        boolean changed = false;

        //merge into existing items first
        if (stack.isStackable())
        {
            for (int i = 0; i < 9 && !stack.isEmpty(); i++)
            {
                ItemStack existing = inv.getStack(i);
                if (!existing.isEmpty() && ItemStack.areItemsEqual(existing, stack))
                {
                    int space = existing.getMaxCount() - existing.getCount();
                    int moved = Math.min(space, stack.getCount());
                    if (moved > 0)
                    {
                        existing.increment(moved);
                        stack.decrement(moved);
                        changed = true;
                    }
                }
            }
        }

        //then fills in empty slot
        for (int i = 0; i < 9 && !stack.isEmpty(); i++)
        {
            if (inv.getStack(i).isEmpty())
            {
                inv.setStack(i, stack.copy());
                stack.setCount(0);
                changed = true;
            }
        }
        return changed;
    }
}

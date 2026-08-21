package net.hasha.kafkamod.mixin;

import net.hasha.kafkamod.slots.ConditionalSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//This class bypasses vanilla insertItem logic to prevent hangs caused by shift-clicking items with a full hotbar
@Mixin(ScreenHandler.class)
public abstract class HotbarOnlyInsertItemMixin
{
    @Inject(method = "insertItem", at = @At("HEAD"), cancellable = true)
    private void hotbarOnly$restrictInsertItem(ItemStack stack, int startIndex, int endIndex, boolean fromLast, CallbackInfoReturnable<Boolean> cir)
    {
        ScreenHandler self = (ScreenHandler) (Object) this;
        boolean touchedLockedSlot = false;

        for(int i = startIndex; i < endIndex && i < self.slots.size(); i++)
        {
            if(self.slots.get(i) instanceof ConditionalSlot cs && cs.isRestricted())
            {
                touchedLockedSlot = true;
                break;
            }
        }
        if(!touchedLockedSlot) return;

        cir.setReturnValue(hotbarOnly$insertSkippingLocked(self, stack, startIndex, endIndex));
    }
    private boolean hotbarOnly$insertSkippingLocked(ScreenHandler handler, ItemStack stack, int startIndex, int endIndex)
    {
        boolean changed = false;

        if (stack.isStackable()) {
            for (int i = startIndex; i < endIndex && i < handler.slots.size() && !stack.isEmpty(); i++) {
                Slot slot = handler.slots.get(i);
                if (slot instanceof ConditionalSlot) continue; // locked — never a valid target
                ItemStack existing = slot.getStack();
                if (!existing.isEmpty() && ItemStack.areItemsEqual(existing, stack) && slot.canInsert(stack)) {
                    int space = Math.min(existing.getMaxCount(), slot.getMaxItemCount()) - existing.getCount();
                    int moved = Math.min(space, stack.getCount());
                    if (moved > 0) {
                        existing.increment(moved);
                        stack.decrement(moved);
                        slot.markDirty();
                        changed = true;
                    }
                }
            }
        }

        for (int i = startIndex; i < endIndex && i < handler.slots.size() && !stack.isEmpty(); i++) {
            Slot slot = handler.slots.get(i);
            if (slot instanceof ConditionalSlot) continue;
            if (slot.getStack().isEmpty() && slot.canInsert(stack)) {
                int amount = Math.min(stack.getCount(), slot.getMaxItemCount());
                slot.setStack(stack.split(amount));
                slot.markDirty();
                changed = true;
            }
        }

        return changed;
    }
}

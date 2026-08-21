package net.hasha.kafkamod.mixin;

import net.hasha.kafkamod.slots.ConditionalSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ScreenHandler.class)
public abstract class HotbarOnlySlotMixin
{
    //replaces vanilla slots with disabled slots
    @ModifyVariable(method = "addSlot", at = @At("HEAD"), argsOnly = true)
    private Slot hotbarOnly$wrapMainInvSlot(Slot slot)
    {
        if (slot.inventory instanceof PlayerInventory pInv)
        {
            int index = slot.getIndex();
            if (index >= 9 && index <= 35)
            {
                return new ConditionalSlot(pInv, index, slot.x, slot.y, pInv.player);
            }
        }
        return slot;
    }
}



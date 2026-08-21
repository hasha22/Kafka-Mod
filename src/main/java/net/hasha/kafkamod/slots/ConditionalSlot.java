package net.hasha.kafkamod.slots;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameMode;

//This class creates restricted slots which replace vanilla inventory slots
public class ConditionalSlot extends Slot
{
    private final PlayerEntity owner;
    //Constructor
    public ConditionalSlot(PlayerInventory inventory, int index, int x, int y, PlayerEntity owner)
    {
        super(inventory, index, x, y);
        this.owner = owner;
    }
    @Override public boolean canInsert(ItemStack stack) { return !isRestricted();  }
    @Override public boolean canTakeItems(PlayerEntity player) { return !isRestricted(); }
    @Override public ItemStack getStack() { return isRestricted() ? ItemStack.EMPTY : super.getStack(); }
    @Override public boolean hasStack() { return !isRestricted() && super.hasStack(); }
    @Override public void setStack(ItemStack stack){ if (isRestricted()) return; super.setStack(stack);}

    //restricts survival/adventure players
    public boolean isRestricted()
    {
       return CheckRestrictedUtil.isRestricted(owner);
    }
}

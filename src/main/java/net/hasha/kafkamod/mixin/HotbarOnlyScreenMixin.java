package net.hasha.kafkamod.mixin;

import net.hasha.kafkamod.slots.ConditionalSlot;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//Explanation: this mixin draws a black rectangle over the player inventory.
@Mixin(HandledScreen.class)
public abstract class HotbarOnlyScreenMixin
{
    @Shadow protected int x;
    @Shadow protected int y;
    @Shadow protected ScreenHandler handler;

    //stops rendering
    @Inject(method = "drawSlot", at = @At("HEAD"), cancellable = true)
    private void hotbarOnly$skipRender(DrawContext context, Slot slot, CallbackInfo ci)
    {
        if (slot instanceof ConditionalSlot cs && hotbarOnly$clientRestricted()) ci.cancel();
    }

    //paints a simple panel
    @Inject(method = "render", at = @At("TAIL"))
    private void hotbarOnly$drawOverlay(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci)
    {
        if (!hotbarOnly$clientRestricted()) return;

        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;
        boolean any = false;

        for (Slot slot : handler.slots) {
            if (slot instanceof ConditionalSlot cs) {
                any = true;
                minX = Math.min(minX, slot.x);
                minY = Math.min(minY, slot.y);
                maxX = Math.max(maxX, slot.x);
                maxY = Math.max(maxY, slot.y);
            }
        }
        if (!any) return;
        int left = this.x + minX - 1;
        int top = this.y + minY - 1;
        int width = (maxX - minX) + 18;
        int height = (maxY - minY) + 18;

        context.fill(left, top, left + width, top + height, 0xFF2B2B2B);
    }
    private static boolean hotbarOnly$clientRestricted()
    {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.interactionManager == null) return false;
        GameMode mode = client.interactionManager.getCurrentGameMode();
        return mode == GameMode.SURVIVAL || mode == GameMode.ADVENTURE;
    }
}

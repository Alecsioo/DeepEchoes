package com.github.alecsio.deepechoes.items;

import com.github.alecsio.deepechoes.capabilities.EchoCapabilities;
import com.github.alecsio.deepechoes.capabilities.IEchoHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class EchoTooltipProvider {

    private static EchoTooltipProvider INSTANCE;

    private EchoTooltipProvider() {}

    public static EchoTooltipProvider getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EchoTooltipProvider();
        }
        return INSTANCE;
    }

    public Component getTooltipComponentFor(ItemStack stack) {
        IEchoHandler echoHandler = stack.getCapability(EchoCapabilities.ECHO_HANDLER_ITEM);
        if (echoHandler != null) {
            ChatFormatting color = ChatFormatting.BLUE;
            return Component.translatable("tooltip.item.echohandler", echoHandler.getStoredEcho(), echoHandler.getMaxEcho()).withStyle(color);
        }
        return Component.empty();
    }
}

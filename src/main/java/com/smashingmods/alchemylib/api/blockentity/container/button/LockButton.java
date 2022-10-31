package com.smashingmods.alchemylib.api.blockentity.container.button;

import com.mojang.blaze3d.vertex.PoseStack;
import com.smashingmods.alchemylib.AlchemyLib;
import com.smashingmods.alchemylib.api.blockentity.container.AbstractProcessingScreen;
import com.smashingmods.alchemylib.api.blockentity.processing.AbstractProcessingBlockEntity;
import com.smashingmods.alchemylib.api.network.ToggleLockButtonPacket;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;

import javax.annotation.Nonnull;

public class LockButton extends AbstractAlchemyButton {

    public LockButton(AbstractProcessingScreen<?> pParent, AbstractProcessingBlockEntity pBlockEntity) {
        super(pParent,
                pBlockEntity,
                pButton -> {
                    boolean toggleLock = !pBlockEntity.isRecipeLocked();
                    pBlockEntity.setRecipeLocked(toggleLock);
                    pBlockEntity.setChanged();
                    AlchemyLib.getPacketHandler().sendToServer(new ToggleLockButtonPacket(pBlockEntity.getBlockPos(), toggleLock));
                });
    }

    @Override
    public void renderButton(@Nonnull PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        super.renderButton(pPoseStack, pMouseX, pMouseY, pPartialTick);
        blit(pPoseStack, x, y, 25 + ((blockEntity.isRecipeLocked() ? 0 : 1) * 20), 0, width, height);
        renderButtonTooltip(pPoseStack, pMouseX, pMouseY);
    }

    @Override
    public MutableComponent getMessage() {
        return blockEntity.isRecipeLocked() ? MutableComponent.create(new TranslatableContents("alchemylib.container.unlock_recipe")) : MutableComponent.create(new TranslatableContents("alchemylib.container.lock_recipe"));
    }
}

package org.luminacc.server.mixins;

import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ExperienceOrbEntity.class)
public interface ExperienceOrbInvokerMixin {
    @Accessor
    int getPickingCount();
    @Accessor
    int getAmount();
    @Invoker("repairPlayerGears")
    public int invokeRepairPlayerGears(PlayerEntity plr,int amount);
}

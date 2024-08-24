package org.luminacc.server.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.luminacc.server.main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ExperienceOrbEntity.class)
public abstract class ExperienceOrbMixin extends Entity {
    public ExperienceOrbMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "Lnet/minecraft/entity/ExperienceOrbEntity;onPlayerCollision(Lnet/minecraft/entity/player/PlayerEntity;)V",at=@At("HEAD"),cancellable = true)
    public void onPlayerCollision(PlayerEntity player, CallbackInfo ci){
        ExperienceOrbEntity orb = (ExperienceOrbEntity)(Object)this;
        ExperienceOrbInvokerMixin invoker = (ExperienceOrbInvokerMixin) orb;
        if (!this.getWorld().isClient) {

                int p;
                for (p=0;p < invoker.getPickingCount();p++) {
                    player.sendPickup(orb, 1);
                    int i = invoker.invokeRepairPlayerGears(player, invoker.getAmount());
                    if (i > 0) {
                        player.addExperience(i);
                    }
                }
                orb.discard();
                ci.cancel();



        }

    }
}

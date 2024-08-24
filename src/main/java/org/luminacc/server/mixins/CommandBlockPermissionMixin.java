package org.luminacc.server.mixins;

import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class CommandBlockPermissionMixin  extends LivingEntity {
    protected CommandBlockPermissionMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }
    @Inject(method = "Lnet/minecraft/entity/player/PlayerEntity;isCreativeLevelTwoOp()Z",at=@At("HEAD"),cancellable = true)
    public void isCreativeLevelTwoOp(CallbackInfoReturnable<Boolean> cir) {
        PlayerEntity plr = (PlayerEntity)(Object)this;
        if (plr.getWorld().isClient) {cir.setReturnValue(true);}
        else{
        cir.setReturnValue(plr.getAbilities().creativeMode && (this.getPermissionLevel() >= 2 || Permissions.check(plr,"minecraft.useopblocks")));
        }
    }

}

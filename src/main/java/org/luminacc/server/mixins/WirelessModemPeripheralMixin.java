package org.luminacc.server.mixins;

import dan200.computercraft.shared.config.Config;
import dan200.computercraft.shared.peripheral.modem.ModemPeripheral;
import dan200.computercraft.shared.peripheral.modem.ModemState;
import dan200.computercraft.shared.peripheral.modem.wireless.WirelessModemPeripheral;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static java.util.Objects.isNull;
import static org.luminacc.server.main.log;

@Mixin(WirelessModemPeripheral.class)
public abstract class WirelessModemPeripheralMixin extends ModemPeripheral {
    protected WirelessModemPeripheralMixin(ModemState state) {
        super(state);
    }
    @Shadow(remap = false) @Final private boolean advanced;

    /**
     * @author Featherwhisker
     * @reason Nerfing ender modems
     */
    @Overwrite(remap = false)
    public boolean isInterdimensional() {
        if (advanced || !getLevel().isThundering()) {
            return true;
        }
        return false;
    }
    @Inject(method = "getRange",at=@At("HEAD"),remap = false,cancellable = true)
    public void getRange(CallbackInfoReturnable<Double> ci) {
        if (advanced && !isNull(getLevel())) {
            double returnValue = Config.modemHighAltitudeRange * 2;
            if (getLevel().isThundering()) {
                returnValue = Config.modemHighAltitudeRangeDuringStorm * 2;
            } else if (getLevel().isRaining()) {
                returnValue = Config.modemHighAltitudeRange ;
            }
            //log.info(String.valueOf(returnValue));
            ci.setReturnValue(returnValue);
            ci.cancel();
        } else if (isNull(getLevel())) {
            ci.setReturnValue((double) 0);
            ci.cancel();
        }
    }
}

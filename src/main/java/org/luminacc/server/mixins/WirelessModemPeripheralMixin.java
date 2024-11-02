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
    /*
     * This nerf was removed by Herr Katze for later rewrite as it doesn't fit the spirit of ender modems
     * The future nerf will not include range, rather it will separate them into its own modem channel.
     */

}

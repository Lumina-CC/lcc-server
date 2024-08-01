package org.luminacc.server.mixins;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.shared.computer.blocks.AbstractComputerBlockEntity;
import dan200.computercraft.shared.computer.blocks.ComputerPeripheral;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import static java.util.Objects.isNull;
import static org.luminacc.server.main.log;

@Mixin(ComputerPeripheral.class)
public abstract class ComputerPeripheralMixin  implements IPeripheral {
    @Final
    @Shadow(remap = false)
    private AbstractComputerBlockEntity owner;
    @Shadow(remap = false)
    public String getType() {
        return null;
    }
    @Shadow(remap = false)
    public boolean equals(@Nullable IPeripheral other) {
        return false;
    }

    @LuaFunction
    public final boolean queueEvent(String event) {
        var computer = owner.getServerComputer();
        if (!isNull(event)) {
            computer.queueEvent(event);
            return true;
        }
        return false;
    }
    @LuaFunction
    public final boolean sendChar(@Nullable String key) {
        var computer = owner.getServerComputer();
        if (!isNull(key)) {
            computer.queueEvent("char",new String[]{key});
            return true;
        }
        return false;
    }
    @LuaFunction
    public final boolean sendKey(int key, @Nullable Object held) {
        var computer = owner.getServerComputer();
        if (!isNull(key)) {
            computer.queueEvent("key",new Object[]{key,held});
            return true;
        }
        return false;
    }
    @LuaFunction
    public final void writeTerm(@Nullable String text) {
        var computer = owner.getServerComputer();
        if (!isNull(computer.getAPIEnvironment()) && !isNull(text)) {
            computer.getAPIEnvironment().getTerminal().write(text);
        }
    }
    @LuaFunction
    public final void setLineTerm(int y, String text, String textColor, String textBackground) {
        var computer = owner.getServerComputer();
        if (!isNull(computer.getAPIEnvironment()) && !isNull(text) && !isNull(y) && !isNull(textColor) && !isNull(textBackground)) {
            computer.getAPIEnvironment().getTerminal().setLine(y,text,textColor,textBackground);
        }
    }
    @LuaFunction
    public final void clearTerm() {
        var computer = owner.getServerComputer();
        if (!isNull(computer.getAPIEnvironment())) {
            computer.getAPIEnvironment().getTerminal().clear();
        }
    }
    @LuaFunction
    public final void setCursorPos(@Nullable int x,int y) {
        var computer = owner.getServerComputer();
        if (!isNull(computer.getAPIEnvironment()) && !isNull(x) && !isNull(y)) {
            computer.getAPIEnvironment().getTerminal().setCursorPos(x,y);
        }
    }
}

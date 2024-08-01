package org.luminacc.server;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import dan200.computercraft.shared.computer.blocks.ComputerBlockEntity;
import net.minecraft.text.Text;

import java.util.logging.Logger;

import static net.minecraft.server.command.CommandManager.*;
public class main implements ModInitializer {

    public static final Logger log = Logger.getLogger("LccServer");

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("test")
            .executes(context -> {
                context.getSource().sendFeedback(() -> Text.literal("Hello, world!"), true);
                return 1;
            })));

    }
}

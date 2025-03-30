package pro.mikey.mods.pop;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ComponentArgument;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.server.command.EnumArgument;
import pro.mikey.mods.pop.data.Placement;
import pro.mikey.mods.pop.net.ClientCreatePopPacket;

public class PopCommands {
    // TODO: Translate
    public static SimpleCommandExceptionType NO_TARGETS = new SimpleCommandExceptionType(Component.literal("No targets found"));

    public static LiteralArgumentBuilder<CommandSourceStack> register(CommandBuildContext context) {
        return Commands.literal("pop").then(Commands
                .literal("create")
                .requires(stack -> stack.hasPermission(Commands.LEVEL_GAMEMASTERS))
                .then(Commands.argument("target", EntityArgument.players())
                    .then(Commands.argument("placement", EnumArgument.enumArgument(Placement.class))
                            .then(Commands.argument("duration", IntegerArgumentType.integer(1))
                                    .then(Commands.argument("content", ComponentArgument.textComponent(context)).executes(PopCommands::sendPop))
                                    .then(Commands.argument("text", StringArgumentType.greedyString()).executes(PopCommands::sendPop))
                            )
                    )
                    .then(Commands.argument("duration", IntegerArgumentType.integer(1))
                            .then(Commands.argument("content", ComponentArgument.textComponent(context)).executes(PopCommands::sendPop))
                            .then(Commands.argument("text", StringArgumentType.greedyString()).executes(PopCommands::sendPop))
                    )
                )
        );
    }

    private static int sendPop(CommandContext<CommandSourceStack> stack) throws CommandSyntaxException {
        var targets = EntityArgument.getPlayers(stack, "target");
        if (targets.isEmpty()) {
            throw NO_TARGETS.create();
        }

        var duration = IntegerArgumentType.getInteger(stack, "duration");

        Placement placement = Placement.MIDDLE_CENTER;
        try {
            placement = stack.getArgument("placement", Placement.class);
        } catch (IllegalArgumentException ignored) {
        }

        Component content = Component.literal("");
        try {
            content =  stack.getArgument("content", Component.class);
        } catch (IllegalArgumentException ignored) {
            try {
                content = Component.literal(StringArgumentType.getString(stack, "text"));
            } catch (IllegalArgumentException ignored2) {}
        }

        var packet = new ClientCreatePopPacket(content, placement, duration);
        for (var target : targets) {
            PacketDistributor.sendToPlayer(target, packet);
        }
        return 0;
    }
}

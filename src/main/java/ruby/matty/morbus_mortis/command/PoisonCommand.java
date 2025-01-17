package ruby.matty.morbus_mortis.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.*;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;

import net.minecraft.commands.*;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerPlayer;

import ruby.matty.morbus_mortis.util.Poison;

public class PoisonCommand {

	public PoisonCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
		dispatcher.register(
			Commands.literal("poison").requires(s -> s.hasPermission(4))
				.then(
					Commands.argument("player", EntityArgument.player())
						.then(
							Commands.argument("poison", StringArgumentType.string()).suggests(POISONS)
								.then(
									Commands.argument("active", BoolArgumentType.bool())
										.executes(this::execute)
								)
						)
				)
		);
	}

	public int execute(CommandContext<CommandSourceStack> source) throws CommandSyntaxException {
		ServerPlayer player = EntityArgument.getPlayer(source, "player");
		Poison poison = Poison.valueOf(StringArgumentType.getString(source, "poison").toUpperCase());
		boolean active = BoolArgumentType.getBool(source, "active");
		var data = player.getPersistentData();
		if (active) data.putBoolean(poison.toString(), true);
		else data.remove(poison.toString());
		return 1;
	}

	public static final SuggestionProvider<CommandSourceStack> POISONS = (context, builder) -> {
		for (Poison poison : Poison.values()) builder.suggest(poison.name().toUpperCase());
		return builder.buildFuture();
	};
}
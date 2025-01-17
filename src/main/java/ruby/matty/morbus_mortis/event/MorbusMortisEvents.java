package ruby.matty.morbus_mortis.event;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;

import net.minecraftforge.event.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import ruby.matty.morbus_mortis.MorbusMortis;
import ruby.matty.morbus_mortis.command.PoisonCommand;
import ruby.matty.morbus_mortis.util.Poison;

import java.util.*;

@Mod.EventBusSubscriber(modid = MorbusMortis.MOD_ID)
public class MorbusMortisEvents {

	public static final Random random = new Random();
	public static final List<Poison> poisons = List.of(Poison.values());
	public static final String FIRST_JOIN = MorbusMortis.MOD_ID + ".first_join";

	public static Poison pickRandom() {
		return poisons.get(random.nextInt(poisons.size()));
	}

	public static List<Poison> getPoisons(ServerPlayer player) {
		var data = player.getPersistentData();
		return new ArrayList<>(poisons.stream().filter(poison -> data.getBoolean(poison.toString())).toList());
	}

	@SubscribeEvent
	public static void onPlayerJoinEvent(PlayerEvent.PlayerLoggedInEvent event) {
		var entity = event.getEntity();
		if (!(entity instanceof ServerPlayer player)) return;
		if (!player.getPersistentData().contains(FIRST_JOIN)) {
			handleFirstJoin(player); // If the player hasn't been on the server before, give them allergies / intolerances / conditions, etc
		}
	}

	public static void handleFirstJoin(ServerPlayer player) {
		var data = player.getPersistentData();
		data.putBoolean(FIRST_JOIN, true);
		if (!(random.nextBoolean() && !random.nextBoolean())) return;
		putNotPresentPoison(data, pickRandom());
		if (random.nextBoolean() && !random.nextBoolean() && !random.nextBoolean()) return;
		putNotPresentPoison(data, pickRandom());
		if (!random.nextBoolean() && random.nextBoolean() && !random.nextBoolean()) return;
		putNotPresentPoison(data, pickRandom());
	}

	public static void putNotPresentPoison(CompoundTag data, Poison poison) {
		while (data.contains(poison.toString())) poison = pickRandom();
		data.putBoolean(poison.toString(), true);
	}

	@SubscribeEvent
	public static void tickEvent(TickEvent.ServerTickEvent event) {
		if (event.phase != TickEvent.Phase.START) return;
		var players = event.getServer().getPlayerList().getPlayers();
		players.stream().filter((player) -> lastPositions.get(player.getUUID()) == null).forEach((player) -> lastPositions.put(player.getUUID(), player.blockPosition()));
		players.stream().filter(ServerPlayer::isAlive).forEach(MorbusMortisEvents::handleTick);
	}

	public static final Map<UUID, Integer> timeSinceLastMoved = new HashMap<>();
	public static final Map<UUID, BlockPos> lastPositions = new HashMap<>();

	public static void handleTick(ServerPlayer player) {
		UUID uuid = player.getUUID();
		var lastPos = lastPositions.get(uuid);
		var pos = player.blockPosition();
		if (lastPos.getCenter().equals(pos.getCenter())) timeSinceLastMoved.put(uuid, timeSinceLastMoved.getOrDefault(uuid, 0) + 1);
		else timeSinceLastMoved.put(uuid, 0);
		lastPositions.put(uuid, pos);

		for (Poison poison : getPoisons(player)) {
			switch (poison) {
				case LACTOSE_INTOLERANCE, CELIAC_DISEASE -> nothing();
				case ASTHMA -> handleAsthma(player);
				case DIABETES -> handleDiabetes(player);
				case ADHD -> handleAdhd(player);
				case CANCER -> handleCancer(player);
			}
		}
	}

	public static void handleAsthma(ServerPlayer player) {}

	public static void handleDiabetes(ServerPlayer player) {}

	public static void handleAdhd(ServerPlayer player) {}

	public static void handleCancer(ServerPlayer player) {}

	public static void nothing() {}

	@SubscribeEvent
	public static void commandRegistryEvent(RegisterCommandsEvent event) {
		new PoisonCommand(event.getDispatcher());
	}
}
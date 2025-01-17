package ruby.matty.morbus_mortis.event;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;

import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import ruby.matty.morbus_mortis.MorbusMortis;
import ruby.matty.morbus_mortis.util.Poison;

import java.util.Random;

@Mod.EventBusSubscriber(modid = MorbusMortis.MOD_ID)
public class MorbusMortisEvents {

	public static final Random random = new Random();
	public static final Poison[] poisons = Poison.values();

	public static final String FIRST_JOIN = MorbusMortis.MOD_ID + ".first_join";

	public static Poison pickRandom() {
		return poisons[random.nextInt(poisons.length)];
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
}
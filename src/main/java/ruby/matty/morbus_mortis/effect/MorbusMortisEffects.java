package ruby.matty.morbus_mortis.effect;

import net.minecraft.world.effect.MobEffect;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.*;

import ruby.matty.morbus_mortis.MorbusMortis;
import ruby.matty.morbus_mortis.effect.custom.AdhdMedsEffect;

public class MorbusMortisEffects {

	public static final DeferredRegister<MobEffect> STATUS_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, MorbusMortis.MOD_ID);

	public static final RegistryObject<MobEffect> MEDICATED = STATUS_EFFECTS.register("medicated", AdhdMedsEffect::new);

	public static void register(IEventBus eventBus) {
		STATUS_EFFECTS.register(eventBus);
	}
}
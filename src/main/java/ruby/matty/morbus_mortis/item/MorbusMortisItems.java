package ruby.matty.morbus_mortis.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.*;

import ruby.matty.morbus_mortis.MorbusMortis;
import ruby.matty.morbus_mortis.effect.MorbusMortisEffects;
import ruby.matty.morbus_mortis.item.custom.LisdexamfetamineDimsylate;

public class MorbusMortisItems {

	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MorbusMortis.MOD_ID);

	public static final RegistryObject<Item> LISDEXAMFETAMINE_DIMSYLATE = ITEMS.register(
		"lisdexamfetamine_dimsylate",
		() -> new LisdexamfetamineDimsylate(new Item.Properties().food(
			new FoodProperties.Builder()
				.alwaysEat()
				.fast()
				.effect(() -> new MobEffectInstance(MorbusMortisEffects.MEDICATED.get(), 8000), Float.MAX_VALUE)
				.build()
		))
	);

	public static void register(IEventBus eventBus) {
		ITEMS.register(eventBus);
	}
}
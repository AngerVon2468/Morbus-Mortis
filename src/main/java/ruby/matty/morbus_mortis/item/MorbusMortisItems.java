package ruby.matty.morbus_mortis.item;

import net.minecraft.world.item.Item;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.*;

import ruby.matty.morbus_mortis.MorbusMortis;

public class MorbusMortisItems {

	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MorbusMortis.MOD_ID);

	public static void register(IEventBus eventBus) {
		ITEMS.register(eventBus);
	}
}
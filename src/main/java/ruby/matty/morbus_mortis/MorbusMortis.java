package ruby.matty.morbus_mortis;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import ruby.matty.morbus_mortis.effect.MorbusMortisEffects;
import ruby.matty.morbus_mortis.item.MorbusMortisItems;

@Mod(MorbusMortis.MOD_ID)
public class MorbusMortis {

    public static final String MOD_ID = "morbus_mortis";

    @SuppressWarnings("removal") // Not all forge versions support parameterized constructors
    public MorbusMortis() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        MorbusMortisItems.register(bus);
		MorbusMortisEffects.register(bus);
    }
}
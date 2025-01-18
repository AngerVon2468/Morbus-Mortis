package ruby.matty.morbus_mortis.effect.custom;

import net.minecraft.world.effect.*;
import net.minecraft.world.entity.LivingEntity;

import java.awt.*;

@SuppressWarnings("NullableProblems")
public class AdhdMedsEffect extends MobEffect {

	public AdhdMedsEffect() {
		super(MobEffectCategory.BENEFICIAL, Color.CYAN.getRGB());
	}

	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier) {
		super.applyEffectTick(entity, amplifier);
	}
}
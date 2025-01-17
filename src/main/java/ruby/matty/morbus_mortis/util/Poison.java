package ruby.matty.morbus_mortis.util;

import ruby.matty.morbus_mortis.MorbusMortis;

/**
 * Not actually poisons, I just don't know what to call this class otherwise.
 */
public enum Poison {

	LACTOSE_INTOLERANCE,
	CELIAC_DISEASE,
	ASTHMA,
	DIABETES,
	ADHD,
	CANCER;

	@Override
	public String toString() {
		return MorbusMortis.MOD_ID + "." + super.toString().toLowerCase();
	}
}
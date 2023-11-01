package chiefarug.mods.hungrychests;

import net.minecraftforge.common.ForgeConfigSpec;

public class HungryChestsConfig {
	private static final ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();

	public static final ForgeConfigSpec.BooleanValue forceToApplyToAllStructures = configBuilder.comment(
					"Force all structures to have a chance for chests to be made hungry.",
					"If you disable this then no structures by default will have hungry chests",
					"You can apply the hungrychests:hungry_chestifier processor list on a structure using a datapack if this option is disabled",
					"That structure processor can also be overridden with a datapack if you wish to modify how hungry chests are applied to structures"
			).define("forceToApplyToAllStructures", true);
	public static final ForgeConfigSpec.DoubleValue featureReplaceChance = configBuilder.comment(
					"The chance for FEATURES to have a chest they place to be replaced with a hungry chest.",
					"Note in vanilla this only affects dungeons.",
					"This will NOT affect the chance of a chest being replaced in a structure, such as a pillager outpost or village",
					"If you want to change that then override the hungrychests:hungry_chestifier processor list in a datapack. The default one can be found inside the mod jar (unzip it) at data/hungrychests/worldgen/processor_list/hungry_chestifier",
					"Setting this to less than 0 (-1) disables replacing chests from features, and setting it to 1 will always replace them"
			).defineInRange("featureReplaceChance", 0.5, -1, 1);
	public static final ForgeConfigSpec.DoubleValue hurtChance = configBuilder.comment(
					"The chance per tick for a player who has a hungry chest open or is punching a hungry chest to get hurt.",
					"The default value of 0.1 is about twice a second (20 ticks a second, 10% chance per tick)."
			).defineInRange("hurtChance", 0.1, -1, 1);
	public static final ForgeConfigSpec SPEC = configBuilder.build();
}

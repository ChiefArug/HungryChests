package chiefarug.mods.hungrychests.mixin;

import chiefarug.mods.hungrychests.HungryChests;
import chiefarug.mods.hungrychests.HungryChestsConfig;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import static net.minecraft.world.level.block.ChestBlock.FACING;

@Mixin(Feature.class)
public class HungrierChesitifier {
	@ModifyVariable(method = "safeSetBlock", argsOnly = true, at = @At("LOAD"))
	private BlockState hungrychests$mojangWhyAreDungeonsStillFeatures_IfTheyWereStructuresIWouldntNeedThisMixinToReplaceChestsWithHungryOnes(BlockState state, WorldGenLevel level) {
		if (state.is(Blocks.CHEST) && HungryChestsConfig.featureReplaceChance.get() > 0 && level.getRandom().nextFloat() < HungryChestsConfig.featureReplaceChance.get()) {//TODO:replace with a tag so that other mods chests are targetable
			return HungryChests.HUNGRY_CHEST.get().defaultBlockState().setValue(FACING, state.getValue(FACING));
		}
		return state;
	}
}

package chiefarug.mods.hungrychests.mixin;

import chiefarug.mods.hungrychests.HungryChests;
import chiefarug.mods.hungrychests.HungryChestsConfig;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import static net.minecraft.world.level.block.ChestBlock.FACING;
import static net.minecraft.world.level.block.ChestBlock.WATERLOGGED;

@Mixin(Feature.class)
public class HungrierChesitifier {
	@ModifyVariable(method = "safeSetBlock", argsOnly = true, at = @At("LOAD"))
	private BlockState hungrychests$mojangWhyAreDungeonsStillFeatures_IfTheyWereStructuresIWouldntNeedThisMixinToReplaceChestsWithHungryOnes(BlockState state, WorldGenLevel level) {
		if (state.is(Blocks.CHEST) && HungryChestsConfig.featureReplaceChance.get() > 0 && level.getRandom().nextFloat() < HungryChestsConfig.featureReplaceChance.get()) {//TODO:replace with a tag so that other mods chests are targetable
			BlockState newState = HungryChests.HUNGRY_CHEST.get().defaultBlockState();

			if (state.hasProperty(FACING)) newState = newState.setValue(FACING, state.getValue(FACING));
			// in case it's a block that can face any direction like barrels, we can still try keep it facing the same direction.
			if (state.hasProperty(BlockStateProperties.FACING) && state.getValue(BlockStateProperties.FACING).getAxis() != Direction.Axis.Y) newState = newState.setValue(FACING, state.getValue(BlockStateProperties.FACING));
			if (state.hasProperty(WATERLOGGED)) newState = newState.setValue(WATERLOGGED, state.getValue(WATERLOGGED));
			return newState;
		}
		return state;
	}
}

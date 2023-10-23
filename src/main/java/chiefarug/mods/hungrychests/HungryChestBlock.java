package chiefarug.mods.hungrychests;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class HungryChestBlock extends ChestBlock {
	public HungryChestBlock(Properties pProperties, Supplier<BlockEntityType<? extends ChestBlockEntity>> pBlockEntityType) {
		super(pProperties, pBlockEntityType);
	}

	@Override // mojang, why
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return this.blockEntityType().create(pos, state);
	}
}

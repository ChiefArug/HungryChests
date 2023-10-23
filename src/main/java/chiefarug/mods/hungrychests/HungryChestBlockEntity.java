package chiefarug.mods.hungrychests;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class HungryChestBlockEntity extends ChestBlockEntity {

	public HungryChestBlockEntity(BlockPos pPos, BlockState pBlockState) {
		super(HungryChests.HUNGRY_CHEST_BLOCK_ENTITY.get(), pPos, pBlockState);
	}

	@Override
	public BlockEntityType<?> getType() {
		return super.getType();
	}
}

package chiefarug.mods.hungrychests;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class HungryChestBlockEntity extends ChestBlockEntity {

	public HungryChestBlockEntity(BlockPos pPos, BlockState pBlockState) {
		super(HungryChests.HUNGRY_CHEST_BLOCK_ENTITY.get(), pPos, pBlockState);
	}

	public static void tryHurtPlayer(ServerPlayer player) {
		if (!player.getMainHandItem().is(HungryChests.PADDED_GLOVE.get()) && player.getRandom().nextFloat() < HungryChestsConfig.hurtChance.get()) {
			player.hurt(new DamageSource(player.server.registryAccess().registry(Registries.DAMAGE_TYPE).get().getHolderOrThrow(DamageTypes.MAGIC)), 2);
		}
	}

	@Override
	protected AbstractContainerMenu createMenu(int id, Inventory player) {
		return HungryChestMenu.threeRows(id, player, this);
	}
}

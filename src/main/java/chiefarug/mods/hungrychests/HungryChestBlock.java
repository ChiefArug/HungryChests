package chiefarug.mods.hungrychests;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.CompoundContainer;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Supplier;

public class HungryChestBlock extends ChestBlock {
	public HungryChestBlock(Properties pProperties, Supplier<BlockEntityType<? extends ChestBlockEntity>> pBlockEntityType) {
		super(pProperties, pBlockEntityType);
	}

	@Override // mojang, why
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return this.blockEntityType().create(pos, state);
	}

	@Nullable
	@Override
	public MenuProvider getMenuProvider(BlockState pState, Level pLevel, BlockPos pPos) {
		return this.combine(pState, pLevel, pPos, false).apply(HUNGRY_MENU_PROVIDER_COMBINER).orElse(null);
	}

	private static final DoubleBlockCombiner.Combiner<ChestBlockEntity, Optional<MenuProvider>> HUNGRY_MENU_PROVIDER_COMBINER = new DoubleBlockCombiner.Combiner<>() {
		public Optional<MenuProvider> acceptDouble(final ChestBlockEntity blockEntity, final ChestBlockEntity otherBlockEntity) {
			final Container container = new CompoundContainer(blockEntity, otherBlockEntity);
			return Optional.of(new MenuProvider() {
				@Nullable
				public AbstractContainerMenu createMenu(int p_51622_, Inventory p_51623_, Player player) {
					if (blockEntity.canOpen(player) && otherBlockEntity.canOpen(player)) {
						blockEntity.unpackLootTable(p_51623_.player);
						otherBlockEntity.unpackLootTable(p_51623_.player);
						return HungryChestMenu.sixRows(p_51622_, p_51623_, container);
					} else {
						return null;
					}
				}

				public Component getDisplayName() {
					return blockEntity.hasCustomName() ? blockEntity.getDisplayName() :
							otherBlockEntity.hasCustomName() ? otherBlockEntity.getDisplayName() :
									Component.translatable("container.chestDouble");
				}
			});
		}

		public Optional<MenuProvider> acceptSingle(ChestBlockEntity blockEntity) {
			return Optional.of(blockEntity);
		}

		public Optional<MenuProvider> acceptNone() {
			return Optional.empty();
		}
	};
}

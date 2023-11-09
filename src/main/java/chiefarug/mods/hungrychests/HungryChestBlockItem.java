package chiefarug.mods.hungrychests;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.util.Lazy;

import java.util.function.Consumer;

public class HungryChestBlockItem extends BlockItem {
	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		consumer.accept(new IClientItemExtensions() {
			private static final Lazy<HungryChestBlockEntity> blockEntity = Lazy.of(() -> new HungryChestBlockEntity(BlockPos.ZERO, HungryChests.HUNGRY_CHEST.get().defaultBlockState()));
			private final BlockEntityWithoutLevelRenderer bewlr = new BlockEntityWithoutLevelRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels()) {
				@Override
				public void renderByItem(ItemStack pStack, ItemDisplayContext pDisplayContext, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
					if (pStack.is(HungryChests.HUNGRY_CHEST_ITEM.get())) {
						Minecraft.getInstance().getBlockEntityRenderDispatcher().renderItem(blockEntity.get(), pPoseStack, pBuffer, pPackedLight, pPackedOverlay);
					}
				}
			};
			public BlockEntityWithoutLevelRenderer getCustomRenderer() {
				return bewlr;
			}
		});
	}

	public HungryChestBlockItem(Block pBlock, Properties pProperties) {
		super(pBlock, pProperties);
	}
}

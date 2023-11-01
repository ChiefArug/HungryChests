package chiefarug.mods.hungrychests;

import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.state.properties.ChestType;

import static chiefarug.mods.hungrychests.HungryChests.MODRL;

public class HungryChestRenderer<T extends BlockEntity & LidBlockEntity> extends ChestRenderer<T> {

	private static final Material SINGLE = new Material(Sheets.CHEST_SHEET, MODRL.withPath("entity/chest/single"));
	private static final Material D_LEFT = new Material(Sheets.CHEST_SHEET, MODRL.withPath("entity/chest/double_left"));
	private static final Material D_RIGHT = new Material(Sheets.CHEST_SHEET, MODRL.withPath("entity/chest/double_right"));


	public HungryChestRenderer(BlockEntityRendererProvider.Context pContext) {
		super(pContext);
	}

	@Override
	protected Material getMaterial(T blockEntity, ChestType chestType) {
		return switch (chestType) {
			case SINGLE -> SINGLE;
			case LEFT -> D_LEFT;
			case RIGHT -> D_RIGHT;
		};
	}
}

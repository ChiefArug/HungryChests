package chiefarug.mods.hungrychests.mixin;

import chiefarug.mods.hungrychests.HungryChestsConfig;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pools.LegacySinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static chiefarug.mods.hungrychests.HungryChests.LGGR;
import static chiefarug.mods.hungrychests.HungryChests.chestHungrifier;

@Mixin(value = {SinglePoolElement.class, LegacySinglePoolElement.class})
public abstract class HungryChestifier {

	@Inject(method = "getSettings", at = @At("RETURN"))
	private void hungrychests$forceHungryChestProcessor(Rotation pRotation, BoundingBox pBoundingBox, boolean pOffset, CallbackInfoReturnable<StructurePlaceSettings> cir) {
		if (!HungryChestsConfig.forceToApplyToAllStructures.get()) return;

		if (chestHungrifier == null) {
			LGGR.warn("Tried to process a structure to add a hungry chest before the server has started. This should be impossible! Structure piece: {}", this);
			return;
		}

		StructurePlaceSettings placeSettings = cir.getReturnValue();
		for (StructureProcessor structureProcessor : chestHungrifier.list()) {
			placeSettings.addProcessor(structureProcessor);
		}

 		LGGR.debug("Hungry Chests modified {} structure piece at {}", this, pBoundingBox);
	}
}

package chiefarug.mods.hungrychests;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;

import static net.minecraftforge.common.ForgeMod.BLOCK_REACH;

public class GloveItem extends Item {
	public GloveItem(Properties pProperties) {
		super(pProperties);
	}

	public static final Multimap<Attribute, AttributeModifier> DEFAULT_ATTRIBUTES = ImmutableMultimap.of(
			BLOCK_REACH.get(), new AttributeModifier("padded glove", -1, AttributeModifier.Operation.ADDITION)
	);
	@Override
	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
		if (slot == EquipmentSlot.MAINHAND)
			return DEFAULT_ATTRIBUTES;
		return super.getDefaultAttributeModifiers(slot);
	}
}

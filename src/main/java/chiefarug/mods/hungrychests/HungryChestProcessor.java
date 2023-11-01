package chiefarug.mods.hungrychests;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.structure.templatesystem.PosAlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.PosRuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity.Passthrough;
import net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity.RuleBlockEntityModifier;
import org.jetbrains.annotations.Nullable;

public class HungryChestProcessor extends StructureProcessor {

	private final RuleTest input;
	private final BlockState output;
	private final boolean copyProperties;
	private final RuleBlockEntityModifier beModifier;
	private final RuleTest location;
	private final PosRuleTest position;

	public HungryChestProcessor(RuleTest input, BlockState output, boolean copyProperties, RuleBlockEntityModifier beModifier, RuleTest location, PosRuleTest position) {
		this.input = input;
		this.output = output;
		this.copyProperties = copyProperties;
		this.beModifier = beModifier;
		this.location = location;
		this.position = position;
	}

	public static final Codec<HungryChestProcessor> CODEC = RecordCodecBuilder.create(i -> i.group(
			RuleTest.CODEC.fieldOf("input_predicate").forGetter(p -> p.input),
			BlockState.CODEC.fieldOf("output_state").forGetter(p -> p.output),
			Codec.BOOL.optionalFieldOf("copy_properties", false).forGetter(p -> p.copyProperties),
			RuleBlockEntityModifier.CODEC.optionalFieldOf("block_entity_modifier", Passthrough.INSTANCE).forGetter(p -> p.beModifier),
			RuleTest.CODEC.fieldOf("location_predicate").forGetter(p -> p.location),
			PosRuleTest.CODEC.optionalFieldOf("position_predicate", PosAlwaysTrueTest.INSTANCE).forGetter(p -> p.position)
	).apply(i, HungryChestProcessor::new));

	@Override
	protected StructureProcessorType<?> getType() {
		return HungryChests.HUNGRY_CHESTIFIER.get();
	}

	@Nullable
	@Override
	@SuppressWarnings({"rawtypes", "unchecked"}) // i hate generics
	public StructureTemplate.StructureBlockInfo process(LevelReader level, BlockPos offset, BlockPos pos, StructureTemplate.StructureBlockInfo blockInfo, StructureTemplate.StructureBlockInfo relativeBlockInfo, StructurePlaceSettings settings, @Nullable StructureTemplate template) {
		RandomSource random = RandomSource.create(Mth.getSeed(relativeBlockInfo.pos()));
		BlockState blockstate = level.getBlockState(relativeBlockInfo.pos());

		if (input.test(relativeBlockInfo.state(), random) && location.test(blockstate, random) && position.test(pos, blockInfo.pos(), relativeBlockInfo.pos(), random)) {
			BlockState output = this.output;
			if (copyProperties) for (Property property : relativeBlockInfo.state().getProperties()) {
				output.trySetValue(property,  relativeBlockInfo.state().getValue(property));
			}
			return new StructureTemplate.StructureBlockInfo(relativeBlockInfo.pos(), output, beModifier.apply(random, relativeBlockInfo.nbt()));
		}

		return relativeBlockInfo;
	}
}

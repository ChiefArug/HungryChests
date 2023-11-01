package chiefarug.mods.hungrychests;

import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

@SuppressWarnings("DataFlowIssue") // Shut up about passing null to datafixer stuff
@Mod(HungryChests.MODID)
public class HungryChests {
	public static final String MODID = "hungrychests";
	public static final ResourceLocation MODRL = new ResourceLocation(MODID, MODID);
	public static final Logger LGGR = LogUtils.getLogger();

	public HungryChests() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, HungryChestsConfig.SPEC);

		IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
		IEventBus bus = MinecraftForge.EVENT_BUS;

		BLOCK.register(modBus);
		BLOCK_ENTITY.register(modBus);
		ITEMS.register((modBus));
		STRUCTURE_PROCESSORS.register(modBus);
		MENUS.register(modBus);

		modBus.addListener(HungryChests::buildCreativeTab);
		modBus.addListener(HungryChests::clientSetup);

		bus.addListener(HungryChests::serverStart);
		bus.addListener(HungryChests::playerTick);
		bus.addListener(HungryChests::playerMine);
	}

	public static final DeferredRegister<Block> BLOCK = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
	public static final DeferredRegister<StructureProcessorType<?>> STRUCTURE_PROCESSORS = DeferredRegister.create(BuiltInRegistries.STRUCTURE_PROCESSOR.key(), MODID);
	public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MODID);

	public static final RegistryObject<Block> HUNGRY_CHEST = BLOCK.register("hungry_chest", () -> new HungryChestBlock(BlockBehaviour.Properties.copy(Blocks.CHEST), HungryChests::blockEntity));
	public static final RegistryObject<BlockEntityType<? extends ChestBlockEntity>> HUNGRY_CHEST_BLOCK_ENTITY = BLOCK_ENTITY.register("hungry_chest", () -> BlockEntityType.Builder.of(HungryChestBlockEntity::new, HUNGRY_CHEST.get()).build(null));
	public static final RegistryObject<Item> HUNGRY_CHEST_ITEM = ITEMS.register("hungry_chest", () -> new BlockItem(HUNGRY_CHEST.get(), new Item.Properties()));
	public static final RegistryObject<Item> PADDED_GLOVE = ITEMS.register("padded_glove", () -> new GloveItem(new Item.Properties().stacksTo(1)));



	public static final RegistryObject<StructureProcessorType<HungryChestProcessor>> HUNGRY_CHESTIFIER = STRUCTURE_PROCESSORS.register("hungry_chestifier", () -> () -> HungryChestProcessor.CODEC);
	public static final RegistryObject<MenuType<HungryChestMenu>> HUNGRY_GENERIC_9x1 = MENUS.register("hungry_chest_9x1", () -> new MenuType<>(HungryChestMenu::oneRow, FeatureFlags.VANILLA_SET));
	public static final RegistryObject<MenuType<HungryChestMenu>> HUNGRY_GENERIC_9x2 = MENUS.register("hungry_chest_9x2", () -> new MenuType<>(HungryChestMenu::twoRows, FeatureFlags.VANILLA_SET));
	public static final RegistryObject<MenuType<HungryChestMenu>> HUNGRY_GENERIC_9x3 = MENUS.register("hungry_chest_9x3", () -> new MenuType<>(HungryChestMenu::threeRows, FeatureFlags.VANILLA_SET));
	public static final RegistryObject<MenuType<HungryChestMenu>> HUNGRY_GENERIC_9x4 = MENUS.register("hungry_chest_9x4", () -> new MenuType<>(HungryChestMenu::fourRows, FeatureFlags.VANILLA_SET));
	public static final RegistryObject<MenuType<HungryChestMenu>> HUNGRY_GENERIC_9x5 = MENUS.register("hungry_chest_9x5", () -> new MenuType<>(HungryChestMenu::fiveRows, FeatureFlags.VANILLA_SET));
	public static final RegistryObject<MenuType<HungryChestMenu>> HUNGRY_GENERIC_9x6 = MENUS.register("hungry_chest_9x6", () -> new MenuType<>(HungryChestMenu::sixRows, FeatureFlags.VANILLA_SET));

	private static BlockEntityType<? extends ChestBlockEntity> blockEntity() {
		return HUNGRY_CHEST_BLOCK_ENTITY.get();
	}

	private static void buildCreativeTab(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey().equals(CreativeModeTabs.REDSTONE_BLOCKS) || event.getTabKey().equals(CreativeModeTabs.FUNCTIONAL_BLOCKS))
			event.accept(HUNGRY_CHEST_ITEM);
	}

	// how on earth does this not crash servers, lambda magic I guess
	private static void clientSetup(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			BlockEntityRenderers.register(HUNGRY_CHEST_BLOCK_ENTITY.get(), HungryChestRenderer::new);
			MenuScreens.register(HUNGRY_GENERIC_9x1.get(), ContainerScreen::new);
			MenuScreens.register(HUNGRY_GENERIC_9x2.get(), ContainerScreen::new);
			MenuScreens.register(HUNGRY_GENERIC_9x3.get(), ContainerScreen::new);
			MenuScreens.register(HUNGRY_GENERIC_9x4.get(), ContainerScreen::new);
			MenuScreens.register(HUNGRY_GENERIC_9x5.get(), ContainerScreen::new);
			MenuScreens.register(HUNGRY_GENERIC_9x6.get(), ContainerScreen::new);
		});
	}

	private static final ResourceLocation processorLocation = MODRL.withPath("hungry_chestifier");
	public static Holder<StructureProcessorList> chestHungrifier;

	private static void serverStart(ServerStartedEvent event) {
		chestHungrifier = event.getServer().registryAccess().registry(Registries.PROCESSOR_LIST).get().getHolderOrThrow(ResourceKey.create(Registries.PROCESSOR_LIST, processorLocation));
	}

	private static void playerTick(TickEvent.PlayerTickEvent event) {
		if (event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.START && event.player.hasContainerOpen() && event.player.containerMenu instanceof HungryChestMenu) {
			HungryChestBlockEntity.tryHurtPlayer((ServerPlayer) event.player);
		}
	}

	private static void playerMine(PlayerEvent.BreakSpeed event) {
		if (event.getState().is(HUNGRY_CHEST.get())) {
			if (event.getEntity() instanceof ServerPlayer player)
				HungryChestBlockEntity.tryHurtPlayer(player);
			event.setNewSpeed(event.getNewSpeed() / 10);
		}
	}
}

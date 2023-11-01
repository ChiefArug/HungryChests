package chiefarug.mods.hungrychests;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MenuType;

public class HungryChestMenu extends ChestMenu {

	private HungryChestMenu(MenuType<?> type, int containerId, Inventory playerInventory, int rows) {
		this(type, containerId, playerInventory, new SimpleContainer(9 * rows), rows);
	}

	public HungryChestMenu(MenuType<?> type, int containerId, Inventory playerInventory, Container container, int rows) {
		super(type, containerId, playerInventory, container, rows);
	}

  public static HungryChestMenu oneRow(int pContainerId, Inventory pPlayerInventory) {
      return new HungryChestMenu(HungryChests.HUNGRY_GENERIC_9x1.get(), pContainerId, pPlayerInventory, 1);
   }

   public static HungryChestMenu twoRows(int pContainerId, Inventory pPlayerInventory) {
      return new HungryChestMenu(HungryChests.HUNGRY_GENERIC_9x2.get(), pContainerId, pPlayerInventory, 2);
   }

   public static HungryChestMenu threeRows(int pContainerId, Inventory pPlayerInventory) {
      return new HungryChestMenu(HungryChests.HUNGRY_GENERIC_9x3.get(), pContainerId, pPlayerInventory, 3);
   }

   public static HungryChestMenu fourRows(int pContainerId, Inventory pPlayerInventory) {
      return new HungryChestMenu(HungryChests.HUNGRY_GENERIC_9x4.get(), pContainerId, pPlayerInventory, 4);
   }

   public static HungryChestMenu fiveRows(int pContainerId, Inventory pPlayerInventory) {
      return new HungryChestMenu(HungryChests.HUNGRY_GENERIC_9x5.get(), pContainerId, pPlayerInventory, 5);
   }

   public static HungryChestMenu sixRows(int pContainerId, Inventory pPlayerInventory) {
      return new HungryChestMenu(HungryChests.HUNGRY_GENERIC_9x6.get(), pContainerId, pPlayerInventory, 6);
   }

   public static HungryChestMenu threeRows(int pContainerId, Inventory pPlayerInventory, Container pContainer) {
      return new HungryChestMenu(HungryChests.HUNGRY_GENERIC_9x3.get(), pContainerId, pPlayerInventory, pContainer, 3);
   }

   public static HungryChestMenu sixRows(int pContainerId, Inventory pPlayerInventory, Container pContainer) {
      return new HungryChestMenu(HungryChests.HUNGRY_GENERIC_9x6.get(), pContainerId, pPlayerInventory, pContainer, 6);
   }
}
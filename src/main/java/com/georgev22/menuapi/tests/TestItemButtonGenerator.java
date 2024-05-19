package com.georgev22.menuapi.tests;

import com.georgev22.menuapi.api.inventory.components.IMenuButton;
import com.georgev22.menuapi.api.inventory.MenuInventoryHolder;
import com.georgev22.menuapi.api.inventory.PageRange;
import com.georgev22.menuapi.inventory.components.AMenuButton;
import com.georgev22.menuapi.inventory.components.MenuItem;
import com.georgev22.menuapi.utilities.SerializableItemStack;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class TestItemButtonGenerator {

    public static List<IMenuButton> generate() {
        List<IMenuButton> buttons = new ArrayList<>();

        buttons.add(
                new AMenuButton(
                        new MenuItem(
                                new SerializableItemStack(new ItemStack(Material.PAPER), BigInteger.ONE)
                        ),
                        45,
                        new PageRange(2, -1)) {
                    @Override
                    public void clickEvent(Player player, InventoryClickEvent event) {
                        Inventory inventory = event.getClickedInventory();
                        if (inventory == null) {
                            return;
                        }

                        if (inventory.getHolder() instanceof MenuInventoryHolder menuInventoryHolder) {
                            menuInventoryHolder.getMenu().open(player, menuInventoryHolder.getMenu().getPage(player) - 1);
                        }

                    }
                }
        );

        buttons.add(
                new AMenuButton(
                        new MenuItem(
                                new SerializableItemStack(new ItemStack(Material.PAPER), BigInteger.ONE)
                        ),
                        49,
                        new PageRange(0, -1)) {
                    @Override
                    public void clickEvent(Player player, InventoryClickEvent event) {
                        Inventory inventory = event.getClickedInventory();
                        if (inventory == null) {
                            return;
                        }

                        if (inventory.getHolder() instanceof MenuInventoryHolder menuInventoryHolder) {
                            menuInventoryHolder.getMenu().close(player);
                        }
                    }
                }
        );

        buttons.add(
                new AMenuButton(
                        new MenuItem(
                                new SerializableItemStack(new ItemStack(Material.PAPER), BigInteger.ONE)
                        ),
                        53,
                        new PageRange(0, -1)) {
                    @Override
                    public void clickEvent(Player player, InventoryClickEvent event) {
                        Inventory inventory = event.getClickedInventory();
                        if (inventory == null) {
                            return;
                        }

                        if (inventory.getHolder() instanceof MenuInventoryHolder menuInventoryHolder) {
                            menuInventoryHolder.getMenu().open(player, menuInventoryHolder.getMenu().getPage(player) + 1);
                        }
                    }
                }
        );


        return buttons;
    }

}

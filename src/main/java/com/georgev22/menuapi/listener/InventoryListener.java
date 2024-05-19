package com.georgev22.menuapi.listener;

import com.georgev22.menuapi.api.inventory.components.IMenuButton;
import com.georgev22.menuapi.api.inventory.MenuInventoryHolder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.Optional;

public class InventoryListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getView().getTopInventory();
        Player player = (Player) event.getWhoClicked();
        //noinspection ConstantValue
        if (inventory == null) {
            return;
        }

        if (!(inventory.getHolder() instanceof MenuInventoryHolder menuInventoryHolder)) {
            return;
        }

        //noinspection ConstantValue
        if (menuInventoryHolder == null) {
            return;
        }

        event.setCancelled(true);

        Optional<IMenuButton> button = menuInventoryHolder.getMenu().getButtons().stream()
                .filter(b -> b.getPageRange().isPageInRange(menuInventoryHolder.getMenu().getPage(player)))
                .filter(b -> b.getSlot() == event.getSlot()).findFirst();


        button.ifPresent(inventoryButton -> inventoryButton.clickEvent(player, event));
    }

}

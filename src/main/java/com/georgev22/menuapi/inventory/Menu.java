package com.georgev22.menuapi.inventory;

import com.georgev22.menuapi.api.inventory.*;
import com.georgev22.menuapi.api.inventory.components.IMenuButton;
import com.georgev22.menuapi.utilities.InventoryUpdate;
import com.georgev22.library.minecraft.BukkitMinecraftUtils.MinecraftVersion;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * The `Menu` class implements the `IMenu` interface and manages the interactive menu in the Minecraft game.
 * It maintains a list of buttons (`IMenuButton`) which provide the various options in the menu, as
 * well as a list of `Viewer` objects, each representing a player viewing the menu.
 * <p>
 * The class provides methods to set and retrieve menu buttons, open the menu for a specific player,
 * close the menu either for a specific player or for all players, and set or retrieve the menu title.
 * <p>
 * A new `Menu` object is initialized with the number of rows in the menu, maximum number of pages,
 * and a list of `IMenuButton` objects. Initial menu creation includes validation rules for row and
 * page counts.
 */
public class Menu implements IMenu {

    private final List<IMenuButton> buttons;

    private final int rows;
    private final List<Viewer> viewers = new ArrayList<>();
    private int maxPages;

    /**
     * Constructs a new Menu.
     * <p>
     * This constructor initializes a Menu object with the specified number of rows and maximum number of pages, along with a list of buttons to be displayed on the menu.
     * The rows represent the number of rows each page of the menu would have.
     * The maxPages represent the maximum number of pages the menu can have.
     * The menu buttons are the interactive components displayed on the menu.
     * <p>
     * The constructor validates the row and page counts to adhere to the following rules:
     * 1. If the menu is to have multiple pages i.e., when maxPages > 1 or maxPages == -1, the rows must be greater than 1.
     * 2. If the menu is to have a single page, the rows must be at least 1.
     * 3. Rows can be at most 6 (as a Minecraft inventory can have a maximum of 6 rows)
     *
     * @param rows     the number of rows in the menu. Must be at least 1. Must not exceed 6.
     * @param maxPages the maximum pages the menu can have. Can be -1 to indicate no page maximum.
     * @param buttons  a list of IMenuButton objects representing the buttons to be displayed on the menu.
     * @throws IllegalArgumentException if row or page conditions stated above are violated.
     */
    public Menu(int rows, int maxPages, List<IMenuButton> buttons) {
        if (maxPages > 1 || maxPages == -1) {
            if (rows < 2) {
                throw new IllegalArgumentException("Rows (Rows = " + rows + ") must be greater than 1 if you want to have multiple pages (Max Pages = " + maxPages + ")");
            }
        } else {
            if (rows < 1) {
                throw new IllegalArgumentException("Rows (Rows = " + rows + ") must be greater than 0 (Max Pages = " + maxPages + ")");
            }
        }
        if (rows > 6) {
            throw new IllegalArgumentException("Rows must be less than or equal to 6 (Rows = " + rows + ") (Max Pages = " + maxPages + ")");
        }
        this.rows = rows;
        this.maxPages = maxPages;
        this.buttons = new ArrayList<>(buttons);
    }


    /**
     * Retrieves the list of menu buttons.
     *
     * @return a list of {@link IMenuButton} objects representing the buttons in the menu.
     */
    @Override
    public @NotNull List<IMenuButton> getButtons() {
        return this.buttons;
    }

    /**
     * Sets the list of menu buttons.
     *
     * @param buttons a list of {@link IMenuButton} objects to be set in the menu.
     */
    @Override
    public void setButtons(@NotNull List<IMenuButton> buttons) {
        this.buttons.clear();
        this.buttons.addAll(buttons);
    }

    /**
     * This method opens the menu for a specific player on a given page. It also adds a new viewer if the player is not already viewing the menu.
     * Button items are added to the inventory for the specific menu page and the player is updated with the open inventory view.
     *
     * @param player       the {@link Player} for whom the menu will be opened.
     * @param page         the page number to be opened in the menu.
     * @param menuConsumer a {@link Consumer} functional interface that serves as a callback mechanism after the menu has been opened.
     */
    @Override
    public void open(@NotNull Player player, int page, Consumer<IMenu> menuConsumer) {
        if (page == 0) return;
        if (page < 1) return;
        if (this.maxPages != -1) if (page > this.maxPages) return;

        if (this.viewers.stream().anyMatch(viewer -> viewer.getPlayer().equals(player))) {
            Optional<Viewer> optionalViewer = this.viewers.stream().filter(viewer -> viewer.getPlayer().equals(player)).findFirst();
            optionalViewer.ifPresent(viewer -> viewer.setPage(page));
        } else {
            this.viewers.add(new Viewer(player, page));
        }

        Inventory inventory = new MenuInventoryHolder(this, player).getInventory();
        inventory.clear();
        for (
                IMenuButton button : this.buttons.stream()
                .filter(b -> b.getPageRange().isPageInRange(page))
                .toList()
        ) {
            ItemStack itemStack = button.getItem().getItemStack().getItemStack();
            itemStack.setAmount(button.getItem().getItemStack().getAmount().intValue());
            inventory.setItem(button.getSlot(), itemStack);
        }

        player.openInventory(inventory);
        menuConsumer.accept(this);
    }

    /**
     * Closes the menu for all players.
     */
    @Override
    public void close() {
        ListIterator<Viewer> iterator = this.viewers.listIterator();
        while (iterator.hasNext()) {
            Viewer viewer = iterator.next();
            viewer.getPlayer().closeInventory();
            iterator.remove();
        }
    }

    /**
     * Closes the menu for a specific player.
     *
     * @param player the {@link Player} for whom the menu will be closed.
     */
    @Override
    public void close(@NotNull Player player) {
        if (player.getOpenInventory().getTopInventory().getHolder() instanceof MenuInventoryHolder) {
            player.closeInventory();
        }

        this.viewers.stream().filter(viewer -> viewer.getPlayer().equals(player)).findFirst().ifPresent(this.viewers::remove);
    }

    /**
     * Sets the title of the menu.
     *
     * @param title the title to be set for the menu.
     */
    @Override
    public void setTitle(@NotNull Player player, @NotNull String title) {
        InventoryView inventoryView = player.getOpenInventory();
        //noinspection ConstantValue
        if (inventoryView == null) return;
        Inventory inventory = inventoryView.getTopInventory();
        //noinspection ConstantValue
        if (inventory == null) return;
        if (inventory.getHolder() instanceof MenuInventoryHolder) {
            if (MinecraftVersion.getCurrentVersion().isAbove(MinecraftVersion.V1_19_R3)) {
                inventoryView.setTitle(title);
            } else {
                InventoryUpdate.updateInventory(player, title);
            }
        }
    }

    /**
     * Retrieves the title of the menu.
     *
     * @return the title of the menu.
     */
    @Override
    public String getTitle(@NotNull Player player) {
        InventoryView inventoryView = player.getOpenInventory();
        //noinspection ConstantValue
        if (inventoryView == null) return "";
        Inventory inventory = inventoryView.getTopInventory();
        //noinspection ConstantValue
        if (inventory == null) return "";
        if (inventory.getHolder() instanceof MenuInventoryHolder) {
            if (MinecraftVersion.getCurrentVersion().isAboveOrEqual(MinecraftVersion.V1_14_R1)) {
                //noinspection deprecation
                return inventoryView.getTitle();
            } else {
                try {
                    return (String) inventory.getClass().getMethod("getTitle").invoke(inventory);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return "";
    }

    /**
     * Retrieves the number of rows in the menu.
     *
     * @return the number of rows in the menu.
     */
    @Override
    public int getRows() {
        return this.rows;
    }

    @Override
    public int getPage(Player player) {
        Viewer viewer = this.viewers.stream().filter(viewer1 -> viewer1.getPlayer().equals(player)).findFirst().orElse(null);
        return viewer != null ? viewer.getPage() : 0;
    }

    /**
     * Retrieves the number of pages in the menu.
     *
     * @return the number of pages in the menu.
     */
    @Override
    public int getPages() {
        return this.maxPages;
    }

    /**
     * Sets the number of pages in the menu.
     *
     * @param pages the number of pages to be set in the menu.
     */
    @Override
    public void setPages(int pages) {
        this.maxPages = pages;
    }
}

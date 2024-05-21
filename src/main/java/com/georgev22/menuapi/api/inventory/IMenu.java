package com.georgev22.menuapi.api.inventory;

import com.georgev22.menuapi.api.inventory.components.IMenuButton;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

public interface IMenu {

    /**
     * Retrieves the list of menu buttons.
     *
     * @return a list of {@link IMenuButton} objects representing the buttons in the menu.
     */
    @NotNull
    List<IMenuButton> getButtons();

    /**
     * Sets the list of menu buttons.
     *
     * @param buttons a list of {@link IMenuButton} objects to be set in the menu.
     */
    void setButtons(@NotNull List<IMenuButton> buttons);

    /**
     * Opens the menu for a specific player on a given page.
     *
     * @param player the {@link Player} for whom the menu will be opened.
     * @param page   the page number to be opened in the menu.
     */
    void open(@NotNull Player player, int page, Consumer<IMenu> menuConsumer);

    /**
     * Closes the menu for all players.
     */
    void close();

    /**
     * Closes the menu for a specific player.
     *
     * @param player the {@link Player} for whom the menu will be closed.
     */
    void close(@NotNull Player player);

    /**
     * Sets the title of the menu.
     *
     * @param title the title to be set in the menu for the specified player.
     */
    void setTitle(@NotNull Player player, @NotNull String title);

    /**
     * Retrieves the title of the menu.
     *
     * @return the title of the menu for the specified player.
     */
    String getTitle(@NotNull Player player);

    /**
     * Retrieves the number of rows in the menu.
     *
     * @return the number of rows in the menu.
     */
    int getRows();


    /**
     * Retrieves the current page of the menu for the specified player.
     *
     * @param player the {@link Player} for whom the page will be retrieved.
     * @return the current page of the menu for the specified player.
     */
    int getPage(Player player);


    /**
     * Retrieves the number of pages in the menu.
     *
     * @return the number of pages in the menu.
     */
    int getPages();

    /**
     * Sets the number of pages in the menu.
     *
     * @param pages the number of pages to be set in the menu.
     */
    void setPages(int pages);



}

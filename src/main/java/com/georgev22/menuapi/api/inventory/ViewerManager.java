package com.georgev22.menuapi.api.inventory;

import com.georgev22.library.maps.ConcurrentObjectMap;
import com.georgev22.library.maps.HashObjectMap;
import com.georgev22.library.maps.ObjectMap;
import com.georgev22.library.maps.UnmodifiableObjectMap;
import com.georgev22.menuapi.inventory.Menu;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manages the viewers for various menus in the Menu API.
 */
public class ViewerManager {

    private static final ObjectMap<Menu, List<Viewer>> viewers = new ConcurrentObjectMap<>();

    /**
     * Adds a viewer to the specified menu.
     *
     * @param menu   the menu to which the viewer is to be added
     * @param viewer the viewer to be added
     */
    public static @NotNull Viewer addViewer(Menu menu, Viewer viewer) {
        List<Viewer> viewerList = viewers.computeIfAbsent(menu, k -> new ArrayList<>());
        if (!viewerList.contains(viewer)) {
            viewerList.add(viewer);
        } else {
            int index = viewerList.indexOf(viewer);
            if (index != -1) {
                viewerList.set(index, viewer);
            }
        }

        return viewer;
    }

    /**
     * Removes a viewer from the specified menu.
     *
     * @param menu   the menu from which the viewer is to be removed
     * @param viewer the viewer to be removed
     */
    public static void removeViewer(Menu menu, Viewer viewer) {
        List<Viewer> viewerList = viewers.get(menu);
        if (viewerList != null) {
            viewerList.remove(viewer);
        }
    }

    /**
     * Retrieves the viewer associated with the specified menu and player.
     *
     * @param menu   the menu from which to retrieve the viewer
     * @param player the player whose viewer is to be retrieved
     * @return the viewer associated with the given menu and player, or null if no such viewer exists
     */
    public static Viewer getViewer(Menu menu, Player player) {
        List<Viewer> viewerList = viewers.get(menu);
        if (viewerList != null) {
            for (Viewer viewer : viewerList) {
                if (viewer.getPlayer().equals(player)) {
                    return viewer;
                }
            }
        }
        return null;
    }

    /**
     * Retrieves a list of viewers for the specified menu.
     *
     * @param menu the menu whose viewers are to be retrieved
     * @return a list of viewers for the given menu, or an empty list if no viewers are present
     */
    @UnmodifiableView
    public static List<Viewer> getViewers(Menu menu) {
        return Collections.unmodifiableList(viewers.getOrDefault(menu, new ArrayList<>()));
    }

    /**
     * Retrieves the mapping of all menus to their respective lists of viewers.
     *
     * @return a map of all menus to their lists of viewers
     */
    @Contract(" -> new")
    @UnmodifiableView
    public static @NotNull UnmodifiableObjectMap<Menu, List<Viewer>> getViewers() {
        ObjectMap<Menu, List<Viewer>> unmodifiableViewers = new HashObjectMap<>();
        viewers.forEach((key, value) -> unmodifiableViewers.put(key, Collections.unmodifiableList(value)));
        return new UnmodifiableObjectMap<>(unmodifiableViewers);
    }

}

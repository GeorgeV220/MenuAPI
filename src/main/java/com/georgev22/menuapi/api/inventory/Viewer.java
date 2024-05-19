package com.georgev22.menuapi.api.inventory;

import org.bukkit.entity.Player;

public class Viewer {

    private final Player player;
    private int page;

    public Viewer(Player player, int page) {
        this.player = player;
        this.page = page;
    }

    public Player getPlayer() {
        return this.player;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "Viewer{" +
                "player=" + player.getName() +
                ", page=" + page +
                '}';
    }
}

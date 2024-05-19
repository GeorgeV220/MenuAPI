package com.georgev22.menuapi;

import co.aikar.commands.PaperCommandManager;
import com.georgev22.menuapi.commands.TestInvCommand;
import com.georgev22.menuapi.listener.InventoryListener;
import com.georgev22.library.minecraft.BukkitMinecraftUtils;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private PaperCommandManager commandManager;

    @Override
    public void onEnable() {
        this.commandManager = new PaperCommandManager(this);
        setupCommands();
        BukkitMinecraftUtils.registerListeners(this, new InventoryListener());
    }

    private void setupCommands() {
        this.commandManager.enableUnstableAPI("help");
        this.commandManager.registerCommand(new TestInvCommand());
    }
}

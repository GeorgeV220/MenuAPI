package com.georgev22.menuapi.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandIssuer;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import com.georgev22.menuapi.api.inventory.IMenu;
import com.georgev22.menuapi.inventory.Menu;
import com.georgev22.menuapi.tests.TestItemButtonGenerator;
import org.jetbrains.annotations.NotNull;

@CommandAlias("testinv")
public class TestInvCommand extends BaseCommand {
    static IMenu menu;

    static {
        menu = new Menu(6, 2, TestItemButtonGenerator.generate());
    }


    @Default
    public void test(@NotNull CommandIssuer issuer, String[] args) {
        if (!issuer.isPlayer()) {
            issuer.sendMessage("Only players can use this command!");
            return;
        }

        menu.open(issuer.getIssuer(), 1);


    }

}

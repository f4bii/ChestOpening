package de.f4bii.api.gui;

import org.bukkit.entity.Player;

@FunctionalInterface
public interface ItemClickExecutor {

    void execute(Player player);
}

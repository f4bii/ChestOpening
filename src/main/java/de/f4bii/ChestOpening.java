package de.f4bii;

import de.f4bii.api.gui.InventoryListener;
import de.f4bii.db.DataProvider;
import de.f4bii.db.LocalDataProvider;
import de.f4bii.listener.InteractListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class ChestOpening extends JavaPlugin {

    @Override
    public void onEnable() {
        DataProvider dataProvider = new LocalDataProvider();
        InteractListener listener = new InteractListener(dataProvider);
        Bukkit.getPluginManager().registerEvents(listener, this);
        Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);
    }
}
package de.f4bii.listener;

import de.f4bii.api.ChestWinnables;
import de.f4bii.db.DataProvider;
import de.f4bii.gui.OpeningGUI;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractListener implements Listener {

    private final DataProvider dataProvider;

    public InteractListener(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    @EventHandler
    public void enderChestInteract(PlayerInteractEvent event){
        if (event.getClickedBlock().getType().equals(Material.ENDER_CHEST)) {
            event.setCancelled(true);
//            if (dataProvider.getChests(event.getPlayer().getUniqueId()) > 0) {
                new OpeningGUI(dataProvider, new ChestWinnables()).open(event.getPlayer());
//            }
        }
    }

}

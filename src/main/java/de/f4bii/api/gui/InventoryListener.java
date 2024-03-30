package de.f4bii.api.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryHolder;

import java.util.ArrayList;
import java.util.List;

public class InventoryListener implements Listener {

    private final List<Object> executed = new ArrayList<>();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClick().equals(ClickType.DOUBLE_CLICK)) {
            return;
        }
        if (event.getClickedInventory() == null || event.getClickedInventory().getHolder() == null ||
            !(event.getClickedInventory().getHolder() instanceof AbstractInventory)) {
            return;
        }
        AbstractInventory inventory = (AbstractInventory) event.getClickedInventory().getHolder();
        if (executed.remove(event)) {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        inventory.executeClick(player,
            event.getView().convertSlot(event.getRawSlot()),
                                    event.getCurrentItem(),
                                    event.getClick()
        );
        event.setCancelled(inventory.isCancel());
        executed.add(event);
    }

    @EventHandler
    public void onInvClose(InventoryCloseEvent event) {
        if (event.getInventory().getHolder() == null ||
            !(event.getInventory().getHolder() instanceof AbstractInventory)) {
            return;
        }
        AbstractInventory inventory = (AbstractInventory) event.getInventory().getHolder();
        inventory.onClose();
    }
}

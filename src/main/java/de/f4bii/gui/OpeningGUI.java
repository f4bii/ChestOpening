package de.f4bii.gui;

import de.f4bii.ChestOpening;
import de.f4bii.api.ChestWinnables;
import de.f4bii.api.ItemStackBuilder;
import de.f4bii.api.animation.Animation;
import de.f4bii.api.gui.AbstractInventory;
import de.f4bii.api.gui.FillerOptions;
import de.f4bii.db.DataProvider;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class OpeningGUI extends AbstractInventory {

    private static final ChestOpening plugin = ChestOpening.getPlugin(ChestOpening.class);
    private final DataProvider dataProvider;
    private final ChestWinnables chestWinnables;
    private Material win;
    private boolean addedWin = false;

    public OpeningGUI(DataProvider dataProvider, ChestWinnables chestWinnables) {
        this.dataProvider = dataProvider;
        this.chestWinnables = chestWinnables;
        this.setInventory(Bukkit.createInventory(this, 27, "ChestOpening"));
    }

    @Override
    protected void build() {
        setFillerOption(FillerOptions.OUTSIDE);
        setFillerItem(ItemStackBuilder.createItem().material(Material.STAINED_GLASS_PANE).displayName("§7").build());

        Animation animation = new Animation(plugin, 5);
        List<Material> materials = chestWinnables.generateItems(24);
        win = materials.get(materials.size()-4);
        for (int i = 0; i < materials.size()-6; i++) {
            int finalI = i;
            animation.addStep(() -> {
                setItem(10, new ItemStack(materials.get(finalI)));
                setItem(11, new ItemStack(materials.get(finalI+1)));
                setItem(12, new ItemStack(materials.get(finalI+2)));
                setItem(13, new ItemStack(materials.get(finalI+3)));
                setItem(14, new ItemStack(materials.get(finalI+4)));
                setItem(15, new ItemStack(materials.get(finalI+5)));
                setItem(16, new ItemStack(materials.get(finalI+6)));
                construct();
            });
        }
        animation.addStep(this::sendWin);
        animation.addEmtpySteps(4);
        animation.addStep(() -> {
            getPlayer().closeInventory();
            animation.stop();
        });
        animation.start();
    }

    @Override
    protected void onClose() {
        if (!addedWin) {
            sendWin();
        }
    }

    private void sendWin() {
        addedWin = true;
        getPlayer().getInventory().addItem(new ItemStack(win));
        getPlayer().sendMessage("§aDu hast das Item §6"+win.name()+"§a gewonnen.");
    }
}

package de.f4bii.api.gui;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Data
@Builder(setterPrefix = "with")
public class ClickableItem {

    private ItemClickExecutor clickExecutor;
    @Singular
    private List<ClickType> clicks;
    private ItemStack itemStack;

    public boolean canClick(ClickType clickType) {
        return clicks.contains(clickType);
    }

    public void execute(Player player) {
        clickExecutor.execute(player);
    }
}

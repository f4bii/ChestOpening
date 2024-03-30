package de.f4bii.api.gui;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@SuppressWarnings("unused")
public abstract class AbstractInventory implements InventoryHolder {

    public static final ItemClickExecutor EMPTY_CLICK_EXECUTOR = (player) -> {};
    public static final ClickType[] KEYBOARD_CLICK = {ClickType.NUMBER_KEY, ClickType.DROP, ClickType.CONTROL_DROP};
    public static final ClickType[] CREATIVE_CLICK = {ClickType.MIDDLE, ClickType.CREATIVE};
    public static final ClickType[] RIGHT_CLICK = {ClickType.RIGHT, ClickType.SHIFT_RIGHT};
    public static final ClickType[] LEFT_CLICK = {ClickType.LEFT, ClickType.SHIFT_LEFT, ClickType.DOUBLE_CLICK, ClickType.CREATIVE};
    public static final ClickType[] SHIFT_CLICK = {ClickType.SHIFT_LEFT, ClickType.SHIFT_RIGHT, ClickType.CONTROL_DROP};

    private final Map<Integer, ClickableItem.ClickableItemBuilder> clickExecutors = new HashMap<>();
    private FillerOptions fillerOption = null;
    private ItemStack fillerItem;
    private AbstractInventory parent;
    private boolean cancel = true;
    private Inventory inventory;
    private Player player;

    public AbstractInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public AbstractInventory() {
    }

    protected void build() {
    }

    protected void onClose() {
    }

    protected synchronized ClickableItem.ClickableItemBuilder setItem(int slot, ItemStack itemStack) {
        return setItem(slot, itemStack, EMPTY_CLICK_EXECUTOR);
    }

    protected synchronized ClickableItem.ClickableItemBuilder setItem(
        int slot, ItemStack itemStack, ItemClickExecutor clickExecutor
    ) {
        return setItem(slot, itemStack, clickExecutor, ClickType.values());
    }

    protected synchronized ClickableItem.ClickableItemBuilder setItem(
        int slot, ItemStack itemStack, ItemClickExecutor clickExecutor, ClickType... clickTypes
    ) {
        Map<ClickType, ItemClickExecutor> out = new HashMap<>();
        for (ClickType clickType : clickTypes) {
            out.put(clickType, clickExecutor);
        }
        ClickableItem.ClickableItemBuilder builder = ClickableItem.builder();
        clickExecutors.put(
            slot,
            builder.withItemStack(itemStack)
                   .withClicks(Arrays.stream(clickTypes).collect(Collectors.toList()))
                   .withClickExecutor(clickExecutor)
        );
        return builder;
    }

    public synchronized void executeClick(Player player, int slot, ItemStack item, ClickType clickType) {
        if (!clickExecutors.containsKey(slot)) {
            return;
        }
        ClickableItem clickTypeItemClickExecutorMap = clickExecutors.get(slot).build();
        if (clickTypeItemClickExecutorMap.canClick(clickType)) {
            clickTypeItemClickExecutorMap.execute(player);
        }
    }

    private boolean isSimilar(ItemStack item, ItemStack item2) {
        ItemMeta meta = item.getItemMeta();
        ItemMeta meta2 = item2.getItemMeta();
        if (meta == null || meta2 == null) {
            return false;
        }
        if (!meta.hasDisplayName() || !meta2.hasDisplayName()) {
            return false;
        }
        return meta.getDisplayName().equals(meta2.getDisplayName())
            && (meta.getLore() == null
            || meta2.getLore() == null
            || meta.getLore().equals(meta2.getLore()))
            && item.getType().equals(item2.getType())
            && item.getAmount() == item2.getAmount();
    }

    protected synchronized void construct() {
        buildFillerItems();
        getInventory().clear();
        clickExecutors.forEach((integer, clickableItem) -> {
            if (integer < 0 || integer >= getInventory().getSize()) {
                return;
            }
            getInventory().setItem(integer, clickableItem.build().getItemStack());
        });
    }

    private void buildFillerItems() {
        int[] fillerSlots = null;
        if (getFillerOption() != null) {
            fillerSlots = getFillerOption().execute(inventory.getSize());
        }
        if (fillerSlots == null) {
            return;
        }
        for (int fillerSlot : fillerSlots) {
            if (getClickExecutors().containsKey(fillerSlot)) {
                continue;
            }
            setItem(fillerSlot, fillerItem);
        }
    }

    public void open(Player player) {
        this.player = player;
        build();
        construct();
        player.openInventory(getInventory());
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
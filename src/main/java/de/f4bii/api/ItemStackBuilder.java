package de.f4bii.api;

import lombok.Builder;
import lombok.Singular;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;

public class ItemStackBuilder {

    @Builder(builderMethodName = "createItem", builderClassName = "NormalItemStackBuilder")
    private static ItemStack createItem0(
        Material material, String displayName, int amount, @Singular("lore") List<String> lore,
        @Singular List<ItemFlag> itemFlags, Map<Enchantment, Integer> enchantments
    ) {
        return buildItem(material, displayName, amount, lore, itemFlags, enchantments);
    }

    private static ItemStack buildItem(
        Material material, String displayName, int amount, List<String> lore, List<ItemFlag> itemFlags,
        Map<Enchantment, Integer> enchantments
    ) {
        ItemStack out = new ItemStack(material);
        out.setAmount(Math.max(1, amount));
        ItemMeta meta = out.getItemMeta();
        if (meta == null) {
            return out;
        }
        meta.setDisplayName(displayName);
        meta.setLore(lore);
        if (itemFlags != null) {
            itemFlags.forEach(meta::addItemFlags);
        }
        if (enchantments != null) {
            enchantments.forEach((enchantment, integer) -> meta.addEnchant(enchantment, integer, true));
        }
        out.setItemMeta(meta);
        return out;
    }
}

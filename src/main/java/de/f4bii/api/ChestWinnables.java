package de.f4bii.api;

import de.f4bii.api.animation.Tuple;
import org.bukkit.Material;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class ChestWinnables {

    private List<Tuple<Material, Float>> possibleItems = new ArrayList<>();
    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    public ChestWinnables() {
        possibleItems.add(new Tuple<>(Material.DIAMOND_BLOCK, .01f));
        possibleItems.add(new Tuple<>(Material.DIAMOND, .09f));
        possibleItems.add(new Tuple<>(Material.IRON_BLOCK, .14f));
        possibleItems.add(new Tuple<>(Material.IRON_INGOT, .26f));
        possibleItems.add(new Tuple<>(Material.COAL, .5f));
    }

    public List<Material> generateItems(int count){
        List<Material> out = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            out.add(getRandomItem());
        }
        return out;
    }

    private Material getRandomItem(){
        float rand = random.nextFloat();
        for (Tuple<Material, Float> item : possibleItems) {
            rand -= item.getValue();
            if (rand <= 0) {
                return item.getKey();
            }
        }
        return possibleItems.get(possibleItems.size() - 1).getKey();
    }
}

package de.f4bii.db;

import java.util.UUID;

public interface DataProvider {

    int getChests(UUID uuid);

    void setChests(UUID uuid, int count);

    default void addChest(UUID uuid) {
        setChests(uuid, getChests(uuid)+1);
    }

    default void removeChest(UUID uuid) {
        setChests(uuid, Math.max(0, getChests(uuid)-1));
    }
}

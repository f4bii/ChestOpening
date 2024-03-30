package de.f4bii.db;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LocalDataProvider implements DataProvider {

    private final Map<UUID, Integer> chests = new HashMap<>();

    @Override
    public int getChests(UUID uuid) {
        return chests.getOrDefault(uuid, 0);
    }

    @Override
    public void setChests(UUID uuid, int count) {
        chests.put(uuid, count);
    }
}

package de.f4bii.api.animation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Tuple<K, V> {
    private K key;
    private V value;
}

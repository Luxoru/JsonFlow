package me.luxoru.jsonflow.core.util.collection;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Tuple<K,V> {

    private final K first;
    private final V second;

}

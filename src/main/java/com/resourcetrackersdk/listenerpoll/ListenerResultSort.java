package com.resourcetrackersdk.listenerpoll;

import java.util.Comparator;

/**
 * Implements comparator used for sorting array of addresses
 * 
 * @author YarikRevich
 */
public class ListenerResultSort implements Comparator<ListenerResult>{
    @Override
    public int compare(ListenerResult o1, ListenerResult o2) {
        return o1.index() > o2.index() ? 0 : 1;
    }
};
package edu.macalester.comp124.mymap;

import sun.jvm.hotspot.utilities.HashtableEntry;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

/**
 * A simple implementation of a hashtable.
 *
 * @author shilad
 *
 * @param <K> Class for keys in the table.
 * @param <V> Class for values in the table.
 */
public class MyMap <K, V> {
	private static final int INITIAL_SIZE = 4;
	
	/** The table is package-protected so that the unit test can examine it. */
	List<MyEntry<K, V>> [] buckets;
	
	/** Number of unique entries (e.g. keys) in the table */
	private int numEntries = 0;
	
	/** Threshold that determines when the table should expand */
	private double loadFactor = 0.75;
	
	/**
	 * Initializes the data structures associated with a new hashmap.
	 */
	public MyMap() {
		buckets = newArrayOfEntries(INITIAL_SIZE);
	}
	
	/**
	 * Returns the number of unique entries (e.g. keys) in the table.
	 * @return the number of entries.
	 */
	public int size() {
		return numEntries;
	}
	
	/**
	 * Adds a new key, value pair to the table.
	 * @param key
	 * @param value
	 */
	public void put(K key, V value) {
        MyEntry<K, V> entry = new MyEntry<K, V>(key, value);
        int num = Integer.parseInt(entry.getKey().toString()) % buckets.length;
        boolean replace = false;

        for (int i = 0; i < buckets[num].size(); i++) {

            MyEntry<K, V> bucket = buckets[num].get(i);
            if (bucket.getKey().equals(key)) {
                bucket.setValue(value);
                replace = true;
            }
        }
        if (!replace) {
            expandIfNecessary();
            buckets[num].add(entry);
            numEntries++;
        }
    }

	/**
	 * Returns the value associated with the specified key, or null if it
	 * doesn't exist.
	 * 
	 * @param key
	 * @return
	 */
	public V get(K key) {
        int num = Integer.parseInt(key.toString()) % buckets.length;
        for (int i = 0; i < buckets[num].size(); i++) {
            MyEntry<K, V> bucket = buckets[num].get(i);
            if (bucket.getKey().equals(key)) {
                return bucket.getValue();
            }
        }
        return null;
    }



    /**
	 * Expands the table to double the size, if necessary.
	 */
	private void expandIfNecessary() {
        int newLength = buckets.length * 2;
        if (size() >= loadFactor * buckets.length) {
            List<MyEntry<K, V>>[] newBuckets = newArrayOfEntries(newLength);
            for (int i = 0; i < buckets.length; i++) {
                for (int j = 0; j < buckets[i].size(); j++) {
                    MyEntry<K, V> entry = buckets[i].get(j);
                    int num = Integer.parseInt(entry.getKey().toString()) % newLength;
                    newBuckets[num].add(buckets[i].get(j));
                }
            }
            buckets = newBuckets;
        }
    }
	
	/**
	 * Returns an array of the specified size, each
	 * containing an empty linked list that can be
	 * filled with MyEntry objects.
	 * 
	 * @param capacity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<MyEntry<K,V>>[] newArrayOfEntries(int capacity) {
		List<MyEntry<K, V>> [] entries = (List<MyEntry<K,V>> []) (
				java.lang.reflect.Array.newInstance(LinkedList.class, capacity));
		for (int i = 0; i < entries.length; i++) {
			entries[i] = new LinkedList();
		}
		return entries;
	}

	
}

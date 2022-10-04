package ServerModule.util;


import common.data.Flat;
import common.data.Transport;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Класс, описывающий коллекцию и определяющий взаимодействие с ней
 */
public class CollectionManager {
    private TreeMap<Integer, Flat> myCollection = new TreeMap();
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;
    private FileManager fileManager;

    public CollectionManager(FileManager fileManager){
        this.lastInitTime = null;
        this.lastSaveTime = null;
        this.fileManager = fileManager;
    }

    /**
     * @return The collection itself.
     */
    public TreeMap<Integer, Flat> getCollection() {
        return myCollection;
    }

    /**
     * @return Last initialization time or null if there wasn't initialization.
     */
    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }

    /**
     * @return Last save time or null if there wasn't saving.
     */
    public LocalDateTime getLastSaveTime() {
        return lastSaveTime;
    }

    /**
     * @return Name of the collection's type.
     */
    public String collectionType() {
        return myCollection.getClass().getName();
    }

    /**
     * @return Size of the collection.
     */
    public int collectionSize() {
        return myCollection.size();
    }

    /**
     * Generates next ID. It will be (the bigger one + 1).
     * @return Next ID.
     */
    public Long generateId() {
        if (myCollection.isEmpty()) return 1L;
        long lastId = 0L;
        for (Flat flat : myCollection.values()) {
            lastId = Math.max(lastId, flat.getId());
        }
        return lastId + 1;
    }

    /**
     * Remove flats greater than the selected one.
     * @param flat A Flat to compare with.
     */
    public void removeGreater(Flat flat) {
        myCollection.values().removeIf(flat1 -> flat1.compareTo(flat) > 0);
    }

    /**
     * Remove flats greater than the selected one.
     * @param flat A Flat to compare with.
     */
    public void removeLower(Flat flat) {
        myCollection.values().removeIf(flat1 -> flat1.compareTo(flat) < 0);
    }

    /**
     * Remove flats greater than the selected one.
     * @param keyToCompare A Key to compare with.
     */
    public void removeGreaterKey(long keyToCompare) {
        myCollection.entrySet().removeIf(entry -> entry.getKey() > keyToCompare);
    }

    /**
     * @param key The key used to take the marine.
     * @return A marine's key.
     */
    public Flat getFromCollection(int key) {
        return myCollection.get(key);
    }

    /**
     * @param id ID of the flat.
     * @return flat by his ID or null if flat isn't found.
     */
    public Integer getKeyById(long id) {
        return myCollection.entrySet().stream().filter(entry -> entry.getValue().getId() == id).map(Map.Entry::getKey).findFirst().get();

    }

    /**
     * Adds a new flat to collection.
     * @param flat A Flat to add.
     */
    public void addToCollection(int key, Flat flat) {
        myCollection.put(key, flat);
    }


    public String filterByTransport(Transport transport) {
        if (myCollection.isEmpty()) return "Коллекция пуста!";
        List<Flat> filteredList = myCollection.values().stream().filter(x -> x.getTransport().equals(transport)).collect(Collectors.toList());

        StringBuilder info = new StringBuilder();
        for (Flat flat : filteredList) {
            info.append(flat.toString());
            info.append("\n\n");
        }
        return info.toString();
    }


    public String filterLessThanNew(boolean nnew) {
        if (myCollection.isEmpty()) return "Коллекция пуста!";
        List<Flat> filteredList = myCollection.values().stream().filter(x -> x.getNewness() != nnew).collect(Collectors.toList());

        StringBuilder info = new StringBuilder();
        for (Flat flat : filteredList) {
            info.append(flat.toString());
            info.append("\n\n");
        }
        return info.toString();
    }

    public String ascendingHeight() {
        if (myCollection.isEmpty()) return "Коллекция пуста!";

        List<Flat> sortedCollection = myCollection.values().stream().sorted(Comparator.comparing(Flat::getHeight)).collect(Collectors.toList());
        StringBuilder info = new StringBuilder();
        for (Flat flat : sortedCollection) {
            info.append(flat.getHeight());
            info.append("\n\n");
        }
        return info.toString();
    }

    /**
     * Removes a new flat to collection.
     * @param key A key to remove.
     */
    public void removeFromCollection(int key) {
        myCollection.remove(key);
    }

    /**
     * Clears the collection.
     */
    public void clearCollection() {
        myCollection.clear();
    }

    /**
     * Saves the collection to file.
     */
    public void saveCollection() {
        fileManager.writeCollection(myCollection);
        lastSaveTime = LocalDateTime.now();
    }

    /**
     * Loads the collection from file.
     */
    public void loadCollection() throws FileNotFoundException {
        myCollection = fileManager.readCollection();
        lastInitTime = LocalDateTime.now();
    }

    /**
     * @param collection myCollection
     * @return The last element of the collection or null if collection is empty
     */
    public Flat getLast(Collection<Flat> collection) {
        if (myCollection.isEmpty()) {return null;}
        else {
            Flat last = null;
            int max = 1;
            for (Flat m : collection) {
                if (m.getId() > max) last = m;
            }
            return last;
        }
    }


    @Override
    public String toString() {
        if (myCollection.isEmpty()) return "Коллекция пуста!";

        List<Flat> printCollection = myCollection.values().stream().sorted(Comparator.comparing(Flat::getCoordinates)).collect(Collectors.toList());

        StringBuilder info = new StringBuilder();
        for (Flat flat : printCollection) {
            info.append(flat);
            info.append("\n\n");
        }
        return info.toString();
    }
}

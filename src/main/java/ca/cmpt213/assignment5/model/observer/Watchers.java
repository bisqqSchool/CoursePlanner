package ca.cmpt213.assignment5.model.observer;

import ca.cmpt213.assignment5.restapi.ApiWatcherWrapper;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Watcher class that implements Observer Interface for updating the events.
 * Stores watchers in a map with unique id
 */

public class Watchers implements ObserverInterface {
    private final Map<Integer, ApiWatcherWrapper> WATCHER_MAP = new TreeMap<>();

    public Watchers() {
    }

    public List<String> getWatcherEvents(int watcherId) {
        ApiWatcherWrapper watcher = WATCHER_MAP.get(watcherId);
        return watcher.events;
    }

    public Map<Integer, ApiWatcherWrapper> getWatcherMap() {
        return WATCHER_MAP;
    }

    @Override
    public void addObserver(int watcherId, ApiWatcherWrapper watcher) {
        WATCHER_MAP.put(watcherId, watcher);
    }

    @Override
    public void removeObserver(int watcherId) {
        WATCHER_MAP.remove(watcherId);
    }

    @Override
    public void notifyObserver() {
        for (var entry : WATCHER_MAP.entrySet()) {
            entry.getValue().update(entry.getValue());
        }
    }
}

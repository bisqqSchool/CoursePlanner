package ca.cmpt213.assignment5.model.observer;

import ca.cmpt213.assignment5.restapi.ApiWatcherWrapper;

/**
 * ObserverInterface with add/remove and notify functions.
 */

public interface ObserverInterface {
    void addObserver(int watcherId, ApiWatcherWrapper watcher);

    void removeObserver(int watcherId);

    void notifyObserver();
}

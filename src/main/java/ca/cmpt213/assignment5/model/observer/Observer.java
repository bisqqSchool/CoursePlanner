package ca.cmpt213.assignment5.model.observer;

import ca.cmpt213.assignment5.restapi.ApiWatcherWrapper;

/*
 * Basic Observer Interface used for updating watchers
 * */

public interface Observer {
    void update(ApiWatcherWrapper watcherObject);
}

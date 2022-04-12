package ca.cmpt213.assignment5.restapi;

import ca.cmpt213.assignment5.model.observer.Observer;

import java.util.List;

/**
 * ApiWatcherWrapper contains all data for watcher wrapper
 * updates when event is changed.
 * contains Observer interface
 */

public class ApiWatcherWrapper implements Observer {
    public int id;
    public ApiDepartmentWrapper department;
    public ApiCourseWrapper course;
    public List<String> events;

    public ApiWatcherWrapper(int id, ApiDepartmentWrapper department, ApiCourseWrapper course, List<String> events) {
        this.id = id;
        this.department = department;
        this.course = course;
        this.events = events;
    }

    @Override
    public void update(ApiWatcherWrapper watcherObject) {
        this.events = watcherObject.events;
    }
}

package ca.cmpt213.assignment5.restapi;

/**
 * ApiNewWatcherWrapper contains all data for new watcher wrapper
 */

public class ApiNewWatcherWrapper {
    public int deptId;
    public int courseId;

    public ApiNewWatcherWrapper(int deptId, int courseId) {
        this.deptId = deptId;
        this.courseId = courseId;
    }

}

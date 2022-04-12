package ca.cmpt213.assignment5.model;

import ca.cmpt213.assignment5.restapi.ApiCourseWrapper;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ApiCourseData manages CourseData using a TreeMap to add / remove courses
 * with its unique ID.
 */

public class ApiCourseData {
    private final Map<Integer, ApiCourseWrapper> COURSE_MAP = new TreeMap<>();
    private final AtomicInteger COURSE_ID = new AtomicInteger();

    public void clearAll() {
        COURSE_MAP.clear();
        COURSE_ID.set(0);
    }

    public void add(String catalog) {
        int id = COURSE_ID.getAndIncrement();

        ApiCourseWrapper newCourse = new ApiCourseWrapper(id, catalog);
        COURSE_MAP.put(id, newCourse);
    }

    public Map<Integer, ApiCourseWrapper> getCourseMap() {
        return COURSE_MAP;
    }

    public ApiCourseWrapper getCourse(int courseId) {
        return COURSE_MAP.get(courseId);
    }
}

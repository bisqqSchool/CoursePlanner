package ca.cmpt213.assignment5.model;

import ca.cmpt213.assignment5.restapi.ApiCourseOfferingWrapper;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ApiCourseOfferingsData manages TreeMap of course offerings to add / remove
 * course offerings with its unique ID.
 */

public class ApiCourseOfferingsData {
    private final Map<Integer, ApiCourseOfferingWrapper> COURSE_OFFERINGS_MAP = new TreeMap<>();
    private final AtomicInteger COURSE_OFFERINGS_ID = new AtomicInteger();

    public ApiCourseOfferingsData() {
    }

    public void clearAll() {
        COURSE_OFFERINGS_MAP.clear();
        COURSE_OFFERINGS_ID.set(0);
    }

    public void add(CourseData course) {
        int id = COURSE_OFFERINGS_ID.getAndIncrement();
        String location = course.getLocation();
        String instructors = course.getInstructors();
        int year = course.getYear();
        int semesterNumber = course.getSemesterCode();
        String term = course.getTerm();

        ApiCourseOfferingWrapper courseOffering = new ApiCourseOfferingWrapper(id, location,
                instructors, term, semesterNumber, year);

        COURSE_OFFERINGS_MAP.put(id, courseOffering);
    }

    public Map<Integer, ApiCourseOfferingWrapper> getCourseOfferingsMap() {
        return COURSE_OFFERINGS_MAP;
    }

    public ApiCourseOfferingWrapper getCourseOfferings(int courseOfferingId) {
        return COURSE_OFFERINGS_MAP.get(courseOfferingId);
    }
}

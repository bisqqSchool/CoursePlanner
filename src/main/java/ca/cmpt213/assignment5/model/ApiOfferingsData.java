package ca.cmpt213.assignment5.model;

import ca.cmpt213.assignment5.restapi.ApiOfferingSectionWrapper;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ApiOfferingData manages TreeMap of offering to add / remove offering
 * with its unique ID.
 */

public class ApiOfferingsData {
    private final Map<Integer, ApiOfferingSectionWrapper> OFFERINGS_MAP = new TreeMap<>();
    private final AtomicInteger OFFERINGS_ID = new AtomicInteger();

    public ApiOfferingsData() {
    }

    public void clearAll() {
        OFFERINGS_MAP.clear();
        OFFERINGS_ID.set(0);
    }

    public void add(CourseData course) {
        int id = OFFERINGS_ID.getAndIncrement();
        String type = course.getComponentCode();
        int enrollmentTotal = course.getEnrollmentTotal();
        int enrollmentCap = course.getEnrollmentCapacity();

        ApiOfferingSectionWrapper newOfferingSection = new ApiOfferingSectionWrapper(type, enrollmentCap, enrollmentTotal);
        OFFERINGS_MAP.put(id, newOfferingSection);
    }

    public Map<Integer, ApiOfferingSectionWrapper> getCourseMap() {
        return OFFERINGS_MAP;
    }

}

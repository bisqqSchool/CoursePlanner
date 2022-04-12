package ca.cmpt213.assignment5.restapi;

/**
 * ApiCourseWrapper contains all data for course offering wrapper
 */

public class ApiCourseOfferingWrapper {
    public int courseOfferingId;
    public String location;
    public String instructors;
    public String term;
    public int semesterCode;
    public int year;


    public ApiCourseOfferingWrapper(int courseOfferingId, String location, String instructors,
                                    String term, int semesterCode, int year) {
        this.courseOfferingId = courseOfferingId;
        this.location = location;
        this.instructors = instructors;
        this.term = term;
        this.semesterCode = semesterCode;
        this.year = year;
    }
}
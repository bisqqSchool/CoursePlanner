package ca.cmpt213.assignment5.restapi;

/**
 * ApiCourseWrapper contains all data for course wrapper
 */

public class ApiCourseWrapper {
    public int courseId;
    public String catalogNumber;

    public ApiCourseWrapper(int courseId, String catalogNumber) {
        this.courseId = courseId;
        this.catalogNumber = catalogNumber;
    }
}
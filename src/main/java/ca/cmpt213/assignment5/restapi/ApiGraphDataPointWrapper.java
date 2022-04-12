package ca.cmpt213.assignment5.restapi;

/**
 * ApiGraphDataPointWrapper contains all data for data point wrapper
 */

public class ApiGraphDataPointWrapper {
    public int semesterCode;
    public int totalCoursesTaken;

    public ApiGraphDataPointWrapper(int semesterCode, int totalCoursesTaken) {
        this.semesterCode = semesterCode;
        this.totalCoursesTaken = totalCoursesTaken;
    }
}

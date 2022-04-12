package ca.cmpt213.assignment5.model;

import java.util.List;
import java.util.Objects;

/**
 * Generic Course class that handles CSV file data. Includes overridden
 * methods such as toString for console output, as well as equal and hashCode
 * for the HashSet. This class also formats the data into yearly dates as
 * well as dealing with multiple professors.
 */

public class CourseData {

    private int year;
    private String term = "";

    private String semesterNumber;

    private String department;
    private String catalogNumber;
    private String location;
    private String componentCode;

    private int enrollmentCapacity;
    private int enrollmentTotal;

    private List<String> instructors;

    public CourseData(String semesterNumber, String department, String catalogNumber, String location,
                      int enrollmentCapacity, int enrollmentTotal, List<String> instructor, String componentCode) {

        this.semesterNumber = semesterNumber;
        this.department = department;
        this.catalogNumber = catalogNumber;
        this.location = location;
        this.enrollmentCapacity = enrollmentCapacity;
        this.enrollmentTotal = enrollmentTotal;
        this.componentCode = componentCode;
        this.instructors = instructor;
        this.year = decodeYear();

        processInstructors();
        decodeTermCode();
    }

    public String getDepartmentAndCatalog() {
        return department + " " + catalogNumber;
    }

    public int getYear() {
        return this.year;
    }

    public String getTerm() {
        return this.term;
    }

    public String getLocation() {
        return this.location;
    }

    public String getComponentCode() {
        return this.componentCode;
    }

    public int getEnrollmentCapacity() {
        return this.enrollmentCapacity;
    }

    public int getEnrollmentTotal() {
        return this.enrollmentTotal;
    }

    public String getDepartment() {
        return this.department;
    }

    public String getCatalogNumber() {
        return this.catalogNumber;
    }

    public Integer getSemesterCode() {
        return Integer.parseInt(this.semesterNumber);
    }

    public String getInstructors() {
        return String.join(",", this.instructors);
    }

    private Integer decodeYear() {
        int firstDigit = Integer.parseInt("" + this.semesterNumber.charAt(0));
        int middleDigit = Integer.parseInt("" + this.semesterNumber.charAt(1) + this.semesterNumber.charAt(2));
        return 1900 + (100 * firstDigit) + middleDigit;
    }

    private void processInstructors() {
        for (int i = 0; i < this.instructors.size(); i++) {
            this.instructors.set(i, this.instructors.get(i).replace("\"", ""));

            String professorName = this.instructors.get(i);
            if (professorName.equals("<null>") || professorName.equals("(null)")) {
                this.instructors.remove(i);
                this.instructors.add(i, "");
            }
        }
    }

    private void decodeTermCode() {
        int termCodeLength = this.semesterNumber.length() - 1;
        char value = this.semesterNumber.charAt(termCodeLength);
        int termCode = Integer.parseInt(String.valueOf(value));

        final int SPRING = 1;
        final int SUMMER = 4;
        final int FALL = 7;

        switch (termCode) {
            case SPRING -> this.term = "Spring";
            case SUMMER -> this.term = "Summer";
            case FALL -> this.term = "Fall";
            default -> {
                assert false;
            }
        }
    }

    @Override
    public String toString() {
        return semesterNumber + " in " +
                location + " by " +
                getInstructors() + "\n\t\t  Type=" +
                componentCode + ", Enrollment=" +
                enrollmentTotal + "/" +
                enrollmentCapacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof CourseData)) {
            return false;
        }

        CourseData course = (CourseData) o;
        return year == course.year && enrollmentCapacity == course.enrollmentCapacity &&
                enrollmentTotal == course.enrollmentTotal && term.equals(course.term) &&
                semesterNumber.equals(course.semesterNumber) && department.equals(course.department)
                && catalogNumber.equals(course.catalogNumber) && location.equals(course.location)
                && componentCode.equals(course.componentCode) && instructors.equals(course.instructors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, term, semesterNumber, department, catalogNumber,
                location, componentCode, enrollmentCapacity, enrollmentTotal, instructors);
    }
}

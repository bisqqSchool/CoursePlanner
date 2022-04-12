package ca.cmpt213.assignment5.restapi;

/**
 * ApiCourseCreationWrapper contains all data for course creation
 */

public class ApiCourseCreationWrapper {
    public String subjectName;
    public String catalogNumber;
    public String location;
    public String component;
    public String instructor;
    public String semester;
    public int enrollmentCap;
    public int enrollmentTotal;

    public int year;
    public String term = "";

    public ApiCourseCreationWrapper(String semester, String subjectName, String catalogNumber, String location,
                                    int enrollmentCap, String component, int enrollmentTotal, String instructor) {

        this.semester = semester;
        this.subjectName = subjectName;
        this.catalogNumber = catalogNumber;
        this.location = location;
        this.component = component;
        this.instructor = instructor;
        this.enrollmentCap = enrollmentCap;
        this.enrollmentTotal = enrollmentTotal;

        this.year = decodeYear();
        decodeTermCode();
    }

    private Integer decodeYear() {
        int firstDigit = Integer.parseInt("" + this.semester.charAt(0));
        int middleDigit = Integer.parseInt("" + this.semester.charAt(1) + this.semester.charAt(2));
        return 1900 + (100 * firstDigit) + middleDigit;
    }

    private void decodeTermCode() {
        int termCodeLength = this.semester.length() - 1;
        char value = this.semester.charAt(termCodeLength);
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
}

package ca.cmpt213.assignment5.csv;

import ca.cmpt213.assignment5.model.CourseData;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.*;

/**
* Generic CSV Reader class to parse data from a file path. This
* class includes the ability to sort and remove duplicated data sets.
* Includes a nicely formatted console print as well.
* */

public class CsvReader {

    private static final int FAILURE = -1;

    private List<CourseData> sortedCourses = null;

    private CsvReader(String filePath) {
        if (checkFilePath(filePath)) {
            parseCsv(filePath);
        }
    }

    public static CsvReader InstantiateCsvReader(String filePath) {
        return new CsvReader(filePath);
    }


    private CourseData newCourse(String[] data) {
        String semester = data[0];
        String department = data[1];
        String catalogNumber = data[2];
        String location = data[3];

        int enrollCapacity = Integer.parseInt(data[4]);
        int enrollTotal = Integer.parseInt(data[5]);

        List<String> instructor = new ArrayList<>();
        String componentCode = " ";

        //number 8 is the length that represents a fully provided data set
        if (data.length > 8) {
            for (int i = 6; i < data.length; i++) {
                if (i == data.length - 1) {
                    componentCode = data[data.length - 1];
                } else {
                    instructor.add(data[i]);
                }
            }

        } else {
            instructor.add(data[6]);
            componentCode = data[7];
        }

        return new CourseData(semester, department, catalogNumber, location,
                enrollCapacity, enrollTotal, instructor, componentCode);
    }

    private void parseCsv(String path) {
        final Set<CourseData> COURSES = new HashSet<>();

        try {
            File filePath = new File(path);
            Scanner fileScanner = new Scanner(filePath);

            fileScanner.nextLine();

            do {
                String courseData = fileScanner.nextLine().replaceAll("\\s+"," ");
                String[] data = courseData.split(",");
                COURSES.add(newCourse(data));

            } while (fileScanner.hasNextLine());

            fileScanner.close();
            sortedCourses = new ArrayList<>(COURSES);
            sortedCourses.sort(Comparator.comparing(CourseData::getSemesterCode));

        } catch (FileNotFoundException e) {
            System.exit(FAILURE);
            e.printStackTrace();
        }
    }

    public void dumpModel() {
        Set<String> courseSubject = new HashSet<>();

        for (CourseData department : sortedCourses) {
            courseSubject.add(department.getDepartmentAndCatalog());
        }

        for (String subject : courseSubject) {
            System.out.println(subject + ":");

            for (CourseData course : sortedCourses) {
                if (subject.equals(course.getDepartmentAndCatalog())) {
                    System.out.println("    " + course);
                }
            }
        }
    }

    private boolean checkFilePath(String filePath) {
        try {
            Paths.get(filePath);
        } catch(InvalidPathException err) {
            System.exit(FAILURE);
            return false;
        }
        return true;
    }

    public List<CourseData> getSortedCourses() {
        return sortedCourses;
    }
}

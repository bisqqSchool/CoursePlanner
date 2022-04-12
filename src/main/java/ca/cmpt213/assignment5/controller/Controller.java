package ca.cmpt213.assignment5.controller;

import ca.cmpt213.assignment5.csv.CsvReader;
import ca.cmpt213.assignment5.model.*;
import ca.cmpt213.assignment5.model.observer.Watchers;
import ca.cmpt213.assignment5.model.abstraction.AboutAbstraction;
import ca.cmpt213.assignment5.restapi.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
* Controller class for updating the UI and retrieving data from the parsed CSV data file.
* Current system outputs about and console dump.
* */

@RestController
public class Controller {
    private final String DATA = "data/course_data_2018.csv";
    private CsvReader csvReader = CsvReader.InstantiateCsvReader(DATA);
    private Watchers watchers = new Watchers();
    private AtomicInteger watcherId = new AtomicInteger();

    private ApiDepartmentData departmentData = new ApiDepartmentData();
    private ApiCourseData courseData = new ApiCourseData();
    private ApiCourseOfferingsData courseOfferingsData = new ApiCourseOfferingsData();
    private ApiOfferingsData offeringsData = new ApiOfferingsData();

    private Set<String> departmentSet = new TreeSet<>();
    private Set<String> catalogSet = new TreeSet<>();

    private List<CourseData> courseObjectList = csvReader.getSortedCourses();
    private List<ApiGraphDataPointWrapper> dataPoints = new ArrayList<>();

    @GetMapping("/api/about")
    public AboutAbstraction getName() {
        return new AboutAbstraction() {
            public String getAuthorName() {
                return "Alex Biscoveanu & Stephen Leung";
            }
            public String getAppName() {
                return "Course Planner";
            }
        };
    }

    @GetMapping("/api/dump-model")
    public void dumpModel() {
        csvReader.dumpModel();
    }

    @GetMapping("/api/departments")
    public Map<Integer, ApiDepartmentWrapper> getDepartments() {
        departmentSet.clear();
        departmentData.clearAll();

        for (CourseData course : courseObjectList) {
            departmentSet.add(course.getDepartment());
        }

        for (String department : departmentSet) {
            departmentData.add(department);
        }

        return departmentData.getDepartmentMap();
    }

    @GetMapping("/api/departments/{departmentId}/courses")
    public Map<Integer, ApiCourseWrapper> getOneDepartment(@PathVariable("departmentId") int departmentId) {
        ApiDepartmentWrapper department = departmentData.getDepartment(departmentId);
        catalogSet.clear();
        courseData.clearAll();

        for (CourseData course : courseObjectList) {
            if (course.getDepartment().equals(department.name)) {
                catalogSet.add(course.getCatalogNumber());
            }
        }

        for (String catalog : catalogSet) {
            courseData.add(catalog);
        }

        return courseData.getCourseMap();
    }

    @GetMapping("/api/departments/{departmentId}/courses/{courseId}/offerings")
    public Map<Integer, ApiCourseOfferingWrapper> getCourseOffering(@PathVariable("departmentId") int departmentId,
                                                                    @PathVariable("courseId") int courseId) {
        ApiDepartmentWrapper department = departmentData.getDepartment(departmentId);
        ApiCourseWrapper courses = courseData.getCourse(courseId);
        courseOfferingsData.clearAll();

        for (CourseData course : courseObjectList) {
            if (course.getDepartment().equals(department.name) &&
                    course.getCatalogNumber().equals(courses.catalogNumber)) {

                courseOfferingsData.add(course);
            }
        }

        return courseOfferingsData.getCourseOfferingsMap();
    }

    @GetMapping("/api/departments/{departmentId}/courses/{courseId}/offerings/{courseOfferingId}")
    public Map<Integer, ApiOfferingSectionWrapper> courseOfferingSectionsList(@PathVariable("departmentId") int departmentId,
                                                                              @PathVariable("courseId") int courseId,
                                                                              @PathVariable("courseOfferingId") int courseOfferingId) {

        ApiDepartmentWrapper department = departmentData.getDepartment(departmentId);
        ApiCourseWrapper courses = courseData.getCourse(courseId);
        ApiCourseOfferingWrapper offering = courseOfferingsData.getCourseOfferings(courseOfferingId);
        offeringsData.clearAll();

        for (CourseData course : courseObjectList) {
            if (course.getDepartment().equals(department.name) &&
                    course.getCatalogNumber().equals(courses.catalogNumber)) {

                if (course.getSemesterCode() == offering.semesterCode) {
                    offeringsData.add(course);
                }
            }
        }

        return offeringsData.getCourseMap();
    }

    @PostMapping("/api/addoffering")
    public void addOffering(@RequestBody ApiCourseCreationWrapper newOffer) {
        try {
            for (var entry : watchers.getWatcherMap().entrySet()) {
                ApiDepartmentWrapper department = entry.getValue().department;
                ApiCourseWrapper course = entry.getValue().course;

                if (newOffer.subjectName.equals(department.name) &&
                        newOffer.catalogNumber.equals(course.catalogNumber)) {

                    String component = newOffer.component;
                    int enrollmentCapacity = newOffer.enrollmentCap;
                    int enrollmentTotal = newOffer.enrollmentTotal;
                    String term = newOffer.term;
                    int year = newOffer.year;

                    final Timestamp TIME_STAMP = new Timestamp(System.currentTimeMillis());
                    final SimpleDateFormat DATE = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");

                    String event = DATE.format(TIME_STAMP) + ": Added section " + component.toUpperCase() + " with enrollment (" +
                            enrollmentTotal + " / " + enrollmentCapacity + ") to offering " + term + " " + year;

                    entry.getValue().events.add(event);
                }
            }

            watchers.notifyObserver();

            throw new ResponseStatusException(HttpStatus.CREATED, "201");

        } catch(ResponseStatusException error) {
            throw new ResponseStatusException(error.getStatus());
        }
    }

    @GetMapping("/api/watchers")
    public Map<Integer, ApiWatcherWrapper> changedWatcher() {
        return watchers.getWatcherMap();
    }

    @PostMapping("/api/watchers")
    public void createWatcher(@RequestBody ApiNewWatcherWrapper newWatcher) {
        try {
            int id = watcherId.getAndIncrement();
            ApiDepartmentWrapper department = departmentData.getDepartment(newWatcher.deptId);
            ApiCourseWrapper course = courseData.getCourse(newWatcher.courseId);

            ApiWatcherWrapper watcher = new ApiWatcherWrapper(id, department, course, new ArrayList<>());
            watchers.addObserver(id, watcher);

            throw new ResponseStatusException(HttpStatus.CREATED, "201");

        } catch(ResponseStatusException error) {
            throw new ResponseStatusException(error.getStatus());
        }
    }

    @GetMapping("/api/watchers/{watcherId}")
    public List<String> getListOfEvents(@PathVariable("watcherId") int watcherId) {
        return watchers.getWatcherEvents(watcherId);
    }

    @DeleteMapping("/api/watchers/{watcherId}")
    public void deleteWatcher(@PathVariable("watcherId") int watcherId) {
        try {
            watchers.removeObserver(watcherId);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "204");

        } catch(ResponseStatusException error) {
            throw new ResponseStatusException(error.getStatus());
        }
    }

    @GetMapping("/api/stats/students-per-semester")
    public List<ApiGraphDataPointWrapper> graphData(@RequestParam(value="deptId") int departmentId) {
        ApiDepartmentWrapper department = departmentData.getDepartment(departmentId);
        dataPoints.clear();

        int currentSemesterCode = 0;
        int previousSemesterCode;
        int sumOfCapacity = 0;
        final int FIRST_INDEX = 0;

        for (CourseData course : courseObjectList) {
            if (course.getDepartment().equals(department.name) &&
                    course.getComponentCode().equals("LEC")) {

                previousSemesterCode = currentSemesterCode;
                currentSemesterCode = course.getSemesterCode();

                if (previousSemesterCode == currentSemesterCode) {
                    sumOfCapacity += course.getEnrollmentTotal();

                } else if (previousSemesterCode != FIRST_INDEX) {
                    ApiGraphDataPointWrapper data = new ApiGraphDataPointWrapper(previousSemesterCode, sumOfCapacity);
                    dataPoints.add(data);
                    sumOfCapacity = 0;
                }
            }
        }

        // used to add the last data point ( changed previous to current semestercode )
        ApiGraphDataPointWrapper data = new ApiGraphDataPointWrapper(currentSemesterCode, sumOfCapacity);
        dataPoints.add(data);

        return dataPoints;
    }
}
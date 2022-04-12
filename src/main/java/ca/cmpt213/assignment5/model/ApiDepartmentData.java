package ca.cmpt213.assignment5.model;

import ca.cmpt213.assignment5.restapi.ApiDepartmentWrapper;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ApiDepartmentData manages TreeMap of departments to add / remove departments
 * with its unique ID.
 */

public class ApiDepartmentData {
    private final Map<Integer, ApiDepartmentWrapper> DEPARTMENT_MAP = new TreeMap<>();
    private final AtomicInteger DEPARTMENT_ID = new AtomicInteger();

    public ApiDepartmentData() {
    }

    public void clearAll() {
        DEPARTMENT_MAP.clear();
        DEPARTMENT_ID.set(0);
    }

    public void add(String department) {
        int id = DEPARTMENT_ID.getAndIncrement();

        ApiDepartmentWrapper newDepartment = new ApiDepartmentWrapper(id, department);
        DEPARTMENT_MAP.put(id, newDepartment);
    }

    public Map<Integer, ApiDepartmentWrapper> getDepartmentMap() {
        return DEPARTMENT_MAP;
    }

    public ApiDepartmentWrapper getDepartment(int departmentId) {
        return DEPARTMENT_MAP.get(departmentId);
    }
}

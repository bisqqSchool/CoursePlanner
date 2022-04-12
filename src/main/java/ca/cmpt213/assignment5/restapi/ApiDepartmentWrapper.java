package ca.cmpt213.assignment5.restapi;

/**
 * ApiDepartmentWrapper contains all data for department wrapper
 */

public class ApiDepartmentWrapper {
    public int deptId;
    public String name;

    public ApiDepartmentWrapper(int deptId, String name) {
        this.deptId = deptId;
        this.name = name;
    }
}


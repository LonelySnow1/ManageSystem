package com.lonelySnow.pojo;

public class Department {
    // 主键
    private Integer departmentId;
    // 姓名
    private String name;
    // 负责人
    private String principal;
    // 准入码
    private String manageCode;
    // 剩余次数
    private Integer time;

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getManageCode() {
        return manageCode;
    }

    public void setManageCode(String manageCode) {
        this.manageCode = manageCode;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Department{" +
                "departmentId=" + departmentId +
                ", name='" + name + '\'' +
                ", principal='" + principal + '\'' +
                ", manageCode='" + manageCode + '\'' +
                ", time=" + time +
                '}';
    }
}

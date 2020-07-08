package com.opus_bd.myapplication.Model.User;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserListModel {
    @SerializedName("aspnetId")
    @Expose
    private String aspnetId;
    @SerializedName("UserName")
    @Expose
    private String userName;
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("UserTypeId")
    @Expose
    private Object userTypeId;
    @SerializedName("companyId")
    @Expose
    private Object companyId;
    @SerializedName("EmpCode")
    @Expose
    private String empCode;
    @SerializedName("FinancialValue")
    @Expose
    private Object financialValue;
    @SerializedName("isActive")
    @Expose
    private Integer isActive;
    @SerializedName("EmpName")
    @Expose
    private String empName;
    @SerializedName("EmployeeId")
    @Expose
    private Integer employeeId;
    @SerializedName("DivisionName")
    @Expose
    private String divisionName;
    @SerializedName("DesignationName")
    @Expose
    private String designationName;
    @SerializedName("userTypeName")
    @Expose
    private Object userTypeName;
    @SerializedName("companyName")
    @Expose
    private String companyName;
    @SerializedName("departmentName")
    @Expose
    private String departmentName;
    @SerializedName("empType")
    @Expose
    private Object empType;
    @SerializedName("joiningDate")
    @Expose
    private Object joiningDate;
    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("status")
    @Expose
    private Object status;
    @SerializedName("photoId")
    @Expose
    private Integer photoId;
    @SerializedName("projectId")
    @Expose
    private Object projectId;
    @SerializedName("roleId")
    @Expose
    private String roleId;
    @SerializedName("Id")
    @Expose
    private Object id;
    @SerializedName("specialBranchUnitId")
    @Expose
    private Object specialBranchUnitId;

    public String getAspnetId() {
        return aspnetId;
    }

    public void setAspnetId(String aspnetId) {
        this.aspnetId = aspnetId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Object getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(Object userTypeId) {
        this.userTypeId = userTypeId;
    }

    public Object getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Object companyId) {
        this.companyId = companyId;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public Object getFinancialValue() {
        return financialValue;
    }

    public void setFinancialValue(Object financialValue) {
        this.financialValue = financialValue;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public String getDesignationName() {
        return designationName;
    }

    public void setDesignationName(String designationName) {
        this.designationName = designationName;
    }

    public Object getUserTypeName() {
        return userTypeName;
    }

    public void setUserTypeName(Object userTypeName) {
        this.userTypeName = userTypeName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Object getEmpType() {
        return empType;
    }

    public void setEmpType(Object empType) {
        this.empType = empType;
    }

    public Object getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(Object joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public Integer getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Integer photoId) {
        this.photoId = photoId;
    }

    public Object getProjectId() {
        return projectId;
    }

    public void setProjectId(Object projectId) {
        this.projectId = projectId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Object getSpecialBranchUnitId() {
        return specialBranchUnitId;
    }

    public void setSpecialBranchUnitId(Object specialBranchUnitId) {
        this.specialBranchUnitId = specialBranchUnitId;
    }
    /*@SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("grpId")
    @Expose
    private Integer grpId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("groupName")
    @Expose
    private Object groupName;
    @SerializedName("tagline")
    @Expose
    private Object tagline;
    @SerializedName("empPhoto")
    @Expose
    private String empPhoto;
    @SerializedName("grpPhoto")
    @Expose
    private Object grpPhoto;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGrpId() {
        return grpId;
    }

    public void setGrpId(Integer grpId) {
        this.grpId = grpId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getGroupName() {
        return groupName;
    }

    public void setGroupName(Object groupName) {
        this.groupName = groupName;
    }

    public Object getTagline() {
        return tagline;
    }

    public void setTagline(Object tagline) {
        this.tagline = tagline;
    }

    public String getEmpPhoto() {
        return empPhoto;
    }

    public void setEmpPhoto(String empPhoto) {
        this.empPhoto = empPhoto;
    }

    public Object getGrpPhoto() {
        return grpPhoto;
    }

    public void setGrpPhoto(Object grpPhoto) {
        this.grpPhoto = grpPhoto;
    }*/

}
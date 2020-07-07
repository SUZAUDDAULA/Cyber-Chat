package com.opus_bd.myapplication.Model.User;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserListModel {

    @SerializedName("Id")
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
    }

}
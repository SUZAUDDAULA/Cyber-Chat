package com.opus_bd.myapplication.Model.User;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DesignationModel {
    @SerializedName("Id")
    @Expose
    private Integer Id;
    @SerializedName("designationCode")
    @Expose
    private String designationCode;
    @SerializedName("designationName")
    @Expose
    private String designationName;
    @SerializedName("designationNameBN")
    @Expose
    private String designationNameBN;
    @SerializedName("shortName")
    @Expose
    private String shortName;


    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public String getDesignationCode() {
        return designationCode;
    }

    public void setDesignationCode(String designationCode) {
        this.designationCode = designationCode;
    }

    public String getDesignationName() {
        return designationName;
    }

    public void setDesignationName(String designationName) {
        this.designationName = designationName;
    }

    public String getDesignationNameBN() {
        return designationNameBN;
    }

    public void setDesignationNameBN(String designationNameBN) {
        this.designationNameBN = designationNameBN;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}

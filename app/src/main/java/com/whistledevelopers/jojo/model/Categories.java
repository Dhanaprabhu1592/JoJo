package com.whistledevelopers.jojo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Categories {


    public Categories(String name,String cover,int id) {
        this.name = name;
        this.cover = cover;
        this.id=id;
    }

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("parent_id")
    @Expose
    private String parentId;
    @SerializedName("cover")
    @Expose
    private String cover;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String  getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }





    @SerializedName("data")
    @Expose
    private List<Categories> data = null;

    public List<Categories> getData() {
        return data;
    }

    public void setData(List<Categories> data) {
        this.data = data;
    }

}

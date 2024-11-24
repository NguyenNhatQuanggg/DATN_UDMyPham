package com.project2.banhangmypham.model;

public class Category {
    private String id ;
    private String name ;
    private Long created_date ;
    private Long modified_date ;

    public Category() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Long created_date) {
        this.created_date = created_date;
    }

    public Long getModified_date() {
        return modified_date;
    }

    public void setModified_date(Long modified_date) {
        this.modified_date = modified_date;
    }
}

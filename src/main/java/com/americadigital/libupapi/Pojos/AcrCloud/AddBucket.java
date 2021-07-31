package com.americadigital.libupapi.Pojos.AcrCloud;

public class AddBucket {
    public String name;
    public String type;
    public String content_type;
    public String region;
    public Integer size;
    public Integer num;
    public Integer net_type;
    public String created_at;
    public String updated_at;
    public Integer id;

    public AddBucket(){

    }

    public AddBucket(String name, String type, String content_type, String region, Integer size, Integer num, Integer net_type, String created_at, String updated_at, Integer id) {
        this.name = name;
        this.type = type;
        this.content_type = content_type;
        this.region = region;
        this.size = size;
        this.num = num;
        this.net_type = net_type;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getNet_type() {
        return net_type;
    }

    public void setNet_type(Integer net_type) {
        this.net_type = net_type;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

package com.wolves.tolink.dto;

import java.io.Serializable;

/**
 * @author xulu
 */
public class ResourceDTO implements Serializable {

    private String name;
    private String url;

    @Override
    public String toString() {
        return "ResourceDTO{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

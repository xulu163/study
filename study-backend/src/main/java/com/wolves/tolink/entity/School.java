package com.wolves.tolink.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author xulu
 */
@Table(name = "school")
public class School implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String address;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date uodateTime;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUodateTime() {
        return uodateTime;
    }

    public void setUodateTime(Date uodateTime) {
        this.uodateTime = uodateTime;
    }
}

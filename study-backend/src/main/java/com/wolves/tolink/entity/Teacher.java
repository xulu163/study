package com.wolves.tolink.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author xulu
 */
@Table(name = "teacher")
public class Teacher implements Serializable {

    @Id
    protected String id;
    protected String name;
    protected Integer sex;
    protected Integer age;
    protected String address;
    @Column(name = "native_place")
    protected String nativePlace;
    protected String tel;
    @Column(name = "school_id")
    protected Integer schoolId;
    @Column(name = "clazz_id")
    protected Integer clazzId;
    @Column(name = "user_id")
    protected Integer userId;
    @Column(name = "create_time")
    protected Date createTime;
    @Column(name = "update_time")
    protected Date updateTime;

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public Integer getClazzId() {
        return clazzId;
    }

    public void setClazzId(Integer clazzId) {
        this.clazzId = clazzId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}

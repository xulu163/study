package com.wolves.tolink.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author xulu
 */
@Table(name = "student")
public class Student implements Serializable {

    @Id
    protected String id;
    protected String name;
    protected Integer sex;
    protected Integer age;
    protected String address;
    @Column(name = "place_native")
    protected String placeNative;
    protected String tel;
    @Column(name = "entry_time")
    protected Date entryTime;
    @Column(name = "school_id")
    protected Integer schoolId;
    @Column(name = "clazz_id")
    protected Integer clazzId;
    @Column(name = "teacher_id")
    protected String teacherId;
    @Column(name = "create_time")
    protected Date createTime;
    @Column(name = "update_time")
    protected Date updateTime;
    @Column(name = "total_score")
    protected Double totalScore;
    @Column(name = "avg_score")
    protected Double avgScore;
    @Column(name = "total_course")
    protected Integer totalCourse;

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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
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

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public Double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Double totalScore) {
        this.totalScore = totalScore;
    }

    public Double getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(Double avgScore) {
        this.avgScore = avgScore;
    }

    public Integer getTotalCourse() {
        return totalCourse;
    }

    public void setTotalCourse(Integer totalCourse) {
        this.totalCourse = totalCourse;
    }

    public String getPlaceNative() {
        return placeNative;
    }

    public void setPlaceNative(String placeNative) {
        this.placeNative = placeNative;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", placeNative='" + placeNative + '\'' +
                ", tel='" + tel + '\'' +
                ", entryTime=" + entryTime +
                ", schoolId=" + schoolId +
                ", clazzId=" + clazzId +
                ", teacherId=" + teacherId +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", totalScore=" + totalScore +
                ", avgScore=" + avgScore +
                ", totalCourse=" + totalCourse +
                '}';
    }
}

package com.wolves.tolink.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author xulu
 */
@Table(name = "student_course")
public class StudentCourse implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;
    @Column(name = "stu_id")
    protected  String stuId;
    @Column(name = "course_id")
    protected Integer courseId;
    protected Double score;
    @Column(name = "create_time")
    protected Date createTime;
    @Column(name = "update_time")
    protected Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
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
}

package com.wolves.tolink.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author xulu
 */
@Table(name = "teacher_course")
public class TeacherCourse implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;
    @Column(name = "teacher_id")
    protected  String teacherId;
    @Column(name = "course_id")
    protected Integer courseId;
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

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
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

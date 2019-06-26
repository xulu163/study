package com.wolves.tolink.dto;

import com.wolves.tolink.entity.Clazz;
import com.wolves.tolink.entity.Course;
import com.wolves.tolink.entity.School;
import com.wolves.tolink.entity.Teacher;

import java.util.List;

/**
 * @author xulu
 */
public class TeacherDTO extends Teacher {

    private School school;
    private Clazz clazz;
    private List<Course> courseList;

    @Override
    public String toString() {
        return "TeacherDTO{" +
                "school=" + school +
                ", clazz=" + clazz +
                ", courseList=" + courseList +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", nativePlace='" + nativePlace + '\'' +
                ", tel='" + tel + '\'' +
                ", schoolId=" + schoolId +
                ", clazzId=" + clazzId +
                ", userId=" + userId +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public Clazz getClazz() {
        return clazz;
    }

    public void setClazz(Clazz clazz) {
        this.clazz = clazz;
    }
}

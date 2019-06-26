package com.wolves.tolink.dto;

import com.wolves.tolink.entity.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author xulu
 */
public class StudentDTO extends Student implements Serializable {

    private School school;
    private Clazz clazz;
    private Teacher teacher;
    private List<CourseDTO> courses;

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

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<CourseDTO> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseDTO> courses) {
        this.courses = courses;
    }
}

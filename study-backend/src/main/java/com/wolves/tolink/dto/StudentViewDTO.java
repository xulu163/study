package com.wolves.tolink.dto;

import com.wolves.tolink.entity.*;
import com.wolves.tolink.entity.Student;

import java.io.Serializable;

/**
 * @author xulu
 */
public class StudentViewDTO extends Student implements Serializable {

    private String schoolName;
    private String clazzName;
    private String teacherName;

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}

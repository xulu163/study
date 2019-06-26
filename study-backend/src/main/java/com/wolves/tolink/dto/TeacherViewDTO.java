package com.wolves.tolink.dto;

import com.wolves.tolink.entity.Teacher;
import java.io.Serializable;

/**
 * @author xulu
 */
public class TeacherViewDTO extends Teacher implements Serializable {

    private String schoolName;
    private String clazzName;
    private String pwd;
    private Integer totalStu;
    private Integer totalCourse;

    public Integer getTotalCourse() {
        return totalCourse;
    }

    public void setTotalCourse(Integer totalCourse) {
        this.totalCourse = totalCourse;
    }

    public Integer getTotalStu() {
        return totalStu;
    }

    public void setTotalStu(Integer totalStu) {
        this.totalStu = totalStu;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

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

}

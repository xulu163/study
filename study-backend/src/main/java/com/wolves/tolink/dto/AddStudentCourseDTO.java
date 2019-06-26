package com.wolves.tolink.dto;

import java.io.Serializable;

/**
 * @author xulu
 */
public class AddStudentCourseDTO implements Serializable {

    private Integer courseId;
    private Double score;

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
}

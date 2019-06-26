package com.wolves.tolink.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author xulu
 */
@ApiModel
public class UpdateStudentCourseDTO implements Serializable {

    @ApiModelProperty(value = "新增传null")
    private Integer id;
    @ApiModelProperty(value = "课程id")
    private Integer courseId;
    @ApiModelProperty(value = "分数")
    private Double score;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}

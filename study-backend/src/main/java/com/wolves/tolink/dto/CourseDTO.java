package com.wolves.tolink.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

@ApiModel
public class CourseDTO implements Serializable {

    @ApiModelProperty(name = "课程id")
    private Integer id;
    @ApiModelProperty(name = "课程名")
    private String name;
    @ApiModelProperty(name = "分数")
    private Double score;
    @ApiModelProperty(name = "学生课程关系id")
    private Integer stuCourseId;
    private Date createTime;
    private Date updateTime;

    @Override
    public String toString() {
        return "CourseDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", score=" + score +
                ", stuCourseId=" + stuCourseId +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getStuCourseId() {
        return stuCourseId;
    }

    public void setStuCourseId(Integer stuCourseId) {
        this.stuCourseId = stuCourseId;
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

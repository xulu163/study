package com.wolves.tolink.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

/**
 * @author xulu
 */
@ApiModel(description = "学生课程")
public class CourseResultDTO implements Serializable {

    @ApiModelProperty(name="stuId", value="学生id")
    private String stuId;
    @ApiModelProperty(name="stuName", value="学生姓名")
    private String stuName;
    @ApiModelProperty(name="sex", value="学生性别（1男，2女）")
    private Integer sex;
    @ApiModelProperty(name="age", value="年龄")
    private Integer age;
    @ApiModelProperty(name="schoolName", value="学校")
    private String schoolName;
    @ApiModelProperty(name="clazzName", value="班级")
    private String clazzName;
    @ApiModelProperty(name = "courseName", value = "课程")
    private String courseName;
    @ApiModelProperty(name="score", value="分数")
    private Double score;
    @ApiModelProperty(name="teacherName", value="教师")
    private String teacherName;
    @ApiModelProperty(name="createTime", value="创建时间")
    private Date createTime;

    @Override
    public String toString() {
        return "CourseResultDTO{" +
                "stuId='" + stuId + '\'' +
                ", stuName='" + stuName + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                ", schoolName='" + schoolName + '\'' +
                ", clazzName='" + clazzName + '\'' +
                ", courseName='" + courseName + '\'' +
                ", score=" + score +
                ", teacherName='" + teacherName + '\'' +
                ", createTime=" + createTime +
                '}';
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

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

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
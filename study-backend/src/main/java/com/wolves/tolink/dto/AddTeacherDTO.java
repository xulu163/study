package com.wolves.tolink.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author xulu
 */
@ApiModel(description = "创建教师账号")
public class AddTeacherDTO implements Serializable {

    @ApiModelProperty(name = "teacherName", value = "教师姓名")
    private String teacherName;
    @ApiModelProperty(name = "sex", value = "性别（1男，2女）")
    private Integer sex;
    @ApiModelProperty(name = "age", value = "年龄")
    private Integer age;
    @ApiModelProperty(name = "address", value = "地址")
    private String address;
    @ApiModelProperty(name = "placeNative", value = "籍贯")
    private String placeNative;
    @ApiModelProperty(name = "tel", value = "账号，传手机号")
    private String tel;
    @ApiModelProperty(name = "schoolId", value = "学校id")
    private Integer schoolId;
    @ApiModelProperty(name = "clazzId", value = "班级id")
    private Integer clazzId;
    @ApiModelProperty(name = "courseIds", value = "课程id数组")
    private List<Integer> courseIds;

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPlaceNative() {
        return placeNative;
    }

    public void setPlaceNative(String placeNative) {
        this.placeNative = placeNative;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }


    public List<Integer> getCourseIds() {
        return courseIds;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public Integer getClazzId() {
        return clazzId;
    }

    public void setClazzId(Integer clazzId) {
        this.clazzId = clazzId;
    }

    public void setCourseIds(List<Integer> courseIds) {
        this.courseIds = courseIds;
    }
}

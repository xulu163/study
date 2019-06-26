package com.wolves.tolink.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author xulu
 */
@ApiModel
public class AddStudentDTO implements Serializable {

    @ApiModelProperty(name = "name", value = "姓名")
    protected String name;
    @ApiModelProperty(name = "sex", value = "性别（1男，2女）")
    protected Integer sex;
    @ApiModelProperty(name = "age", value = "年龄")
    protected Integer age;
    @ApiModelProperty(name = "address", value = "地址")
    protected String address;
    @ApiModelProperty(name = "placeNative", value = "籍贯")
    protected String placeNative;
    @ApiModelProperty(name = "tel", value = "手机号")
    protected String tel;
    @ApiModelProperty(name = "entryTime", value = "入学时间")
    protected Date entryTime;
    @ApiModelProperty(name = "schoolId", value = "学校id")
    protected Integer schoolId;
    @ApiModelProperty(name = "clazzId", value = "班级id")
    protected Integer clazzId;
    @ApiModelProperty(name = "courseList", value = "课程id数组")
    protected List<AddStudentCourseDTO> courseList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
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

    public List<AddStudentCourseDTO> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<AddStudentCourseDTO> courseList) {
        this.courseList = courseList;
    }
}

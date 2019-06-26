package com.wolves.tolink.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xulu
 */
public class TeaQueryDTO implements Serializable {

    @ApiModelProperty(name="teacherId", value="教师id")
    private String teacherId;
    @ApiModelProperty(name="teacherName", value="教师姓名")
    private String teacherName;
    @ApiModelProperty(name="sex", value="教师性别（1男，2女）")
    private Integer sex;
    @ApiModelProperty(name="ageStart", value="年龄范围起始")
    private Integer ageStart;
    @ApiModelProperty(name="ageEnd", value="年龄范围结束")
    private Integer ageEnd;
    @ApiModelProperty(name="schoolId", value="学校id")
    private Integer schoolId;
    @ApiModelProperty(name="clazzId", value="班级id")
    private Integer clazzId;
    @ApiModelProperty(name="placeNative", value="籍贯")
    private String placeNative;
    @ApiModelProperty(name="address", value="地址")
    private String address;
    @ApiModelProperty(name="tel", value="账号")
    private String tel;
    @ApiModelProperty(name="createTimeStart", value="创建时间起始")
    private Date createTimeStart;
    @ApiModelProperty(name="createTimeEnd", value="创建时间结束")
    private Date createTimeEnd;
    @ApiModelProperty(name="orderColumn", value="排序字段（必传：id，create_time）")
    private String orderColumn;
    @ApiModelProperty(name="orderIndex", value="排序方式（必传：倒序desc，asc升序）")
    private String orderIndex;
    @ApiModelProperty(name="page", value="页码")
    private Integer page;
    @ApiModelProperty(name="size", value="每页条数")
    private Integer size;

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

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

    public Integer getAgeStart() {
        return ageStart;
    }

    public void setAgeStart(Integer ageStart) {
        this.ageStart = ageStart;
    }

    public Integer getAgeEnd() {
        return ageEnd;
    }

    public void setAgeEnd(Integer ageEnd) {
        this.ageEnd = ageEnd;
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

    public String getPlaceNative() {
        return placeNative;
    }

    public void setPlaceNative(String placeNative) {
        this.placeNative = placeNative;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Date getCreateTimeStart() {
        return createTimeStart;
    }

    public void setCreateTimeStart(Date createTimeStart) {
        this.createTimeStart = createTimeStart;
    }

    public Date getCreateTimeEnd() {
        return createTimeEnd;
    }

    public void setCreateTimeEnd(Date createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }

    public String getOrderColumn() {
        return orderColumn;
    }

    public void setOrderColumn(String orderColumn) {
        this.orderColumn = orderColumn;
    }

    public String getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(String orderIndex) {
        this.orderIndex = orderIndex;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}

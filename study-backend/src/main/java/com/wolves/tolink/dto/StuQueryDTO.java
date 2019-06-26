package com.wolves.tolink.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

/**
 * @author xulu
 */
@ApiModel(description = "学生信息筛选条件")
public class StuQueryDTO implements Serializable {

    @ApiModelProperty(name="stuId", value="学生id")
    private String stuId;
    @ApiModelProperty(name="stuName", value="学生姓名")
    private String stuName;
    @ApiModelProperty(name="sex", value="学生性别（1男，2女）")
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
    @ApiModelProperty(name="tel", value="手机号码")
    private String tel;
    @ApiModelProperty(name="teacherName", value="老师姓名")
    private String teacherName;
    @ApiModelProperty(name="avgScoreStart", value="平均分起始")
    private Double avgScoreStart;
    @ApiModelProperty(name="avgScoreEnd", value="平均分结束")
    private Double avgScoreEnd;
    @ApiModelProperty(name="totalScoreStart", value="总分起始")
    private Double totalScoreStart;
    @ApiModelProperty(name="totalScoreEnd", value="总分结束")
    private Double totalScoreEnd;
    @ApiModelProperty(name="totalCourseStart", value="课程数起始")
    private Integer totalCourseStart;
    @ApiModelProperty(name="totalCourseEnd", value="课程数结束")
    private Integer totalCourseEnd;
    @ApiModelProperty(name="entryTimeStart", value="入学时间起始")
    private Date entryTimeStart;
    @ApiModelProperty(name="entryTimeEnd", value="入学时间结束")
    private Date entryTimeEnd;
    @ApiModelProperty(name="createTimeStart", value="创建时间起始")
    private Date createTimeStart;
    @ApiModelProperty(name="createTimeEnd", value="创建时间结束")
    private Date createTimeEnd;
    @ApiModelProperty(name="orderColumn", value="排序字段（必传：id,age,avg_score,total_score,total_course,entry_time,create_time）")
    private String orderColumn;
    @ApiModelProperty(name="orderIndex", value="排序方式（必传：倒序desc，asc升序）")
    private String orderIndex;
    @ApiModelProperty(name="teacherId", value="老师id（不要传）")
    private String teacherId;
    @ApiModelProperty(name="page", value="页码")
    private Integer page;
    @ApiModelProperty(name="size", value="每页条数")
    private Integer size;

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

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
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

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public Double getAvgScoreStart() {
        return avgScoreStart;
    }

    public void setAvgScoreStart(Double avgScoreStart) {
        this.avgScoreStart = avgScoreStart;
    }

    public Double getAvgScoreEnd() {
        return avgScoreEnd;
    }

    public void setAvgScoreEnd(Double avgScoreEnd) {
        this.avgScoreEnd = avgScoreEnd;
    }

    public Double getTotalScoreStart() {
        return totalScoreStart;
    }

    public void setTotalScoreStart(Double totalScoreStart) {
        this.totalScoreStart = totalScoreStart;
    }

    public Double getTotalScoreEnd() {
        return totalScoreEnd;
    }

    public void setTotalScoreEnd(Double totalScoreEnd) {
        this.totalScoreEnd = totalScoreEnd;
    }

    public Integer getTotalCourseStart() {
        return totalCourseStart;
    }

    public void setTotalCourseStart(Integer totalCourseStart) {
        this.totalCourseStart = totalCourseStart;
    }

    public Integer getTotalCourseEnd() {
        return totalCourseEnd;
    }

    public void setTotalCourseEnd(Integer totalCourseEnd) {
        this.totalCourseEnd = totalCourseEnd;
    }

    public Date getEntryTimeStart() {
        return entryTimeStart;
    }

    public void setEntryTimeStart(Date entryTimeStart) {
        this.entryTimeStart = entryTimeStart;
    }

    public Date getEntryTimeEnd() {
        return entryTimeEnd;
    }

    public void setEntryTimeEnd(Date entryTimeEnd) {
        this.entryTimeEnd = entryTimeEnd;
    }

    public Date getCreateTimeStart() {
        return createTimeStart;
    }

    public void setCreateTimeStart(Date createTimeStart) {
        this.createTimeStart = createTimeStart;
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

    public Date getCreateTimeEnd() {
        return createTimeEnd;
    }

    public void setCreateTimeEnd(Date createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }
}

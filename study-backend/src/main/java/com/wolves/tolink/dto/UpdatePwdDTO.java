package com.wolves.tolink.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author xulu
 */
@ApiModel
public class UpdatePwdDTO implements Serializable {

    private String oldPwd;
    private String newPwd;
    private String surePwd;
    private String timeStamp;
    @ApiModelProperty(name = "sign",value = "oldPwd+newPwd+timeStamp进行md5加密后的结果")
    private String sign;

    @Override
    public String toString() {
        return "UpdatePwdDTO{" +
                "oldPwd='" + oldPwd + '\'' +
                ", newPwd='" + newPwd + '\'' +
                ", surePwd='" + surePwd + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    public String getSurePwd() {
        return surePwd;
    }

    public void setSurePwd(String surePwd) {
        this.surePwd = surePwd;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}

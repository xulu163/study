<template>
    <div class="pageBg">
        <div class="main">
            <!-- <div class="logo"></div> -->
            <div class="welcome"></div>
            <el-form :model="ruleForm2" :rules="rules2" ref="ruleForm2" label-position="left" label-width="0px" class="demo-ruleForm login-container" @keyup.enter.native="handleSubmit2">
                <h3 class="title">管理系统登录</h3>
                <el-form-item prop="account">
                    <span class="icon-name"></span>
                    <el-input type="text" v-model="ruleForm2.account" auto-complete="off" placeholder="账号"></el-input>
                </el-form-item>
                <el-form-item prop="checkPass">
                    <span class="icon-password"></span>
                    <el-input type="password" v-model="ruleForm2.checkPass" auto-complete="off" placeholder="密码"></el-input>
                </el-form-item>

                <el-form-item style="width:100%;">
                    <el-button type="primary" style="width:100%;" @click.native.prevent="handleSubmit2" :loading="logining">登录</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
import {
    requestLogin,
    getModuleAuthBySystemName,
    menuList
} from '../api/api';
export default {
    data() {
        return {
            logining: false,
            ruleForm2: {
                account: '',
                checkPass: ''
            },
            rules2: {
                account: [{
                        required: true,
                        message: '请输入账号',
                        trigger: 'blur'
                    },
                    //{ validator: validaePass }
                ],
                checkPass: [{
                        required: true,
                        message: '请输入密码',
                        trigger: 'blur'
                    },
                    //{ validator: validaePass2 }
                ]
            },
            checked: true
        };
    },
    methods: {
        handleSubmit2(ev) {
            var _this = this;
            this.$refs.ruleForm2.validate((valid) => {
                if (valid) {

                    var loginParams = {
                        name: this.ruleForm2.account,
                        pwd: this.ruleForm2.checkPass
                    };
                    requestLogin(loginParams).then(data => {
                        console.log(data);
                        if(data.code!=0){
                          this.$message.error(data.msg);
                          return false;
                        }
                        let users = {
                            name: this.ruleForm2.account,
                            token: data.data
                        };
                        axios.defaults.headers.common['Authorization'] = data.data;
                        sessionStorage.setItem('user', JSON.stringify(users));
                        sessionStorage.setItem('userTelephone', _this.ruleForm2.account);
                        menuList({}).then(res => {
                          console.log(res.data);
                          if(res.data.length==0){
                            this.$message.error("您暂无权限！");
                            return false;
                          }
                          let menu_list = [];
                          res.data.forEach((item,k)=>{
                            if(item.name=="教师管理"){
                              sessionStorage.setItem('isadmin', "isadmin");
                              menu_list.push("orderquery");
                            }
                            if(item.name=="学生管理"){
                              menu_list.push("publishedBusiness");
                            }
                            if(item.name=="课程管理"){
                              menu_list.push("slideshow");
                            }
                          })
                          sessionStorage.setItem('module', JSON.stringify(menu_list))
                          this.$router.push({
                              path: menu_list[0]
                          });
                        })
                        return false;
                        var arr = ['pairs_backstage','paris_receipts','paris_feedback','paris_messNotice','paris_audits','paris_coupons','paris_chainCompensat'];
                        for(var i = 0; i < module.length; i++){
                            for(var j=0;j<arr.length;j++){
                                var index = module.indexOf(arr[j]);
                                if(index>-1){
                                    module.splice(index,1);
                                }
                            }
                        }
                        console.log(module.concat(arr));
                        sessionStorage.setItem('module', JSON.stringify(arr))
                        this.$router.push({
                            path: 'publishedBusiness'
                        });

                        // let para = {
                        //     systemName: 'paris'
                        // }
                        // getModuleAuthBySystemName(para).then(res => {
                        //     let module = res.data;
                        //     var arr = ['pairs_backstage','paris_receipts','paris_feedback','paris_messNotice','paris_audits','paris_coupons','paris_chainCompensat'];
                        //     for(var i = 0; i < module.length; i++){
                        //         for(var j=0;j<arr.length;j++){
                        //             var index = module.indexOf(arr[j]);
                        //             if(index>-1){
                        //                 module.splice(index,1);
                        //             }
                        //         }
                        //     }
                        //     //console.log(module.concat(arr));
                        //     sessionStorage.setItem('module', JSON.stringify(module.concat(arr)))
                            // if (res.data.length > 0) {
                            //     let url = "" + res.data[0].replace(/paris_/, "");
                            //     this.$router.push({
                            //         path: 'slideshow'
                            //     });
                            // }

                        //});

                    });
                } else {
                    return false;
                }
            });
        }
    }
}
</script>

<style lang="scss" scoped>
    .login-container {
        -webkit-border-radius: 5px;
        border-radius: 5px;
        -moz-border-radius: 5px;
        background-clip: padding-box;
        margin: 180px auto;
        width: 350px;
        padding: 35px 35px 15px;
        background: #fff;
        border: 1px solid #eaeaea;
        box-shadow: 0 0 25px #cac6c6;
        .title {
            margin: 0 auto 30px;
            text-align: center;
            color: #505458;
        }
    }
    .pageBg{
        position: absolute;
        top: 0;
        bottom: 0;
        left: 0;
        right: 0;
        background:url("../assets/bg.png") no-repeat  top center ;
        background-size:100% 100%;
        .main{
            margin: 0 auto;
            width: 1024px;
        }
        .login-container{
            width: 300px;
            padding:35px 50px 20px;
            margin:0 auto;
            box-shadow: 0 0 0 10px rgba(0,0,0,0.3);
            border-radius:2px;
        }
        .logo{
            margin:50px 0 0 0;
            width: 80px;
            height: 68px;
            background:url("../assets/logo.png") no-repeat  top center ;
            background-size:100% 100%;

        }
        .welcome{
            margin:70px auto 50px;
            width: 356px;
            height: 21px;
            background:url("../assets/welcome.png") no-repeat  top center ;
            background-size:100% 100%;

        }
        .el-button--primary {
            color: #fff;
            background-color: #008dff;
            border-color: #008dff;
            &:hover{
                background-color: #20a0ff;
                border-color: #20a0ff;
            }
        }
        .el-input__inner{
            padding-left:50px;
        }
    }</style>

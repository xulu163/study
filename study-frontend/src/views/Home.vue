<template>
<el-row class="container">
    <el-col  class="header">
        <el-col :span="9" class="logo" :class="collapsed?'logo-collapse-width':'logo-width'">
            <!--<img :src="this.sysUserAvatar" />--> {{collapsed?'':sysName}}
        </el-col>
        <el-col :span="6">
            <!--
				<div class="tools" @click.prevent="collapse">
					<i class="fa fa-align-justify"></i>
				</div>
				-->
        </el-col>
        <el-col :span="1" class="userinfo">
            <div class="tools" @click.prevent="logout">
                <i class="fa fa-power-off"></i>
            </div>
        </el-col>
        <el-col :span="4" class="userinfo">
            <span class="el-dropdown-link userinfo-inner" @click.prevent="updatePwd"> <i class="fa fa-user"></i> {{sysUserName}}</span>
        </el-col>

    </el-col>
    <el-col :span="24" class="main">
        <aside :class="collapsed?'menu-collapsed':'menu-expanded'">
            <!--导航菜单-->
            <el-menu :default-active="$route.path" class="el-menu-vertical-demo" @open="handleopen" @close="handleclose" @select="handleselect" unique-opened router v-show="!collapsed">
                <template v-for="(item,index) in routesItem">
						<el-submenu :index="index+''" v-if="item.flag">
							<template slot="title"><i :class="item.iconCls"></i>{{item.name}}</template>
                <el-menu-item v-for="child in item.children" :index="child.path" :key="child.path">{{child.name}}</el-menu-item>
                </el-submenu>
                </template>
            </el-menu>
        </aside>
        <section class="content-container">
            <div class="grid-content bg-purple-light">
                <el-col :span="24" class="breadcrumb-container">
                    <strong class="title">{{$route.name}}</strong>
                    <!--
						<el-breadcrumb separator="/" class="breadcrumb-inner">
							<el-breadcrumb-item v-for="item in $route.matched" :key="item.path">
								{{ item.name }}
							</el-breadcrumb-item>
						</el-breadcrumb>
						-->
                </el-col>
                <el-col :span="24" class="content-wrapper">
                    <transition name="fade" mode="out-in">
                        <router-view></router-view>
                    </transition>
                </el-col>
            </div>
        </section>
    </el-col>
    <!--弹窗框-->
    <div id="modelAll">
        <!--编辑banner-->
        <el-dialog title="修改密码" custom-class="dialogBox2" :visible.sync="editBannerDialog" @closed="resetForm('editOutput')"  width="40%">
            <el-form :model="editOutputData" :inline="true" :rules="editOutputRules" ref="editOutput"  label-width="100px">

                <el-form-item label="旧密码：" prop="oldPwd">
                    <el-input v-model="editOutputData.oldPwd" placeholder="请填旧密码" size="small" style="width:120px"></el-input>
                </el-form-item>
                <el-form-item label="新密码：" prop="newPwd">
                    <el-input v-model="editOutputData.newPwd" placeholder="请填新密码" size="small" style="width:120px"></el-input>
                </el-form-item>
                <el-form-item label="确认密码：" prop="surePwd">
                    <el-input v-model="editOutputData.surePwd" placeholder="请确认密码" size="small" style="width:120px"></el-input>
                </el-form-item>

                <el-form-item class="btnBox" style="text-align:center">
                    <el-button type="primary" @click="resetForm('editOutput')">取消</el-button>
                    <el-button type="primary" @click="editSubmitForm('editOutput')">提交</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</el-row>
</template>

<script>
import md5 from 'js-md5';
import {
  updatePwd,

} from '../api/api';
export default {
    data() {
        var validatePass = (rule, value, callback) => {
          if (value === '') {
            callback(new Error('请输入密码'));
          } else if(value.length<6||value.length>12) {
            callback(new Error('请输入6-12的密码'));
          }else{
            var regNumber = /\d+/; //验证0-9的任意数字最少出现1次。
            var regString = /[a-zA-Z]+/; //验证大小写26个字母任意字母最少出现1次。
            if (regNumber.test(value)&&regString.test(value)) {
              //this.$refs.ruleForm.validateField('newPwd');
              callback();
            }else{
              callback(new Error('密码必须包含数字和字母'));
            }

          }
        };
        var validatePass2 = (rule, value, callback) => {
          if (value === '') {
            callback(new Error('请再次输入密码'));
          } else if (value !== this.editOutputData.newPwd) {
            callback(new Error('两次输入密码不一致!'));
          } else {
            callback();
          }
        };
        return {
            sysName: '管理系统',
            collapsed: false,
            sysUserName: '',
            editBannerDialog:false,
            editOutputData:{
              "newPwd": "",
              "oldPwd": "",
              "sign": "",
              "surePwd": "",
              "timeStamp": ""
            },
            sysUserAvatar: 'static/logo.png',
            editOutputRules:{
              oldPwd: [
                { required: true , message: '请输入原密码',  trigger: 'blur'}
              ],
              newPwd: [
                { required: true , validator: validatePass, trigger: 'blur' }
              ],
              surePwd: [
                { required: true , validator: validatePass2, trigger: 'blur' }
              ]

            },
            form: {
                name: '',
                region: '',
                date1: '',
                date2: '',
                delivery: false,
                type: [],
                resource: '',
                desc: ''
            },
            routesItem: {
                fjy: {
                    flag: false,
                    name: '',
                    children: []
                },
                cd: {
                    flag: false,
                    name: '',
                    children: []
                },
                pay: {
                    flag: false,
                    name: '',
                    children: []
                },
                mgm: {
                    flag: false,
                    name: '',
                    children: []
                },
                ddl: {
                    flag: false,
                    name: '',
                    children: []
                }
            }
        }
    },
    methods: {
        onSubmit() {},
        handleopen() {},
        handleclose() {},
        handleselect: function(a, b) {},
        updatePwd(){
          this.editBannerDialog = true;
          this.editOutputData={
            "newPwd": "",
            "oldPwd": "",
            "sign": "",
            "surePwd": "",
            "timeStamp": ""
          }
        },
        editSubmitForm(formName) {
  				this.$refs[formName].validate((valid) => {
  					if (valid) {
              let current_time = new Date().getTime();
              let sign = md5(this.editOutputData.oldPwd+this.editOutputData.newPwd+current_time)
  						let param = {
                "newPwd": this.editOutputData.newPwd,
                "oldPwd": this.editOutputData.oldPwd,
                "sign": sign,
                "surePwd": this.editOutputData.surePwd,
                "timeStamp": current_time
  						};
  						updatePwd(param).then((res) => {
  							if (res.code == 0) {
  								this.editBannerDialog = false;
  								//this.getLoanUserList();
  								this.$message.success('成功');
  							} else {
  								this.$message.error(res.msg);
  							}
  						});
  					} else {
  						console.log('error submit!!');
  						return false;
  					}
  				});
  			},
        resetForm(formName) {

  				this.$refs[formName].resetFields();
          this.editBannerDialog = false;
  			},
        //退出登录
        logout: function() {
            var _this = this;
            this.$confirm('确认退出吗?', '提示', {
                //type: 'warning'
            }).then(() => {
                sessionStorage.removeItem('user');
                sessionStorage.removeItem('isadmin');
                _this.$router.push('/login');
            }).catch(() => {

            });
        },
        showMenu(i, status) {
            this.$refs.menuCollapsed.getElementsByClassName('submenu-hook-' + i)[0].style.display = status ? 'block' : 'none';
        },

        MergeArray(arr1,arr2){
            for(var i=0;i<arr1.length;i++){
                for(var j=0;j<arr2.length;j++){
                    var index = arr1.indexOf(arr2[j]);
                    if(index>-1){
                        arr1.splice(index,1);
                    }
                }

            }
            return arr1;

        }


    },
    created() {
      console.log(md5("123456"),new Date().getTime());
        var user = sessionStorage.getItem('user');
        if (user) {
            user = JSON.parse(user);
            this.sysUserName = user.name || '';

        }
        var module = JSON.parse(sessionStorage.getItem('module'));

        // console.log(module);
        // for (var i = 0; i < module.length; i++) {
        //
        //
        //     if (module[i] == "paris_slideshow") {
        //         this.routesItem.ddl.flag = true;
        //         this.routesItem.ddl.name = "DD链";
        //         this.routesItem.ddl.children.push({
        //             path: "/slideshow",
        //             name: "轮播图配置"
        //         })
        //     } else if (module[i] == "paris_audits") {
        //         this.routesItem.ddl.flag = true;
        //         this.routesItem.ddl.name = "DD链";
        //         this.routesItem.ddl.children.push({
        //             path: "/audits",
        //             name: "收佣审核"
        //         })
        //     } else if (module[i] == "paris_receipts") {
        //         this.routesItem.ddl.flag = true;
        //         this.routesItem.ddl.name = "DD链";
        //         this.routesItem.ddl.children.push({
        //             path: "/receipts",
        //             name: "收付流水"
        //         })
        //     } else if (module[i] == "paris_feedback") {
        //         this.routesItem.ddl.flag = true;
        //         this.routesItem.ddl.name = "DD链";
        //         this.routesItem.ddl.children.push({
        //             path: "/feedback",
        //             name: "意见反馈"
        //         })
        //     } else if (module[i] == "paris_coupons") {
        //         this.routesItem.ddl.flag = true;
        //         this.routesItem.ddl.name = "DD链";
        //         this.routesItem.ddl.children.push({
        //             path: "/coupons",
        //             name: "礼券兑换流水"
        //         })
        //     } else if (module[i] == "paris_syncCustomer") {
        //         this.routesItem.ddl.flag = true;
        //         this.routesItem.ddl.name = "DD链";
        //         this.routesItem.ddl.children.push({
        //             path: "/syncCustomer",
        //             name: "DD链配置"
        //         })
        //     } else if (module[i] == "paris_chainCompensat") {
        //         this.routesItem.ddl.flag = true;
        //         this.routesItem.ddl.name = "DD链";
        //         this.routesItem.ddl.children.push({
        //             path: "/chainCompensate",
        //             name: "上链补偿"
        //         })
        //     } else if (module[i] == "paris_messNotice") {
        //         this.routesItem.ddl.flag = true;
        //         this.routesItem.ddl.name = "DD链";
        //         this.routesItem.ddl.children.push({
        //             path: "/messNotice",
        //             name: "消息通知"
        //         })
        //     } else if (module[i] == "pairs_backstage") {
        //         this.routesItem.ddl.flag = true;
        //         this.routesItem.ddl.name = "DD链";
        //         this.routesItem.ddl.children.push({
        //             path: "/backstage",
        //             name: "活动报名经纪人查询"
        //         })
        //         this.routesItem.ddl.children.push({
        //             path: "/orderquery",
        //             name: "订单查询"
        //         })
        //     }
        // }
      if (module.indexOf("publishedBusiness") != -1 ) {
  	    this.routesItem.ddl.flag = true;
  	    this.routesItem.ddl.name = "学生信息";
  	    this.routesItem.ddl.children.push({
  		    path: "/publishedBusiness",
  		    name: "学生列表"
  	    });
      }
      if (module.indexOf("slideshow") != -1 ) {
        this.routesItem.ddl.flag = true;
  	    this.routesItem.ddl.name = "学生信息";
  	    this.routesItem.ddl.children.push({
  		    path: "/slideshow",
  		    name: "学生课程"
  	    });
      }
      if (module.indexOf("orderquery") != -1 ) {
        this.routesItem.fjy.flag = true;
  	    this.routesItem.fjy.name = "教师信息";
  	    this.routesItem.fjy.children.push({
  		    path: "/orderquery",
  		    name: "教师列表"
  	    });
      }
	    // this.routesItem.ddl.flag = true;
	    // this.routesItem.ddl.name = "DD链";
	    // this.routesItem.ddl.children.push({
		 //    path: "/finance",
		 //    name: "财务管理"
	    // });
    }
}
</script>

<style scoped lang="scss">
@import '~scss_vars';

.dialogBox1 {
    width: 50%;

}
.container {
    position: absolute;
    top: 0;
    bottom: 0;
    width: 100%;
    .header {
        height: 60px;
        line-height: 60px;
        background: $color-primary;
        color: #fff;
        .userinfo {
            text-align: right;
            padding-right: 35px;
            float: right;
            .userinfo-inner {
                cursor: pointer;
                color: #fff;
                img {
                    width: 40px;
                    height: 40px;
                    border-radius: 20px;
                    margin: 10px 0 10px 10px;
                    float: right;
                }
            }
        }
        .logo {

            height: 60px;
            font-size: 22px;
            padding-left: 20px;
            padding-right: 20px;

            img {
                width:40px;
                float: left;
                margin: 10px 10px 10px 0;
            }
            .txt {
                color: #fff;
            }
        }
        .logo-width {
            width: 360px;
        }
        .logo-collapse-width {
            width: 60px;
        }
        .tools {
            padding: 0 40px 0 23px;
            width: 14px;
            height: 60px;
            line-height: 60px;
            cursor: pointer;
        }
    }
    .main {
        display: flex;
        // background: #324057;
        position: absolute;
        top: 60px;
        bottom: 0;
        overflow: hidden;
        aside {
            flex: 0 0 230px;
            width: 230px;
            // position: absolute;
            // top: 0px;
            // bottom: 0px;
            overflow: auto;
            .el-menu {
                height: 100%;
            }
            .collapsed {
                width: 60px;
                .item {
                    position: relative;
                }
                .submenu {
                    position: absolute;
                    top: 0;
                    left: 60px;
                    z-index: 99999;
                    height: auto;
                    display: none;
                }

            }
        }
        .menu-collapsed {
            flex: 0 0 60px;
            width: 60px;
        }
        .menu-expanded {
            flex: 0 0 230px;
            width: 230px;
        }
        .content-container {
            // background: #f1f2f7;
            flex: 1;
            // position: absolute;
            // right: 0px;
            // top: 0px;
            // bottom: 0px;
            // left: 230px;
            overflow-y: scroll;
            padding: 20px;
            .breadcrumb-container {
                //margin-bottom: 15px;
                .title {
                    width: 200px;
                    float: left;
                    color: #475669;
                }
                .breadcrumb-inner {
                    float: right;
                }
            }
            .content-wrapper {
                background-color: #fff;
                box-sizing: border-box;
            }
        }
    }
}
</style>

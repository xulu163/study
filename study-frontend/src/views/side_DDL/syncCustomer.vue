<template>
	<section style="position: relative;">
		<!--同步经纪人手机号-->
		<h3>同步经纪人手机号:</h3>
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
            <el-form :inline="true" :model="brokerModule" :rules="brokerModule.brokerModuleRules" style="margin-left:30px;">
                <el-form-item label="经纪人手机号" prop="brokerTel">
                    <el-input v-model.number="brokerModule.brokerTel" placeholder="请输入经纪人手机号" :maxlength='11'></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="syncBtn">同步</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="syncCustomerCountBtn">查询同步数量</el-button>
                </el-form-item>
            </el-form>
		</el-col>
		<!--操作版本号-->
		<h3 class="title">操作版本号:</h3>
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
            <el-form :inline="true" :model="currentVersionModule" style="margin-left:30px;">
                <el-form-item label="当前版本号" prop="currentVersion">
                    <el-input v-model="currentVersionModule.currentVersion" placeholder="请输入当前版本号"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-radio v-model="radio" label="0">关闭审核</el-radio>
					<el-radio v-model="radio" label="1">打开审核</el-radio>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="editMiniVersion">修改</el-button>
                </el-form-item>
            </el-form>
		</el-col>
	</section>
</template>

<script>
	import { syncCustomer,syncCustomerCount,queryVersion,editVersion } from '../../api/ddlApi';

	export default {
		data() {
			//自定义验证
			let telValidator = (rule, value, callback) => {
				if( value ){
					if( !(/^[1][3,4,5,7,8][0-9]{9}$/.test( value )) ){
						callback( new Error( '手机号码不正确' ) );
					}else {
						callback();
					}
				}else {
					callback();
				}
			};
			return {
				radio: '0',
				headers:{},
				brokerModule:{
					brokerTel:'', //经纪人手机号码
					brokerModuleRules: {
						brokerTel: [
							{ type: 'number', message: '此处必须为手机号',trigger:'change'},
							{ validator: telValidator, trigger: 'blur'},
						]
					}
				},
				currentVersionModule:{
					currentVersion: '',//当前版本号
				}
			}
		},
		mounted() {
			let user = sessionStorage.getItem('user');
			if (user) {
				user = JSON.parse(user);
				let token = user.token || '';

				if(token){
					this.headers = {Authorization:'bearer '+token} ;
				}
			}
			this.queryMiniVersion();
		},
		methods: {
			//同步
			syncBtn: function () {
				let param = {
					telephone: this.brokerModule.brokerTel
				};
				syncCustomer(param).then((res) => {
					this.$message( res.msg );
				})
			},
			//查询同步数量
			syncCustomerCountBtn: function() {
				let param = {
					telephone: this.brokerModule.brokerTel
				};
				if(param.telephone) {
					syncCustomerCount(param).then((res) => {
						if(res.code == 0) {
							this.$message( res.data || '0' );
						}
					})
				}else{
					this.$message( '请输入经纪人手机号' );
				}
			},
			//获取版本号信息
			queryMiniVersion: function() {
				queryVersion().then(res => {
					if(res.code == 0) {
						this.currentVersionModule.currentVersion = res.data.currVersion;
						this.radio = String(res.data.flag);
					}else{
						this.$message( res.msg );
					}
				})
			},
			//修改版本号
			editMiniVersion: function(){
				 let params = {
				 	"currVersion" : this.currentVersionModule.currentVersion,
				 	"flag" : this.radio,
				  }
				 editVersion( params ).then( (res) => {
				 	if(res.code == 0) {
				 		this.$message( '修改成功' );
				 	}else{
				 		this.$message( res.msg );
				 	}
				 } )
			},
		}
	}
</script>

<style scoped>
	.tabLine tbody td{
		height: 50px;
	}
	.tabLine .cell{
		height: 40px;
		overflow: hidden;
		text-overflow: ellipsis;
		-webkit-line-clamp: 1;
		line-clamp: 1;
		-webkit-box-orient: vertical;
		-moz-box-orient: vertical;
		-ms-box-orient: vertical;
		box-orient: vertical;
		display: -webkit-box;
		line-height: 40px;
	}
	.tabLine  th>.cell{
		padding: 5px 0;
	}
	.divider{
		height: 1px;
		background-color: #dfe6ec;
        margin-bottom: 15px;
	}
    .reconLeve{
    }
    .btnBox{
        padding-bottom:30px;
    }
    .title{
    	margin-top: 120px;
    }
</style>
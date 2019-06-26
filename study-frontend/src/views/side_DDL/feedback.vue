<template>
	<section style="position: relative;">
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
            <el-button type="primary" @click="getExport"><i class="fa fa-search"></i> 导出</el-button>
		</el-col>


		<!--列表-->
		<el-table :data="loanUserList" highlight-current-row v-loading="listLoading" border style="width: 100%;" stripe class="tabLine2">
			<el-table-column prop="brokerName" label="经纪人姓名" align="center" width="200">
			</el-table-column>
			<el-table-column prop="brokerPhone" label="经纪人手机号" header-align="center" align="center" width="200">
			</el-table-column>
			<el-table-column label="反馈时间" header-align="center" align="center" width="200">
				<template slot-scope="scope">
					<span>{{ formatDate(scope.row.gmtCreate) }}</span>
				</template>
			</el-table-column>
            <el-table-column prop="suggestion" label="反馈意见" header-align="center" align="center">

            </el-table-column>
		</el-table>


		<!--工具条-->
		<el-col :span="24" class="toolbar">
			<el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size=size :total="total" style="float:right;">
			</el-pagination>
		</el-col>

	</section>
</template>

<script>
	import { formatDate } from '../../common/js/data';
	import {  queryPageList, updateMgm } from '../../api/api';
	import {  suggestionList,suggestionListExport } from '../../api/ddlApi';

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
				headers:{},
				/******分页数据******/
				total: 0,
				page: 1,
				size: 10,

				/******列表数据******/
				listLoading: false,
				loanUserList: [],

				exportLink:'',

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
			this.getLoanUserList();
		},
		methods: {
			handleCurrentChange(val) {
				this.page = val;
				this.getLoanUserList();
			},
			//查询数据
			getLoanUserList: function () {
				let params = {
					page: this.page,
					pageSize: this.size,
				};
				suggestionList( params ).then( res => {
					if( res.code == 0 ) {
                        this.loanUserList = res.data.rows;
					}else{
						this.$message( res.msg );
					}
				} )
			},
			//导出
			getExport: function () {
				suggestionListExport().then( (res) => {
					let url = window.URL.createObjectURL(new Blob([res], {type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8'}));
					let link = document.createElement('a');
					link.style.display = 'none';
					link.href = url;
					link.setAttribute('download', '意见反馈.xls');
					document.body.appendChild(link);
					link.click();
				} )
			},

			//转换时间
			formatDate: function ( time ) {
                if(time){
	                var date = new Date(time);
	                return formatDate(date, "yyyy/MM/dd hh:mm:ss");
                }
            },
		},
	}
</script>

<style scoped>
	.tabLine tbody td{
		height: 50px !important;
	}
	.tabLine .cell{
		height: auto !important;
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
        text-align:center;
    }
    .btnBox{
        padding-bottom:30px;
    }
</style>
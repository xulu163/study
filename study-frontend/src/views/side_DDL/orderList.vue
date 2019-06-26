<template>
    <section style="position: relative;">
        <!--工具条-->
        <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
            <el-form :inline="true" :model="statisticSearch" ref="statisticSearch">
                <el-form-item label="订单号：">
                    <el-input v-model="statisticSearch.orderNo" clearable placeholder="请输入订单号"></el-input>
                </el-form-item>
                <el-form-item label="客户姓名：">
                    <el-input v-model="statisticSearch.customerName" clearable placeholder="请输入客户姓名"></el-input>
                </el-form-item>
                <el-form-item label="购买人：">
                    <el-input v-model="statisticSearch.purchaser" clearable placeholder="请输入购买人姓名"></el-input>
                </el-form-item>
                <el-form-item label="状态：">
                    <el-select v-model="statisticSearch.orderStatus" clearable placeholder="请选择状态">
                        <el-option label="待支付" value="1"></el-option>
                        <el-option label="处理中" value="2"></el-option>
                        <el-option label="已完成" value="3"></el-option>
                        <el-option label="支付失败" value="4"></el-option>
                        <el-option label="已取消" value="5"></el-option>
                        <el-option label="已退款" value="6"></el-option>
                        <el-option label="申请退款" value="7"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="getLoanUserList"><i class="fa fa-search"></i> 搜索</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="reset()"><i class="fa fa-search"></i> 重置</el-button>
                </el-form-item>
                <!--啊 -->
            </el-form>
        </el-col>


        <!--列表-->
        <el-table :data="loanUserList" ref="multipleTable" @selection-change="handleSelectionChange"
                  highlight-current-row v-loading="listLoading" border style="width: 100%;" stripe>
            <el-table-column prop="orderNo" label="订单号" header-align="center" align="center" min-width="100">
            </el-table-column>
            <el-table-column prop="customerName" label="客户姓名" header-align="center" align="center">
            </el-table-column>
            <el-table-column prop="customerTelephone" label="手机号" header-align="center" align="center">
            </el-table-column>
            <el-table-column prop="purchaser" label="购买人" header-align="center" align="center">
            </el-table-column>
            <el-table-column prop="orderAmount" label="订单金额(元)" header-align="center" align="center"></el-table-column>
            <el-table-column prop="gmtCreate" label="创建时间" header-align="center" align="center"></el-table-column>
            <el-table-column prop="payTime" label="支付时间" header-align="center" align="center"></el-table-column>
            <el-table-column prop="dealNo" label="关联交易号" header-align="center" align="center"></el-table-column>
            <el-table-column prop="orderStatus" label="状态" header-align="center" align="center">
                <template slot-scope="scope">
                    <span v-if="scope.row.orderStatus == 1">待支付</span>
                    <span v-if="scope.row.orderStatus == 2">处理中</span>
                    <span v-if="scope.row.orderStatus == 4">支付失败</span>
                    <span v-if="scope.row.orderStatus == 5">已取消</span>
                    <span v-if="scope.row.orderStatus == 6">已退款</span>
                    <span v-if="scope.row.orderStatus == 7">申请退款</span>
                    <span v-if="scope.row.orderStatus == 3 && scope.row.isComment == true">已评价</span>
                    <span v-if="scope.row.orderStatus == 3 && scope.row.isComment == false">已完成</span>

                </template>
            </el-table-column>
            <el-table-column label="操作" header-align="center" align="center" min-width="100">
                <template slot-scope="scope">
                    <!--<el-button type="info" size="small" @click="preRefundBtn(scope.row)" v-if="scope.row.orderStatus == 7">退款审批-->
                    <!--</el-button>-->
                    <el-button type="info" size="small" @click="commentBtn(scope.row)"
                        v-if="scope.row.isComment == true">查看评价
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
        <!--工具条-->
        <el-col :span="24" class="toolbar">
            <el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size=size
                           :total="total" style="float:right;">
            </el-pagination>
        </el-col>

        <!--弹窗框-->
        <div id="modelAll">
            <!--编辑banner-->
            <el-dialog title="经纪人评价" custom-class="dialogBoxOrderList" :visible.sync="editBannerDialog" >
                <div class="font">{{commentLevel}}</div>
                <el-button type="primary" @click="editBannerDialog = false">确认</el-button>
            </el-dialog>
        </div>

    </section>
</template>

<script>
	import {
		formatDate
	} from '../../common/js/data';
	import {
		couponsExchangeListExport,
		getOrderList,
		preRefundToCpa
	} from '../../api/ddlApi';

	export default {
		data() {
			//自定义验证
			let telValidator = (rule, value, callback) => {
				if (value) {
					if (!(/^[1][3,4,5,7,8][0-9]{9}$/.test(value))) {
						callback(new Error('手机号码不正确'));
					} else {
						callback();
					}
				} else {
					callback();
				}
			};
			let startTimeValidator = (rule, value, callback) => {
				if (value) {
					this.start = new Date(value).getTime();
					if (this.statisticSearch.endTime) {
						this.end = new Date(this.statisticSearch.endTime).getTime();
						if (this.start > this.end) {
							this.statisticSearch.startTime = '';
							this.start = '';
							callback(new Error('起始时间不能大于截止时间'));
						}
					} else {
						callback();
					}
				} else {
					callback();
				}
			};
			let endTimeValidator = (rule, value, callback) => {
				if (value) {
					this.end = new Date(value).getTime();
					if (this.statisticSearch.startTime) {
						this.start = new Date(this.statisticSearch.startTime).getTime();
						console.log(this.start, 'this')
						if (this.end < this.start) {
							this.statisticSearch.endTime = '';
							this.end = '';
							callback(new Error('截止时间不能小于起始时间'));
						}
					} else {
						callback();
					}
				} else {
					callback();
				}
			};
			var checkValue = (rule, value, callback) => {
				let valueNum = /^[0-9]\d*$/;
				if (value == "") {
					return callback();
				}
				if (!valueNum.test(value)) {
					callback(new Error('请填写正确的数字格式'));
				} else {
					callback();
				}
			};
			return {
				headers: {},
				statisticSearch: {
					orderNo: '',
					orderStatus: '', //经纪人姓名
					customerName: '', //发放状态
					purchaser:'',
				},
				/******分页数据******/
				total: 0,
				page: 1,
				size: 10,

				/******列表数据******/
				listLoading: false,
				loanUserList: [],
				batchSelection: [], //批量发放
				toggleStatue: true,

				userTelephone: "",
				editBannerDialog: false,
				commentLevel: "",

			}
		},
		mounted() {
			let user = sessionStorage.getItem('user');
			if (user) {
				user = JSON.parse(user);
				let token = user.token || '';

				if (token) {
					this.headers = {
						Authorization: 'bearer ' + token
					};
				}
			}
			this.getLoanUserList();
		},
		created() {
			this.userTelephone = sessionStorage.getItem('userTelephone');
		},
		methods: {
			handleCurrentChange(val) {
				this.page = val;
				this.getLoanUserList();
			},
			//startChange
			startChange(event) {
				this.start = new Date(this.statisticSearch.startTime).getTime();
			},
			//endChange
			endChange(event) {
				this.end = new Date(this.statisticSearch.endTime).getTime();
			},

			//重置
			reset() {
				this.statisticSearch = {}
			},
			//查询数据
			getLoanUserList() {
				let params = {
					page: this.page,
					pageSize: this.size,
					orderStatus: this.statisticSearch.orderStatus,
					customerName: this.statisticSearch.customerName,
					telephone: this.userTelephone,
					orderNo: this.statisticSearch.orderNo,
					buyerName: this.statisticSearch.purchaser,
				};
				getOrderList(params).then(res => {
					if (res.code == 0) {
						if (res.data.rows) {
							console.log(res, 'getOrderList');
							this.total = parseInt(res.data.total);
							this.loanUserList = res.data.rows;
						} else {
							this.total = 0;
							this.loanUserList = [];
						}
					} else {
						this.$message(res.msg);
					}
				})
			},
			handleSelectionChange(val) {
				this.batchSelection = val;
			},

			//编辑按钮
			commentBtn(row) {
				this.editBannerDialog = true;
				this.commentLevel = row.commentLevel;
				if(this.commentLevel == 'A'){
					this.commentLevel = '好评';
                }else if(this.commentLevel == 'B'){
					this.commentLevel = '中评';
                }else if(this.commentLevel == 'C'){
					this.commentLevel = '差评';
                }
			},

			//退款审批
			preRefundBtn(row) {
				let params = {
					"orderId": row.customerId,
					"telephone": this.userTelephone
				};
				preRefundToCpa(params).then(res => {
					if (res.code == 0) {
						this.getLoanUserList();
						this.$message.success('退款成功');
					} else {
						this.$message.error(res.msg);
					}
				})
			},

			//转换时间
			formatDate: function (time) {
				if (time) {
					var date = new Date(time);
					return formatDate(date, "yyyy-MM-dd");
				}
			},
		},
	}
</script>

<style>
    .dialogBoxOrderList {
        width: 500px;
    }
    .dialogBoxOrderList .el-dialog__body{
        text-align: center;
    }
    .dialogBoxOrderList .font{
        padding:0 0 20px 0
    }
</style>

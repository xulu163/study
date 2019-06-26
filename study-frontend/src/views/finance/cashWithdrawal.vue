<template>
    <section style="position: relative;">
        <!--工具条-->
        <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
            <el-form :inline="true" :model="statisticSearch" ref="statisticSearch"
                     :rules="statisticSearch.statisticSearchRules">
                <el-form-item label="提现人：">
                    <el-input v-model="statisticSearch.customerName" clearable placeholder="请输入提现人"></el-input>
                </el-form-item>
                <el-form-item label="状态：">
                    <el-select v-model="statisticSearch.sellStatus" clearable placeholder="请选择状态">
                        <el-option label="已上架" value="1"></el-option>
                        <el-option label="未上架" value="2"></el-option>
                        <el-option label="已出售" value="3"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="getLoanUserList"><i class="fa fa-search"></i> 搜索</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="reset()"><i class="fa fa-search"></i> 重置</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="reset()"><i class="fa fa-search"></i> 导出</el-button>
                </el-form-item>
            </el-form>
        </el-col>


        <!--列表-->
        <el-table :data="loanUserList" ref="multipleTable" @selection-change="handleSelectionChange"
                  highlight-current-row v-loading="listLoading" border style="width: 100%;" stripe>
            <el-table-column prop="id" label="申请编号" header-align="center" align="center" min-width="100">
            </el-table-column>
            <el-table-column prop="customerName" label="提现金额（元）" header-align="center" align="center" min-width="120">
            </el-table-column>
            <el-table-column prop="customerTelephone" label="提现人" header-align="center" align="center">
            </el-table-column>
            <el-table-column prop="loanAmount" label="银行卡号" header-align="center" align="center">
            </el-table-column>
            <el-table-column prop="customerPrice" label="银行" header-align="center" align="center"></el-table-column>
            <el-table-column prop="sellStatus" label="状态" header-align="center" align="center">
                <template slot-scope="scope">
                    <span v-if="scope.row.sellStatus == 1">已上架</span>
                    <span v-if="scope.row.sellStatus == 2">未上架</span>
                    <span v-if="scope.row.sellStatus == 3">已出售</span>
                </template>
            </el-table-column>
            <el-table-column prop="gmtCreate" label="创建时间" header-align="center" align="center"></el-table-column>
            <el-table-column label="操作" header-align="center" align="center" min-width="120">
                <template slot-scope="scope">
                    <el-button type="primary" size="small" @click="editBtn(scope.row.id)">审核</el-button>
                    <el-button type="primary" size="small" @click="editBtn(scope.row.id)">上传付款凭证</el-button>
                </template>
            </el-table-column>
        </el-table>


        <!--弹窗框-->
        <div id="modelAll">
            <!--编辑banner-->
            <el-dialog title="基本信息" custom-class="dialogBox" :visible.sync="editBannerDialog" >
                <el-form :model="editOutputData" :inline="true" :rules="editOutputRules" ref="editOutputRules"
                         label-width="140px">
                    <el-form-item label="客户姓名：">
                        <el-input v-model="editOutputData.customerName" placeholder="请填写"
                                  size="small" :disabled="true"></el-input>
                    </el-form-item>

                    <el-form-item class="btnBox">
                        <el-button type="primary" @click="editBannerDialog = false">取消</el-button>
                        <el-button type="primary" @click="editSubmitForm('editOutputRules')">提交</el-button>
                    </el-form-item>
                </el-form>
            </el-dialog>
        </div>

    </section>
</template>

<script>
	import {Loading} from 'element-ui';
	import {
		formatDate
	} from '../../common/js/data';
	import {
		getCPACustomerList,
		batchUpperShelf,
		batchDownShelf,
		deleteCustomer,
		getCPACustomerDetail,
		editCPACustomerInfo,
		improtOcdcExcel
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
			var priceValidator = (rule, value, callback) => {
				if (value == "") {
					callback(new Error('请输入价格'));
				}
				if (value < 5) {
					callback(new Error('价格范围为5-99999'));
				} else {
					callback();
				}
			};
			var commissionRateValidator = (rule, value, callback) => {
				if (value == "") {
					callback(new Error('请输入分佣比例'));
				}
				if (value > 100) {
					callback(new Error('佣金比例范围0-100'));
				} else {
					callback();
				}
			};
			return {
				headers: {},
				// 上传文件的全屏loading
				uploadLoading: false,
				importData: {
					fileName: '',
					mchId: '',
					priceMethod: '1',
					url: '',
				},
				importDataRules: {
					mchId: [
						{required: true, message: '请输入商户号', trigger: 'blur'}
					],
					priceMethod: [
						{required: true, message: '请输入定价方式', trigger: 'blur'}
					],
					url: [
						{required: true, message: '请填写活动形式', trigger: 'blur'}
					],
				},
				statisticSearch: {
					startTime: '',
					endTime: '',
					sellStatus: '', //经纪人姓名
					customerName: '', //发放状态
					statisticSearchRules: {
						startTime: [{
							validator: startTimeValidator,
							trigger: 'blur'
						},],
						endTime: [{
							validator: endTimeValidator,
							trigger: 'blur'
						},],

					},
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
				editOutputData: {
					customerName: "",
					telephone: "",
					priceMethod: "",
					costPoint: "",
					commissionRate: "",
					customerSource: "",
					loanAmount: "",
					registerTime: "",
					gmtCreate: "",
					city: "",
					particleLoan: "",
					houseStatus: "",
					haveCar: "",
					haveSocialSecurity: "",
					haveAccumulationFund: "",
					haveInsurancePolicy: "",
					bankPaysWages: "",
					haveCreditCard: "",
				},
				editOutputRules: {
					priceMethod: [{
						required: true,
						message: '请选择商机类型',
						trigger: 'change'
					}],
					customerPrice: [{
						validator: priceValidator,
						trigger: 'blur'
					}],
					costPoint: [{
						required: true,
						message: '请填写所需积分',
						trigger: 'blur'
					}],
					commissionRate: [{
						validator: commissionRateValidator,
						trigger: 'blur'
					}],
				},
				editBannerDialog: true,
				detailId: "",
				priceMethodList: [{
					labels: "1",
					value: "CPA"
				}, {
					labels: "2",
					value: "CPS"
				}],

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
			importExcel(event) {
				if (this.uploadLoading) {
					return;
				}
				event.preventDefault();
				this.$refs.uploadEventFile.click();
			},
			fileChange(el) {
				el.preventDefault();//取消默认行为
				let uploadEventFile = this.$refs.uploadEventFile.value;
				let file = el.target.files[0];
				if (uploadEventFile == '') {
					this.$message('请择excel,再上传');
					this.$refs.uploadEventFile.value = null;
					return
				}
				if (this.importData.mchId == '') {
					this.$message('请输入商户号');
					this.$refs.uploadEventFile.value = null;
					return
				}
				if (this.importData.priceMethod == '1' && this.importData.url == '') {
					this.$message('请输入url');
					this.$refs.uploadEventFile.value = null;
					return
				}
				if (uploadEventFile.lastIndexOf(".xls") > 0 || uploadEventFile.lastIndexOf(".XLS") > 0) {
					let formData = new FormData();
					// 向 formData 对象中添加文件
					formData.append('uploadFile', file);
					formData.append('mchId', this.importData.mchId);
					formData.append('priceMethod', this.importData.priceMethod);
					formData.append('url', this.importData.url);
					this.uploadLoading = true;
					improtOcdcExcel(formData).then(res => {
						console.log(res, '导入res');
						if (res.code == 0) {
							this.$message({
								message: '导入成功',
								type: 'success'
							});
							this.getLoanUserList();
						} else {
							this.$message(res.msg);
						}
						this.$refs.uploadEventFile.value = null;
						this.uploadLoading = false;
					})
				} else {
					this.$message('只能上传excel文件');
					this.$refs.uploadEventFile.value = null;
				}
			},
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
					sellStatus: this.statisticSearch.sellStatus,
					customerName: this.statisticSearch.customerName,
					telephone: this.userTelephone,
				};
				getCPACustomerList(params).then(res => {
					if (res.code == 0) {
						if (res.data.rows) {
							console.log(res, 'getCPACustomerList');
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
			//批量发放
			batchShelfUpBtn() {
				if (this.batchSelection.length == 0) {
					this.$message("请先选择客户!");
				} else {
					let ids = [];
					this.batchSelection.forEach(function (v) {
						ids.push(v.id);
					});
					this.$confirm('确定上架吗？', '提示', {}).then(() => {
						let param = {
							customerIds: ids
						};
						batchUpperShelf(param).then((res) => {
							if (res.code == 0) {
								this.getLoanUserList();
								this.$message.success(res.data + '个商机上架成功');
							} else {
								this.$message.error(res.msg);
							}
						});
					});
				}
			},
			//批量发放
			batchShelfDownBtn() {
				if (this.batchSelection.length == 0) {
					this.$message("请先选择客户!");
				} else {
					let ids = [];
					this.batchSelection.forEach(function (v) {
						ids.push(v.id);
					});
					this.$confirm('确定下架吗？', '提示', {}).then(() => {
						let param = {
							customerIds: ids
						};
						batchDownShelf(param).then((res) => {
							if (res.code == 0) {
								this.getLoanUserList();
								this.$message.success(res.data + '个商机下架成功');
							} else {
								this.$message.error(res.msg);
							}
						});
					});
				}
			},
			//批量发放
			batchDeleteBtn() {
				if (this.batchSelection.length == 0) {
					this.$message("请先选择客户!");
				} else {
					let ids = [];
					this.batchSelection.forEach(function (v) {
						ids.push(v.id);
					});
					this.$confirm('确定删除吗？', '提示', {}).then(() => {
						let param = {
							customerIds: ids
						};
						deleteCustomer(param).then((res) => {
							if (res.code == 0) {
								this.getLoanUserList();
								this.$message.success(res.data + '个商机删除成功');
							} else {
								this.$message.error(res.msg);
							}
						});
					});
				}
			},
			//上架按钮
			shelfUpBtn(id) {
				this.$confirm('确定上架吗？', '提示', {}).then(() => {
					let param = {
						customerIds: [id]
					};
					batchUpperShelf(param).then((res) => {
						if (res.code == 0) {
							this.getLoanUserList();
							this.$message.success('上架成功');
						} else {
							this.$message.error(res.msg);
						}
					});
				});
			},
			//下架按钮
			shelfDownBtn(id) {
				this.$confirm('确定下架吗？', '提示', {}).then(() => {
					let param = {
						customerIds: [id]
					};
					batchDownShelf(param).then((res) => {
						if (res.code == 0) {
							this.getLoanUserList();
							this.$message.success('下架成功');
						} else {
							this.$message.error(res.msg);
						}
					});
				});
			},
			//删除按钮
			deleteBtn(id) {
				this.$confirm('确定删除吗？', '提示', {}).then(() => {
					let param = {
						customerIds: [id]
					};
					deleteCustomer(param).then((res) => {
						if (res.code == 0) {
							this.getLoanUserList();
							this.$message.success('删除成功');
						} else {
							this.$message.error(res.msg);
						}
					});
				});
			},
			//编辑按钮
			editBtn(id) {
				this.editBannerDialog = true;
				this.detailId = id;
				this.getDetail();
			},
			//查询客户详情
			getDetail() {
				let params = {
					id: this.detailId,
				};
				getCPACustomerDetail(params).then(res => {
					if (res.code == 0) {
						this.editOutputData = res.data;
						if (this.editOutputData.priceMethod == "1") {
							this.editOutputData.priceMethod = "CPA"
						}
						if (this.editOutputData.priceMethod == "2") {
							this.editOutputData.priceMethod = "CPS"
						}
					} else {
						this.$message(res.msg);
					}
				})
			},
			//编辑保存按钮
			editSubmitForm(formName) {
				this.$refs[formName].validate((valid) => {
					if (valid) {
						if (this.editOutputData.priceMethod == "CPA") {
							this.editOutputData.priceMethod = "1"
						}
						if (this.editOutputData.priceMethod == "CPS") {
							this.editOutputData.priceMethod = "2"
						}
						let param = {
							"commissionRate": this.editOutputData.commissionRate,
							"costPoint": this.editOutputData.costPoint,
							"customerPrice": this.editOutputData.customerPrice,
							"id": this.detailId,
							"priceMethod": this.editOutputData.priceMethod
						};
						editCPACustomerInfo(param).then((res) => {
							if (res.code == 0) {
								this.editBannerDialog = false;
								this.getLoanUserList();
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
    .dialogBox {
        width: 810px;
    }

    .dialogBox .el-form-item {
        width: 47%
    }

    .dialogBox .el-form-item .el-input {
        display: inline-block;
        width: 200px;
    }

    .dialogBox .title {
        margin: 10px 0;
        font-size: 16px;
        font-weight: bold;
        color: #000;
    }

    .dialogBox .btnBox {
        width: 100%;
        display: block;
        text-align: center;
    }

</style>
<style scoped>

</style>

<template>
    <section style="position: relative;">
        <!--工具条-->
        <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
            <el-form :inline="true" :model="statisticSearch" ref="statisticSearch">
                <el-form-item label="渠道名称：">
                    <el-input v-model="statisticSearch.channelName" clearable placeholder="请输入渠道名称"></el-input>
                </el-form-item>
                <el-form-item label="类型：">
                    <el-select v-model="statisticSearch.type" clearable placeholder="请选择类型">
                        <el-option label="H5" value="1"></el-option>
                        <el-option label="API" value="2"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="getLoanUserList"><i class="fa fa-search"></i> 搜索</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="reset()"><i class="fa fa-search"></i> 重置</el-button>
                </el-form-item>
            </el-form>
        </el-col>


        <!--列表-->
        <el-table :data="loanUserList" ref="multipleTable" @selection-change="handleSelectionChange"
                  highlight-current-row v-loading="listLoading" border style="width: 100%;" stripe>
            <el-table-column prop="channelName" label="渠道名称" header-align="center" align="center" min-width="60">
            </el-table-column>
            <el-table-column prop="type" label="类型" header-align="center" align="center">
                <template slot-scope="scope">
                    <span v-if="scope.row.type == '1'">H5</span>
                    <span v-if="scope.row.type == '2'">API</span>
                </template>
            </el-table-column>
            <el-table-column prop="url" label="URL" header-align="center" align="center">
            </el-table-column>
            <el-table-column prop="price" label="客户单价(元)" header-align="center" align="center">
            </el-table-column>
            <el-table-column prop="gmtUpdate" label="最后更新时间" header-align="center" align="center"></el-table-column>
            <el-table-column label="操作" header-align="center" align="center" min-width="120">
                <template slot-scope="scope">
                    <el-button type="info" size="small" @click="editBtn(scope.row)">编辑</el-button>
                    <el-button type="danger" size="small" @click="deleteBtn(scope.row)">删除</el-button>
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
            <el-dialog title="编辑客户单价" custom-class="dialogBoxChannelList" :visible.sync="editBannerDialog">
                <el-form :model="editOutputData" :inline="true" :rules="editOutputRules" ref="editOutputRules"
                         label-width="140px">
                    <el-form-item label="客户单价（元）：" prop="price">
                        <el-input v-model="editOutputData.price" placeholder="请填写" :maxlength="5"
                                  size="small"></el-input>
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
	import {
		formatDate
	} from '../../common/js/data';
	import {
		getChannelList,
		editChannelPrice,
		deleteChannel,
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
					type: '',
					channelName: '',
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
					price: "",
				},
				editOutputRules: {
					price: [{
						required: true,
						message: '请填写客户单价',
						trigger: 'blur'
					}],
				},
				editBannerDialog: false,
				detailId: "",

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
					type: this.statisticSearch.type,
					channelName: this.statisticSearch.channelName,
					telephone: this.userTelephone,
				};
				getChannelList(params).then(res => {
					if (res.code == 0) {
						if (res.data.rows) {
							console.log(res, 'getChannelList');
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
			//删除按钮
			deleteBtn(row) {
				this.$confirm('确定删除吗？', '提示', {}).then(() => {
					let param = {
						"channelId": row.id,
						"price": row.price,
						"telephone": this.userTelephone
					};
					deleteChannel(param).then((res) => {
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
			editBtn(row) {
				this.editBannerDialog = true;
				this.detailId = row.id;
				this.editOutputData.price = row.price;
			},
			//编辑保存按钮
			editSubmitForm(formName) {
				this.$refs[formName].validate((valid) => {
					if (valid) {
						let param = {
							"channelId": this.detailId,
							"price": this.editOutputData.price,
							"telephone": this.userTelephone
						};
						editChannelPrice(param).then((res) => {
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
    .dialogBoxChannelList {
        width:500px;
    }

    .dialogBoxChannelList .btnBox {
        width: 100%;
        display: block;
        text-align: center;
    }

</style>
<style scoped>

</style>

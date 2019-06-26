<template>
    <section style="position: relative;">
        <!--工具条-->
        <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
            <el-form :inline="true" :model="searchParams" ref="statisticSearch">
                <el-form-item label="学生ID:">
                    <el-input v-model="searchParams.stuId" clearable placeholder="请输入学生ID"></el-input>
                </el-form-item>
                <el-form-item label="学生姓名:">
                    <el-input v-model="searchParams.stuName" clearable placeholder="请输入学生姓名"></el-input>
                </el-form-item>
                <el-form-item label="学生姓名:">
                    <el-input v-model="searchParams.stuName" clearable placeholder="请输入学生姓名"></el-input>
                </el-form-item>
                <el-form-item label="性别：">
                    <el-select v-model="searchParams.sex" clearable placeholder="请选择性别">
                        <el-option label="男" value="1"></el-option>
                        <el-option label="女" value="2"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="年龄:">
                  <el-input v-model="searchParams.ageStart" clearable style="width:50px"></el-input>
                </el-form-item>
                <el-form-item label="-">
                  <el-input v-model="searchParams.ageEnd" clearable style="width:50px"></el-input>
                </el-form-item>
                <el-form-item label="学校:">
                  <el-select v-model="searchParams.sex" clearable placeholder="请选择性别">
                      <el-option label="男" value="1"></el-option>
                      <el-option label="女" value="2"></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="班级:">
                  <el-select v-model="searchParams.sex" clearable placeholder="请选择性别">
                      <el-option label="男" value="1"></el-option>
                      <el-option label="女" value="2"></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="籍贯:">
                  <el-select v-model="searchParams.sex" clearable placeholder="请选择性别">
                      <el-option label="男" value="1"></el-option>
                      <el-option label="女" value="2"></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="家庭住址:">
                    <el-input v-model="searchParams.address" clearable placeholder="请输入家庭住址"></el-input>
                </el-form-item>
                <el-form-item label="联系电话:">
                    <el-input v-model="searchParams.tel" clearable placeholder="请输入联系电话"></el-input>
                </el-form-item>
                <el-form-item label="教师:">
                    <el-input v-model="searchParams.teacherName" clearable placeholder="请输入教师"></el-input>
                </el-form-item>
                <el-form-item label="平均分数:">
                  <el-input v-model="searchParams.ageStart" clearable style="width:50px"></el-input>
                </el-form-item>
                <el-form-item label="-">
                  <el-input v-model="searchParams.ageEnd" clearable style="width:50px"></el-input>
                </el-form-item>
                <el-form-item label="总分数:">
                  <el-input v-model="searchParams.ageStart" clearable style="width:50px"></el-input>
                </el-form-item>
                <el-form-item label="-">
                  <el-input v-model="searchParams.ageEnd" clearable style="width:50px"></el-input>
                </el-form-item>
                <el-form-item label="课程数:">
                  <el-input v-model="searchParams.ageStart" clearable style="width:50px"></el-input>
                </el-form-item>
                <el-form-item label="-">
                  <el-input v-model="searchParams.ageEnd" clearable style="width:50px"></el-input>
                </el-form-item>
                <el-form-item label="入学日期" prop='startTime'>
                    <el-date-picker type="date" v-model='searchParams.startTime' placeholder="年/月/日" @change="startChange"></el-date-picker>&nbsp;&nbsp;&nbsp;--
                </el-form-item>
                <el-form-item prop='endTime'>
                  <el-date-picker type="date" format="yyyy 年 MM 月 dd 日" value-format="yyyy-MM-dd" v-model='searchParams.endTime' placeholder="年/月/日" @change="endChange"></el-date-picker>
                </el-form-item>
                <el-form-item label="创建时间" prop='startTime'>
                    <el-date-picker type="date" v-model='searchParams.startTime' placeholder="年/月/日" @change="startChange"></el-date-picker>&nbsp;&nbsp;&nbsp;--
                </el-form-item>
                <el-form-item prop='endTime'>
                    <el-date-picker type="date" format="yyyy 年 MM 月 dd 日" value-format="yyyy-MM-dd" v-model='searchParams.endTime' placeholder="年/月/日" @change="endChange"></el-date-picker>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="getLoanUserList"><i class="fa fa-search"></i> 搜索</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="reset()"><i class="fa fa-search"></i> 重置</el-button>
                </el-form-item>

                <el-button type="primary" class="importBtn" @click="add()">添加</el-button>
            </el-form>
        </el-col>



        <!--列表-->
        <el-table :data="loanUserList" ref="multipleTable" @selection-change="handleSelectionChange" highlight-current-row v-loading="listLoading" border style="width: 100%;" stripe>
            <el-table-column type="selection" label="全选" header-align="center" align="center" min-width="70"></el-table-column>
            <el-table-column prop="id" label="ID" header-align="center" align="center" min-width="60"></el-table-column>
            <el-table-column prop="customerName" label="学生姓名" header-align="center" align="center"></el-table-column>
            <el-table-column prop="customerTelephone" label="性别" header-align="center" align="center"></el-table-column>
            <el-table-column prop="loanAmount" label="年龄" header-align="center" align="center"></el-table-column>
            <el-table-column prop="customerPrice" label="学校" header-align="center" align="center"></el-table-column>
            <el-table-column prop="customerSource" label="班级" header-align="center" align="center"></el-table-column>
            <el-table-column prop="customerSource" label="籍贯" header-align="center" align="center"></el-table-column>
            <el-table-column prop="customerSource" label="家庭住址" header-align="center" align="center"></el-table-column>
            <el-table-column prop="customerSource" label="联系电话" header-align="center" align="center"></el-table-column>
            <el-table-column prop="customerSource" label="平均分数" header-align="center" align="center"></el-table-column>
            <el-table-column prop="customerSource" label="总分数" header-align="center" align="center"></el-table-column>
            <el-table-column prop="customerSource" label="课程数" header-align="center" align="center"></el-table-column>
            <el-table-column prop="customerSource" label="入学日期" header-align="center" align="center"></el-table-column>
            <el-table-column prop="customerSource" label="教师" header-align="center" align="center"></el-table-column>
            <el-table-column prop="gmtCreate" label="创建时间" header-align="center" align="center"></el-table-column>
            <el-table-column label="操作" header-align="center" align="center" min-width="120">
                <template scope="scope">
                    <el-button type="primary" size="small" @click="editBtn(scope.row.id)" v-if="scope.row.sellStatus == 2">编辑</el-button>
                    <el-button type="info" size="small" @click="shelfUpBtn(scope.row.id)"
                               v-if="scope.row.sellStatus == 2">上架
                    </el-button>
                    <el-button type="info" size="small" @click="shelfDownBtn(scope.row.id)"
                               v-if="scope.row.sellStatus == 1">下架
                    </el-button>
                    <el-button type="danger" size="small" @click="deleteBtn(scope.row.id)" v-if="scope.row.sellStatus == 2">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <!--工具条-->
        <el-col :span="24" class="toolbar">
            <el-button @click="batchShelfUpBtn(batchSelection)"><i class="el-icon-fa-sitemap"></i>批量上架</el-button>
            <el-button @click="batchShelfDownBtn(batchSelection)"><i class="el-icon-fa-sitemap"></i>批量下架</el-button>
            <el-button @click="batchDeleteBtn(batchSelection)"><i class="el-icon-fa-sitemap"></i>批量删除</el-button>
            <el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size=size  :total="total" style="float:right;"> </el-pagination>
        </el-col>

        <!--弹窗框-->
        <div id="modelAll">
            <!--编辑banner-->
            <el-dialog title="基本信息" custom-class="dialogBox" :visible.sync="editBannerDialog" width="80%">
                <el-form :model="editOutputData" :inline="true" :rules="editOutputRules" ref="editOutputRules" label-width="140px">
                    <el-form-item label="客户姓名：">
                        <el-input v-model="editOutputData.customerName" placeholder="请填写"
                                  size="small" :disabled="true"></el-input>
                    </el-form-item>
                    <el-form-item label="手机号：">
                        <el-input v-model="editOutputData.telephone" placeholder="请填写" size="small"
                                  :disabled="true"></el-input>
                    </el-form-item>

                    
                    <el-form-item label="价格：" v-if="editOutputData.priceMethod == 'CPA'" prop="customerPrice">
                        <el-input v-model="editOutputData.customerPrice" placeholder="请填写" :maxlength="5"
                                  size="small" clearable></el-input>
                    </el-form-item>
                    <el-form-item label="所需积分：" v-if="editOutputData.priceMethod == 'CPS'" prop="costPoint">
                        <el-input v-model="editOutputData.costPoint" placeholder="请填写" :maxlength="5"
                                  size="small" clearable></el-input>
                    </el-form-item>
                    <el-form-item label="分佣比例（%）：" v-if="editOutputData.priceMethod == 'CPS'" prop="commissionRate">
                        <el-input v-model="editOutputData.commissionRate" placeholder="请填写" :maxlength="3"
                                  size="small" clearable></el-input>
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
	import { Loading } from 'element-ui';
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
  import {
		studentList,
    clazzList,
    courseList,
    schoolList

	} from '../../api/api';
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
        searchParams:{
          address:"" ,//地址 ,
          ageEnd:"" ,//年龄范围结束 ,
          ageStart: "", //年龄范围起始 ,
          avgScoreEnd: "", //平均分结束 ,
          avgScoreStart: "", //平均分起始 ,
          clazzId:"", // 班级id ,
          createTimeEnd:"", // 创建时间结束 ,
          createTimeStart: "", //创建时间起始 ,
          entryTimeEnd: "" ,//入学时间结束 ,
          entryTimeStart: "", //入学时间起始 ,
          orderColumn: "" ,//排序字段（age,avg_score,total_score,total_course,entry_time,create_time） ,
          orderIndex: "" ,//排序方式（倒序desc，asc升序） ,
          page: "" ,//页码 ,
          placeNative: "", //籍贯 ,
          schoolId: "" ,//学校id ,
          sex: "" ,//学生性别（1男，2女） ,
          size: "" ,//每页条数 ,
          stuId: "" ,//学生id ,
          stuName: "" ,//学生姓名 ,
          teacherId: "", //老师id（不要传） ,
          teacherName: "", //老师姓名 ,
          tel: "" ,//手机号码 ,
          totalCourseEnd: "" ,//课程数结束 ,
          totalCourseStart: "", //课程数起始 ,
          totalScoreEnd: "" ,//总分结束 ,
          totalScoreStart: "" //总分起始
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
				editBannerDialog: false,



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
			add(){
        this.editBannerDialog= true
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
          "address": "",
          "ageEnd": 0,
          "ageStart": 0,
          "avgScoreEnd": 0,
          "avgScoreStart": 0,
          "clazzId": 0,
          "createTimeEnd": "",
          "createTimeStart": "",
          "entryTimeEnd": "",
          "entryTimeStart": "",
          "orderColumn": "",
          "orderIndex": "",
          "page": 0,
          "placeNative": "",
          "schoolId": 0,
          "sex": 0,
          "size": 0,
          "stuId": "",
          "stuName": "",
          "teacherId": "",
          "teacherName": "",
          "tel": "",
          "totalCourseEnd": 0,
          "totalCourseStart": 0,
          "totalScoreEnd": 0,
          "totalScoreStart": 0
        }
				studentList(params).then(res => {
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
      getClazzList(){
        let params = {};
        studentList(params).then(res => {
          console.log(res);
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
								this.$message.success(res.data+'个商机上架成功');
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
								this.$message.success(res.data+'个商机下架成功');
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
								this.$message.success(res.data+'个商机删除成功');
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

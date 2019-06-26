<template>
<section style='position:relative'>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
        <el-form :inline="true" :model="statisticSearch" ref="statisticSearch" :rules="statisticSearch.statisticSearchRules">
            <el-form-item label="客源经纪人姓名：">
                <el-input placeholder="请输入经纪人姓名" v-model='statisticSearch.brokerName'></el-input>
            </el-form-item>
            <el-form-item label="所在城市：">
                <el-input placeholder="请输入经纪人所在城市" v-model='statisticSearch.city'></el-input>
            </el-form-item>
            <el-form-item label="报名日期" prop='startTime'>
                <el-date-picker type="date" v-model='statisticSearch.startTime' placeholder="年/月/日" @change="startChange"></el-date-picker>&nbsp;&nbsp;&nbsp;--
            </el-form-item <el-form-item prop='endTime'>
            <el-date-picker type="date" format="yyyy 年 MM 月 dd 日" value-format="yyyy-MM-dd" v-model='statisticSearch.endTime' placeholder="年/月/日" @change="endChange"></el-date-picker>
            </el-form-item>
            <el-form-item label="分配状态：">
                <el-select clearable placeholder="分配状态--全部" v-model="statisticSearch.billStatus">
                    <el-option label="分配状态--分配" value="0"></el-option>
                    <el-option label="分配状态--暂停" value="1"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click='search'><i class="fa fa-search"></i> 搜索</el-button>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click='exportdoc'><i class="fa fa-search"></i> 导出</el-button>
            </el-form-item>
        </el-form>
    </el-col>

    <el-table ref="multipleTable" :data='formData' highlight-current-row border style="width: 100%;" stripe class="tabLine">
        <el-table-column label="经纪人姓名" prop='userName' header-align="center" align="center">
        </el-table-column>
        <el-table-column label="所在城市" prop='city' header-align="center" align="center">
        </el-table-column>
        <el-table-column label="活动名称" prop='activityName' header-align="center" align="center">
        </el-table-column>
        <el-table-column label="报名日期" prop='applyDate' header-align="center" align="center" min-width="120">
        </el-table-column>
        <el-table-column label="已分配客源数" prop='alreadyAllocateCount' header-align="center" align="center">
        </el-table-column>
        <el-table-column label="已抢客源数" prop='alreadyRobCount' header-align="center" align="center">
        </el-table-column>
        <el-table-column label="成功数" prop='successCount' header-align="center" align="center">
        </el-table-column>
        <el-table-column label="失败数" prop='failCount' header-align="center" align="center">
        </el-table-column>
        <el-table-column label="已用名额/总名额" prop='alreadyQuota' header-align="center" align="center" min-width="100">
            <template slot-scope="scope">
                    <span  class='midimg'>
                        {{scope.row.alreadyQuota+'/'+scope.row.totalQuota}}
                    </span>
            </template>
        </el-table-column>
        <el-table-column label="当前分配数" prop='currentAllocateCount' header-align="center" align="center">
        </el-table-column>
        <el-table-column label="成功率" prop='successAllocateRate' header-align="center" align="center">
            <template slot-scope="scope">
                <span>{{scope.row.successAllocateRate}} %</span>
            </template>
        </el-table-column>
        <el-table-column label="状态" prop='allocateStatus' header-align="center" align="center">
            <template slot-scope="scope">
                    <span  class='midimg'>
                        {{scope.row.allocateStatus==0? '分配':'暂停'}}
                    </span>
                </template>
        </el-table-column>
        <el-table-column label="操作" header-align="center" align="center" min-width="180">
            <template slot-scope="scope">
                <el-button type="primary" size="small" @click='editCustomer(scope)'><span v-if="scope.row.allocateStatus != 1">暂停</span><span v-if="scope.row.allocateStatus == 1">分配</span></el-button>
                <el-button type="primary" size="small" @click="totalBtn(scope.row)">总名额调整</el-button>
                    <!--<span  class='midimg'>-->
                        <!--<img :src="scope.row.allocateStatus == 1 ? '../../static/play.png' : '../../static/cause.png'" width='26px' @click='editCustomer(scope)'>-->
                         <!--<img src="../../assets/detail.png" width='26px' @click='detailDialog(scope)'>-->
                    <!--</span>-->
                </template>
        </el-table-column>
    </el-table>

    <el-col :span="24" class="toolbar">
        <el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size=size :total="total" style="float:right;">
        </el-pagination>
    </el-col>

    <el-dialog title="总名额调整" v-model="dialogVisible" custom-class="dialogBoxBackstage">
        <el-form :inline="true" :model="totalForm" :rules="totalQuotaRules" ref="totalForm">
            <el-col align="center">
                <el-form-item label="总名额：" prop="totalQuota">
                    <el-input v-model="totalForm.totalQuota" maxlength="9"></el-input>
                </el-form-item>
            </el-col>
            <el-col align="center">
                <el-form-item>
                    <el-button @click="resetForm('totalForm')">取消</el-button>
                    <el-button type="primary" @click="totalQuotaBtn('totalForm')">确认</el-button>
                </el-form-item>
            </el-col>
        </el-form>
    </el-dialog>
</section>
</template>

<script>
import {
    activityApplyList,
    pauseActivityApplyBroker,
    allocationActivityApplyBroker,
    activityApplyListExport,
	editActivityTotalQuota
} from '../../api/ddlApi';
import {
    formatDate
} from '../../common/js/data';
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
export default {
    created() {
        this.userTelephone = sessionStorage.getItem('userTelephone');
        this.getDataList();
    },
    data() {
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
        return {
            statisticSearch: {
                statisticSearchRules: {
                    startTime: [{
                        validator: startTimeValidator,
                        trigger: 'blur'
                    }, ],
                    endTime: [{
                        validator: endTimeValidator,
                        trigger: 'blur'
                    }, ]
                },
                endTime: '', //截止时间
                startTime: '', //开始时间
                billStatus: '', //分配状态(0=分配，1=暂停)
                brokerName: '', //经纪人姓名
                city:'',
            },
            start: '',
            end: '',
            userTelephone: '',
            formData: [],
            page: 1,
            size: 10,
            total: 0,
            dialogVisible: false,
            detailScope: {},
            imgIcon: '',
            imgIconcause: '',
	        totalForm:{
		        totalQuota:'',
            },
	        totalId:'',
	        alreadyQuotaNum:'',
	        totalQuotaRules:{
		        totalQuota: [{
			        validator: checkValue,
			        trigger: 'blur'
		        }],
            }
        }
    },
    methods: {
        startChange() {
            this.start = this.statisticSearch.startTime;
            console.log(this.start);
        },
        endChange() {
            this.end = this.statisticSearch.endTime;
        },
        getDataList() {
            let _this = this;
            let params = {
                allocateStatus: _this.statisticSearch.billStatus,
                applyEndTime: _this.formatDate(this.start),
                applyStartTime: _this.formatDate(this.end),
                brokerName: _this.statisticSearch.brokerName,
                city: _this.statisticSearch.city,
                page: _this.page,
                pageSize: _this.size,
                telephone: _this.userTelephone
            }
            activityApplyList(params).then(res => {
                _this.formData = res.data.rows;
                _this.total = res.data.total;
                _this.size = res.data.size;
            })
        },
	    totalBtn(scope){
		    this.dialogVisible = true;
		    this.totalForm.totalQuota = scope.totalQuota;
		    this.totalId = scope.activityApplyId;
		    this.alreadyQuotaNum = scope.alreadyQuota;
	    },
	    totalQuotaBtn(formName) {
		    this.$refs[formName].validate((valid) => {
			    if (valid) {
			    	if(this.totalForm.totalQuota < this.alreadyQuotaNum){
					    this.$message("总名额不可小于当前已用名额");
					    return false
                    }
				    let params = {
					    id: this.totalId,
					    num: this.totalForm.totalQuota,
				    }
				    editActivityTotalQuota(params).then(res => {
					    if(res.code == 0){
						    this.$message.success('修改成功');
						    this.dialogVisible = false;
						    this.getDataList();
					    }else{
						    this.$message.error(res.msg);
					    }
				    })
			    } else {
				    console.log('error submit!!');
				    return false;
			    }
		    });
	    },
	    resetForm(formName) {
		    this.dialogVisible = false;
		    this.$refs[formName].resetFields();
	    },
        // totalQuotaBtn(){
		 //    let params = {
			//     id: this.totalId,
			//     num: this.totalQuota,
		 //    }
		 //    editActivityTotalQuota(params).then(res => {
		 //    	if(res.code == 0){
		 //    		console.log(res,'dd')
			// 	    this.$message.success(res.msg);
			// 	    this.dialogVisible = false;
			// 	    this.getDataList();
			//     }else{
			// 	    this.$message.error(res.msg);
			//     }
		 //    })
        // },
        search() {
            this.getDataList();
        },
        handleCurrentChange(val) {
            this.page = val;
            this.getDataList();
        },
        //转换时间
        formatDate: function(time) {
            if (time) {
                var date = new Date(time);
                return formatDate(date, "yyyy-MM-dd");
            }
        },
        editCustomer(scope) {
            this.detailScope = scope.row;
            let _this = this;
            let params = {
                activityApplyId: this.detailScope.activityApplyId,
                telephone: this.userTelephone
            }
            if (this.detailScope.allocateStatus) {
                allocationActivityApplyBroker(params).then(res => {
                	if(res.code == 0){
		                _this.$message.success("状态修改成功");
		                _this.getDataList();
                    }else{
		                _this.$message(res.msg);
	                }
                })
            } else {
                pauseActivityApplyBroker(params).then(res => {
                	if(res.code == 0){
		                _this.$message.success("状态修改成功");
		                _this.getDataList();
                    }else{
		                _this.$message(res.msg);
                    }
                })
            }
        },
        exportdoc() {
            let _this = this;
            let params = {
                allocateStatus: _this.statisticSearch.billStatus,
                applyEndTime: _this.formatDate(this.start),
                applyStartTime: _this.formatDate(this.end),
                brokerName: _this.statisticSearch.brokerName,
	            city: _this.statisticSearch.city,
                page: _this.page,
                pageSize: _this.size,
                telephone: _this.userTelephone
            }
            activityApplyListExport(params).then(res => {
                let url = window.URL.createObjectURL(new Blob([res], {
                    type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8'
                }));
                let link = document.createElement('a');
                link.style.display = 'none';
                link.href = url;
                link.setAttribute('download', '活动报名经纪人.xlsx');
                document.body.appendChild(link);
                link.click();
            })
        }
    }
}
</script>
<style>
    .dialogBoxBackstage{
        width: 500px;
    }
</style>
<style lang="css" scoped>
.midimg{
    display: block;
    display: flex;
    flex-direction: row;
    justify-content: space-around;
    align-items: center;
    height: 100%;

}
</style>

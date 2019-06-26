<template>
<section style="position: relative;">
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
        <el-form :inline="true" :model="posterSearch">
            <el-form-item label="DD链交易序号：">
                <el-input v-model.number="posterSearch.dealTransactionsId" placeholder="请输入交易序号"></el-input>
            </el-form-item>
            <el-form-item label="客源经纪人姓名：">
                <el-input v-model.number="posterSearch.icName" placeholder="请输入经纪人姓名"></el-input>
            </el-form-item>
            <el-form-item label="CRM交易编号：">
                <el-input v-model.number="posterSearch.loanCode" placeholder="请输入交易编号"></el-input>
            </el-form-item>
            <el-form-item label="积分状态：">
                <el-select v-model="posterSearch.dealStatus" clearable placeholder="请选择">
                    <el-option label="冻结" value="9"></el-option>
                    <el-option label="解冻" value="10"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="getLoanUserList" style="margin-left: 30px;"><i class="fa fa-search"></i> 搜索</el-button>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="getExport"><i class="fa fa-search"></i> 导出</el-button>
            </el-form-item>
        </el-form>
    </el-col>


    <!--列表-->
    <el-table :data="loanUserList" highlight-current-row v-loading="listLoading" border style="width: 100%;" stripe class="tabLine">
        <el-table-column prop="dealTransactionsId" label="DD链交易序号" align="center" width="250">
        </el-table-column>
        <el-table-column prop="icName" label="客源经纪人姓名" header-align="center" align="center">
        </el-table-column>
        <el-table-column prop="telephone" label="客源经纪人手机号" header-align="center" align="center">
        </el-table-column>
        <el-table-column prop="loanAmount" label="放款金额(万)" header-align="center" align="center">
        </el-table-column>
        <el-table-column prop="commissionAmount" label="收佣(元)" header-align="center" align="center">
        </el-table-column>
        <el-table-column prop="freezingIntegral" label="交易获得积分" header-align="center" align="center">
        </el-table-column>
        <el-table-column label="积分状态" header-align="center" align="center">
            <template slot-scope="scope">
					<span v-if="scope.row.dealStatus == 9">冻结</span>
					<span v-if="scope.row.dealStatus == 10">解冻</span>
				</template>
        </el-table-column>
        <el-table-column prop="loanCode" label="CRM交易编号" header-align="center" align="center">
        </el-table-column>
        <el-table-column label="积分解冻" header-align="center" align="center" min-width="120">
            <template slot-scope="scope">
					<el-button type="info" size="small" v-if="scope.row.dealStatus == 10" disabled>解冻</el-button>
					<el-button type="info" size="small"  v-if="scope.row.dealStatus == 10" disabled>修改</el-button>
					<el-button type="primary" size="small" @click="thawBtn(scope.row.dealId)" v-if="scope.row.dealStatus == 9">解冻</el-button>
					<el-button type="danger" size="small" @click="editBtn(scope.row.freezingIntegral,scope.row.dealId)" v-if="scope.row.dealStatus == 9">修改</el-button>
				</template>
        </el-table-column>
    </el-table>


    <!--工具条-->
    <el-col :span="24" class="toolbar">
        <el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size=size :total="total" style="float:right;">
        </el-pagination>
    </el-col>

    <!--操作弹出框-->
    <el-dialog title="" width="80%" v-model="editDialog" class="reconLeve">
        <el-form :inline="true" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
            <el-col :offset="4">
                <el-form-item label="原积分：">{{ruleForm.integral}} 分</el-form-item>
            </el-col>
            <el-col :offset="4">
                <el-form-item label="修改积分：" prop="integralIng">
                    <el-input @input="reviseIntegral" type="number" v-model="ruleForm.integralIng" placeholder="增加填正数，减少填负数"></el-input>
                </el-form-item>
            </el-col>
            <el-col :offset="4">
                <el-form-item label="修改后积分：">
                    {{ruleForm.integralEd}} <span v-if="ruleForm.integralEd != ''">分</span>
                </el-form-item>
            </el-col>

            <el-col align="center">
                <el-form-item>
                    <el-button type="primary" @click="resetForm('ruleForm')">取消</el-button>
                    <el-button type="primary" @click="submitForm('ruleForm')">保存</el-button>
                </el-form-item>
            </el-col>
        </el-form>
    </el-dialog>
</section>
</template>

<script>
import {
    formatDate
} from '../../common/js/data';
import {
    queryPageList,
    updateMgm
} from '../../api/api';
import {
    commissionCheckList,
    commissionCheckListExport,
    editIntegral,
    relieve
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
        return {
            headers: {},
            posterSearch: {
                dealTransactionsId: '',
                icName: '',
                loanCode: '',
                dealStatus: '',
            },
            editDialog: false,
            /******分页数据******/
            total: 0,
            page: 1,
            size: 10,

            /******列表数据******/
            listLoading: false,
            loanUserList: [],

            ruleForm: {
                integral: '',
                integralIng: '',
                integralEd: '',
            },
            rules: {
                integralIng: [{
                    required: true,
                    message: '请输入修改积分',
                    trigger: 'blur'
                }, ],
            },
            dealId: '',

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
    methods: {
        handleCurrentChange(val) {
            this.page = val;
            this.getLoanUserList();
        },
        //查询数据
        getLoanUserList: function() {
            let params = {
                page: this.page,
                pageSize: this.size,
                dealTransactionsId: this.posterSearch.dealTransactionsId,
                icName: this.posterSearch.icName,
                loanCode: this.posterSearch.loanCode,
                dealStatus: this.posterSearch.dealStatus,
            };
            commissionCheckList(params).then(res => {
                if (res.code == 0) {
                    if (res.data.rows) {
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
        //导出
        getExport: function() {
            let param = {
                dealTransactionsId: this.posterSearch.dealTransactionsId,
                icName: this.posterSearch.icName,
                loanCode: this.posterSearch.loanCode,
                dealStatus: this.posterSearch.dealStatus,
            };
            commissionCheckListExport(param).then((res) => {
                console.log(res, 'asdasd');
                let url = window.URL.createObjectURL(new Blob([res], {
                    type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8'
                }));
                let link = document.createElement('a');
                link.style.display = 'none';
                link.href = url;
                link.setAttribute('download', '收佣审核.xls');
                document.body.appendChild(link);
                link.click();
            })
        },
        //修改
        editBtn(integral, dealId) {
            this.ruleForm.integral = integral;
            this.ruleForm.integralIng = '';
            this.ruleForm.integralEd = '';
            this.dealId = dealId;
            this.editDialog = true;

        },
        //解冻
        thawBtn(dealId) {
            this.$confirm('确定解冻吗？', '提示', {}).then(() => {
                let params = {
                    "dealId": dealId
                };
                relieve(params).then((res) => {
                    if (res.code == 0) {
                        this.getLoanUserList();
                    } else {
                        this.$message(res.msg)
                    }
                })
            })
        },
        //修改保存j
        submitForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    let params = {
                        "changeIntegral": this.ruleForm.integralIng,
                        "dealId": this.dealId
                    };
                    editIntegral(params).then((res) => {
                        if (res.code == 0) {
                            this.editDialog = false;
                            this.getLoanUserList();
                            this.$refs[formName].resetFields();
                        } else {
                            this.$message(res.msg)
                        }
                    })
                } else {
                    return false;
                }
            });
        },
        //取消修改
        resetForm(formName) {
            this.editDialog = false;
            this.ruleForm.integralEd = '';
            this.$refs[formName].resetFields();
        },
        reviseIntegral() {
            this.ruleForm.integralEd = -(-(this.ruleForm.integral) - this.ruleForm.integralIng);
        },
        //转换时间
        formatDate: function(time) {
            if (time) {
                var date = new Date(time);
                return formatDate(date, "yyyy/MM/dd hh:mm:ss");
            }
        },
    },
}
</script>

<style scoped>
.tabLine tbody td {
    height: 50px;
}

.tabLine .cell {
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

.tabLine th>.cell {
    padding: 5px 0;
}

.divider {
    height: 1px;
    background-color: #dfe6ec;
    margin-bottom: 15px;
}

.reconLeve {}

.btnBox {
    padding-bottom: 30px;
}
</style>

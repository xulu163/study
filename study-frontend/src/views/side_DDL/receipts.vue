<template>
<section style="position: relative;">
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
        <el-form :inline="true" :model="posterSearch">
            <el-form-item label="流水号：">
                <el-input v-model="posterSearch.detailNo" placeholder="请输入流水号"></el-input>
            </el-form-item>
            <el-form-item label="订单支付时间" prop="startTime">
                <el-date-picker type="date" v-model="posterSearch.startTime" placeholder="开始时间" @change="startChange"></el-date-picker>&nbsp;&nbsp;&nbsp;--
            </el-form-item>
            <el-form-item prop="endTime">
                <el-date-picker type="date" v-model="posterSearch.endTime" placeholder="结束时间" @change="endChange"></el-date-picker>
            </el-form-item>
            <el-form-item label="交易类型：">
                <el-select v-model="posterSearch.detailType" clearable placeholder="请选择">
                    <el-option label="佣收" value="1"></el-option>
                    <el-option label="提现" value="3"></el-option>
                    <el-option label="退款" value="2"></el-option>
                    <el-option label="收款" value="4"></el-option>
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

    <el-col :span="24">总收款：{{totalAmount}}万元</el-col>
    <!--列表-->
    <el-table :data="loanUserList" highlight-current-row v-loading="listLoading" border style="width: 100%;" stripe class="tabLine">
        <el-table-column prop="detailNo" label="支付流水号" align="center" width="250">
        </el-table-column>
        <el-table-column prop="detailType" label="交易类型" header-align="center" align="center">
            <template slot-scope="scope">
                <span v-if="scope.row.detailType   == 1">佣收</span>
                <span v-if="scope.row.detailType   == 2">退款</span>
                <span v-if="scope.row.detailType   == 3">提现</span>
                <span v-if="scope.row.detailType   == 4">收款</span>
            </template>
        </el-table-column>
        <el-table-column prop="amount" label="金额(元)" header-align="center" align="center">
        </el-table-column>
        <el-table-column label="支付状态" header-align="center" align="center">
            <template slot-scope="scope">
				<span v-if="scope.row.detailStatus  == 1">处理中</span>
				<span v-if="scope.row.detailStatus  == 2">支付成功</span>
                <span v-if="scope.row.detailStatus  == 3">失败</span>
            </template>
        </el-table-column>
        <el-table-column prop="startTime" label="订单支付时间" header-align="center" align="center">
            <template slot-scope="scope">
                <span>{{ formatDate(scope.row.startTime) }}</span>
            </template>
        </el-table-column>
        <el-table-column prop="transactionId" label="TRN交易号" header-align="center" align="center">
        </el-table-column>
        <el-table-column prop="remark" label="备注" header-align="center" align="center">
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
import {
    formatDate
} from '../../common/js/data';
import {
    queryPageList,
    updateMgm
} from '../../api/api';
import {
    orderDetailManangeList,
    orderDetailManangeListExport,
    getTotalReceivableAmount
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
                detailNo: '',
                detailType: '',
                startTime: '',
                endTime: '',
            },
            editDialog: false,
            /******分页数据******/
            total: 0,
            page: 1,
            size: 10,
            telephone:"",
            totalAmount:0,

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
            console.log(user)
            this.telephone = user.name
            let token = user.token || '';

            if (token) {
                this.headers = {
                    Authorization: 'bearer ' + token
                };
            }
        }
        this.getTotalAmount();
        this.getLoanUserList();

    },
    methods: {
        handleCurrentChange(val) {
            this.page = val;
            this.getLoanUserList();
        },
        //startChange
        startChange(event) {
            this.start = new Date(this.posterSearch.startTime).getTime();
        },
        //endChange
        endChange(event) {
            this.end = new Date(this.posterSearch.endTime).getTime();
        },
        //查询数据
        getLoanUserList: function() {
            let params = {
                page: this.page,
                pageSize: this.size,
                detailNo:this.posterSearch.detailNo,
                detailType: this.posterSearch.detailType,
                telephone:this.telephone,
                startTime:this.posterSearch.startTime,
                endTime:this.posterSearch.endTime,
            };
            orderDetailManangeList(params).then(res => {
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
                page: this.page,
                pageSize: this.size,
                detailNo:this.posterSearch.detailNo,
                detailType: this.posterSearch.detailType,
                telephone:this.telephone,
                startTime:this.posterSearch.startTime,
                endTime:this.posterSearch.endTime,
            };
            orderDetailManangeListExport(param).then((res) => {
                
                let url = window.URL.createObjectURL(new Blob([res], {
                    type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8'
                }));
                let link = document.createElement('a');
                link.style.display = 'none';
                link.href = url;
                link.setAttribute('download', '收付流水.xls');
                document.body.appendChild(link);
                link.click();
            })
        },
        getTotalAmount:function(){
            let param = {telephone:this.telephone};
            getTotalReceivableAmount(param).then((res) => {
                if (res.code == 0) {
                    this.totalAmount = res.data
                }
            })
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

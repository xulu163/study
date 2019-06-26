<template>
<section style="position: relative;">
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
        <el-form :inline="true" :model="statisticSearch" ref="statisticSearch" :rules="statisticSearch.statisticSearchRules">
            <el-form-item label="经纪人姓名：">
                <el-input v-model="statisticSearch.icName" placeholder="请输入经纪人姓名"></el-input>
            </el-form-item>
            <el-form-item label="兑换时间" prop="startTime">
                <el-date-picker type="date" v-model="statisticSearch.startTime" placeholder="兑换开始时间" @change="startChange"></el-date-picker>&nbsp;&nbsp;&nbsp;--
            </el-form-item>
            <el-form-item prop="endTime">
                <el-date-picker type="date" v-model="statisticSearch.endTime" placeholder="兑换结束时间" @change="endChange"></el-date-picker>
            </el-form-item>
            <el-form-item label="发放状态：">
                <el-select v-model="statisticSearch.billStatus" clearable placeholder="请选择发放状态">
                    <el-option label="已发放" value="1"></el-option>
                    <el-option label="未发放" value="0"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="getLoanUserList"><i class="fa fa-search"></i> 搜索</el-button>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="getExport"><i class="fa fa-search"></i> 导出</el-button>
            </el-form-item>
        </el-form>
    </el-col>


    <!--列表-->
    <el-table :data="loanUserList" ref="multipleTable" @select-all="toggle" @selection-change="handleSelectionChange" @select="selectOnce" highlight-current-row v-loading="listLoading" border style="width: 100%;" stripe class="tabLine">
        <el-table-column type="selection" label="全选" header-align="center" align="center" min-width="70"></el-table-column>
        <el-table-column prop="icName" label="经纪人姓名" header-align="center" align="center">
        </el-table-column>
        <el-table-column prop="telephone" label="经纪人手机号" header-align="center" align="center">
        </el-table-column>
        <el-table-column prop="gmtCreate" label="兑换时间" header-align="center" align="center">
        </el-table-column>
        <el-table-column prop="obtain" label="礼券金额" header-align="center" align="center">
        </el-table-column>
        <el-table-column label="发放状态" header-align="center" align="center">
            <template slot-scope="scope">
                    <span v-if="scope.row.billStatus == 0">未发放</span>
                    <span v-if="scope.row.billStatus == 1">已发放</span>
                    <span v-if="scope.row.billStatus == 2">失败</span>
                </template>
        </el-table-column>
        <el-table-column label="操作" header-align="center" align="center" min-width="120">
            <template slot-scope="scope">
                    <el-button type="info" size="small" v-if="scope.row.billStatus == 1" disabled>发放</el-button>
                    <el-button type="primary" size="small" @click="editBtn(scope.row.id)" v-if="scope.row.billStatus != 1">发放</el-button>
                </template>
        </el-table-column>
    </el-table>
    <!--工具条-->
    <el-col :span="24" class="toolbar">
        <el-button @click="BatchDistribution(batchSelection)"><i class="el-icon-fa-sitemap"></i>批量发放</el-button>
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
    couponsExchangeList,
    grantCoupons,
    couponsExchangeListExport
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
        return {
            headers: {},
            statisticSearch: {
                startTime: '',
                endTime: '',
                icName: '', //经纪人姓名
                billStatus: '', //发放状态
                statisticSearchRules: {
                    startTime: [{
                        validator: startTimeValidator,
                        trigger: 'blur'
                    }, ],
                    endTime: [{
                        validator: endTimeValidator,
                        trigger: 'blur'
                    }, ],

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
        //startChange
        startChange(event) {
            this.start = new Date(this.statisticSearch.startTime).getTime();
        },
        //endChange
        endChange(event) {
            this.end = new Date(this.statisticSearch.endTime).getTime();
        },
        //查询数据
        getLoanUserList() {
            let params = {
                page: this.page,
                pageSize: this.size,
                icName: this.statisticSearch.icName,
                timeStart: this.formatDate(this.statisticSearch.startTime),
                timeEnd: this.formatDate(this.statisticSearch.endTime),
                billStatus: this.statisticSearch.billStatus,
            };
            couponsExchangeList(params).then(res => {
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
        toggle(val) {
            // console.log('toggle',val)
            var valNew = [];
            let that = this;
            this.batchSelection = [];
            val.forEach(function(v) {
                if (v.billStatus == 1) {
                    valNew.push(v);
                } else if (v.billStatus == 0) {
                    that.batchSelection.push(v);
                }
            })
            if (this.toggleStatue) {
                valNew.forEach(function(v) {
                    that.$refs.multipleTable.toggleRowSelection(v);
                });
                this.toggleStatue = false;
            } else {
                this.toggleStatue = true;
            }
        },
        handleSelectionChange(val) {
            //console.log('val',val)
            this.batchSelection = val;
        },
        selectOnce(selcetion, row) {
            this.toggleStatue = true;
        },
        //批量发放
        BatchDistribution() {
            // console.log(this.batchSelection,'11');
            if (this.batchSelection.length == 0) {
                this.$message("请先选择客户!");
            } else {
                let ids = [];
                this.batchSelection.forEach(function(v) {
                    ids.push(v.id);
                });
                this.$confirm('确定发放吗？', '提示', {}).then(() => {
                    let param = {
                        ids: ids
                    };
                    grantCoupons(param).then((res) => {
                        if (res.code == 0) {
                            this.getLoanUserList();
                        } else {
                            this.$message(res.msg);
                        }
                    });
                });
            }
        },
        //发放按钮
        editBtn(id) {
            this.$confirm('确定发放吗？', '提示', {}).then(() => {
                let param = {
                    ids: [id]
                };
                grantCoupons(param).then((res) => {
                    if (res.code == 0) {
                        this.getLoanUserList();
                    } else {
                        this.$message(res.msg);
                    }
                });
            });
        },
        //导出
        getExport: function() {
            let param = {
                icName: this.statisticSearch.icName,
                timeStart: this.formatDate(this.statisticSearch.startTime),
                timeEnd: this.formatDate(this.statisticSearch.endTime),
                billStatus: this.statisticSearch.billStatus,
            };
            couponsExchangeListExport(param).then((res) => {
                let url = window.URL.createObjectURL(new Blob([res], {
                    type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8'
                }));
                let link = document.createElement('a');
                link.style.display = 'none';
                link.href = url;
                link.setAttribute('download', '礼券兑换流水.xls');
                document.body.appendChild(link);
                link.click();
            })
        },
        //转换时间
        formatDate: function(time) {
            if (time) {
                var date = new Date(time);
                return formatDate(date, "yyyy-MM-dd");
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

.reconLeve {
    text-align: center;
}

.btnBox {
    padding-bottom: 30px;
}
</style>

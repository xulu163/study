<template>
	<section style="position: relative;" class="messNotice">
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
            <el-form :inline="true" :model="posterSearch" class="flex">
                <div>
                    <el-form-item>
                        <el-button type="success" @click="addNoticeDialog">新增消息通知人</el-button>
                    </el-form-item>
                </div>
                <div>
                    <el-form-item label="消息通知类型：">
                        <el-select v-model="posterSearch.noticeType" clearable placeholder="请选择">
                            <el-option label="区块高度不增长" value="1"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="getNoticeList" ><i class="fa fa-search"></i> 搜索</el-button>
                    </el-form-item>
                </div>
            </el-form>
		</el-col>

		<!--列表-->
		<el-table :data="listData" highlight-current-row v-loading="listLoading" border style="width: 100%;" stripe class="tabLine">
			<el-table-column prop="id" label="序号" align="center" >
			</el-table-column>
			<el-table-column prop="name" label="消息接收人" header-align="center" align="center" >
			</el-table-column>
            <el-table-column prop="telephone" label=" 消息接收人手机号" header-align="center" align="center">
			</el-table-column>
			<el-table-column prop="noticeType" label="消息通知类型" header-align="center" align="center">
                <template slot-scope="scope">
					{{scope.row.noticeType === 1 ? '区块高度不增长' : ''}}
				</template>
			</el-table-column>
			<el-table-column label="操作" header-align="center" align="center" min-width="120">
				<template slot-scope="scope">
					<el-button type="primary" size="small" @click="editNoticeDialog(scope.row)">修改</el-button>
					<el-button type="danger" size="small" @click="delNotice(scope.row.id)">删除</el-button>
				</template>
			</el-table-column>
		</el-table>

		<!--工具条-->
		<el-col :span="24" class="toolbar">
			<el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size=size :total="total" style="float:right;">
			</el-pagination>
		</el-col>

        <!--操作弹出框-->
        <el-dialog :title="dialogTitle" width="80%" v-model="editDialog">
            <el-form  :model="ruleForm" :rules="rules" ref="ruleForm" label-width="150px" class="demo-ruleForm">
                <el-form-item label="消息接收人：" prop="name">
                    <el-input v-model="ruleForm.name" placeholder="请填写消息接收人" class="w-217"></el-input>
                </el-form-item>
                <el-form-item label="消息通知类型：" prop="noticeType">
                    <el-select v-model="ruleForm.noticeType" clearable placeholder="请选择通知类型" :disabled="this.dialogTitle === '修改消息通知'">
                        <el-option label="区块高度不增长" value="1"></el-option>
                    </el-select>
                </el-form-item>
            
                <el-form-item label="消息接收人手机号：" prop="telephone">
                    <el-input v-model="ruleForm.telephone" placeholder="请填写消息接收人手机号" class="w-217"></el-input>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" @click="resetForm('ruleForm')">取消</el-button>
                    <el-button type="primary" @click="submitForm('ruleForm')">保存</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
	</section>
</template>

<script>
import {
  noticeList,
  addNotice,
  editNotice,
  deleteNotice
} from "../../api/ddlApi";

export default {
  data() {
    //自定义验证
    let telValidator = (rule, value, callback) => {
      if (value) {
        if (!/^[1][3,4,5,7,8][0-9]{9}$/.test(value)) {
          callback(new Error("手机号码不正确"));
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
        noticeType: "1"
      },
      editDialog: false,
      /******分页数据******/
      total: 0,
      page: 1,
      size: 10,
      /******列表数据******/
      listLoading: true,
      listData: [],
      ruleForm: {
        name: "",
        noticeType: "",
        telephone: ""
      },
      rules: {
        name: [
          { required: true, message: "请填写消息接收人", trigger: "blur" }
        ],
        noticeType: [
          { required: true, message: "请选择通知类型", trigger: "blur" }
        ],
        telephone: [{ validator: telValidator, trigger: "blur" }]
      },
      dialogTitle: "",
      id: null
    };
  },
  mounted() {
    let user = sessionStorage.getItem("user");
    if (user) {
      user = JSON.parse(user);
      let token = user.token || "";

      if (token) {
        this.headers = { Authorization: "bearer " + token };
      }
    }
    this.getNoticeList();
  },
  methods: {
    addNoticeDialog() {
      this.ruleForm = {};
      this.dialogTitle = "新增消息通知人";
      this.editDialog = true;
    },
    editNoticeDialog(row) {
      this.ruleForm = {};
      this.ruleForm.name = row.name;
      this.ruleForm.noticeType = row.noticeType + "";
      this.ruleForm.telephone = row.telephone;
      this.dialogTitle = "修改消息通知";
      this.editDialog = true;
      this.id = row.id;
    },
    delNotice(id) {
      this.$confirm("是否确认删除?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          let params = {
            id
          };
          deleteNotice(params).then(res => {
            if (res.code == 0) {
              this.$message({
                type: "success",
                message: "删除成功!"
              });
              this.getNoticeList();
            } else {
              this.$message(res.msg);
            }
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    },
    handleCurrentChange(val) {
      this.page = val;
      this.getNoticeList();
    },
    //查询数据
    getNoticeList: function() {
      let params = {
        page: this.page,
        size: this.size,
        noticeType: Number(this.posterSearch.noticeType)
      };
      noticeList(params).then(res => {
        if (res.code == 0) {
          this.listLoading = false;
          if (res.data.rows) {
            this.total = parseInt(res.data.total);
            this.listData = res.data.rows;
          } else {
            this.total = 0;
            this.listData = [];
          }
        } else {
          this.$message(res.msg);
        }
      });
    },
    // 新增/修改
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          if (this.dialogTitle === "新增消息通知人") {
            let { name, noticeType, telephone } = this.ruleForm;
            let params = {
              name,
              noticeType: Number(noticeType),
              telephone
            };
            addNotice(params).then(res => {
              if (res.code == 0) {
                this.editDialog = false;
                this.getNoticeList();
                this.$refs[formName].resetFields();
              } else {
                this.$message(res.msg);
              }
            });
          } else if (this.dialogTitle === "修改消息通知") {
            let { name, telephone } = this.ruleForm;
            let { id } = this;
            let params = {
              id,
              name,
              telephone
            };
            editNotice(params).then(res => {
              if (res.code == 0) {
                this.editDialog = false;
                this.getNoticeList();
                this.$refs[formName].resetFields();
              } else {
                this.$message(res.msg);
              }
            });
          }
        } else {
          return false;
        }
      });
    },
    //取消修改
    resetForm(formName) {
      this.editDialog = false;
      this.$refs[formName].resetFields();
    }
  }
};
</script>

<style lang="scss">
.messNotice {
  .el-dialog--small {
    width: 410px;
  }
  .w-217 {
    width: 217px;
  }
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
  .tabLine th > .cell {
    padding: 5px 0;
  }
  .divider {
    height: 1px;
    background-color: #dfe6ec;
    margin-bottom: 15px;
  }
  .btnBox {
    padding-bottom: 30px;
  }
  .flex {
    display: flex;
    justify-content: space-between;
  }
}
</style>
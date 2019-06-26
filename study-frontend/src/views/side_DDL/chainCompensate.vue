<template>
	<section style="position: relative;" class="chainCompensate">
		<!--工具条-->
        <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
          <p class="tag">当前区块高度：<el-tag type="info">{{height}}</el-tag></p>
          <el-button type="primary" class="right mb-10" @click="allPushChain">全部重新上链</el-button>
        </el-col>
		<!--列表-->
		<el-table :data="listData" highlight-current-row v-loading="listLoading" border style="width: 100%;" stripe class="tabLine">
			<el-table-column prop="id" label="序号" align="center" width="250">
			</el-table-column>
			<el-table-column prop="transTypeStr" label="上链交易类型" header-align="center" align="center" >
			</el-table-column>
			<el-table-column prop="transactionsId" label="区块交易id" header-align="center" align="center">
			</el-table-column>
			<el-table-column prop="blockData" label="上链数据" header-align="center" align="left">
			</el-table-column>
			<el-table-column label="操作" header-align="center" align="center" min-width="120">
				<template slot-scope="scope">
					<el-button type="primary" size="small" @click="pushChain(scope.row.id)">重新上链</el-button>
				</template>
			</el-table-column>
		</el-table>

        <el-col :span="24" class="toolbar">
			<el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size=size :total="total" style="float:right;">
			</el-pagination>
		</el-col>
	</section>
</template>

<script>
import {
  blockHeight,
  chainData,
  pushChain,
  allPushChain
} from "../../api/ddlApi";

export default {
  data() {
    return {
      headers: {},
      total: 0,
      page: 1,
      size: 10,
      listData: [],
      listLoading: true,
	  height: null,
	  timer:null
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
    this.timer =  setInterval(() => {
      this.blockHeight();
    }, 15000);
    this.blockHeight();
    this.chainData();
  },
  beforeDestroy(){
	  clearInterval(this.timer)
  },
  methods: {
    // 全部上链
    allPushChain() {
      this.listLoading = true;
      allPushChain().then(res => {
        if (res.code == 0) {
          this.listLoading = false;
          this.$message({
            type: "success",
            message: "重新上链成功!"
          });
          this.chainData();
        } else {
          this.listLoading = false;
          this.$message(res.msg);
        }
      });
    },
    // 单个上链
    pushChain(id) {
      let params = {
        id
      };
      this.listLoading = true;
      pushChain(params).then(res => {
        if (res.code == 0) {
          this.listLoading = false;
          this.$message({
            type: "success",
            message: "重新上链成功!"
          });
          this.chainData();
        } else {
          this.listLoading = false;
          this.$message(res.msg);
        }
      });
    },
    blockHeight() {
      blockHeight().then(res => {
        if (res.code == 0) {
          this.height = res.data;
        } else {
          this.$message(res.msg);
        }
      });
    },
    chainData() {
      let { page, size } = this;
      let params = {
        page,
        size
      };
      chainData(params).then(res => {
        let data = res.data;
        if (res.code == 0) {
          this.listData = data.rows;
          this.listLoading = false;
          this.total = data.total;
        } else {
          this.$message(res.msg);
        }
      });
    },
    handleCurrentChange(val) {
      this.page = val;
      this.chainData();
    }
  }
};
</script>

<style scoped lang="scss">
.chainCompensate {
  .tag {
    float: left;
    padding: 0;
    margin: 8px 0 10px 0;
    .el-tag {
      height: 26px;
      line-height: 26px;
      font-size: 16px;
      min-width: 65px;
      text-align: center;
    }
  }
  .right {
    float: right;
  }
  .mb-10 {
    margin-bottom: 10px;
  }
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
.reconLeve {
}
.btnBox {
  padding-bottom: 30px;
}
</style>

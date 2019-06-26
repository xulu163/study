<template>
    <section style="position: relative;">
        <!--工具条-->
        <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
            <el-form :inline="true" :model="searchParams" ref="statisticSearch">
                <el-form-item label="ID:">
                    <el-input v-model="searchParams.teacherId" size="mini" style="width:100px" clearable placeholder="请输入ID"></el-input>
                </el-form-item>
                <el-form-item label="教师姓名:">
                    <el-input v-model="searchParams.teacherName" size="mini" style="width:100px" clearable placeholder="请输入教师姓名"></el-input>
                </el-form-item>

                <el-form-item label="性别：">
                    <el-select v-model="searchParams.sex" size="mini" style="width:100px" clearable placeholder="选择性别">
                        <el-option label="男" value="1"></el-option>
                        <el-option label="女" value="2"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="年龄:">
                  <el-input v-model="searchParams.ageStart" size="mini"  clearable style="width:50px"></el-input>
                </el-form-item>
                <el-form-item label="-">
                  <el-input v-model="searchParams.ageEnd" size="mini"  clearable style="width:50px"></el-input>
                </el-form-item>
                <el-form-item label="学校:">
                  <el-select v-model="searchParams.schoolId" size="mini" style="width:110px" clearable placeholder="请选择学校">
                    <el-option v-for="item in school_list" :key="item.name" :label="item.name" :value="item.id" ></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="班级:">
                  <el-select v-model="searchParams.clazzId" size="mini" style="width:110px" clearable placeholder="请选择班级">
                    <el-option v-for="item in clazz_list" :key="item.name" :label="item.name" :value="item.id" ></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="籍贯:">
                  <el-select v-model="searchParams.placeNative" size="mini" style="width:110px" clearable placeholder="请选择籍贯">
                    <el-option value="河南">河南</el-option>
                    <el-option value="山东">山东</el-option>
                    <el-option value="山西">山西</el-option>
                    <el-option value="江苏">江苏</el-option>
                    <el-option value="湖北">湖北</el-option>
                    <el-option value="安徽">安徽</el-option>
                    <el-option value="陕西">陕西</el-option>
                    <el-option value="湖南">湖南</el-option>
                    <el-option value="广西">广西</el-option>
                    <el-option value="广东">广东</el-option>
                    <el-option value="新疆">新疆</el-option>
                    <el-option value="浙江">浙江</el-option>
                    <el-option value="内蒙古">内蒙古</el-option>
                    <el-option value="宁夏">宁夏</el-option>
                    <el-option value="四川">四川</el-option>
                  </el-select>
                </el-form-item>

                <el-form-item label="账号:">
                    <el-input v-model="searchParams.tel" size="mini" style="width:100px" clearable placeholder="请输入账号"></el-input>
                </el-form-item>


                <el-form-item label="创建时间" prop='entryTimeStart'>
                    <el-date-picker type="date" v-model='searchParams.createTimeStart' size="mini" style="width:100px" placeholder="年/月/日" ></el-date-picker>&nbsp;&nbsp;&nbsp;--
                </el-form-item>
                <el-form-item prop='entryTimeEnd'>
                  <el-date-picker type="date"  size="mini" style="width:100px" v-model='searchParams.createTimeEnd' placeholder="年/月/日"></el-date-picker>
                </el-form-item>
                <el-form-item label="学生数:">
                  <el-input v-model="searchParams.totalCourseStart" size="mini"  clearable style="width:50px"></el-input>
                </el-form-item>
                <el-form-item label="-">
                  <el-input v-model="searchParams.totalCourseEnd" size="mini" clearable style="width:50px"></el-input>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" @click="searchData()"><i class="fa fa-search"></i> 搜索</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="reset()"><i class="fa "></i> 重置</el-button>
                </el-form-item>


            </el-form>
        </el-col>
        <el-col  :span="24" class="toolbar"><el-button type="primary" class="importBtn" @click="add()">添加</el-button></el-col>



        <!--列表-->
        <el-table :data="loanUserList" ref="multipleTable" @selection-change="handleSelectionChange" @sort-change="sortby" highlight-current-row v-loading="listLoading" border style="width: 100%;" stripe>
            <el-table-column type="selection" label="全选" header-align="center" align="center" min-width="20"></el-table-column>
            <el-table-column prop="id" label="ID" header-align="center" align="center" min-width="190">
              <template slot-scope="scope">
                  <a size="small" @click="editBtn(scope.row.id)" href="#">{{scope.row.id}}</a>
              </template>
            </el-table-column>
            <el-table-column prop="name" label="教师姓名" header-align="center" min-width="100" align="center"></el-table-column>
            <el-table-column prop="sex" label="性别" header-align="center"  align="center">
              <template slot-scope="scope">
                  <span>{{ scope.row.sex==1?"男":"女" }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="age" label="年龄" header-align="center"  align="center"></el-table-column>
            <el-table-column prop="nativePlace" label="籍贯" header-align="center" align="center"></el-table-column>
            <el-table-column prop="address" label="家庭住址" header-align="center" min-width="600" align="center"></el-table-column>
            <el-table-column prop="tel" label="账号" header-align="center" min-width="125" align="center"></el-table-column>
            <el-table-column prop="pwd" label="密码" header-align="center" min-width="120" align="center"></el-table-column>
            <el-table-column prop="totalStu" label="学生数量" header-align="center"  min-width="120" align="center"></el-table-column>
            <el-table-column prop="totalCourse" label="课程数量" header-align="center"   min-width="120" align="center"></el-table-column>
            <el-table-column prop="schoolName" label="学校" header-align="center" min-width="140" align="center"></el-table-column>
            <el-table-column prop="clazzName" label="班级" header-align="center" min-width="180" align="center"></el-table-column>
            <el-table-column prop="createTime" label="创建时间" header-align="center" sortable="custom" min-width="120" align="center">
              <template slot-scope="scope">
                  <span>{{ formatDate(scope.row.createTime) }}</span>
              </template>
            </el-table-column>
            <el-table-column label="操作" header-align="center" align="center" min-width="160">
                <template slot-scope="scope">
                    <el-button type="primary" size="small" @click="editBtn(scope.row.id)">详情</el-button>

                </template>
            </el-table-column>
        </el-table>
        <!--工具条-->
        <el-col :span="24" class="toolbar">
          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page.sync="page"
            :page-sizes="[10, 20, 30, 50]"
            :page-size="size"
            layout="total,sizes, prev, pager, next"
            :total="total" style="float:right;">
          </el-pagination>
            <!-- <el-pagination layout="prev, pager, next" @size-change="handleSizeChange"  @current-change="handleCurrentChange" :page-size="size" :page-sizes="[10, 20, 50, 100]"  :total="total" style="float:right;"> </el-pagination> -->
        </el-col>


        <!--弹窗框-->
        <div id="modelAll">
            <!--编辑banner-->
            <el-dialog title="添加教师" custom-class="dialogBox1" :visible.sync="editBannerDialog" @close="resetForm('editOutput')" width="100%">
                <el-form :model="editOutputData" :inline="true" :rules="editOutputRules" ref="editOutput" label-width="100px">
                    <el-row>
                      <el-col :span="12">
                        <el-form-item label="教师姓名：" prop="teacherName">
                            <el-input v-model="editOutputData.teacherName"  placeholder="请填写"size="small" style="width:150px"></el-input>
                        </el-form-item>
                        <el-form-item label="性别：" prop="sex">
                          <el-radio v-model="editOutputData.sex" label="1" >男</el-radio>
                          <el-radio v-model="editOutputData.sex" label="2" >女</el-radio>
                        </el-form-item>
                        <el-form-item label="年龄：" prop="age">
                            <el-input v-model="editOutputData.age" placeholder="请填写年龄"size="small"></el-input>
                        </el-form-item>
                        <el-form-item label="家庭住址：" prop="address">
                            <el-input v-model="editOutputData.address" placeholder="请填写家庭住址"size="small"></el-input>
                        </el-form-item>

                        <el-form-item label="籍贯：" prop="placeNative">
                          <el-select v-model="editOutputData.placeNative" style="width:100px" clearable placeholder="请选择籍贯">
                            <el-option value="河南">河南</el-option>
                            <el-option value="山东">山东</el-option>
                            <el-option value="山西">山西</el-option>
                            <el-option value="江苏">江苏</el-option>
                            <el-option value="湖北">湖北</el-option>
                            <el-option value="安徽">安徽</el-option>
                            <el-option value="陕西">陕西</el-option>
                            <el-option value="湖南">湖南</el-option>
                            <el-option value="广西">广西</el-option>
                            <el-option value="广东">广东</el-option>
                            <el-option value="新疆">新疆</el-option>
                            <el-option value="浙江">浙江</el-option>
                            <el-option value="内蒙古">内蒙古</el-option>
                            <el-option value="宁夏">宁夏</el-option>
                            <el-option value="四川">四川</el-option>
                          </el-select>
                        </el-form-item>
                        <el-form-item label="联系电话：" prop="tel">
                            <el-input v-model="editOutputData.tel" placeholder="请填写联系电话"size="small"></el-input>
                        </el-form-item>

                        <el-form-item label="学校：" prop="schoolId">
                          <el-select v-model="editOutputData.schoolId" clearable placeholder="请选择学校">
                            <el-option v-for="item in school_list" :key="item.name" :label="item.name" :value="item.id" ></el-option>
                          </el-select>
                        </el-form-item>
                        <el-form-item label="班级：" prop="clazzId">
                          <el-select v-model="editOutputData.clazzId" clearable placeholder="请选择班级">
                            <el-option v-for="item in clazz_list" :key="item.name" :label="item.name" :value="item.id" ></el-option>
                          </el-select>
                        </el-form-item>
                      </el-col>
                      <el-col :span="12">

                            <el-form-item  label="课程：">
                              <el-row>
                                <el-col v-for="(item,key) in course_list" :span="8">
                                  <el-checkbox v-model="item.ischecked" @change="clickCheck(item.ischecked,key)" label="" ></el-checkbox>
                                  <el-input v-model="item.name" placeholder="请填写" size="small" style="width:100px"></el-input>
                                </el-col>
                              </el-row>
                            </el-form-item>



                      </el-col>
                    </el-row>
                    <el-form-item class="btnBox" style="text-align:center">
                        <el-button type="primary" @click="resetForm('editOutput')">取消</el-button>
                        <el-button type="primary" @click="editSubmitForm('editOutput')">提交</el-button>
                    </el-form-item>
                </el-form>
            </el-dialog>

            <el-dialog title="教师详情" custom-class="dialogBox1" :visible.sync="detailDialog" width="100%">
                <el-form :model="detailData" :inline="true" :rules="editOutputRules" ref="editOutputRules" label-width="100px">
                    <el-row>
                      <el-col :span="12">
                        <el-form-item label="教师姓名：">
                            <el-input v-model="detailData.name" readonly placeholder="请填写"size="small" style="width:150px"></el-input>
                        </el-form-item>
                        <el-form-item label="性别：" prop="sex">
                          <el-radio v-model="detailData.sex" label="1" readonly >男</el-radio>
                          <el-radio v-model="detailData.sex" label="2" readonly >女</el-radio>
                        </el-form-item>
                        <el-form-item label="年龄：">
                            <el-input v-model="detailData.age" readonly placeholder="请填写年龄"size="small"></el-input>
                        </el-form-item>
                        <el-form-item label="家庭住址：">
                            <el-input v-model="detailData.address" readonly placeholder="请填写家庭住址"size="small"></el-input>
                        </el-form-item>

                        <el-form-item label="籍贯：">
                          <el-select v-model="detailData.nativePlace" style="width:100px" disabled clearable placeholder="请选择籍贯">
                            <el-option value="河南">河南</el-option>
                            <el-option value="山东">山东</el-option>
                            <el-option value="山西">山西</el-option>
                            <el-option value="江苏">江苏</el-option>
                            <el-option value="湖北">湖北</el-option>
                            <el-option value="安徽">安徽</el-option>
                            <el-option value="陕西">陕西</el-option>
                            <el-option value="湖南">湖南</el-option>
                            <el-option value="广西">广西</el-option>
                            <el-option value="广东">广东</el-option>
                            <el-option value="新疆">新疆</el-option>
                            <el-option value="浙江">浙江</el-option>
                            <el-option value="内蒙古">内蒙古</el-option>
                            <el-option value="宁夏">宁夏</el-option>
                            <el-option value="四川">四川</el-option>
                          </el-select>
                        </el-form-item>
                        <el-form-item label="联系电话：">
                            <el-input v-model="detailData.tel" placeholder="请填写联系电话" readonly size="small"></el-input>
                        </el-form-item>

                        <el-form-item label="学校：">
                          <el-select v-model="detailData.schoolId" disabled clearable placeholder="请选择学校">
                            <el-option v-for="item in school_list" :key="item.name" :label="item.name"  :value="item.id" ></el-option>
                          </el-select>
                        </el-form-item>
                        <el-form-item label="班级：">
                          <el-select v-model="detailData.clazzId" clearable disabled placeholder="请选择班级">
                            <el-option v-for="item in clazz_list" :key="item.name" :label="item.name"  :value="item.id" ></el-option>
                          </el-select>
                        </el-form-item>
                      </el-col>
                      <el-col :span="12">

                            <el-form-item  label="课程：">
                              <el-row>
                                <el-col v-for="(item,key) in detailData.courseList" :span="8">

                                  <el-input v-model="item.name" placeholder="请填写" size="small" readonly style="width:100px"></el-input>
                                </el-col>
                              </el-row>
                            </el-form-item>



                      </el-col>
                    </el-row>

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
		teacherList,
    addTeacher,
    teacherDetail,
    clazzList,
    courseList,
    schoolList

	} from '../../api/api';
	export default {
		data() {

			return {
        isadmin:false,
        searchParams:{
          address:"" ,//地址 ,
          ageEnd:"" ,//年龄范围结束 ,
          ageStart: "", //年龄范围起始 ,
          clazzId:"", // 班级id ,
          createTimeEnd:"", // 创建时间结束 ,
          createTimeStart: "", //创建时间起始 ,
          orderColumn :"create_time",
          orderIndex: "desc" ,//排序方式（倒序desc，asc升序） ,
          page: "" ,//页码 ,
          placeNative: "", //籍贯 ,
          schoolId: "" ,//学校id ,
          sex: "" ,//学生性别（1男，2女） ,
          size: "" ,//每页条数 ,
          teacherName: "", //老师姓名 ,
          tel: "" ,//手机号码 ,
          totalCourseEnd:"",
          totalCourseStart:"",
          teacherId:""
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
          address:"",// 地址 ,
          age :"",// 年龄 ,
          clazzId :"",// 班级id ,
          courseIds :[],// 课程id数组 ,
          placeNative:"",// 籍贯 ,
          schoolId:"",//学校id ,
          sex:"1",//:"",//性别（1男，2女） ,
          teacherName:"",// 教师姓名 ,
          tel :"",// 账号，传手机号
				},
        detailData:{

        },
				editOutputRules: {
					teacherName: [{
						required: true,
						message: '请输入教师名',
						trigger: 'blur'
					}],
          sex: [{
						required: true,
						message: '请选择性别',
						trigger: 'blur'
					}],
					age: [{
						required: true,
						message: '请输入年龄',
						trigger: 'blur'
					}],
          address: [{
						required: true,
						message: '请输入地址',
						trigger: 'blur'
					}],
          tel: [{
						required: true,
						message: '请输入账号',
						trigger: 'blur'
					}],
          placeNative:[{
            required: true,
						message: '请选择籍贯',
						trigger: 'change'
          }],
          schoolId:[{
            required: true,
						message: '请选择学校',
						trigger: 'change',
            type:"number"
          }],
          clazzId:[{
            required: true,
						message: '请选择班级',
						trigger: 'change',
            type:"number"
          }]

				},
				editBannerDialog: false,
        detailId:0,
        clazz_list:[],
        school_list:[],
        course_list:[],
        detailDialog:false


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
      this.getClazzList();
      this.getSchoolList();
      this.getCourseList();
		},
		created() {
			this.userTelephone = sessionStorage.getItem('userTelephone');

		},
		methods: {
      sortby(val){
        console.log(val);
        //（age,avg_score,total_score,total_course,entry_time,create_time）
        let orderColumn = "create_time";

        if(val.prop=="totalCourse"){
          orderColumn = "total_course";
        }
        if(val.prop=="totalStu"){
          orderColumn = "total_stu";
        }
        if(val.prop=="createTime）"){
          orderColumn = "create_time";
        }
        this.searchParams.orderColumn = orderColumn;
        this.searchParams.orderIndex = this.searchParams.orderIndex=="asc"?"desc":"asc";
        this.getLoanUserList();
      },
      clickCheck(i,a){
        console.log(i,a);
        //console.log(this.editOutputData.sex);
        this.course_list[a].ischecked = i;
        this.$forceUpdate() ;
      },
			add(){
        //this.$editOutputRules.reset();
        // this.$nextTick(function() {
        //   this.$refs["editOutput"].resetFields();
        // })
        this.editOutputData = {
          address:"",// 地址 ,
          age :"",// 年龄 ,
          clazzId :"",// 班级id ,
          courseIds :[],// 课程id数组 ,
          placeNative:"",// 籍贯 ,
          schoolId:"",//学校id ,
          sex:"1",//:"",//性别（1男，2女） ,
          teacherName:"",// 教师姓名 ,
          tel :"",// 账号，传手机号
				},

        this.editBannerDialog= true;
        this.getCourseList();
        this.$forceUpdate() ;
      },
      // resetForm(formName) {
      //
      //    this.$nextTick(function() {
      //      this.$refs[formName].resetFields();
      //    });
      //    this.editBannerDialog= false;
      // },

			handleCurrentChange(val) {
				this.page = val;
				this.getLoanUserList();
			},
      handleSizeChange(val){
        this.size =  val;
        this.getLoanUserList();
      },
      searchData(){
        this.page = 1;
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
				this.searchParams = {
          address:"" ,//地址 ,
          ageEnd:"" ,//年龄范围结束 ,
          ageStart: "", //年龄范围起始 ,
          clazzId:"", // 班级id ,
          createTimeEnd:"", // 创建时间结束 ,
          createTimeStart: "", //创建时间起始 ,
          orderColumn :"create_time",
          orderIndex: "desc" ,//排序方式（倒序desc，asc升序） ,
          page: "" ,//页码 ,
          placeNative: "", //籍贯 ,
          schoolId: "" ,//学校id ,
          sex: "" ,//学生性别（1男，2女） ,
          size: "" ,//每页条数 ,
          teacherName: "", //老师姓名 ,
          tel: "" ,//手机号码 ,
          totalCourseEnd:"",
          totalCourseStart:""
        };
        this.getLoanUserList();
			},
			//查询数据
			getLoanUserList() {
				let params = {
          address:this.searchParams.address ,//地址 ,
          ageEnd:this.searchParams.ageEnd ,//年龄范围结束 ,
          ageStart: this.searchParams.ageStart, //年龄范围起始 ,
          clazzId:this.searchParams.clazzId, // 班级id ,
          createTimeEnd:this.searchParams.createTimeEnd, // 创建时间结束 ,
          createTimeStart: this.searchParams.createTimeStart, //创建时间起始 ,
          orderColumn: this.searchParams.orderColumn ,//排序
          orderIndex: this.searchParams.orderIndex ,//排序方式（倒序desc，asc升序） ,
          page: this.page ,//页码 ,
          placeNative: this.searchParams.placeNative, //籍贯 ,
          totalCourseStart:this.searchParams.totalCourseStart,
          totalCourseEnd:this.searchParams.totalCourseEnd,
          schoolId: this.searchParams.schoolId ,//学校id ,
          sex: this.searchParams.sex ,//学生性别（1男，2女） ,
          size:this.size ,//每页条数 ,
          teacherName: this.searchParams.teacherName, //老师姓名 ,
          tel: this.searchParams.tel ,//手机号码 ,
          teacherId:this.searchParams.teacherId
        }
				teacherList(params).then(res => {
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
        clazzList(params).then(res => {
          if (res.code == 0) {
            this.clazz_list = res.data
          }
        })
      },
      getCourseList(){
        let params = {};
        courseList(params).then(res => {
          if (res.code == 0) {
            this.course_list = res.data;
            this.course_list.forEach((item,k)=>{
              this.course_list[k].ischecked = false;
            })
            console.log(this.course_list)
          }
        })
      },
      getSchoolList(){
        let params = {};
        schoolList(params).then(res => {
          if (res.code == 0) {
            this.school_list = res.data
          }
        })
      },
      getTeacherDetail(id){
        let params = {id:id};
        teacherDetail(params).then(res => {
          if (res.code == 0) {
            this.detailData = res.data;
            this.detailData.sex = res.data.sex.toString();
          }
        })
      },
			handleSelectionChange(val) {
				this.batchSelection = val;
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
				this.detailDialog = true;
				this.detailId = id;
				this.getDetail();
			},
			//查询客户详情
			getDetail() {
				let params = {
					id: this.detailId,
				};
				teacherDetail(params).then(res => {
					if (res.code == 0) {
						this.detailData = res.data;
            this.detailData.sex = res.data.sex.toString();
					} else {
						this.$message(res.msg);
					}
				})
			},
			//编辑保存按钮
			editSubmitForm(formName) {
        //console.log(this.course_list);
        let courseIds = [];
        this.course_list.forEach((item,key)=>{
          if(item.ischecked==true){
            courseIds.push(item.id);
          }
        })
        if(courseIds.length==0){
          this.$message.error("请至少选择一门课程");
          return false;
        }
				this.$refs[formName].validate((valid) => {
					if (valid) {

						let param = {
              address:this.editOutputData.address,// 地址 ,
              age :this.editOutputData.age,// 年龄 ,
              clazzId :this.editOutputData.clazzId,// 班级id ,
              courseIds :courseIds,// 课程id数组 ,
              placeNative:this.editOutputData.placeNative,// 籍贯 ,
              schoolId:this.editOutputData.schoolId,//学校id ,
              sex:this.editOutputData.sex,//:"",//性别（1男，2女） ,
              teacherName:this.editOutputData.teacherName,// 教师姓名 ,
              tel :this.editOutputData.tel,// 账号，传手机号
						};
						addTeacher(param).then((res) => {
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
        this.editBannerDialog = false;
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

    .dialogBox1 .el-form-item {
        width: 100%
    }

    .dialogBox1 .el-form-item .el-input {
        display: inline-block;
        width: 150px;
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

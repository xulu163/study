<template>
    <section style="position: relative;">
        <!--工具条-->
        <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
            <el-form :inline="true" :model="searchParams" ref="statisticSearch">
                <el-form-item label="学生ID:">
                    <el-input v-model="searchParams.stuId" id="stuId" name="stuId" size="mini" style="width:100px"  clearable placeholder="请输入学生ID"></el-input>
                </el-form-item>
                <el-form-item label="学生姓名:">
                    <el-input v-model="searchParams.stuName"  id="stuName" name="stuName"size="mini" style="width:120px"  clearable placeholder="请输入学生姓名"></el-input>
                </el-form-item>

                <el-form-item label="性别：">
                    <el-select v-model="searchParams.sex" id="sex" name="sex" size="mini" style="width:80px"  clearable placeholder="性别">
                        <el-option label="男" value="1"></el-option>
                        <el-option label="女" value="2"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="年龄:">
                  <el-input v-model="searchParams.ageStart" id="ageStart" name="ageStart" size="mini" clearable style="width:50px"></el-input>
                </el-form-item>
                <el-form-item label="-">
                  <el-input v-model="searchParams.ageEnd" id="ageEnd" name="ageEnd" size="mini" clearable style="width:50px"></el-input>
                </el-form-item>
                <el-form-item label="学校:">
                  <el-select v-model="searchParams.schoolId" id="schoolId" name="schoolId" size="mini" style="width:110px" clearable placeholder="请选择学校">
                    <el-option v-for="item in school_list" :key="item.name" :label="item.name" :value="item.id" ></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="班级:">
                  <el-select v-model="searchParams.clazzId" id="clazzId" name="clazzId" size="mini" style="width:110px"  clearable placeholder="请选择班级">
                    <el-option v-for="item in clazz_list" :key="item.name" :label="item.name" :value="item.id" ></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="籍贯:">
                  <el-select v-model="searchParams.placeNative" id="placeNative" name="placeNative" size="mini" style="width:110px"  clearable placeholder="请选择籍贯">
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
                <el-form-item label="家庭住址:">
                    <el-input v-model="searchParams.address" id="address" name="address" size="mini" style="width:120px"   clearable placeholder="请输入家庭住址"></el-input>
                </el-form-item>
                <el-form-item label="联系电话:">
                    <el-input v-model="searchParams.tel" id="tel" name="tel" size="mini" style="width:100px"  clearable placeholder="请输入联系电话"></el-input>
                </el-form-item>
                <el-form-item label="教师:">
                    <el-input v-model="searchParams.teacherName" id="teacherName" name="teacherName" size="mini" style="width:100px"  clearable placeholder="请输入教师"></el-input>
                </el-form-item>
                <el-form-item label="平均分数:">
                  <el-input v-model="searchParams.avgScoreStart" id="avgScoreStart" name="avgScoreStart" size="mini"  clearable style="width:50px"></el-input>
                </el-form-item>
                <el-form-item label="-">
                  <el-input v-model="searchParams.avgScoreEnd" id="avgScoreEnd" name="avgScoreEnd" size="mini"  clearable style="width:50px"></el-input>
                </el-form-item>
                <el-form-item label="总分数:">
                  <el-input v-model="searchParams.totalScoreStart"  id="totalScoreStart" name="totalScoreStart" size="mini"  clearable style="width:50px"></el-input>
                </el-form-item>
                <el-form-item label="-">
                  <el-input v-model="searchParams.totalScoreEnd" id="totalScoreEnd" name="totalScoreEnd" size="mini"  clearable style="width:50px"></el-input>
                </el-form-item>
                <el-form-item label="课程数:">
                  <el-input v-model="searchParams.totalCourseStart" id="totalCourseStart" name="totalCourseStart" size="mini"  clearable style="width:50px"></el-input>
                </el-form-item>
                <el-form-item label="-">
                  <el-input v-model="searchParams.totalCourseEnd" id="totalCourseEnd" name="totalCourseEnd" size="mini"  clearable style="width:50px"></el-input>
                </el-form-item>
                <el-form-item label="入学日期" prop='entryTimeStart'>
                    <el-date-picker type="date" v-model='searchParams.entryTimeStart' id="entryTimeStart" name="entryTimeStart" size="mini" style="width:120px"  placeholder="年/月/日" ></el-date-picker>&nbsp;&nbsp;&nbsp;--
                </el-form-item>
                <el-form-item prop='entryTimeEnd'>
                  <el-date-picker type="date" v-model='searchParams.entryTimeEnd' id="entryTimeEnd" name="entryTimeEnd" size="mini" style="width:120px"  placeholder="年/月/日"></el-date-picker>
                </el-form-item>
                <el-form-item label="创建时间" prop='createTimeStart'>
                    <el-date-picker type="date" v-model='searchParams.createTimeStart' id="createTimeStart" name="createTimeStart" size="mini" style="width:120px"  placeholder="年/月/日" ></el-date-picker>&nbsp;&nbsp;&nbsp;--
                </el-form-item>
                <el-form-item prop='createTimeEnd'>
                    <el-date-picker type="date"  v-model='searchParams.createTimeEnd' id="createTimeEnd" name="createTimeEnd" size="mini" style="width:120px"  placeholder="年/月/日"></el-date-picker>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" id="search" name="searchbtn" @click="searchData()"><i class="fa fa-search"></i> 搜索</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" id="reset" name="resetbtn" @click="reset()"><i class="fa"></i> 重置</el-button>
                </el-form-item>


            </el-form>
        </el-col>
        <el-col  :span="24" class="toolbar">
          <el-button type="primary" class="importBtn" id="addbtn" name="addbtn" :disabled="isadmin"  @click="add()">添加</el-button>
          <el-button type="primary" class="importBtn" id="delbtn" name="delbtn" @click="batchDelete()">删除</el-button>
        </el-col>



        <!--列表-->
        <el-table :data="loanUserList" ref="multipleTable" @selection-change="handleSelectionChange" @sort-change="sortby"  v-loading="listLoading" border style="width: 100%;">
            <el-table-column type="selection" label="全选" header-align="center" align="center" min-width="20"></el-table-column>
            <el-table-column prop="id" label="ID" header-align="center" align="center" min-width="190">
              <template slot-scope="scope">
                  <a @click="detailBtn(scope.row.id)" href="#">{{scope.row.id}}</a>
              </template>
            </el-table-column>
            <el-table-column prop="name" label="学生姓名" header-align="center" min-width="100" align="center"></el-table-column>
            <el-table-column prop="sex" label="性别" min-width="70" header-align="center" align="center">
              <template slot-scope="scope">
                  <span>{{ scope.row.sex==1?"男":"女" }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="age" label="年龄" sortable="custom"   header-align="center" min-width="90" align="center"></el-table-column>
            <el-table-column prop="schoolName" label="学校" header-align="center" min-width="180" align="center"></el-table-column>
            <el-table-column prop="clazzName" label="班级" header-align="center" min-width="180" align="center"></el-table-column>
            <el-table-column prop="placeNative" label="籍贯" header-align="center" min-width="70" align="center"></el-table-column>
            <el-table-column prop="address" label="家庭住址" header-align="center" min-width="600" align="center"></el-table-column>
            <el-table-column prop="tel" label="联系电话" header-align="center" min-width="125" align="center"></el-table-column>
            <el-table-column prop="avgScore" label="平均分数" sortable="custom" header-align="center" min-width="120" align="center"></el-table-column>
            <el-table-column prop="totalScore" label="总分数" sortable="custom" header-align="center" min-width="100" align="center"></el-table-column>
            <el-table-column prop="totalCourse" label="课程数" header-align="center" align="center"></el-table-column>
            <el-table-column prop="entryTime" label="入学日期" sortable="custom" header-align="center" min-width="125" align="center">
              <template slot-scope="scope">
                  <span>{{ formatDate(scope.row.entryTime) }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="teacherName" label="教师" header-align="center" align="center"></el-table-column>
            <el-table-column prop="createTime" label="创建时间" sortable="custom" header-align="center" min-width="125" align="center">
              <template slot-scope="scope">
                  <span>{{ formatDate(scope.row.createTime) }}</span>
              </template>
            </el-table-column>
            <el-table-column label="操作" header-align="center" align="center" min-width="160">
                <template slot-scope="scope">
                    <el-button type="primary" :disabled="isadmin" :id="scope.row.id" size="small" @click="editBtn(scope.row.id)" >编辑</el-button>
                    <!-- <el-button type="danger" size="small" @click="deleteBtn(scope.row.id)" >删除</el-button> -->
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
            <!-- <el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size=size  :total="total" style="float:right;"> </el-pagination> -->
        </el-col>

        <!--弹窗框-->
        <div id="modelAll">
            <!--编辑banner-->
            <el-dialog title="添加学生" custom-class="dialogBox1" :visible.sync="editBannerDialog" @close="resetForm('addOutput')" width="100%">
                <el-form :model="editOutputData" :inline="true" :rules="addOutputRules" ref="addOutput" label-width="100px">
                    <el-row>
                      <el-col :span="12">
                        <el-form-item label="学生姓名：" prop="name">
                            <el-input v-model="editOutputData.name" placeholder="请填写" size="small" style="width:150px"></el-input>
                        </el-form-item>
                        <el-form-item label="性别：" prop="sex" >
                          <el-radio v-model="editOutputData.sex" label="1" >男</el-radio>
                          <el-radio v-model="editOutputData.sex" label="2" >女</el-radio>
                        </el-form-item>
                        <el-form-item label="年龄：" prop="age">
                            <el-input v-model="editOutputData.age" placeholder="请填写年龄" size="small"></el-input>
                        </el-form-item>
                        <el-form-item label="家庭住址：" prop="address">
                            <el-input v-model="editOutputData.address" placeholder="请填写家庭住址" size="small"></el-input>
                        </el-form-item>

                        <el-form-item label="籍贯：" prop="placeNative">
                          <el-select v-model="editOutputData.placeNative" clearable placeholder="请选择籍贯">
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

                        <el-form-item label="入学日期：" prop="entryTime">
                            <el-date-picker type="date" format="yyyy 年 MM 月 dd 日" value-format="yyyy-MM-dd" v-model='editOutputData.entryTime' placeholder="年/月/日" ></el-date-picker>
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

                        <el-form-item label="" v-for="(item,key) in own_course_list" :key="key">
                            <el-checkbox v-model="item.ischeckeds" @change="clickCheck(item.ischeckeds,key)" label=""></el-checkbox>
                            <el-input v-model="item.name" placeholder="请填写" size="small" style="width:100px"></el-input>
                            分数<el-input v-model="item.score" placeholder="请填写" :disabled="!item.ischecked"  size="small" style="width:100px"></el-input>
                        </el-form-item>

                      </el-col>
                    </el-row>
                    <el-form-item class="btnBox" style="text-align:center">
                        <el-button type="primary" @click="resetForm('addOutput')">取消</el-button>
                        <el-button type="primary" @click="editSubmitForm('addOutput')">提交</el-button>
                    </el-form-item>
                </el-form>
            </el-dialog>

            <!--编辑banner-->
            <el-dialog title="编辑学生" custom-class="dialogBox1" :visible.sync="editDialog" @close="resetForm1('editOutputRules')" width="100%">
                <el-form :model="addOutputData" :inline="true" :rules="editOutputRules" ref="editOutputRules" label-width="100px">
                    <el-row>
                      <el-col :span="12">
                        <el-form-item label="学生姓名：" prop="name">
                            <el-input v-model="addOutputData.name" placeholder="请填写" size="small" style="width:150px"></el-input>
                        </el-form-item>
                        <el-form-item label="性别：" prop="sex">
                          <el-radio v-model="addOutputData.sex" label="1" >男</el-radio>
                          <el-radio v-model="addOutputData.sex" label="2" >女</el-radio>
                        </el-form-item>
                        <el-form-item label="年龄：" prop="age">
                            <el-input v-model="addOutputData.age" placeholder="请填写年龄" size="small"></el-input>
                        </el-form-item>
                        <el-form-item label="家庭住址：" prop="address">
                            <el-input v-model="addOutputData.address" placeholder="请填写家庭住址" size="small"></el-input>
                        </el-form-item>

                        <el-form-item label="籍贯：" prop="placeNative">
                          <el-select v-model="addOutputData.placeNative" clearable placeholder="请选择籍贯">
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
                            <el-input v-model="addOutputData.tel" placeholder="请填写联系电话"size="small"></el-input>
                        </el-form-item>

                        <el-form-item label="入学日期：" prop="entryTime">
                            <el-date-picker type="date" format="yyyy 年 MM 月 dd 日" value-format="yyyy-MM-dd" v-model='addOutputData.entryTime' placeholder="年/月/日" ></el-date-picker>
                        </el-form-item>
                        <el-form-item label="学校：" prop="schoolId">
                          <el-select v-model="addOutputData.schoolId" clearable placeholder="请选择学校">
                            <el-option v-for="item in school_list" :key="item.name" :label="item.name" :value="item.id" ></el-option>
                          </el-select>
                        </el-form-item>
                        <el-form-item label="班级：" prop="clazzId">
                          <el-select v-model="addOutputData.clazzId" clearable placeholder="请选择班级">
                            <el-option v-for="item in clazz_list" :key="item.name" :label="item.name" :value="item.id" ></el-option>
                          </el-select>
                        </el-form-item>
                      </el-col>
                      <el-col :span="12">

                      <el-form-item label="" v-for="(item,key) in addOutputData.courses" :key="key">
                          <el-checkbox v-model="item.ischeckeds" @change="clickCheck1(item.ischeckeds,key)" label=""></el-checkbox>
                          <el-input v-model="item.name" placeholder="请填写" size="small" style="width:100px"></el-input>
                          分数<el-input v-model="item.score" placeholder="请填写" :disabled="!item.ischeckeds"  size="small" style="width:100px"></el-input>
                      </el-form-item>

                      </el-col>
                    </el-row>
                    <el-form-item class="btnBox" style="text-align:center">
                        <el-button type="primary" @click="resetForm1('editOutputRules')">取消</el-button>
                        <el-button type="primary" @click="addSubmitForm('editOutputRules')">提交</el-button>
                    </el-form-item>
                </el-form>
            </el-dialog>

            <!--编辑banner-->
            <el-dialog title="学生详情" custom-class="dialogBox1" :visible.sync="detailDialog" width="100%">
                <el-form :model="addOutputData" :inline="true"  ref="detailRules" label-width="100px">
                    <el-row>
                      <el-col :span="12">
                        <el-form-item label="学生姓名：">
                            <el-input v-model="addOutputData.name" placeholder="请填写" size="small" style="width:150px"></el-input>
                        </el-form-item>
                        <el-form-item label="性别：" prop="sex">
                          <el-radio v-model="addOutputData.sex" label="1" >男</el-radio>
                          <el-radio v-model="addOutputData.sex" label="2" >女</el-radio>
                        </el-form-item>
                        <el-form-item label="年龄：">
                            <el-input v-model="addOutputData.age" placeholder="请填写年龄" size="small"></el-input>
                        </el-form-item>
                        <el-form-item label="家庭住址：">
                            <el-input v-model="addOutputData.address" placeholder="请填写家庭住址" size="small"></el-input>
                        </el-form-item>

                        <el-form-item label="籍贯：">
                          <el-select v-model="addOutputData.placeNative" clearable placeholder="请选择籍贯">
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
                            <el-input v-model="addOutputData.tel" placeholder="请填写联系电话"size="small"></el-input>
                        </el-form-item>

                        <el-form-item label="入学日期：">
                            <el-date-picker type="date" format="yyyy 年 MM 月 dd 日" value-format="yyyy-MM-dd" v-model='addOutputData.entryTime' placeholder="年/月/日" ></el-date-picker>
                        </el-form-item>
                        <el-form-item label="学校：">
                          <el-select v-model="addOutputData.schoolId" clearable placeholder="请选择学校">
                            <el-option v-for="item in school_list" :key="item.name" :label="item.name" :value="item.id" ></el-option>
                          </el-select>
                        </el-form-item>
                        <el-form-item label="班级：">
                          <el-select v-model="addOutputData.clazzId" clearable placeholder="请选择班级">
                            <el-option v-for="item in clazz_list" :key="item.name" :label="item.name" :value="item.id" ></el-option>
                          </el-select>
                        </el-form-item>
                      </el-col>
                      <el-col :span="12">

                      <el-form-item label="" v-for="(item,key) in addOutputData.courses" :key="key">
                          <el-checkbox v-model="item.ischeckeds"  label=""></el-checkbox>
                          <el-input v-model="item.name" placeholder="请填写" size="small" style="width:100px"></el-input>
                          分数<el-input v-model="item.score" placeholder="请填写"  size="small" style="width:100px"></el-input>
                      </el-form-item>

                      </el-col>
                    </el-row>
                    <el-form-item class="btnBox" style="text-align:center">
                        <el-button type="primary" @click="detailDialog = false">取消</el-button>
                    </el-form-item>
                </el-form>
            </el-dialog>

            <el-dialog title="删除学生" fullscreen custom-class="dialogBox2" :visible.sync="deleteDialog" width="20%">
              <span>请确认是否删除以下{{batchSelection.length}}位学生</span>
              <div style="height:200px;overflow:auto">
                <p v-for="item in batchSelection" >
                  姓名：{{item.name}} ID:{{item.id}}
                </p>
              </div>
              <span slot="footer" class="dialog-footer" style="text-align:center">
                <el-button @click="deleteDialog = false">取 消</el-button>
                <el-button type="primary" @click="confirmBatchDel">确 定</el-button>
              </span>
            </el-dialog>

            <!-- <el-dialog
              title="提示"
              :visible.sync="deleteDialog"
              width="30%"
              center>
              <span>需要注意的是内容是默认不居中的</span>
              <span slot="footer" class="dialog-footer">
                <el-button @click="centerDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="centerDialogVisible = false">确 定</el-button>
              </span>
            </el-dialog> -->


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
    addStudent,
    deleteStudent,
    updateStudent,
    studentDetail,
    clazzList,
    courseList,
    teacherCourseList,
    schoolList,
    ownCourseList

	} from '../../api/api';
	export default {
		data() {

			return {
        isadmin:false,
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
          orderColumn: "create_time" ,//排序字段（age,avg_score,total_score,total_course,entry_time,create_time） ,
          orderIndex: "desc" ,//排序方式（倒序desc，asc升序） ,
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
          address:"" ,//地址 ,
          age: "" ,//年龄 ,
          clazzId:"" ,// 班级id ,
          courseList:[] ,// 课程id数组 ,
          entryTime:"" ,//: 入学时间 ,
          name:"" ,//: 姓名 ,
          placeNative:"" ,//籍贯 ,
          schoolId:"" , //学校id ,
          sex:"1" , //性别（1男，2女） ,
          teacherId:"" ,// 老师id ,
          tel:"" ,//  手机号
				},
        editDialog:false,
        addOutputData: {
          id:"",
          address:"" ,//地址 ,
          age: "" ,//年龄 ,
          clazzId:"" ,// 班级id ,
          courseList:[] ,// 课程id数组 ,
          entryTime:"" ,//: 入学时间 ,
          name:"" ,//: 姓名 ,
          placeNative:"" ,//籍贯 ,
          schoolId:"" , //学校id ,
          sex:"1" , //性别（1男，2女） ,
          teacherId:"" ,// 老师id ,
          tel:"" ,//  手机号
				},
				editOutputRules: {
          name: [{
						required: true,
						message: '请输入学生姓名',
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
          entryTime:[{
            type:'date',
						required: true,
						message: '请输入入学日期',
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
        addOutputRules:{
          name: [{
						required: true,
						message: '请输入学生姓名',
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
          entryTime:[{
            type:'date',
						required: true,
						message: '请输入入学日期',
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
        own_course_list:[],
        teacher_course_list:[],
        teacherId:0,
        deleteDialog:false,
        detailDialog:false,


			}
		},
		mounted() {
			let user = sessionStorage.getItem('user');
      let isadmin = sessionStorage.getItem('isadmin');
      console.log(isadmin,'fffffff');
      if(isadmin){
        this.isadmin = true;
      }else{
        this.isadmin = false;
      }
			if (user) {
				user = JSON.parse(user);
				let token = user.token || '';
        let name = user.name ||'';

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

      //console.log(this.isadmin);
		},
		methods: {
      sortby(val){
        console.log(val);
        //（age,avg_score,total_score,total_course,entry_time,create_time）
        let orderColumn = "create_time";
        if(val.prop=="age"){
          orderColumn ="age"
        }
        if(val.prop=="avgScore"){
          orderColumn = "avg_score";
        }
        if(val.prop=="totalScore"){
          orderColumn = "total_score";
        }
        if(val.prop=="totalCourse"){
          orderColumn = "total_course";
        }
        if(val.prop=="entryTime"){
          orderColumn = "entry_time";
        }
        if(val.prop=="createTime"){
          orderColumn = "create_time";
        }
        this.searchParams.orderColumn = orderColumn;
        this.searchParams.orderIndex = this.searchParams.orderIndex=="asc"?"desc":"asc";
        this.getLoanUserList();
      },
      batchDelete(){
        this.deleteDialog = true;
      },
      clickCheck(i,a){
        //console.log(this.editOutputData.sex);
        console.log(i,a);
        this.own_course_list[a].ischecked = i;
        if(i!=false){

          this.own_course_list[a].score = "";
        }

      },
      clickCheck1(i,a){
        //console.log(this.editOutputData.sex);
        console.log(i,a);
        this.addOutputData.courses[a].ischeckeds = i;
        this.$forceUpdate();
        if(i!=false){

          this.addOutputData.courses[a].score = "";
        }

      },
			add(){
        this.editBannerDialog= true;
        this.editOutputData={
          address:"" ,//地址 ,
          age: "" ,//年龄 ,
          clazzId:"" ,// 班级id ,
          courseList:[] ,// 课程id数组 ,
          entryTime:"" ,//: 入学时间 ,
          name:"" ,//: 姓名 ,
          placeNative:"" ,//籍贯 ,
          schoolId:"" , //学校id ,
          sex:"1" , //性别（1男，2女） ,
          teacherId:"" ,// 老师id ,
          tel:"" ,//  手机号
				};
        this.getOwnCourseList();

        console.log(this.own_course_list);
      },
      searchData(){
        this.page = 1;
        this.searchParams.orderColumn="create_time";
        this.searchParams.orderIndex="desc";
        this.getLoanUserList();
      },
			handleCurrentChange(val) {
				this.page = val;
				this.getLoanUserList();
			},
      handleSizeChange(val){
        this.size =  val;
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
          avgScoreEnd: "", //平均分结束 ,
          avgScoreStart: "", //平均分起始 ,
          clazzId:"", // 班级id ,
          createTimeEnd:"", // 创建时间结束 ,
          createTimeStart: "", //创建时间起始 ,
          entryTimeEnd: "" ,//入学时间结束 ,
          entryTimeStart: "", //入学时间起始 ,
          orderColumn: "create_time" ,//排序字段（age,avg_score,total_score,total_course,entry_time,create_time） ,
          orderIndex: "desc" ,//排序方式（倒序desc，asc升序） ,
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
        };
        this.getLoanUserList();
			},
			//查询数据
			getLoanUserList() {
				let params = {
          "address": this.searchParams.address,
          "ageEnd": this.searchParams.ageEnd,
          "ageStart": this.searchParams.ageStart,
          "avgScoreEnd": this.searchParams.avgScoreEnd,
          "avgScoreStart": this.searchParams.avgScoreStart,
          "clazzId": this.searchParams.clazzId,
          "createTimeEnd": this.searchParams.createTimeEnd,
          "createTimeStart": this.searchParams.createTimeStart,
          "entryTimeEnd": this.searchParams.entryTimeEnd,
          "entryTimeStart": this.searchParams.entryTimeStart,
          "orderColumn": this.searchParams.orderColumn,
          "orderIndex": this.searchParams.orderIndex,
          "page": this.page,
          "placeNative": this.searchParams.placeNative,
          "schoolId": this.searchParams.schoolId,
          "sex": this.searchParams.sex,
          "size": this.size,
          "stuId": this.searchParams.stuId,
          "stuName": this.searchParams.stuName,
          //"teacherId": this.searchParams.address,
          "teacherName": this.searchParams.teacherName,
          "tel": this.searchParams.tel,
          "totalCourseEnd":this.searchParams.totalCourseEnd,
          "totalCourseStart":this.searchParams.totalCourseStart,
          "totalScoreEnd": this.searchParams.totalScoreEnd,
          "totalScoreStart": this.searchParams.totalScoreStart
        }
				studentList(params).then(res => {
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
            this.course_list = res.data
          }
        })
      },
      getOwnCourseList(){
        let params = {};
        ownCourseList(params).then(res => {
          if (res.code == 0) {

            this.own_course_list = res.data
            this.own_course_list.forEach((item,key)=>{
              this.own_course_list[key].ischecked = false;
            })

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
      getTeacherCourseList(){
        let params = {id:this.teacherId};
        teacherCourseList(params).then(res => {
          if (res.code == 0) {
            this.teacher_course_list = res.data
          }
        })
      },
			handleSelectionChange(val) {
				this.batchSelection = val;
        console.log(val);
			},
      confirmBatchDel(){
        let ids = [];
        this.batchSelection.forEach((item,k)=>{
          ids.push(item.id);
        });

        deleteStudent(ids).then((res) => {
          if (res.code == 0) {
            this.deleteDialog =false;
            this.getLoanUserList();
            this.$message.success('删除成功');
          } else {
            this.$message.error(res.msg);
          }
        });
      },
			//删除按钮
			deleteBtn(id) {
				this.$confirm('确定删除吗？', '提示', {}).then(() => {
					let param = {
						ids: [id]
					};
					deleteStudent([id]).then((res) => {
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
				this.editDialog = true;
				this.detailId = id;

				this.getDetail1();


			},
      //编辑按钮
			detailBtn(id) {
				this.detailDialog = true;
				this.detailId = id;
        this.getDetail();

			},
			//查询客户详情
			getDetail() {
				let params = {
					id: this.detailId,
				};
				studentDetail(params).then(res => {
					if (res.code == 0) {
            res.data.courses.forEach((item,key)=>{
              res.data.courses[key].ischeckeds = true;
            })
						this.addOutputData = res.data;

            this.addOutputData.sex = res.data.sex.toString();
            this.addOutputData.age = res.data.age.toString();
            this.$forceUpdate();
					} else {
						this.$message(res.msg);
					}
				})
			},
      //查询客户详情
			getDetail1() {
				let params = {
					id: this.detailId,
				};
        let that = this;
        //let params = {};
        ownCourseList().then(result => {
          if (result.code == 0) {

            this.own_course_list = result.data

            studentDetail(params).then(res => {
    					if (res.code == 0) {
                this.own_course_list.forEach((item,key)=>{
                  this.own_course_list[key].ischeckeds = false;
                  this.own_course_list[key].stuCourseId = "";
                  res.data.courses.forEach((i,k)=>{
                    //res.data.courses[key].ischeckeds = true;
                    if(item.id==i.id){
                      this.own_course_list[key].stuCourseId = i.stuCourseId;
                      this.own_course_list[key].score = i.score;
                      this.own_course_list[key].ischeckeds = true;
                    }
                  })
                });

                // res.data.courses.forEach((item,key)=>{
                //   res.data.courses[key].ischeckeds = true;
                // })
                console.log(this.own_course_list);
    						this.addOutputData = res.data;
                this.addOutputData.courses = this.own_course_list;

                this.addOutputData.sex = res.data.sex.toString();
                this.addOutputData.age = res.data.age.toString();
    					} else {
    						this.$message(res.msg);
    					}
    				})

          }
        })

			},
			//编辑保存按钮
			editSubmitForm(formName) {
        this.editOutputData.entry_time = new Date(this.editOutputData.entry_time);
				this.$refs[formName].validate((valid) => {
					if (valid) {
            let courseList = [];
            this.own_course_list.forEach((item,k)=>{
              if(item.ischeckeds){
                courseList.push({courseId:item.id,score:item.score});
              }

            })
						let param = {
              address:this.editOutputData.address ,//地址 ,
              age:this.editOutputData.age ,//年龄 ,
              clazzId:this.editOutputData.clazzId ,// 班级id ,
              //courseList:[] ,// 课程id数组 ,
              entryTime:this.editOutputData.entryTime ,//: 入学时间 ,
              name:this.editOutputData.name ,//: 姓名 ,
              placeNative:this.editOutputData.placeNative ,//籍贯 ,
              schoolId:this.editOutputData.schoolId , //学校id ,
              sex:this.editOutputData.sex , //性别（1男，2女） ,
              //teacherId:1 ,// 老师id ,
              tel:this.editOutputData.tel ,//  手机号
              courseList:courseList
						};
						addStudent(param).then((res) => {
							if (res.code == 0) {
								this.editBannerDialog = false;
								this.getLoanUserList();
								this.$message.success('成功');
							} else {
								this.$message.error("失败");
							}
						});
					} else {
						console.log('error submit!!');
						return false;
					}
				});
			},
      addSubmitForm(formName){
        this.addOutputData.entry_time = new Date(this.addOutputData.entry_time);
        this.$refs[formName].validate((valid) => {
					if (valid) {
            let courseList = [];
            this.addOutputData.courses.forEach((item,k)=>{
              if(item.ischeckeds){
                courseList.push({courseId:item.id,id:item.stuCourseId,score:item.score});
              }

            })
						let param = {
              address:this.addOutputData.address ,//地址 ,
              age:this.addOutputData.age ,//年龄 ,
              clazzId:this.addOutputData.clazzId ,// 班级id ,
              //courseList:[] ,// 课程id数组 ,
              entryTime:this.addOutputData.entryTime ,//: 入学时间 ,
              name:this.addOutputData.name ,//: 姓名 ,
              placeNative:this.addOutputData.placeNative ,//籍贯 ,
              schoolId:this.addOutputData.schoolId , //学校id ,
              sex:this.addOutputData.sex , //性别（1男，2女） ,
              //teacherId:1 ,// 老师id ,
              tel:this.addOutputData.tel ,//  手机号
              courseList:courseList,
              id:this.addOutputData.id
						};
						updateStudent(param).then((res) => {
							if (res.code == 0) {
								this.editDialog = false;
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
        this.editBannerDialog = false;
				this.$refs[formName].resetFields();
			},
      resetForm1(formName) {
        this.editDialog = false;
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
 .dialogBox2{
   width: 320px;
 }

</style>
<style>

</style>

import axios from 'axios';
import ElementUI from 'element-ui'

let URL = process.env.API_URL;
var access_token = process.env.ACCESS_TOKEN
//let URL = 'http://192.168.1.128:1030/fjs/'//测试环境
//var access_token = "467405f6-331c-4914-beb7-42027bf09a01";
let baseParis = URL+"paris-backend/api/v1";//paris服务
let baseParis1 = URL + "paris-backend/apiwithout/v1";
let baseParisApi = URL + "paris-backend/api/";

let basePrometheus = URL+"prometheus-backend/api/v1";//Prometheus服务
let baseWallet = URL+"wallet-backend/api/v1";//Wallet服务
let baseWalletAnother = URL+"wallet-backend";//Wallet服务另
let loginURL = URL + 'thor-backend';//登录




let user = sessionStorage.getItem('user');
console.log(user);
if (user) {
	user = JSON.parse(user);
	let token = user.token || '';
	if(token){
		axios.defaults.headers.common['Authorization'] = token;
	}
}

//设置响应数据为文件流
var instance = axios.create({
    responseType: 'blob' //返回数据的格式，可选值为arraybuffer,blob,document,json,text,stream，默认值为json
});


// 添加请求拦截器
axios.interceptors.request.use(function (config) {
    // 在发送请求之前做些什么

    return config;
  }, function (error) {
    // 对请求错误做些什么

    return Promise.reject(error);
});

// 添加响应拦截器
axios.interceptors.response.use(function (response) {
    // 对响应数据做点什么

    return response;
  }, function (error) {
    // 对响应错误做点什么
    ElementUI.Message({
              message: error.response.data.error,
              type: 'error'
	});
});



//用户管理
export const requestLogin = params => { //登录
	return axios.post(`${URL}/user/login`, params , {emulateJson:true}).then(res => res.data);
};
export const logout = params => { //退出登录
	return axios.post(`${URL}/user/logout`, params , {emulateJson:true}).then(res => res.data);
};
export const updatePwd = params => { //修改用户密码
	return axios.post(`${URL}/user/updatePwd`, params , {emulateJson:true}).then(res => res.data);
};

//学生管理
export const studentList = params => {//学生列表
	return axios.post(`${URL}/student/list`, params , {emulateJson:true}).then(res => res.data);
};
export const deleteStudent = params => { //删除学生
	return axios.post(`${URL}/student/delete`, params , {emulateJson:true}).then(res => res.data);
};
export const addStudent = params => { //添加学生
	return axios.post(`${URL}/student/add`, params , {emulateJson:true}).then(res => res.data);
};
export const updateStudent = params => { //添加学生
	return axios.post(`${URL}/student/updateStu`, params , {emulateJson:true}).then(res => res.data);
};
export const studentDetail = params => { //学生详情
	return axios.get(`${URL}/student/detail`,  { params: params }).then(res => res.data);
};

//教师管理
export const teacherList = params => {  //教师列表
	return axios.post(`${URL}/teacher/list`, params , {emulateJson:true}).then(res => res.data);
};
export const deleteTeacher = params => { //删除老师
	return axios.post(`${URL}/teacher/delete`, params , {emulateJson:true}).then(res => res.data);
};
export const addTeacher = params => { //添加教师
	return axios.post(`${URL}/teacher/add`, params , {emulateJson:true}).then(res => res.data);
};
export const teacherDetail = params => { //教师详情
	return axios.get(`${URL}/teacher/detail`,  { params: params }).then(res => res.data);
};

//学校管理
export const schoolList = params => {  //学校列表
	return axios.get(`${URL}/school/list`, { params: params }).then(res => res.data);
};
//获取用户菜单
export const menuList = params => {  //用户菜单
	return axios.get(`${URL}/menu/list`,  { params: params }).then(res => res.data);
};
//获取班级列表
export const clazzList = params => {  //获取班级列表
	return axios.get(`${URL}/clazz/list`,  { params: params }).then(res => res.data);
};
//获取课程列表
export const courseList = params => {  //获取课程列表
	return axios.get(`${URL}/course/list`,  { params: params }).then(res => res.data);
};

//学生课程列表管理
export const getStuCourseList = params => {  //教师列表
	return axios.post(`${URL}/course/query`, params , {emulateJson:true}).then(res => res.data);
};

//获取教师课程列表
export const teacherCourseList = params => {  //获取课程列表
	return axios.get(`${URL}/course/teacher`,  { params: params }).then(res => res.data);
};
//获取教师课程列表
export const ownCourseList = params => {  //获取课程列表
	return axios.get(`${URL}/course/own`,  { params: params }).then(res => res.data);
};


//权限接口
export const getModuleAuthBySystemName = params => {
	return axios.get(`${loginURL}/api/v1/getModuleAuthBySystemName`,  { params: params }).then(res => res.data);
};



//房金云商户端——热点管理
export const hotlist = params =>{//查询接口
    return axios.post(`${baseParis}/hotSpot/backend/list`, params , {emulateJson:true}).then(res => res.data);
};

export const hotAddOrUpdate = params =>{//新增和修改接口
    return axios.post(`${baseParis}/hotSpot/backend/addOrUpdate`, params , {emulateJson:true}).then(res => res.data);
};

export const hotDelete = params =>{//删除接口
    return axios.post(`${baseParis}/hotSpot/backend/delete`, params , {emulateJson:true}).then(res => res.data);
};



//房金云商户端——工具管理
//=======>营销工具
export const getMarketing = params =>{//查询已创建的专栏接口
    return axios.post(`${baseParis}/manage/marketingType/list`, params , {emulateJson:true}).then(res => res.data);
};
export const addOrUpdateMarketing = params =>{//创建或修改专栏接口
    return axios.post(`${baseParis}/manage/marketingType/addOrUpdate`, params , {emulateJson:true}).then(res => res.data);
};
export const deleteMarketing = params =>{//删除专栏接口
    return axios.post(`${baseParis}/manage/marketingType/delete`, params , {emulateJson:true}).then(res => res.data);
};
export const sortMarketing = params =>{//排序专栏接口
    return axios.post(`${baseParis}/manage/marketingType/position`, params , {emulateJson:true}).then(res => res.data);
};
export const getChildMarketing = params =>{//专栏子页查询接口
    return axios.post(`${baseParis}/manage/marketing/list`, params , {emulateJson:true}).then(res => res.data);
};
export const addChildMarketing = params =>{//专栏子页上传接口
    return axios.post(`${baseParis}/manage/marketing/add`, params , {emulateJson:true}).then(res => res.data);
};
export const editChildMarketing = params =>{//专栏子页修改接口
    return axios.post(`${baseParis}/manage/marketing/update`, params , {emulateJson:true}).then(res => res.data);
};
export const deleteChildMarketing = params =>{//专栏子页删除接口
    return axios.post(`${baseParis}/manage/marketing/delete`, params , {emulateJson:true}).then(res => res.data);
};
export const upLoadChildMarketing = baseParis + '/manage/marketing/upLoad'//专栏子页图片上传接口

//=======>广告页
export const advertiseUploadUrl = baseParis + '/advertise/upLoad'//图片上传接口

export const queryAdvertiseList = params =>{//列表查询接口
    return axios.get(`${baseParis}/advertise/queryAdvertiseList`, { params: params } ).then(res => res.data);
};

export const addAdvertise = params =>{//上传接口
    return axios.post(`${baseParis}/advertise/addAdvertise`, params , {emulateJson:true}).then(res => res.data);
};

export const deleteAdvertise = params =>{//删除接口
    return axios.get(`${baseParis}/advertise/deleteAdvertise`, { params: params }).then(res => res.data);
};

export const editAdvertise = params =>{//修改接口
    return axios.post(`${baseParis}/advertise/editAdvertise`, params , {emulateJson:true}).then(res => res.data);
};

export const queryAdvertise = params =>{//获取当前列详情接口
    return axios.get(`${baseParis}/advertise/queryAdvertise`, { params: params }).then(res => res.data);
};
//=======>悬浮窗
export const floatUploadUrl = baseParis + '/manage/floatAdvertisementConfig/upLoad'//图片上传接口

export const floatList = params =>{//列表查询接口
    return axios.post(`${baseParis}/manage/floatAdvertisementConfig/list`, params , {emulateJson:true}).then(res => res.data);
};

export const floatAddOrUpdate = params =>{//上传和修改接口
    return axios.post(`${baseParis}/manage/floatAdvertisementConfig/addOrUpdate`, params , {emulateJson:true}).then(res => res.data);
};

export const floatDelet = params =>{//删除接口
    return axios.post(`${baseParis}/manage/floatAdvertisementConfig/delete`, params , {emulateJson:true}).then(res => res.data);
};



//房金云商户端——商户端操作系统
export const queryMerchantConfig = params =>{//查询接口
    return axios.get(`${baseParis1}/queryBiosConfig`, { params: params } ).then(res => res.data);
};

export const editMerchantConfig = params =>{//修改接口
    return axios.post(`${baseParis}/editBiosConfig`, params , {emulateJson:true}).then(res => res.data);
};



//房金云商户端——版本更新配置
export const queryMerchantVersions = params =>{//查询接口
    return axios.get(`${baseParis}/versionConfig/versionListForB`, { params: params } ).then(res => res.data);
};

export const queryVersionsUpdate = params =>{//获取更新接口
    return axios.get(`${baseParis}/versionConfig/versionInfo`, { params: params } ).then(res => res.data);
};

export const editVersions = params =>{//修改接口
    return axios.post(`${baseParis}/versionConfig/editVersionConfig`, params , {emulateJson:true}).then(res => res.data);
};

export const addVersions = params =>{//新增接口
    return axios.post(`${baseParis}/versionConfig/addVersionConfig`, params , {emulateJson:true}).then(res => res.data);
};

export const queryVersionList = params =>{//更多查询接口
    return axios.get(`${baseParis}/versionConfig/versionListByTye`, { params: params } ).then(res => res.data);
};
export const getIntefRecords = params =>{//根据手机号查询银行卡、运营商接口调用
    return axios.get(`${baseParis}/getIntefRecords`, { params: params } ).then(res => res.data);
};
export const getAcResult = params =>{//根据手机号查询银行卡、运营商接口调用
    return axios.get(`${baseParis}/getAcResult`, { params: params } ).then(res => res.data);
};




//C端配置——平均贷款
export const avgConfig = params =>{//查询接口
    return axios.get(`${baseParis}/average/avgConfig`, { params: params } ).then(res => res.data);
};

export const editAvgConfig = params =>{//修改接口
    return axios.post(`${baseParis}/average/editAvgConfig`, params , {emulateJson:true}).then(res => res.data);
};

export const queryAvgConfigInfo = params =>{//获取当前修改项详情接口
    return axios.get(`${baseParis}/average/queryAvgConfigInfo`, { params: params }).then(res => res.data);
};



//C端配置——iosOS
export const queryIosConfig = params =>{//查询接口
    return axios.get(`${baseParis1}/queryIosConfig`, { params: params } ).then(res => res.data);
};

export const editIosConfig = params =>{//修改接口
    return axios.post(`${baseParis}/editIosConfig`, params , {emulateJson:true}).then(res => res.data);
};




//C端配置——立即申请配置banner配置
export const queryHomeConfig = params =>{//查询接口
    return axios.get(`${basePrometheus}/homeLoanCfg/queryHomeLoanCfg`, { params: params } ).then(res => res.data);
};

export const editHomeConfig = params =>{//修改接口
    return axios.post(`${basePrometheus}/homeLoanCfg/editHomeLoanCfg`, params , {emulateJson:true}).then(res => res.data);
};



//C端配置——轮播图配置
export const queryBannerConfig = params =>{//查询接口
    return axios.get(`${baseParis}/bannerConfig/queryBannerConfigList`, { params: params } ).then(res => res.data);
};

export const addBannerConfig = params =>{//新增接口
    return axios.post(`${baseParis}/bannerConfig/addBannerConfig`, params , {emulateJson:true}).then(res => res.data);
};

export const editBannerConfig = params =>{//修改接口
    return axios.post(`${baseParis}/bannerConfig/editBannerConfig`, params , {emulateJson:true}).then(res => res.data);
};

export const deleteBannerConfig = params =>{//删除接口
    return axios.get(`${baseParis}/bannerConfig/deleteBannerConfig`, { params: params } ).then(res => res.data);
};



//C端配置——ch5OS
export const queryH5Config = params =>{//查询接口
    return axios.get(`${baseParis1}/queryCh5Config`, { params: params } ).then(res => res.data);
};

export const editH5Config = params =>{//修改接口
    return axios.post(`${baseParis}/editCh5Config`, params , {emulateJson:true}).then(res => res.data);
};



//C端配置——版本更新配置
export const queryCVersions = params =>{//查询接口
    return axios.get(`${baseParis}/versionConfig/versionListForC`, { params: params } ).then(res => res.data);
};




//支付设置——银行限额export
export const parseExcelForLianLian = baseParis + '/manage/bankQuotaCfg/parseExcelForLianLian';//图片上传接口

export const queryLianLianInfoList = params =>{//连连支付列表查询
    return axios.get(`${baseParis}/manage/bankQuotaCfg/queryLianLianInfoList`, { params: params } ).then(res => res.data);
};



//============>房金云 C端-钱包服务
//文本规则管理
export const textRuleList = params =>{//文本规则管理列表查询
    return axios.get(`${baseWallet}/queryAllImgRule`, { params: params } ).then(res => res.data);
};

export const saveTextRule = params =>{//上传接口
    return axios.post(`${baseWallet}/saveTextRule`, params , {emulateJson:true}).then(res => res.data);
};

export const textRuleUploadUrl = baseWallet + '/uploadImg'//图片上传接口

export const editTextRule = params =>{//修改接口
    return axios.post(`${baseWallet}/updateImgInfoById`, params , {emulateJson:true}).then(res => res.data);
};

export const deleteTextRule = params =>{//删除接口
    return axios.post(`${baseWallet}/delImgInfoById`, params , {emulateJson:true}).then(res => res.data);
};

export const mgmCreateQRcode = params =>{//合成接口
    return axios.get(`${baseParis1}/mgmCreateQRcode`, { params: params } ).then(res => res.data);
};

//设置分佣配置
export const addCommisionAmount = params =>{//新增返费金额接口
    return axios.post(`${baseWallet}/manage/addCommisionAmount`, params , {emulateJson:true}).then(res => res.data);
};

export const addCommisionScale = params =>{//新增返佣比例接口
    return axios.post(`${baseWallet}/manage/addCommisionScale`, params , {emulateJson:true}).then(res => res.data);
};

//分佣配置管理
export const inviteList = params =>{//返佣节点列表接口
    return axios.get(`${baseWallet}/manage/queryAmountList`, { params: params } ).then(res => res.data);
};

export const dealList = params =>{//返佣比例列表接口
    return axios.get(`${baseWallet}/manage/queryScaleList`, { params: params } ).then(res => res.data);
};

export const deleteInvite = params =>{//返佣节点删除接口
    return axios.get(`${baseWallet}/manage/deleteAmount`, { params: params }).then(res => res.data);
};

export const deletePercent = params =>{//返佣比例删除接口
    return axios.get(`${baseWallet}/manage/deleteScale`, { params: params }).then(res => res.data);
};

export const editAmountInfo = params =>{//返佣节点修改接口
    return axios.get(`${baseWallet}/manage/editAmountInfo`, { params: params }).then(res => res.data);
};

export const editScaleInfo = params =>{//返佣比例修改接口
    return axios.get(`${baseWallet}/manage/editScaleInfo`, { params: params }).then(res => res.data);
};

//调账管理
export const searchInviterMainInfo = params =>{//搜索贷款交易信息接口
    return axios.get(`${baseWalletAnother}/searchInviterMainInfo`, { params: params }).then(res => res.data);
};

export const reconManageList = params =>{//调账管理列表接口
    return axios.post(`${baseWalletAnother}/findPageAdjustWallet`, params , {emulateJson:true}).then(res => res.data);
};

export const addAdjustWallet = params =>{//一级管理员添加调账接口
    return axios.post(`${baseWalletAnother}/addAdjustWallet`, params , {emulateJson:true}).then(res => res.data);
};

export const accountInfo = params =>{//查看调账账号信息接口
    return axios.get(`${baseWalletAnother}/accountInfo`, { params: params }).then(res => res.data);
};

export const adjustWalletInfo = params =>{//某一条调账的信息
    return axios.post(`${baseWalletAnother}/adjustWalletInfo`, params , {emulateJson:true}).then(res => res.data);
};

export const getadjustDetail = params =>{//某一条调账的操作记录
    return axios.post(`${baseWalletAnother}/detail`, params , {emulateJson:true}).then(res => res.data);
};

export const updateAdjustWallet = params =>{//对某一条调账进行修改
    return axios.post(`${baseWalletAnother}/updateAdjustWallet`, params , {emulateJson:true}).then(res => res.data);
};

export const adjustAdjust = params =>{//对某一条调账进行审核
    return axios.post(`${baseWalletAnother}/adjust`, params , {emulateJson:true}).then(res => res.data);
};

//调账日志
export const reconLogList = params =>{//调账日志列表接口
    return axios.post(`${baseWalletAnother}/pageData`, params , {emulateJson:true}).then(res => res.data);
};

//统计管理
export const superviseList = params =>{//统计管理列表接口
    return axios.get(`${baseWallet}/manage/superviseList`, { params: params }).then(res => res.data);
};

export const superviseDownLoad = params =>{//统计管理报表下载接口
    return instance.get(`${baseWallet}/manage/superviseDownLoad`, { params: params }).then(res => res.data);
};

//贷款用户
export const loanRecordList = params =>{//贷款用户列表接口
    return axios.get(`${baseWallet}/manage/loanRecordList`, { params: params }).then(res => res.data);
};

export const loanRecordInfo = params =>{//贷款用户查看详情接口
    return axios.get(`${baseWallet}/manage/loanRecordInfo`, { params: params }).then(res => res.data);
};

//赚拥宝 -- 海报
export const queryPageList = params =>{//列表
	return axios.get(`${baseParisApi}/manage/mgmPosterApproval/queryPageList`, { params: params } ).then(res => res.data);
};
//赚拥宝 -- 海报 -- 更新审批状态
export const updateMgm = params =>{//列表
	// return axios.post(`${baseParisApi}/manage/mgmPosterApproval/update`, { params: params } ).then(res => res.data);
	return axios.post(`${baseParisApi}/manage/mgmPosterApproval/update`, params , {emulateJson:true}).then(res => res.data);
};

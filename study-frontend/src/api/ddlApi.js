import axios from 'axios';
import ElementUI from 'element-ui'

let URL = process.env.API_URL;
var access_token = process.env.ACCESS_TOKEN
//let URL = 'http://192.168.1.128:1030/fjs/'//测试环境
//var access_token = "467405f6-331c-4914-beb7-42027bf09a01";
let oureaParis = URL + "ourea-backend"; //paris服务

let user = sessionStorage.getItem('user');
if (user) {
    user = JSON.parse(user);
    let token = user.token || '';
    if (token) {
        axios.defaults.headers.common['Authorization'] =  token;
    }
}

//设置响应数据为文件流
var instance = axios.create({
    responseType: 'blob' //返回数据的格式，可选值为arraybuffer,blob,document,json,text,stream，默认值为json
});


// 添加请求拦截器
axios.interceptors.request.use(function(config) {
    // 在发送请求之前做些什么

    return config;
}, function(error) {
    // 对请求错误做些什么

    return Promise.reject(error);
});

// 添加响应拦截器
axios.interceptors.response.use(function(response) {
    // 对响应数据做点什么

    return response;
}, function(error) {
    // 对响应错误做点什么
    ElementUI.Message({
        message: error.response.data.error,
        type: 'error'
    });
});

//DD链 -- 意见反馈
export const suggestionList = params => {
    return axios.get(`${oureaParis}/apiwithout/api/SuggestionFeedback/v1/suggestionList`, {
        params: params
    }).then(res => res.data);
};

//DD链 -- 意见反馈导出
export const suggestionListExport = params => {
    return instance.post(`${oureaParis}/apiwithout/api/SuggestionFeedback/v1/suggestionListExport`, params, {
        emulateJson: true
    }).then(res => res.data);
};

//DD链 -- 发放礼券
export const grantCoupons = params => {
    return axios.post(`${oureaParis}/apiwithout/api/Bill/v1/grantCoupons`, params, {
        emulateJson: true
    }).then(res => res.data);
};

//DD链 -- 后台礼券兑换流水列表导出Excel
export const couponsExchangeListExport = params => {
    return instance.post(`${oureaParis}/apiwithout/api/Bill/v1/couponsExchangeListExport`, params, {
        emulateJson: true
    }).then(res => res.data);
};

//DD链 -- 后台礼券兑换流水列表
export const couponsExchangeList = params => {
    return axios.get(`${oureaParis}/apiwithout/api/Bill/v1/couponsExchangeList`, {
        params: params
    }).then(res => res.data);
};

//DD链 -- 后台收佣审核列表
export const commissionCheckList = params => {
    return axios.get(`${oureaParis}/apiwithout/api/Commission/v1/commissionCheckList`, {
        params: params
    }).then(res => res.data);
};

//DD链 -- 后台收佣审核列表导出Excel
export const commissionCheckListExport = params => {
    return instance.post(`${oureaParis}/apiwithout/api/Commission/v1/commissionCheckListExport`, params, {
        emulateJson: true
    }).then(res => res.data);
};

//DD链 -- 修改冻结积分
export const editIntegral = params => {
    return axios.post(`${oureaParis}/apiwithout/commission/v1/editIntegral`, params, {
        emulateJson: true
    }).then(res => res.data);
};

//DD链 -- 解冻积分
export const relieve = params => {
    return axios.post(`${oureaParis}/apiwithout/commission/v1/relieve`, params, {
        emulateJson: true
    }).then(res => res.data);
};


//DD链 -- 同步客户
export const syncCustomer = params => {
    return axios.get(`${oureaParis}/apiwithout/loginApi/addCustomerByCrm`, {
        params: params
    }).then(res => res.data);
};


//DD链 -- 查询同步客户数量
export const syncCustomerCount = params => {
    return axios.get(`${oureaParis}/apiwithout/customerManage/crmCustomerCount`, {
        params: params
    }).then(res => res.data);
};


//DD链 -- 获取当前版本信息
export const queryVersion = params => {
    return axios.get(`${oureaParis}/apiwithout/miniApprovalManage/queryVersion`, {
        params: params
    }).then(res => res.data);
};


//DD链 -- 修改当前版本信息
export const editVersion = params => {
    return axios.post(`${oureaParis}/apiwithout/miniApprovalManage/editVersion`, params, {
        emulateJson: true
    }).then(res => res.data);
};

//DD链 -- 获取区块高度
export const blockHeight = params => {
    return axios.get(`${oureaParis}/apiwithout/blockDataInfo/v1/blockHeight`, {
        params: params
    }).then(res => res.data);
};

//DD链 -- 获取固化的上链信息列表
export const chainData = params => {
    return axios.get(`${oureaParis}/apiwithout/blockDataInfo/v1/chainData`, {
        params: params
    }).then(res => res.data);
};

//DD链 -- 单个记录重新发起上链
export const pushChain = params => {
    return axios.post(`${oureaParis}/apiwithout/blockDataInfo/v1/pushChain`, params, {
        emulateJson: true
    }).then(res => res.data);
};

//DD链 -- 全部数据重新发起数据上链接口
export const allPushChain = params => {
    return axios.post(`${oureaParis}/apiwithout/blockDataInfo/v1/allPushChain`, params, {
        emulateJson: true
    }).then(res => res.data);
};

//DD链 -- 获取消息通知人列表
export const noticeList = params => {
    return axios.get(`${oureaParis}/apiwithout/smsNotice/v1/noticeList`, {
        params: params
    }).then(res => res.data);
};

//DD链 -- 新增消息通知人
export const addNotice = params => {
    return axios.post(`${oureaParis}/apiwithout/smsNotice/v1/addNotice`, params, {
        emulateJson: true
    }).then(res => res.data);
};

//DD链 -- 修改消息通知
export const editNotice = params => {
    return axios.post(`${oureaParis}/apiwithout/smsNotice/v1/editNotice`, params, {
        emulateJson: true
    }).then(res => res.data);
};

//DD链 -- 删除消息通知
export const deleteNotice = params => {
    return axios.get(`${oureaParis}/apiwithout/smsNotice/v1/deleteNotice`, {
        params: params
    }).then(res => res.data);
};

//DD链 -- 活动B端列表
export const activityApplyList = params => {
    return axios.post(`${oureaParis}/apiwithout/api/v2/activityManage/activityApplyList`, params, {
        emulateJson: true
    }).then(res => res.data);
};
//暂停活动报名的资方经纪人
export const pauseActivityApplyBroker = params => {
    return axios.post(`${oureaParis}/apiwithout/api/v2/activityManage/pauseActivityApplyBroker`, params, {
        emulateJson: true
    }).then(res => res.data);
};
//分配活动报名的资方经纪人
export const allocationActivityApplyBroker = params => {
    return axios.post(`${oureaParis}/apiwithout/api/v2/activityManage/allocationActivityApplyBroker`, params, {
        emulateJson: true
    }).then(res => res.data);
};
///活动申请列表导出Excel
export const activityApplyListExport = params => {
    return instance.post(`${oureaParis}/apiwithout/api/v2/activityManage/activityApplyListExport`, params, {
        emulateJson: true
    }).then(res => res.data);
};

//B端后台订单查询列表API
export const backstageOrderList = params => {
    return axios.get(`${oureaParis}/apiwithout/api/activityOrder/v2/backstageOrderList`, {
        params: params
    }).then(res => res.data);
};
//B端后台订单查询列表导出ExcelAPI
export const backstageOrderListExport = params => {
    return instance.get(`${oureaParis}/apiwithout/api/activityOrder/v2/backstageOrderListExport`, {
        params: params
    }).then(res => res.data);
};

//DD链 -- 后台收付流水列表
export const orderDetailManangeList = params => {
    return axios.post(`${oureaParis}/apiwithout/api/v2/orderDetailManage/orderDetailManangeList`, params, {
        emulateJson: true
    }).then(res => res.data);
};

//DD链 -- 导出收付流水列表
export const orderDetailManangeListExport = params => {
    return instance.post(`${oureaParis}/apiwithout/api/v2/orderDetailManage/orderDetailManangeListExport`, params, {
        emulateJson: true
    }).then(res => res.data);
};

//DD链 -- 获取总收款
export const getTotalReceivableAmount = params => {
    return axios.get(`${oureaParis}/apiwithout/api/v2/orderDetailManage/getTotalReceivableAmount`, {
        params: params
    }).then(res => res.data);
};

//DD链 -- 商机发布列表
export const getCPACustomerList = params => {
    return axios.get(`${oureaParis}/apiwithout/customerCpa/getCPACustomerList`, {
        params: params
    }).then(res => res.data);
};
//DD链 -- 商机列表查看客户信息
export const getCPACustomerDetail = params => {
    return axios.get(`${oureaParis}/apiwithout/customerCpa/getCPACustomerDetail`, {
        params: params
    }).then(res => res.data);
};
//DD链 -- 商机修改客户信息
export const editCPACustomerInfo = params => {
	return axios.post(`${oureaParis}/apiwithout/customerCpa/editCPACustomerInfo`, params, {
		emulateJson: true
	}).then(res => res.data);
};
//DD链 -- 商机发布上架
export const batchUpperShelf = params => {
	return axios.post(`${oureaParis}/apiwithout/customerCpa/batchUpperShelf`, params, {
		emulateJson: true
	}).then(res => res.data);
};
//DD链 -- 商机发布下架
export const batchDownShelf = params => {
	return axios.post(`${oureaParis}/apiwithout/customerCpa/batchDownShelf`, params, {
		emulateJson: true
	}).then(res => res.data);
};

//DD链 -- 商机发布删除
export const deleteCustomer = params => {
	return axios.post(`${oureaParis}/apiwithout/customerCpa/deleteCustomer`, params, {
		emulateJson: true
	}).then(res => res.data);
};

//DD链 -- 商机订单列表
export const getOrderList = params => {
	return axios.get(`${oureaParis}/apiwithout/customerCpa/getOrderList`, {
		params: params
	}).then(res => res.data);
};
//DD链 -- 商机渠道列表
export const getChannelList = params => {
	return axios.get(`${oureaParis}/apiwithout/customerCpa/getChannelList`, {
		params: params
	}).then(res => res.data);
};
//DD链 -- 设置渠道价格
export const editChannelPrice = params => {
	return axios.post(`${oureaParis}/apiwithout/customerCpa/editChannelPrice`, params, {
		emulateJson: true
	}).then(res => res.data);
};
//DD链 -- 商机渠道删除
export const deleteChannel = params => {
	return axios.post(`${oureaParis}/apiwithout/customerCpa/deleteChannel`, params, {
		emulateJson: true
	}).then(res => res.data);
};
//DD链 -- CPA订单退款
export const preRefundToCpa = params => {
	return axios.post(`${oureaParis}/api/v2/customerCpa/preRefundToCpa`, params, {
		emulateJson: true
	}).then(res => res.data);
};
//DD链 -- 编辑设置报名活动经济人当前总名额
export const editActivityTotalQuota = params => {
	return axios.post(`${oureaParis}/apiwithout/api/v2/activityManage/editActivityTotalQuota`, params, {
		emulateJson: true
	}).then(res => res.data);
};
//DD链 -- 商机发布管理 - 导入
export const improtOcdcExcel = params => {
	return axios.post(`${oureaParis}/apiwithout/customerManage/improtOcdcExcel`, params, {
		emulateJson: true
	}).then(res => res.data);
};

import Login from './views/Login.vue'
import NotFound from './views/404.vue'
import Home from './views/Home.vue'
import Main from './views/Main.vue'

import slideshow from './views/side_DDL/slideshow.vue'
import audits from './views/side_DDL/audits.vue'
import receipts from './views/side_DDL/receipts.vue'
import feedback from './views/side_DDL/feedback.vue'
import coupons from './views/side_DDL/coupons.vue'
import syncCustomer from './views/side_DDL/syncCustomer.vue'
import chainCompensate from './views/side_DDL/chainCompensate.vue'
import messNotice from './views/side_DDL/messNotice.vue'
import backstage from './views/side_DDL/backstage.vue'
import orderquery from './views/side_DDL/orderquery.vue'
import publishedBusiness from './views/side_DDL/publishedBusiness.vue'
import finance from './views/finance/finance.vue' //财务管理
import orderList from './views/side_DDL/orderList.vue'
import channelList from './views/side_DDL/channelList.vue'

let routes = [{
        path: '/login',
        component: Login,
        name: '',
        hidden: true
    },
    {
        path: '/404',
        component: NotFound,
        name: '',
        hidden: true
    },
    {
        path: '/',
        component: Home,
        name: 'DD链',
        iconCls: 'fa fa-picture-o',
        // leaf: true,//只有一个节点
        children: [{
                path: '/slideshow',
                component: slideshow,
                name: '学生课程'
            }, {
                path: '/audits',
                component: audits,
                name: '收佣审核'
            },
            {
                path: '/receipts',
                component: receipts,
                name: '收付流水'
            },
            {
                path: '/feedback',
                component: feedback,
                name: '意见反馈'
            },
            {
                path: '/coupons',
                component: coupons,
                name: '礼券兑换流水'
            },
            {
                path: '/syncCustomer',
                component: syncCustomer,
                name: 'DD链配置'
            },
            {
                path: '/chainCompensate',
                component: chainCompensate,
                name: '上链补偿'
            },
            {
                path: '/messNotice',
                component: messNotice,
                name: '消息通知'
            },
            {
                path: '/backstage',
                component: backstage,
                name: '活动报名经纪人查询'
            },
            {
                path: '/orderquery',
                component: orderquery,
                name: '订单查询'
            },
            {
                path: '/publishedBusiness',
                component: publishedBusiness,
                name: '学生列表'
            },
            {
                path: '/finance',
                component: finance,
            },
            {
                path: '/orderList',
                component: orderList,
                name: '商户订单管理'
            },
            {
                path: '/channelList',
                component: channelList,
                name: '渠道定价管理'
            },
        ]
    },
    {
        path: '*',
        hidden: true,
        redirect: {
            path: '/404'
        }
    }
];

export default routes;

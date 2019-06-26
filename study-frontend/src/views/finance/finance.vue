<template>
    <el-tabs @tab-click="handleClick">
        <el-tab-pane label="提现管理">
            <cashWithdrawal v-if="activeName == '提现管理'"></cashWithdrawal>
        </el-tab-pane>
        <el-tab-pane label="退款管理">
            <refund v-if="activeName == '退款管理'"></refund>
        </el-tab-pane>
    </el-tabs>
</template>

<script>
	import { Loading } from 'element-ui';
	import cashWithdrawal from './cashWithdrawal.vue'
	import refund from './refund.vue'
	export default {
		components:{
			cashWithdrawal,
			refund
		},
		data() {
			return {
				activeName: '提现管理'
			}
		},
		mounted() {

		},
		created() {
		},
		methods: {
			handleClick(e) {
				this.activeName = e.label;
                console.log(e.label,11)
			}
		},
	}
</script>

<style scoped>

</style>

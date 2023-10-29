<template>
  <el-dialog
    title="预付费充值"
    :visible.sync="dialogTableVisible"
    width="80%"
    @open="openDialog"
  >
    <el-form :inline="true" :model="payment" class="demo-form-inline" label-position="right" label-width="80px">
      <el-form-item label="用户名">
        <el-input v-model="userName" placeholder="用户名称" :disabled="true" />
      </el-form-item>
      <el-form-item label="表号">
        <el-input v-model="meterCode" placeholder="水表表号" :disabled="true" />
      </el-form-item>
      <el-form-item label="余额">
        <el-input v-model="balance" placeholder="当前余额" :disabled="true" />
      </el-form-item>
      <el-form-item label="充值方式">
        <el-select v-model="payment.accountmode" placeholder="请选择充值方式">
          <el-option v-for="(item, idx ) in accountmodeList" :key="item" :label="item" :value="idx" />
        </el-select>
      </el-form-item>
      <el-form-item label="充值金额">
        <el-input v-model="payment.amount" placeholder="充值金额" />
      </el-form-item>
      <el-form-item label="打印小票">
        <el-select v-model="payment.paperflag" placeholder="是否开具小票">
          <el-option v-for="(item, idx ) in paperflagList" :key="item" :label="item" :value="idx" />
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="onSubmit">充值</el-button>
        <el-button type="primary" @click="onCancle">取消</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script>

import { queryChargeList, chargeBalance } from '@/api/chargeManager'
import { initAllDictionary, getDescByBeanAttrList } from '@/api/dictionaryManager'
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination

export default {
  components: { Pagination },
  directives: { waves },
  filters: {
  },
  props: {
    userid: {},
    meterCode: 0,
    balance: 0
  },
  data() {
    return {
      dialogTableVisible: false,
      userName: '用户',
      payment: { metercode: undefined, balance: undefined, accountmode: '1', amount: 0, paperflag: '0' },
      tableKey: 0,
      paperflagList: getDescByBeanAttrList('paymentModel', 'paperflag'),
      accountmodeList: getDescByBeanAttrList('paymentModel', 'accountmode'),
      total: 0,
      listLoading: true,
      showReviewer: false,
      downloadLoading: false
    }
  },

  created() {
    // this.getList();
  },
  methods: {
    openDialog() {
      this.$forceUpdate()
    },
    onSubmit() {
      this.payment.metercode = this.meterCode
      this.payment.userid = this.userid


      chargeBalance(this.payment).then(resp => {
        this.$notify({
          title: '操作结果',
          message: '充值成功',
          type: 'success',
          duration: 2000
        })
      })

      this.dialogTableVisible = false
    },
    onCancle() {
      this.dialogTableVisible = false
    },
    handlePrint(row) {
      alert('很抱歉，功能正在实现中，后续开放使用')
    }
  }
}
</script>

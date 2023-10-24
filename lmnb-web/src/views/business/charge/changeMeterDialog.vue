<template>
  <el-dialog title="换表操作"
             :visible.sync="dialogTableVisible"
             width="80%"
             @open="openDialog">
    <el-form :inline="true" :model="payment" class="demo-form-inline" label-position="right" label-width="100px" >
      <el-form-item label="户号">
        <el-input v-model="meter.id" placeholder="户号标识" :disabled="true" />
      </el-form-item>
      <el-form-item label="姓名">
        <el-input v-model="userName" placeholder="用户名称" :disabled="true" />
      </el-form-item>
      <el-form-item label="电话">
        <el-input v-model="meter.phone" placeholder="当前余额" :disabled="true" />
      </el-form-item>
      <el-form-item label="旧表表号">
        <el-input v-model="meterCode" placeholder="水表表号" :disabled="true" />
      </el-form-item>
      <el-form-item label="旧表余额">
        <el-input v-model="meter.balance" placeholder="当前余额" :disabled="true" />
      </el-form-item>
      <el-form-item label="最近抄表时间">
        <el-input v-model="meter.recentreadtime" placeholder="当前余额" :disabled="true" />
      </el-form-item>
      <el-form-item label="最近抄表读数">
        <el-input v-model="meter.recentreadnum" placeholder="当前余额" :disabled="true" />
      </el-form-item>

      <el-form-item label="换表费用">
        <el-input v-model="changeMeterCost" placeholder="请输入换表费用" />
      </el-form-item>
      <el-form-item label="新表表号">
        <el-input v-model="changeMeterCode" placeholder="请输入新表表号" />
      </el-form-item>
      <el-form-item label="旧表止码">
        <el-input v-model="oldReadNum" placeholder="请输入旧表止码" />
      </el-form-item>
      <el-form-item label="新表起码">
        <el-input v-model="changeReadNum" placeholder="请输入新表起码" />
      </el-form-item>
      <el-form-item label="维修工">
        <el-input v-model="changeFixUser" placeholder="请输入维修工姓名" />
      </el-form-item>
      <el-form-item label="备注">
        <el-input v-model="remark1" placeholder="请输入备注说明" />
      </el-form-item>

      <el-form-item label=" ">
        <el-button type="primary" @click="onSubmit">确定</el-button>
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
      meter: {},
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

        alert(this.payment.metercode + ',' + this.payment.userid)

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

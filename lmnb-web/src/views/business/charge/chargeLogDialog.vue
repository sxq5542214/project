<template>
  <el-dialog
    :title="userName"
    :visible.sync="dialogTableVisible"
    width="80%"
    @open="openDialog"
  >
    <el-table
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%; margin-bottom: 50px;"
    >
      <el-table-column property="dealmode" label="类型">
        <template slot-scope="{row}">
          <span>{{ findDescByValue('paymentModel','dealmode',row.dealmode) }}</span>
        </template>
      </el-table-column>
      <el-table-column property="paychannel" label="渠道">
        <template slot-scope="{row}">
          <span>{{ findDescByValue('paymentModel','paychannel',row.paychannel) }}</span>
        </template>
      </el-table-column>
      <el-table-column property="amount" label="应收" />
      <el-table-column property="payamount" label="实收" />
      <el-table-column property="createtime" label="充值时间" />
      <el-table-column property="prebalance" label="充值前余额" />
      <el-table-column property="afterbalacne" label="充值后余额" />

      <!--       <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right">
        <template slot-scope="{row}">
          <el-button type="primary" size="mini" @click="handlePrint(row)" icon="el-icon-printer">打印账单</el-button>
        </template>
      </el-table-column> -->
    </el-table>
  </el-dialog>
</template>

<script>

import { queryChargeList } from '@/api/chargeManager'
import { initAllDictionary, getDescByBeanAttrValue } from '@/api/dictionaryManager'
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
    meterCode: {}
  },
  data() {
    return {
      dialogTableVisible: false,
      userName: '用户',
      findDescByValue: getDescByBeanAttrValue,
      tableKey: 0,
      list: null,
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
    getList() {
      if (this.userid) {
        this.listLoading = true
        queryChargeList({ userid: this.userid, meterCode: this.meterCode }).then(response => {
          this.list = response.data
          this.total = response.total

          // Just to simulate the time of the request
          setTimeout(() => {
            this.listLoading = false
          }, 0.5 * 1000)
        })
      }
    },
    openDialog() {
      this.getList()
    },
    handlePrint(row) {
      alert('很抱歉，功能正在实现中，后续开放使用')
    }
  }
}
</script>

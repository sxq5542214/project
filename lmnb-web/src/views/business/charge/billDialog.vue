<template>
  <el-dialog
    :title="userName"
    :visible.sync="dialogTableVisible"
    width="90%"
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
      <el-table-column property="billmonth" label="月份" />
      <el-table-column property="quantity" label="用水量" />
      <el-table-column property="realbillamount" label="水费" />
      <el-table-column property="cyclestartbalance" label="期初余额" />
      <el-table-column property="cyclebuyamount" label="充值金额" />
      <el-table-column property="cycleendbalance" label="期末余额" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right">
        <template slot-scope="{row}">
          <el-button type="primary" size="mini" icon="el-icon-printer" @click="handlePrint(row)">打印账单</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-dialog>
</template>

<script>

import { queryBillList } from '@/api/billManager'
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
        queryBillList({ userid: this.userid }).then(response => {
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

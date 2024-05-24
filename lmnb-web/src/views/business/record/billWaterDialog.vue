<template>
  <el-dialog
    :title="titleName"
    :visible.sync="dialogTableVisible"
    width="80%"
    @open="openDialog"
  >
    <el-table v-loading="listLoading"
              :data="list"
              border
              fit
              highlight-current-row
              style="width: 100%; margin-bottom: 50px;">
      <el-table-column property="id" label="账单标识" align="center" width="80px">
        <template slot-scope="{row}">
          <span>{{ row.id }}</span>
        </template>
      </el-table-column>
      <el-table-column property="billmonth" label="抄表年月" align="center"  />
      <el-table-column property="curnum" label="本月止码" align="center" :show-overflow-tooltip="true"  width="100px" />
      <el-table-column property="quantity" label="用水量" align="center" width="120px" />
      <el-table-column property="cyclestartbalance" label="期初金额" align="center" :show-overflow-tooltip="true" />
      <el-table-column property="cycleendbalance" label="期末金额" align="center" width="100px" />
      <el-table-column property="cyclebuyamount" label="充值金额" align="center" width="100px" />
<!--      <el-table-column property="minconsumamount" label="最低消费" align="center" width="100px" />
-->

      <!--       <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right">
    <template slot-scope="{row}">
      <el-button type="primary" size="mini" @click="handlePrint(row)" icon="el-icon-printer">打印账单</el-button>
    </template>
  </el-table-column> -->
    </el-table>
  </el-dialog>
</template>

<script>

  import { ajaxQueryBillWaterList } from '@/api/billManager'
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
    meterid: {}
  },
  data() {
    return { 
      dialogTableVisible: false,
      titleName: '用水记录',
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
     //this.getList();
  },
  methods: {
    getList() {

      if(this.meterid) {
        this.listLoading = true
        ajaxQueryBillWaterList({ meterid: this.meterid }).then(response => {
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
    }
  }
}
</script>

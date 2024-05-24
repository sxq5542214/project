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
      <el-table-column property="id" label="命令标识" align="center"  width="80px">
        <template slot-scope="{row}">
          <span>{{ row.id }}</span>
        </template>
      </el-table-column>
      <el-table-column property="type" label="命令类型" align="center"  width="100px">
        <template slot-scope="{row}">
          <span>{{ findDescByValue('cmdModel','type',row.type) }}</span>
        </template>
      </el-table-column>
      <el-table-column property="createtime" label="创建时间"  align="center"  :show-overflow-tooltip="true"/>
      <el-table-column property="operatorname" label="操作员" align="center"  width="120px"/>
      <el-table-column property="exetime" label="执行时间" align="center"  :show-overflow-tooltip="true"/>
      <el-table-column property="type" label="执行结果" align="center"  width="100px">
        <template slot-scope="{row}">
          <span>{{ findDescByValue('cmdModel','state',row.state) }}</span>
        </template> 
      </el-table-column>

      <!--       <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right">
    <template slot-scope="{row}">
      <el-button type="primary" size="mini" @click="handlePrint(row)" icon="el-icon-printer">打印账单</el-button>
    </template>
  </el-table-column> -->
    </el-table>
  </el-dialog>
</template>

<script>

  import { queryCMDLogList } from '@/api/cmdManager'
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
      titleName: '命令记录',
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

      if(this.meterCode) {
        this.listLoading = true
        queryCMDLogList({  meterCode: this.meterCode }).then(response => {
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

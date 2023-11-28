<template>
  <div class="app-container">
   <!-- <div class="filter-container">
      <div class="demo-input-suffix">
        <el-input
          v-model="listQuery.name"
          placeholder="请输入户名"
          style="width: 200px;"
          class="filter-item"
          @keyup.enter.native="handleFilter"
        />
        <el-input
          v-model="listQuery.phone"
          placeholder="请输入电话"
          style="width: 200px;margin-left: 10px;"
          class="filter-item"
        />
        <el-input
          v-model="listQuery.idcard"
          placeholder="请输入证件号码"
          style="width: 200px;margin-left: 10px;"
          class="filter-item"
        />
        <el-button
          v-waves
          class="filter-item"
          type="primary"
          icon="el-icon-search"
          style="margin-left: 10px;"
          @click="handleFilter"
        >查询</el-button>
      </div>
    </div>-->

    <el-table :key="tableKey"
              v-loading="listLoading"
              :data="list"
              border
              fit
              highlight-current-row
              style="width: 100%;"
              @sort-change="sortChange">
      <el-table-column
          v-for="(item, index) in listLabel"
          :key="index"
          :prop="item.code"
          :label="item.name"
                                     >

      </el-table-column>

    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="listQuery.page"
      :limit.sync="listQuery.rows"
      @pagination="getList"
    />

    <el-dialog :visible.sync="dialogPvVisible" title="Reading statistics">
      <el-table :data="pvData" border fit highlight-current-row style="width: 100%">
        <el-table-column prop="key" label="Channel" />
        <el-table-column prop="pv" label="Pv" />
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogPvVisible = false">{{ $t('table.confirm') }}</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import { querySimpleReportList, querySimpleReportDataView, querySimpleReportTableColumns } from '@/api/reportManager'
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination

export default {
  name: 'ReportTable',
  components: { Pagination },
  directives: { waves },
  data() {
    return {
      tableKey: 0,
      list: null,
      total: 0,
      listLoading: true,
      listLabel: null ,
      listQuery: {
        page: 1,
        rows: 20,
        importance: undefined,
        title: undefined,
        type: undefined,
        sort: '+id'
      },
      showReviewer: false,
      downloadLoading: false
    }
  },

    created() {
      if (this.$route.query) {
        this.listQuery.id = this.$route.query.report_id
        this.listQuery.code = this.$route.query.code
      }

      querySimpleReportTableColumns({ report_id: this.listQuery.id, code: this.listQuery.code }).then(response => {
        this.listLabel = response.data;
      })


      this.getList()

  },
  methods: {
    getList() {
      this.listLoading = true
      querySimpleReportList().then(response => {
        this.list = response.data;
        this.listLoading = false;
        })


    },
    handleClick() {
      alert('视线中')
    },
    getSortClass: function(key) {
      const sort = this.listQuery.sort
      return sort === `+${key}` ? 'ascending' : 'descending'
    }
  }
}
</script>

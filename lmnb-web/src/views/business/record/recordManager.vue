<template>
  <div class="app-container">
    <div class="filter-container">
      <div class="demo-input-suffix">
        <el-input
          v-model="listQuery.username"
          placeholder="请输入用户名"
          style="width: 200px;"
          class="filter-item"
          @keyup.enter.native="handleFilter"
        />
        <el-input
          v-model="listQuery.billmonth"
          placeholder="请输入账单年月"
          style="width: 200px;margin-left: 10px;"
          class="filter-item"
        />
        <el-input
          v-model="listQuery.pricename"
          placeholder="请输入水价名称"
          style="width: 200px;margin-left: 10px;"
          class="filter-item"
        />
        <el-button
          v-waves
          class="filter-item"
          type="primary"
          icon="el-icon-search"
          style="margin-left: 10px;"
          @click="handleClick"
        >查询</el-button>
      </div>
    </div>

    <el-table :key="tableKey"
              v-loading="listLoading"
              :data="list"
              border
              fit
              highlight-current-row
              style="width: 100%;">
      <el-table-column label="序号"
                       prop="id"
                       align="center"
                       width="80"
                       :class-name="getSortClass('id')">
        <template slot-scope="{row}">
          <span>{{ row.id }}</span>
        </template>
      </el-table-column>
      <el-table-column label="用户名" align="center">
        <template slot-scope="{row}">
          <span>{{ row.username }}</span>
        </template>
      </el-table-column>
      <el-table-column label="表号" align="center">
        <template slot-scope="{row}">
          <span>{{ row.metercode }}</span>
        </template>
      </el-table-column>
      <el-table-column label="账单年月" width="100" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ row.billmonth }}</span>
        </template>
      </el-table-column>
      <el-table-column label="抄表时间"  align="center" >
        <template slot-scope="{row}">
          <span>{{ row.curtime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="本次读数"  width="100" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ row.curnum }}</span> 
        </template>
      </el-table-column>
      <el-table-column label="本次用量" width="100" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ row.curquantity }}</span>
        </template>
      </el-table-column>
      <el-table-column label="上次读数" width="100" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ row.lastnum }}</span>
        </template>
      </el-table-column>
      <el-table-column label="价格类型" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ row.pricename }}</span>
        </template>
      </el-table-column>


      <!--      <el-table-column label="操作"
                   align="center"
                   class-name="small-padding fixed-width"
                   fixed="right">
    <template slot-scope="{row}">
      <el-button type="primary" size="mini" @click="handleClick(row)">查看</el-button>
    </template>
  </el-table-column>-->
    </el-table> 

    <pagination 
      v-show="total>0"
      :total="total"
      :page.sync="listQuery.page"
      :limit.sync="listQuery.rows"
      @pagination="getList"
    />


  </div>
</template>

<script>

import { queryRecordList } from '@/api/recordManager'
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination

export default {
  name: 'RecordTable',
  components: { Pagination },
  directives: { waves },
  data() {
    return {
      tableKey: 0,
      list: null,
      total: 0,
      listLoading: true,
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
    this.getList()
  },
    methods: {
      getList() {
        this.listLoading = true
        queryRecordList(this.listQuery).then(response => {
          this.list = response.data
          this.total = response.total

          // Just to simulate the time of the request
          setTimeout(() => {
            this.listLoading = false
          }, 0.5 * 1000)
        })
      },
    handleClick() {
      this.getList(); 
    },

    formatJson(filterVal) {
      return this.list.map(v =>
        filterVal.map(j => {
          if (j === 'timestamp') {
            return parseTime(v[j])
          } else {
            return v[j]
          }
        })
      )
    },
    getSortClass: function(key) {
      const sort = this.listQuery.sort
      return sort === `+${key}` ? 'ascending' : 'descending'
    }
  }
}
</script>

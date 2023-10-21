<template>
  <div class="app-container">
    <div class="filter-container">
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
    </div>

    <el-table
      :key="tableKey"
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%;"
      @sort-change="sortChange"
    >
      <el-table-column
        label="序号"
        prop="id"
        sortable="custom"
        align="center"
        width="80"
        :class-name="getSortClass('id')"
      >
        <template slot-scope="{row}">
          <span>{{ row.id }}</span>
        </template>
      </el-table-column>
      <el-table-column label="户名" width="150px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="地址" min-width="150px" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ row.area1 }} - {{ row.area2 }} - {{ row.area3 }}</span>
        </template>
      </el-table-column>
      <el-table-column label="电话" width="110px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.phone }}</span>
        </template>
      </el-table-column>
      <el-table-column label="身份证号" width="110px" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ row.idcard }}</span>
        </template>
      </el-table-column>
      <el-table-column label="开户时间" width="100px" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ row.createtime }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
        fixed="right"
      >
        <template slot-scope="{row}">
          <el-button type="primary" size="mini" @click="handleClick(row)">查看</el-button>
          <!--           <el-button   type="danger" size="mini"  @click="handleDelete(row,$index)">
                      删除
          </el-button>-->
        </template>
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

import {
  queryUserList,
  addUser,
  updateUser,
  deleteUser
} from '@/api/userManager'
import { queryAddressList } from '@/api/addressManager'
import { addMeter, queryMeterList, updateMeter } from '@/api/meterManager'
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
    },
    handleClick() {
      alert('视线中')
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

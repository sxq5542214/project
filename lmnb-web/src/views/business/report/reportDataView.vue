<template>
  <div class="app-container">
    <div>
      <el-form :model="listQuery" ref="queryForm" label-width="auto" :inline="true">

        <el-form-item label="起始日期" prop="timestamp">
          <el-date-picker v-model="start_date" type="datetime" placeholder="请选择时间" format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss" />
        </el-form-item>

        <el-form-item label="结束日期" prop="timestamp">
          <el-date-picker v-model="end_date" type="datetime" placeholder="请选择时间" format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss" />
        </el-form-item>

        <el-form-item v-for="(item,index) in listParam" :key="index" :label="item.param_name">
          <el-date-picker v-if="item.param_type == 'date'" v-model="'listQuery.'+item.param_code"
                          type="date" placeholder="请选择日期">
          </el-date-picker>
          <el-input v-else v-model="'listQuery.' + item.param_code" :type="item.param_type"></el-input>
        </el-form-item>
        <!-- 可以添加更多的查询条件 -->

      </el-form>

      <el-button :loading="listLoading" style="margin:0 0" type="Info" icon="el-icon-arrow-left" @click="goback">
        返回
      </el-button><el-button :loading="listLoading" style="margin:0 0 20px 20px;" type="primary" icon="el-icon-document" @click="getList">
        查询数据
      </el-button>
      <el-button :loading="downloadLoading" style="margin:0 0 20px 20px;" type="warning" icon="el-icon-document" @click="handleDownload">
        导出数据
      </el-button>

    </div>

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
              style="width: 100%;">
      <el-table-column :show-overflow-tooltip="true"
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
      start_date: parseTime(new Date().setHours(0, 0, 0),null),
      end_date: parseTime(new Date().setHours(23, 59, 59),null),
      tableKey: 0,
      list: null,
      total: 0,
      listLoading: true,
      listLabel: null,
      listParam: [],
      listQuery: {
        page: 1,
        rows: 20,
        importance: undefined,
        title: undefined,
        type: undefined,
        sort: '+id'
      },
      datetest: '2023-12-12',
      showReviewer: false,
      filename: '数据导出',
      autoWidth: true,
      bookType: 'xlsx',
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
      querySimpleReportDataView({ report_id: this.listQuery.id, code: this.listQuery.code , queryParam : this.listQuery ,start_date:this.start_date,end_date:this.end_date }).then(response => {
        this.list = response.data.dataList;

        this.listParam = response.data.paramsList;
        //console.log('listParam', this.listParam);
        this.listLoading = false;
        })


    },
    handleDownload() {
      this.downloadLoading = true;
      var labels = new Array();
      var codes = new Array();
      for (var i = 0; i < this.listLabel.length; i++) {
        var item = this.listLabel[i];
        labels.push(item.name);
        codes.push(item.code);
      }
      console.log(labels);
      import('@/vendor/Export2Excel').then(excel => {
        const tHeader = labels
        const filterVal = codes
        const list = this.list
        const data = this.formatJson(filterVal, list)
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename: this.filename,
          autoWidth: this.autoWidth,
          bookType: this.bookType
        })
        this.downloadLoading = false
      })
    },
    handleClick() {
      alert('视线中')
    },
    goback() {
      this.$router.go(-1)
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map(v => filterVal.map(j => {
        if (j === 'timestamp') {
          return parseTime(v[j])
        } else {
          return v[j]
        }
      }))
    }
  }
}
</script>

<template>
  <div class="app-container">
    <div>
      <el-form :model="listQuery" ref="queryForm" label-width="70px" :inline="true" >

        <el-form-item label="起始日期" prop="timestamp" style="margin-bottom:5px;">
          <el-date-picker v-model="start_date" type="datetime" placeholder="请选择时间" format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss" style="width: 200px; " />
        </el-form-item>

        <el-form-item label="结束日期" prop="timestamp" style="margin-bottom:5px;">
          <el-date-picker v-model="end_date" type="datetime" placeholder="请选择时间" format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss"  style="width: 200px; " />
        </el-form-item>
        <el-form-item v-for="(item,index) in listParam" :key="index" :label="item.param_name" style="margin-bottom:5px;">
          <el-date-picker v-if="item.param_type == 'date'" v-model="'listQuery.'+item.param_code" style="width: 200px; "
                          type="date" placeholder="请选择日期">
          </el-date-picker>

          <el-select v-else-if="item.param_type == 'select'" clearable v-model="listQuery[item.param_code]" placeholder="请选择" style="width: 200px; " @change="updateSelectValue()" >
            <el-option v-for="op in listSelect[item.param_code]" :key="op.value" :label="op.label" :value="op.value"></el-option>
          </el-select>

          <el-input v-else :value="listQuery[item.param_code]" @input="updateParamValue(item.param_code, $event)" style="width: 200px; "
                    :type="item.param_type"></el-input>
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
              :data="currentData"
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

    <pagination v-show="total>0"
                @size-change="handlePageSizeChange"
                :total="total"
                :page.sync="listQuery.page"
                :limit.sync="listQuery.rows"
                @pagination="handlePagination"
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
      currentData : null,
      total: 0,
      listLoading: true,
      listLabel: null,
      listParam: [],
      listSelect: {},
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
        querySimpleReportDataView({ report_id: this.listQuery.id, code: this.listQuery.code, queryParam: this.listQuery, start_date: this.start_date, end_date: this.end_date }).then(response => {
          this.list = response.data.dataList;

          this.listParam = response.data.paramsList;

          // 查询条件参数的初始化
          for (var i = 0; i < this.listParam.length; i++) {
            var param = this.listParam[i];
            //下拉框的初始化
            if (param.param_type == 'select') {
              var selectJson = JSON.parse(param.param_url);
              this.listSelect[param.param_code] = selectJson;

            }
            // 默认值的初始化
            if (param.default_value === undefined) param.default_value = '';
            this.listQuery[param.param_code] = param.default_value;
          }

          //console.log('listSelect', this.listSelect);
          this.listLoading = false;
          this.handleCurrentPageChange(this.total == this.list.length ? this.listQuery.page : 1);
          this.$forceUpdate();
        })

      },
      handlePagination() {
        this.handleCurrentPageChange(this.total == this.list.length ? this.listQuery.page : 1);
      },
      handleCurrentPageChange(val) {  // 前端分页判断
        if (val) {
          this.listQuery.page = val;
        }
        this.total = this.list.length ;
        this.currentData = this.list.slice(
          (this.listQuery.page - 1) * this.listQuery.rows,
          this.listQuery.page * this.listQuery.rows
        );
      },
      handlePageSizeChange(val) {  // 前端分页判断
        this.listQuery.rows = val;
        this.handleCurrentPageChange(1);

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
      //console.log(labels);
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
    updateParamValue(paramCode, event) {
      //alert(paramCode + "," + event);
      if (this.listQuery[paramCode] === undefined ) { this.listQuery[paramCode] = ''; }
      var newValue = event;
      this.listQuery[paramCode] = newValue;
      this.$forceUpdate();
      //
    },
    updateSelectValue() {
      this.$forceUpdate();
    },
    goback() {
      this.$router.go(-1)
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map(v => filterVal.map(j => {

        try {
          const num = Number(v[j]);
          if (!isNaN(num) && num < 99999999) {
            return num;
          } else {
            return v[j];// 无法转化为数字
          }
        } catch (e) { // 无法转化为数字
          return v[j];
        }

      }))
    }
  }
}
</script>

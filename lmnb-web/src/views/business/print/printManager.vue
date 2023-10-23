<template>
  <div class="app-container">
    <!--  <div class="filter-container">
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
    </div>-->

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
      <el-table-column label="模板名称" align="center">
        <template slot-scope="{row}">
          <span>{{ row.title }}</span>
        </template>
      </el-table-column>
      <el-table-column label="修改时间" align="center">
        <template slot-scope="{row}">
          <span>{{ row.createtime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ row.remark }}</span>
        </template>
      </el-table-column>


      <el-table-column label="操作"
                       align="center"
                       class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <el-button type="primary" size="mini" @click="handleClick(row)">设计打印模板</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0"
                :total="total"
                :page.sync="listQuery.page"
                :limit.sync="listQuery.rows"
                @pagination="getList" />

  </div>
</template>

<script>
  
  import { queryPrintTemplateList, updatePrintTemplateStyle} from '@/api/printManager'
  import { getLodop, checkOrTryHttp } from './lodop/LodopFuncs'
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination

export default {
  name: 'PrintTable',
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
    this.getList();

  },
    methods: {
      getList() {
        this.listLoading = true
        queryPrintTemplateList(this.listQuery).then(response => {
          this.list = response.data
          this.total = response.total

          // Just to simulate the time of the request
          setTimeout(() => {
            this.listLoading = false
          }, 0.5 * 1000)
        })
      },
      handleClick(row) {
        var styleStr = row.style;
        var LODOP = getLodop();
        eval(styleStr);
        LODOP.SET_PRINT_MODE("PRINT_SETUP_PROGRAM", true); //保存修改的设计模板一定要加上这句

        //LODOP.SET_PRINT_STYLEA("chargeOperator", 'CONTENT','xxxxxxxxxx');   //用来修改模板中的变量内容

        //窗口关闭后，回调函数中保存的设计代码
        if (LODOP.CVERSION) CLODOP.On_Return = function (TaskID, Value) {

         // console.log('Value:' + Value);  //这个是返回的完整的设计代码
          if(Value){
            row.style = Value;
            updatePrintTemplateStyle(row).then(response => {
              alert('成功修改模板');
              //this.$notify({
              //  title: '成功',
              //  message: '修改成功',
              //  type: 'success',
              //  duration: 2000
              //})
            });
          } else {
            alert('已取消修改模板');
            //this.$notify({
            //  title: '已取消',
            //  message: '放弃修改',
            //  type: 'success',
            //  duration: 2000
            //})
          }
          
        };
        LODOP.PRINT_DESIGN();

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

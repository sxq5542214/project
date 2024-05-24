<template>
  <div class="app-container">
    <div class="filter-container">
      <div class="demo-input-suffix">

        <el-form :inline="true" class="demo-form-inline" label-width="70px">
          <el-form-item label="镇名" style="margin-bottom:0px;">
            <el-select v-model="listQuery.area1"
                       placeholder="请选择镇名"
                       style="width: 180px;"
                       class="filter-item"
                       filterable
                       @change="updateAllArea($event,1)">
              <el-option v-for="item in areaList[0]"
                         :key="item.id"
                         :label="item.name"
                         :value="item.name" />
            </el-select>
          </el-form-item>

          <el-form-item label="村名" style="margin-bottom:0px;">
            <el-select v-model="listQuery.area2"
                       placeholder="请选择村名"
                       style="width: 180px; "
                       class="filter-item"
                       filterable
                       @change="updateAllArea($event,2)">
              <el-option v-for="item1 in areaList[1]"
                         :key="item1.id"
                         :label="item1.name"
                         :value="item1.name" />
            </el-select>
          </el-form-item>

          <el-form-item label="组名" style="margin-bottom:0px;">
            <el-select v-model="listQuery.area3"
                       placeholder="请选择组名"
                       style="width: 180px; "
                       class="filter-item"
                       filterable
                       @change="setArea3($event)">
              <el-option v-for="item2 in areaList[2]"
                         :key="item2.id"
                         :label="item2.name"
                         :value="item2.name" />
            </el-select>
          </el-form-item>

          <el-form-item label="户号" style="margin-bottom:0px;">
            <el-input v-model="listQuery.userCode"
                      placeholder="请输入户号"
                      style="width: 180px; "
                      class="filter-item"
                      @keyup.enter.native="handleFilter" />
          </el-form-item>
          <el-form-item label="户名" style="margin-bottom:0px;">
            <el-input v-model="listQuery.userName"
                      placeholder="请输入户名"
                      style="width: 180px; "
                      class="filter-item"
                      @keyup.enter.native="handleFilter" />
          </el-form-item>

          <el-form-item label="电话" style="margin-bottom:0px;">
            <el-input v-model="listQuery.phone"
                      placeholder="请输入电话"
                      style="width: 180px; "
                      class="filter-item" />
          </el-form-item>
          <el-form-item label="水表号" style="margin-bottom:0px;">
            <el-input v-model="listQuery.code"
                      placeholder="请输入水表号"
                      style="width: 180px; "
                      class="filter-item" />
          </el-form-item>

          <el-form-item label="信号强度" style="margin-bottom:0px;">
            <el-input v-model="listQuery.strength"
                      placeholder="请输入"
                      style="width: 180px; "
                      class="filter-item" />
          </el-form-item>
        </el-form>

        <div class="btn-group">
          <el-button v-waves
                     class="filter-item"
                     type="primary"
                     icon="el-icon-search"
                     style="margin-left: 10px;"
                     @click="handleFilter">查询</el-button>
          <el-button class="filter-item"
                     style="margin-left: 10px;"
                     type="success"
                     icon="el-icon-money"
                     @click="checkDeviceStation">校验</el-button>
          <el-button v-waves
                     :loading="downloadLoading"
                     style="margin-left: 10px;"
                     class="filter-item"
                     type="primary"
                     icon="el-icon-check"
                     @click="openValve">开阀</el-button>
          <el-button v-waves
                     :loading="downloadLoading"
                     style="margin-left: 10px;"
                     class="filter-item"
                     type="primary"
                     icon="el-icon-close"
                     @click="closeValve">关阀</el-button>

        </div>
      </div>
    </div>

    <el-table :key="tableKey"
              v-loading="listLoading"
              :data="list"
              border
              fit
              highlight-current-row
              style="width: 100%;"
              @row-click="rowClick">
      <el-table-column label="户号"
                       prop="id"
                       align="center"
                       width="60">
        <template slot-scope="{row}">
          <span> {{ row.userCode }} </span>
        </template>
      </el-table-column>
      <el-table-column label="户名" align="center" width="80px" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ row.userName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="地址" min-width="150px" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ row.area1 }} - {{ row.area2 }} - {{ row.area3 }} {{ row.area4 }} </span>
        </template>
      </el-table-column>
      <el-table-column label="电话" width="110px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.phone }}</span>
        </template>
      </el-table-column>
      <el-table-column label="表号" width="120px" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ row.code }}</span>
        </template>
      </el-table-column>
      <el-table-column label="校验" width="80px" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{  findDescByValue('meterModel','stationchecked',row.stationchecked)  }}</span>
        </template>
      </el-table-column>
      <el-table-column label="当前读数" width="80px" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ row.recentreadnum }}</span>
        </template>
      </el-table-column>
      <el-table-column label="最近上线时间" width="120px" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ row.recentreadtime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="日用量" width="80px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.dayuse }}</span>
        </template>
      </el-table-column>
      <el-table-column label="月用量" width="80px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.monthuse }}</span>
        </template>
      </el-table-column>
      <el-table-column label="任务名称" width="80px" align="center">
        <template slot-scope="{row}">
          <span>{{  findDescByValue('cmdModel','type',row.recentcmdtype) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="下发时间" width="120px" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ row.recentcmdtime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="执行结果" width="80px" align="center">
        <template slot-scope="{row}">
          <span>{{ findDescByValue('cmdModel','state',row.recentcmdstate) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="型号强度" width="80px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.strength }}</span>
        </template>
      </el-table-column>
      <el-table-column label="阀门" align="center" width="60px">
        <template slot-scope="{row}">
          <span>{{ findDescByValue('meterModel','valvestate',row.valvestate) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="电池" align="center" width="60px">
        <template slot-scope="{row}">
          <span>{{ findDescByValue('meterModel','batterystate',row.batterystate) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="传感器" align="center" width="100px">
        <template slot-scope="{row}">
          <span>{{ findDescByValue('meterModel','sensorstate',row.sensorstate) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="流量报警" align="center" width="80px">
        <template slot-scope="{row}">
          <span>{{ findDescByValue('meterModel','flowstate',row.flowstate) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="抄表周期" align="center" width="80px">
        <template slot-scope="{row}">
          <span>{{ row.timer }}</span>
        </template>
      </el-table-column>
      <el-table-column label="IMEI号" align="center" width="100px" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ row.imei }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="180px"
                       align="center"
                       class-name="small-padding fixed-width"
                       fixed="right">
        <template slot-scope="{row}">
          <el-button type="primary" size="mini" @click="showCMDLogDialog(row)" plain>命令记录</el-button>
          <el-button type="primary" size="mini" @click="showBillWaterDialog(row)" plain>用水记录</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0"
                :total="total"
                :page.sync="listQuery.page"
                :limit.sync="listQuery.rows"
                @pagination="getList" />
    <cmdLogDialog ref="cmdLogDialog" :userid="selectRow.userid" :meterCode="selectRow.code" />
    <billWaterDialog ref="billWaterDialog" :userid="selectRow.userid" :meterid="selectRow.id" />

  </div>
</template>

<script>

import { queryAddressList } from '@/api/addressManager'
  import { queryMeterAndUserList, openValveByCode, closeValveByCode, ajaxCheckDeviceStation } from '@/api/meterManager'
import { initAllDictionary, getDescByBeanAttrValue } from '@/api/dictionaryManager'
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination

  import cmdLogDialog from './cmdLogDialog'
  import billWaterDialog from './billWaterDialog'
  import { MessageBox } from 'element-ui';
export default {
  name: 'ChargeTable',
    components: { Pagination, cmdLogDialog, billWaterDialog },
  directives: { waves },
  filters: {
  },
  data() {
    return {
      findDescByValue: getDescByBeanAttrValue,
      tableKey: 0,
      list: null,
      total: 0,
      radioMeter: undefined,
      listLoading: true,
      listQuery: {
        page: 1,
        rows: 20,
        importance: undefined,
        title: undefined,
        type: undefined,
        sort: '+id'
      },
      selectRow: {},
      areaList: [],
      showReviewer: false,
      downloadLoading: false
    }
  },

  created() {
    initAllDictionary()
    this.getList()
    this.updateAddressList(1, null)
  },
  methods: {
    getList() {
      this.listLoading = true
      queryMeterAndUserList(this.listQuery).then(response => {
        this.list = response.data
        this.total = response.total

        // Just to simulate the time of the request
        setTimeout(() => {
          this.listLoading = false
        }, 0.5 * 1000)
      })
    },
    updateAddressList(level, parentId) {
      if (!level) {
        level = 1
      }
      queryAddressList({ level: level, parentId: parentId }).then(response => {
        this.areaList[level - 1] = response.data
        this.$forceUpdate()
      })
    },
    updateAllArea(val, level) {
      if (level == 1) {
        this.areaList[1] = {}
        this.areaList[2] = {}
        this.listQuery.area2 = ''
        this.listQuery.area3 = ''
      } else if (level == 2) {
        this.areaList[2] = {}
        this.listQuery.area3 = ''
      }
      var obj = this.areaList[level - 1].find(o => o.name === val)

      this.updateAddressList(level + 1, obj.id)
    },
    setArea3(val) {
      this.listQuery.area3 = val
      this.$forceUpdate()
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    rowClick(row, column, event) {
      //alert(row.pricename);
      this.selectRow = row;
    },
    showBillWaterDialog(row) {
      if (row.id) {
        this.$refs.billWaterDialog.meterid = row.id;
        this.$refs.billWaterDialog.dialogTableVisible = true
      }
    },
    showCMDLogDialog(row) {
      if (row.code) {
        this.$refs.cmdLogDialog.meterCode = row.code;
        this.$refs.cmdLogDialog.dialogTableVisible = true
      }
    },
    openValve() {
      if (this.selectRow.code) {
        MessageBox.confirm('您确定要执行开阀操作吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          // 用户点击了确定按钮，执行对应操作
          openValveByCode({ code: this.selectRow.code }).then(response => {
            this.$notify({
              title: '操作结果',
              message: response.message,
              type: 'success',
              duration: 2000
            })
          })

        }).catch(() => {
          // 用户点击了取消按钮，取消对应操作
        });
         
      }
    },
    closeValve() {
      if (this.selectRow.code) {
        MessageBox.confirm('您确定要执行关阀操作吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          // 用户点击了确定按钮，执行对应操作
          closeValveByCode({ code: this.selectRow.code }).then(response => {
            this.$notify({
              title: '操作结果',
              message: response.message,
              type: 'success',
              duration: 2000
            })
          })

        }).catch(() => {
          // 用户点击了取消按钮，取消对应操作
        });

      }
    },
    checkDeviceStation() {
      if (this.selectRow.code) {
         ajaxCheckDeviceStation({ meterid: this.selectRow.id }).then(response => {
            this.$notify({
              title: '操作结果',
              message: response.message,
              type: 'success',
              duration: 2000
            })
          })
      }
    }
  }
}
</script>

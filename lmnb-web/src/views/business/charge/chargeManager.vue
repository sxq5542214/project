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

          <el-form-item label="身份证" style="margin-bottom:0px;">
            <el-input v-model="listQuery.idcard"
                      placeholder="请输入证件号码"
                      style="width: 180px; "
                      class="filter-item" />
          </el-form-item>
          <el-form-item label="房间号" style="margin-bottom:0px;">
            <el-input v-model="listQuery.area4"
                      placeholder="请输入房间号"
                      style="width: 180px; "
                      class="filter-item" />
          </el-form-item>
          <el-form-item label="水表号" style="margin-bottom:0px;">
            <el-input v-model="listQuery.code"
                      placeholder="请输入水表号"
                      style="width: 180px; "
                      class="filter-item" />
          </el-form-item>
        </el-form>
          <!--

            <el-select v-model="listQuery.area1"
                       placeholder="请输入镇名"
                       style="width: 200px;"
                       class="filter-item"
                       filterable
                       @change="updateAllArea($event,1)">
              <el-option v-for="item in areaList[0]"
                         :key="item.id"
                         :label="item.name"
                         :value="item.name" />
            </el-select>
            <el-select v-model="listQuery.area2"
                       placeholder="请输入村名"
                       style="width: 200px;margin-left: 10px;"
                       class="filter-item"
                       filterable
                       @change="updateAllArea($event,2)">
              <el-option v-for="item1 in areaList[1]"
                         :key="item1.id"
                         :label="item1.name"
                         :value="item1.name" />
            </el-select>
            <el-select v-model="listQuery.area3"
                       placeholder="请输入组名"
                       style="width: 200px;margin-left: 10px;"
                       class="filter-item"
                       filterable
                       @change="setArea3($event)">
              <el-option v-for="item2 in areaList[2]"
                         :key="item2.id"
                         :label="item2.name"
                         :value="item2.name" />
            </el-select>-->

<!--

          <el-input v-model="listQuery.userName"
                    placeholder="请输入户名"
                    style="width: 200px;"
                    class="filter-item"
                    @keyup.enter.native="handleFilter" />
          <el-input v-model="listQuery.phone"
                    placeholder="请输入电话"
                    style="width: 200px;margin-left: 10px;"
                    class="filter-item" />
          <el-input v-model="listQuery.idcard"
                    placeholder="请输入证件号码"
                    style="width: 200px;margin-left: 10px;"
                    class="filter-item" />-->

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
                       @click="showChargeDialog">充值</el-button>
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
            <el-button v-waves
                       :loading="downloadLoading"
                       style="margin-left: 10px;"
                       class="filter-item"
                       type="danger"
                       icon="el-icon-guide"
                       @click="showChangeMeterDialog">换表</el-button>


            <el-button v-waves
                       :loading="downloadLoading"
                       style="margin-left: 10px;"
                       class="filter-item"
                       type="warning"
                       icon="el-icon-s-order"
                       @click="showChargeLogDialog">充值记录</el-button>
            <el-button v-waves
                       style="margin-left: 10px;"
                       class="filter-item"
                       type="warning"
                       icon="el-icon-s-operation"
                       @click="showBillDialog">账单记录</el-button>
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
                       width="80">
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
      <el-table-column label="表号" width="120px" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ row.code }}</span>
        </template>
      </el-table-column>
      <el-table-column label="阀门" align="center" width="60px">
        <template slot-scope="{row}">
          <span>{{ findDescByValue('meterModel','valvestate',row.valvestate) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="余额" align="center" width="80px">
        <template slot-scope="{row}">
          <span>{{ row.balance }}</span>
        </template>
      </el-table-column>
      <el-table-column label="抄表时间" align="center" width="110px" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ row.recentreadtime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="最近缴费时间" width="160px" align="center">
        <template slot-scope="{row}">
          <span>{{row.lastbuytime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="缴费金额" align="center">
        <template slot-scope="{row}">
          <span>{{ row.lastbuyamount }}</span>
        </template>
      </el-table-column>
      <el-table-column label="当月用量" width="110px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.monthuse }}</span>
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
      <el-table-column label="价格类型" width="100px" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ row.pricename }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" width="80px" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ findDescByValue('meter','opened',row.opened)  }}</span>
        </template>
      </el-table-column>
      <el-table-column label="报停时间" width="80px" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ row.stoptime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作"
                       align="center"
                       class-name="small-padding fixed-width"
                       fixed="right">
        <template slot-scope="{row}">
          <el-button type="primary" size="mini" @click="handleUpdate(row)">编辑</el-button>
          <!--           <el-button   type="danger" size="mini"  @click="handleDelete(row,$index)">
                  删除
      </el-button>-->
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0"
                :total="total"
                :page.sync="listQuery.page"
                :limit.sync="listQuery.rows"
                @pagination="getList" />
    <billDialog ref="billDialog" :userid="selectRow.userid" :user-name="selectRow.userName" :metercode="selectRow.code" :pricename="selectRow.pricename" :priceid="selectRow.pricecode" :address="selectRow.area1+ selectRow.area2 + selectRow.area3 " />
    <chargeLogDialog ref="chargeLogDialog" :userid="selectRow.userid" :user-name="selectRow.userName" :meter-code="selectRow.code" />
    <chargeDialog ref="chargeDialog" :userid="selectRow.userid" :user-name="selectRow.userName" :meter-code="selectRow.code" :balance="selectRow.balance" />
    <changeMeterDialog ref="changeMeterDialog" :userid="selectRow.userid" :user-name="selectRow.userName" :meter-code="selectRow.code" :meter="selectRow" />

  </div>
</template>

<script>

import { queryAddressList } from '@/api/addressManager'
import { queryMeterAndUserList, openValveByCode, closeValveByCode } from '@/api/meterManager'
import { initAllDictionary, getDescByBeanAttrValue } from '@/api/dictionaryManager'
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination

import billDialog from './billDialog'
import chargeLogDialog from './chargeLogDialog'
import chargeDialog from './chargeDialog'
  import changeMeterDialog from './changeMeterDialog'
  import { MessageBox } from 'element-ui';
export default {
  name: 'ChargeTable',
    components: { Pagination, billDialog, chargeLogDialog, chargeDialog, changeMeterDialog },
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
    showBillDialog() {
      if (this.$refs.billDialog.userid) {
        this.$refs.billDialog.userName = this.selectRow.userName + '的月账单'
        this.$refs.billDialog.dialogTableVisible = true
      } else {
        this.$notify({
          title: '请先选择用户',
          message: '请先选择用户，再查询账单',
          type: 'error',
          duration: 2000
        })
      }
    },
    showChargeLogDialog() {
      if (this.$refs.chargeLogDialog.userid) {
        this.$refs.chargeLogDialog.userName = this.selectRow.userName + '的充值记录'
        this.$refs.chargeLogDialog.dialogTableVisible = true
      } else {
        this.$notify({
          title: '请先选择用户',
          message: '请先选择用户，再查询充值记录',
          type: 'error',
          duration: 2000
        })
      }
    },

    showChangeMeterDialog() {      if (this.$refs.changeMeterDialog.userid) {        this.$refs.changeMeterDialog.userName = this.selectRow.userName        this.$refs.changeMeterDialog.meterCode = this.selectRow.code        this.$refs.changeMeterDialog.balance = this.selectRow.balance        this.$refs.changeMeterDialog.meter = this.selectRow        this.$refs.changeMeterDialog.dialogTableVisible = true      } else {        this.$notify({          title: '请先选择水表',          message: '请先选择用户水表，再点击换表',          type: 'error',          duration: 2000        })      }    },
    
    showChargeDialog() {
      if (this.$refs.chargeDialog.userid) {
        this.$refs.chargeDialog.userName = this.selectRow.userName
        this.$refs.chargeDialog.meterCode = this.selectRow.code
        this.$refs.chargeDialog.balance = this.selectRow.balance

        this.$refs.chargeDialog.dialogTableVisible = true
      } else {
        this.$notify({
          title: '请先选择用户',
          message: '请先选择用户，再点击充值',
          type: 'error',
          duration: 2000
        })
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
    }
  }
}
</script>

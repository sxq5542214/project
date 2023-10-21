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
        <br>
        <el-select
          v-model="listQuery.area1"
          placeholder="请输入镇名"
          style="width: 200px;"
          class="filter-item"
          filterable
          @change="updateAllArea($event,1)"
        >
          <el-option
            v-for="item in areaList[0]"
            :key="item.id"
            :label="item.name"
            :value="item.name"
          />
        </el-select>
        <el-select
          v-model="listQuery.area2"
          placeholder="请输入村名"
          style="width: 200px;margin-left: 10px;"
          class="filter-item"
          filterable
          @change="updateAllArea($event,2)"
        >
          <el-option
            v-for="item1 in areaList[1]"
            :key="item1.id"
            :label="item1.name"
            :value="item1.name"
          />
        </el-select>
        <el-select
          v-model="listQuery.area3"
          placeholder="请输入组名"
          style="width: 200px;margin-left: 10px;"
          class="filter-item"
          filterable
          @change="setArea3($event)"
        >
          <el-option
            v-for="item2 in areaList[2]"
            :key="item2.id"
            :label="item2.name"
            :value="item2.name"
          />
        </el-select>
        <el-button
          v-waves
          class="filter-item"
          type="primary"
          icon="el-icon-search"
          style="margin-left: 10px;"
          @click="handleFilter"
        >查询</el-button>
        <el-button
          class="filter-item"
          style="margin-left: 10px;"
          type="success"
          icon="el-icon-edit"
          @click="handleCreate"
        >添加</el-button>
        <el-button
          v-waves
          :loading="downloadLoading"
          class="filter-item"
          type="primary"
          icon="el-icon-download"
          @click="handleDownload"
        >导出</el-button>
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
          <el-button type="primary" size="mini" @click="handleUpdate(row)">编辑</el-button>
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

    <el-dialog title="新增/编辑用户" :visible.sync="dialogFormVisible" width="80%">
      <el-form
        ref="dataForm"
        :rules="rules"
        :model="temp"
        :inline="true"
        label-position="left"
        label-width="auto"
        style=" "
      >
        <el-form-item label="镇级" prop="area1">
          <el-select
            v-model="temp.area1"
            placeholder="请选择镇"
            class="filter-item"
            filterable
            @change="updateTempArea($event,1)"
          >
            <el-option
              v-for="item in tempAreaList[0]"
              :key="item.id"
              :label="item.name"
              :value="item.name"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="村级" prop="area2">
          <el-select
            v-model="temp.area2"
            placeholder="请选择村"
            class="filter-item"
            filterable
            @change="updateTempArea($event,2)"
          >
            <el-option
              v-for="item1 in tempAreaList[1]"
              :key="item1.id"
              :label="item1.name"
              :value="item1.name"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="组级" prop="area3">
          <el-select
            v-model="temp.area3"
            placeholder="请选择组"
            class="filter-item"
            filterable
            @change="setTempArea3($event)"
          >
            <el-option
              v-for="item2 in tempAreaList[2]"
              :key="item2.id"
              :label="item2.name"
              :value="item2.name"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="户名" prop="name">
          <el-input v-model="temp.name" placeholder="请输入用户名称" />
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="temp.phone" placeholder="请输入电话号码" />
        </el-form-item>
        <el-form-item label="身份证" prop="idcard">
          <el-input v-model="temp.idcard" placeholder="请输入身份证号码" />
        </el-form-item>
        <el-form-item label="户号" prop="id">
          <el-input v-model="temp.id" placeholder="无需输入自动生成" disabled="disabled" />
        </el-form-item>
        <div class="tab-container">
          <el-tabs v-model="activeName" style="margin-top:15px;" type="border-card">
            <el-tab-pane
              v-for="item in meterTabs"
              :key="item.id"
              ref="meterTabPane"
              :label="item.code"
              :name="item.code"
            >
              <keep-alive>
                <tab-pane v-if="activeName==item.code" :type="item.code" :cur-meter="item" @create="showCreatedTimes" />
              </keep-alive>
            </el-tab-pane>
          </el-tabs>
        </div>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">{{ $t('table.cancel') }}</el-button>
        <el-button
          type="primary"
          @click="dialogStatus==='create'?createData():updateData()"
        >{{ $t('table.confirm') }}</el-button>
      </div>
    </el-dialog>

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
import TabPane from './meterTab/meterTab'
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

const calendarTypeOptions = [
  { key: 'CN', display_name: 'China' },
  { key: 'US', display_name: 'USA' },
  { key: 'JP', display_name: 'Japan' },
  { key: 'EU', display_name: 'Eurozone' }
]

// arr to obj, such as { CN : "China", US : "USA" }
const calendarTypeKeyValue = calendarTypeOptions.reduce((acc, cur) => {
  acc[cur.key] = cur.display_name
  return acc
}, {})

export default {
  name: 'ComplexTable',
  components: { Pagination, TabPane },
  directives: { waves },
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: 'success',
        draft: 'info',
        deleted: 'danger'
      }
      return statusMap[status]
    },
    typeFilter(type) {
      return calendarTypeKeyValue[type]
    }
  },
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
      meterTabs: [
        { code: '新增水表', id: undefined }
      ],
      activeName: '新增水表',
      createdTimes: 0,
      importanceOptions: [1, 2, 3],
      calendarTypeOptions,
      areaList: [],
      tempAreaList: [],
      statusOptions: ['published', 'draft', 'deleted'],
      showReviewer: false,
      temp: {
        id: undefined,
        importance: 1,
        remark: '',
        timestamp: new Date(),
        title: '',
        type: '',
        status: 'published'
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: 'Edit',
        create: 'Create'
      },
      dialogPvVisible: false,
      pvData: [],
      rules: {
        area1: [{ required: true, message: '请选择镇' }],
        area2: [{ required: true, message: '请选择村' }],
        area3: [{ required: true, message: '请选择组' }],
        name: [{ required: true, message: '请输入用户姓名' }],
        phone: [{ required: true, message: '请输入用户电话' }]
      },
      downloadLoading: false
    }
  },

  created() {
    this.getList()
    this.updateAddressList(1, null)

    // init the default selected meterTab
    this.activeName = '新增水表'
  },
  methods: {
    getList() {
      this.listLoading = true
      queryUserList(this.listQuery).then(response => {
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
        if (level == 1) {
          this.tempAreaList[0] = response.data
        }
        this.$forceUpdate()
      })
    },
    updateTempAreaList(level, parentId) {
      if (!level) {
        level = 1
      }
      queryAddressList({ level: level, parentId: parentId }).then(response => {
        this.tempAreaList[level - 1] = response.data
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
    updateTempArea(val, level) {
      if (level == 1) {
        this.tempAreaList[1] = {}
        this.tempAreaList[2] = {}
        this.temp.area2 = ''
        this.temp.area3 = ''
      } else if (level == 2) {
        this.tempAreaList[2] = {}
        this.temp.area3 = ''
      }
      var obj = this.tempAreaList[level - 1].find(o => o.name === val)

      this.updateTempAreaList(level + 1, obj.id)
    },
    setArea3(val) {
      this.listQuery.area3 = val
      this.$forceUpdate()
    },
    setTempArea3(val) {
      this.temp.area3 = val
      this.$forceUpdate()
    },
    showCreatedTimes() {
      this.createdTimes = this.createdTimes + 1
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    handleModifyStatus(row, status) {
      this.$message({
        message: '操作成功',
        type: 'success'
      })
      row.status = status
    },
    sortChange(data) {
      const { prop, order } = data
      if (prop === 'id') {
        this.sortByID(order)
      }
    },
    sortByID(order) {
      if (order === 'ascending') {
        this.listQuery.sort = '+id'
      } else {
        this.listQuery.sort = '-id'
      }
      this.handleFilter()
    },
    resetTemp() {
      this.temp = {
        id: undefined,
        importance: 1,
        remark: '',
        timestamp: new Date(),
        title: '',
        status: 'published',
        type: ''
      }
    },
    handleCreate() {
      this.resetTemp()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true

      this.meterTabs = this.$options.data().meterTabs // 初始化的数据
      this.activeName = this.meterTabs[0].code

      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    createData() {
      console.log(this.$refs.meterTabPane[0].$children[0])
      this.$refs.meterTabPane[0].$children[0].validateForm()
      var canMeterSubmit = this.$refs.meterTabPane[0].$children[0].validateFlag
      if (!canMeterSubmit) return
      // 创建用户
      this.$refs['dataForm'].validate(valid => {
        if (valid) {
          addUser(this.temp).then(response => {
            this.temp.id = response.data.id
            this.temp.createData = response.data.createData
            this.list.unshift(this.temp)
            this.dialogFormVisible = false
            // 创建水表
            this.$notify({
              title: '用户创建成功',
              message: '用户创建成功，正常创建用户的水表',
              type: 'success',
              duration: 2000
            })

            // 创建水表
            if (canMeterSubmit) {
              var meter = this.$refs.meterTabPane[0].$children[0].meter
              meter.userid = this.temp.id

              addMeter(meter).then(response => {
                this.$notify({
                  title: '创建成功',
                  message: '用户及水表创建成功',
                  type: 'success',
                  duration: 2000
                })
              })
            }
          })
        }
      })
    },
    handleUpdate(row) {
      this.temp = row // copy obj
      // this.temp.timestamp = new Date(this.temp.timestamp)
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      // this.$nextTick(() => {
      //   this.$refs['dataForm'].clearValidate()
      // })
      // 取用户的水表清单
      var data = {}
      data.userid = row.id
      queryMeterList(data).then(response => {
        this.meterTabs = response.data
        if (this.meterTabs.length > 0) {
          this.meterTabs.push(this.$options.data().meterTabs[0])
        } else {
          this.meterTabs = this.$options.data().meterTabs
        }
        this.activeName = this.meterTabs[0].code
      })
    },
    updateData() {
      this.$refs['dataForm'].validate(valid => {
        if (valid) {
          const tempData = Object.assign({}, this.temp)

          updateUser(this.temp).then(response => {
            const index = this.list.findIndex(v => v.id === this.temp.id)
            this.list.splice(index, 1, this.temp)

            // 修改水表
            const tabIndex = this.$refs.meterTabPane.findIndex(v => v.name === this.activeName)
            var meter = this.$refs.meterTabPane[tabIndex].$children[0].meter
            meter.userid = meter.userid = this.temp.id
            // meter.userid = this.temp.id;
            updateMeter(meter).then(response => {
              this.$notify({
                title: '更新成功',
                message: '用户及水表更新成功',
                type: 'success',
                duration: 2000
              })
            })

            this.dialogFormVisible = false
            // this.$notify({
            //   title: "成功",
            //   message: "更新成功",
            //   type: "success",
            //   duration: 2000
            // });
          })
        }
      })
    },
    handleDelete(row, index) {
      deleteUser(row).then(response => {
        this.list.splice(index, 1)
        this.$notify({
          title: '成功',
          message: '删除成功',
          type: 'success',
          duration: 2000
        })
      })
    },
    handleFetchPv(pv) {
      fetchPv(pv).then(response => {
        this.pvData = response.data.pvData
        this.dialogPvVisible = true
      })
    },
    handleDownload() {
      this.downloadLoading = true
      import('@/vendor/Export2Excel').then(excel => {
        const tHeader = ['timestamp', 'title', 'type', 'importance', 'status']
        const filterVal = [
          'timestamp',
          'title',
          'type',
          'importance',
          'status'
        ]
        const data = this.formatJson(filterVal)
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename: 'table-list'
        })
        this.downloadLoading = false
      })
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

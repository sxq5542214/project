<template>
  <el-form ref="dataForm" 
           :rules="rules"
           :model="meter"
           :inline="true"
           label-position="left"
           label-width="auto"
           style=" ">
    <el-form-item label="表号" prop="code">
      <el-input v-model="meter.code" placeholder="请输入表号"  style="width:200px" />
    </el-form-item>
    <el-form-item label="表地址" prop="installposition">
      <el-input v-model="meter.installposition" placeholder="请输入表安装地址"  style="width:200px" />
    </el-form-item>
    <el-form-item label="表底数" prop="basenum">
      <el-input v-model="meter.basenum" placeholder="请输入用户名称" :disabled="isDisabled"  style="width:200px" />
    </el-form-item>
    <el-form-item label="开户费" prop="openfee">
      <el-input  v-model.number="meter.openfee" placeholder="请输入开户费用" style="width:200px" />
    </el-form-item>
    <el-form-item label="价格类型" prop="pricecode">
      <el-select v-model="meter.pricecode" placeholder="请选择" class="filter-item" filterable style="width:200px" >
        <el-option v-for="item in priceList" :key="item.id" :label="item.name" :value="item.id" />
      </el-select>
    </el-form-item>
    <el-form-item label="厂商" prop="factorycode">
      <el-select v-model="meter.factorycode" placeholder="请选择" class="filter-item" filterable style="width:200px" >
        <el-option v-for="item in supplierList" :key="item.id" :label="item.name" :value="item.id" />
      </el-select>
    </el-form-item>
    <el-form-item label="产品类型" prop="productid">
      <el-select v-model="meter.productid" placeholder="请选择" class="filter-item" filterable style="width:200px" >
        <el-option v-for="item in productList" :key="item.id" :label="item.name" :value="item.id" />
      </el-select>
    </el-form-item>
    <el-form-item label="口径" prop="caliber">
      <el-select v-model="meter.caliber" placeholder="请选择" class="filter-item" filterable style="width:200px" >
        <el-option v-for="item in caliberList"
                   :key="item.value"
                   :label="item.description"
                   :value="item.value" />
      </el-select>
    </el-form-item>
    <el-form-item label="账户状态" prop="opened">
      <el-select v-model="meter.opened" placeholder="请选择" class="filter-item" filterable style="width:200px" >
        <el-option v-for="item in openedList"
                   :key="item.value"
                   :label="item.description"
                   :value="item.value" />
      </el-select>
    </el-form-item>
  </el-form>
</template>

<script>
import { queryPriceList } from '@/api/priceManager'
import { querySupplierList } from '@/api/supplierManager'
import { queryProductList } from '@/api/productManager'
import { findMeterByCode } from '@/api/meterManager'
import { queryMeterCaliberList, queryMeterOpenedList } from '@/api/dictionaryManager'

export default {
  filters: {
  },
  props: {
    type: { },
    curMeter: {}
  },
  data() {
    return {
      list: null,
      validateFlag: false,
      meter: { basenum: 0, code: '', installposition: '', factorycode: '', pricecode: '', productid: '', caliber: '', opened: '', userid: '', openfee: undefined, id: undefined },
      priceList: {},
      supplierList: {},
      productList: {},
      caliberList: {},
      openedList: {},
      isDisabled: false,
      listQuery: {
        page: 1,
        limit: 5,
        // type: this.type,
        sort: '+id'
      },
      rules: {
        code: [{ required: true, message: '请输入表号' }],
        openfee: [{ required: true, message: '请正确输入开户费用', type: 'number' }]
      },
      loading: false
    }
  },
  created() {
    this.getList()
  },
  methods: {
    validateForm() {
      this.$refs['dataForm'].validate(valid => {
        this.validateFlag = valid
      })
    },

    getMeter() {
      return this.meter
    },

    getList() {
      this.loading = true
      this.meter.code = this.curMeter.code
      this.meter.installposition = this.curMeter.installposition
      // this.$emit("create"); // for test

      querySupplierList().then(response => {
        this.supplierList = response.data
        this.meter.factorycode = response.data[0].id
      })
      queryPriceList().then(response => {
        this.priceList = response.data
        this.meter.pricecode = response.data[0].id
      })
      queryProductList().then(response => {
        this.productList = response.data
        this.meter.productid = response.data[0].id
      })
      queryMeterCaliberList().then(response => {
        this.caliberList = response.data
        this.meter.caliber = response.data[0].value
      })
      queryMeterOpenedList().then(response => {
        this.openedList = response.data
        this.meter.opened = response.data[0].value
      })
      if (this.curMeter.code != '新增水表') {
        // 加载数据
        setTimeout(() => {
          this.meter.basenum = this.curMeter.basenum
          this.meter.factorycode = Number(this.curMeter.factorycode)
          this.meter.pricecode = this.curMeter.pricecode
          this.meter.productid = this.curMeter.productid
          this.meter.caliber = this.curMeter.caliber
          this.meter.opened = this.curMeter.opened + ''
          this.meter.userid = this.curMeter.userid
          this.meter.id = this.curMeter.id
          this.isDisabled = true

          this.$forceUpdate()
        }, 0.5 * 1000)
      } else {
        this.meter.code = ''
      }
      this.loading = false
    }
  }
}
</script>


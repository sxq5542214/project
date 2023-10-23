<template>
  <el-dialog
    :title="userName"
    :visible.sync="dialogTableVisible"
    width="90%"
    @open="openDialog"
  >
    <el-table
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%; margin-bottom: 50px;"
    >
      <el-table-column property="billmonth" label="月份" />
      <el-table-column property="quantity" label="用水量" />
      <el-table-column property="realbillamount" label="水费" />
      <el-table-column property="cyclestartbalance" label="期初余额" />
      <el-table-column property="cyclebuyamount" label="充值金额" />
      <el-table-column property="cycleendbalance" label="期末余额" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right">
        <template slot-scope="{row}">
          <el-button type="primary" size="mini" icon="el-icon-printer" @click="handlePrint(row)">打印账单</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-dialog>
</template>

<script>
import { queryPrintTemplateList, updatePrintTemplateStyle } from '@/api/printManager'
import { getLodop, checkOrTryHttp } from '@/views/business/print/lodop/LodopFuncs'
  import { queryBillList } from '@/api/billManager'
  import { queryPriceDetail } from '@/api/priceManager'
  import { initAllDictionary, getDescByBeanAttrValue } from '@/api/dictionaryManager'
  import moment from 'moment';
  import waves from '@/directive/waves' // waves directive
  import { parseTime } from '@/utils'
  import { amountToChinese } from '@/utils/price'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination

export default {
  components: { Pagination },
  directives: { waves },
  filters: {
  },
    props: {
      metercode: {},
      pricename: {},
      priceid: {},
      address: {},
      userid: {}
  },
  data() {
    return {
      dialogTableVisible: false,
      findDescByValue: getDescByBeanAttrValue,
      tableKey: 0,
      list: null,
      template: null,
      priceDetail: null,
      userName: '用户',
      total: 0,
      listLoading: true,
      showReviewer: false,
      downloadLoading: false
    }
  },

  created() {
    queryPrintTemplateList({ id: 1 }).then(response => {
      this.template = response.data

    });
  },
    methods: {
      getList() {
        console.log(amountToChinese(0.00));
      if (this.userid) {
        this.listLoading = true
        queryBillList({ userid: this.userid }).then(response => {
          this.list = response.data
          this.total = response.total

          // Just to simulate the time of the request
          setTimeout(() => {
            this.listLoading = false
          }, 0.5 * 1000)
        })
      }
    },
    openDialog() {
      this.getList();

      queryPriceDetail({ priceid: this.priceid }).then(response => {
        this.priceDetail = response.data
      })
    },
    handlePrint(row) {

      var date = moment().format('YYYY-MM-DD');
      var time = moment().format('HH:mm:ss');

      var styleStr = this.template.style;
      var LODOP = getLodop();
      eval(styleStr);
      //用来修改模板中的变量内容
      LODOP.SET_PRINT_STYLEA("chargeOperator", 'CONTENT', window.sessionStorage.getItem('operatorName'));
      LODOP.SET_PRINT_STYLEA("invoiceDate", 'CONTENT', date+" "+time);
      LODOP.SET_PRINT_STYLEA("customerName", 'CONTENT', this.userName);
      LODOP.SET_PRINT_STYLEA("customerAddress", 'CONTENT', this.address);
      LODOP.SET_PRINT_STYLEA("meterNo", 'CONTENT', this.metercode);
      LODOP.SET_PRINT_STYLEA("lastBalance", 'CONTENT', row.cyclestartbalance);
      LODOP.SET_PRINT_STYLEA("currentBalance", 'CONTENT', row.cycleendbalance);
      LODOP.SET_PRINT_STYLEA("chargeAmountUpperCase", 'CONTENT', amountToChinese(row.cyclebuyamount));
      LODOP.SET_PRINT_STYLEA("chargeAmount", 'CONTENT', row.cyclebuyamount);
      LODOP.SET_PRINT_STYLEA("chargeDate", 'CONTENT', row.billmonth);
      LODOP.SET_PRINT_STYLEA("rateName", 'CONTENT', this.pricename);
      LODOP.SET_PRINT_STYLEA("chargeUnitPrice0", 'CONTENT', this.priceDetail.price1);
      LODOP.SET_PRINT_STYLEA("chargeCount0", 'CONTENT', row.quantity);
      LODOP.SET_PRINT_STYLEA("chargeAmount0", 'CONTENT', row.cyclebuyamount);
      LODOP.SET_PRINT_STYLEA("currentNum", 'CONTENT', '');
      LODOP.SET_PRINT_STYLEA("customerNo", 'CONTENT', row.meterid);

      LODOP.PRINT_DESIGN();

    }
  }
}
</script>

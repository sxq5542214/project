<template>
  <div class="dashboard-editor-container">
    <github-corner class="github-corner" />

    <panel-group @handleSetLineChartData="handleSetLineChartData" />

    <el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
      <line-chart :chart-data="lineChartData" />
    </el-row>

    <el-row :gutter="32">
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <raddar-chart />
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <pie-chart />
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <bar-chart />
        </div>
      </el-col>
    </el-row>

    <el-row :gutter="8">
      <el-col :xs="{span: 24}" :sm="{span: 24}" :md="{span: 24}" :lg="{span: 12}" :xl="{span: 12}" style="padding-right:8px;margin-bottom:30px;">
        <transaction-table />
      </el-col>
      <el-col :xs="{span: 24}" :sm="{span: 12}" :md="{span: 12}" :lg="{span: 6}" :xl="{span: 6}" style="margin-bottom:30px;">
        <todo-list />
      </el-col>
      <el-col :xs="{span: 24}" :sm="{span: 12}" :md="{span: 12}" :lg="{span: 6}" :xl="{span: 6}" style="margin-bottom:30px;">
        <box-card />
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import { queryDayBuyAmountAdminListData, queryDayBuyCountListData, queryDayMeterReadingCountListData, queryOpenedMeterCountListData } from '@/api/dashboardManager'
import GithubCorner from '@/components/GithubCorner'
import PanelGroup from './components/PanelGroup'
import LineChart from './components/LineChart'
import RaddarChart from './components/RaddarChart'
import PieChart from './components/PieChart'
import BarChart from './components/BarChart'
import TransactionTable from './components/TransactionTable'
import TodoList from './components/TodoList'
import BoxCard from './components/BoxCard'


const lineChartData = {
  newVisitis: {
    lastMonthData: [100, 120, 161, 134, 105, 160, 165],
    thisMonthData: [120, 82, 91, 154, 162, 140, 145]
  },
  messages: {
    lastMonthData: [200, 192, 120, 144, 160, 130, 140],
    thisMonthData: [180, 160, 151, 106, 145, 150, 130]
  },
  purchases: {
    lastMonthData: [80, 100, 121, 104, 105, 90, 100],
    thisMonthData: [120, 90, 100, 138, 142, 130, 130]
  },
  shoppings: {
    lastMonthData: [130, 140, 141, 142, 145, 150, 160],
    thisMonthData: [120, 82, 91, 154, 162, 140, 130]
  },
  dayBuyAmountListData: {
    lastMonthData: [],
    thisMonthData: []
  },
  dayBuyCountListData: {
    lastMonthData: [],
    thisMonthData: []
  },
  dayMeterReadingListData: {
    lastMonthData: [],
    thisMonthData: []
  },
  dayOpenUserListData: {
    lastMonthData: [],
    thisMonthData: []
  }
}

export default {
  name: 'DashboardAdmin',
  components: {
    GithubCorner,
    PanelGroup,
    LineChart
    // RaddarChart,
    // PieChart,
    // BarChart,
    // TransactionTable,
    // TodoList,
    // BoxCard
  },
  data() {
    return {
      lineChartData: lineChartData.dayBuyAmountListData
    }
  },

    created() {
       
      queryDayBuyAmountAdminListData().then(response => {
        for (var i = 0; i < response.data.length; i++) {
          lineChartData.dayBuyAmountListData.lastMonthData.push(response.data[i].lastMonthData);
          lineChartData.dayBuyAmountListData.thisMonthData.push(response.data[i].thisMonthData);
        }
      });
      queryDayBuyCountListData().then(response => {
        for (var i = 0; i < response.data.length; i++) {
          lineChartData.dayBuyCountListData.lastMonthData.push(response.data[i].lastMonthData);
          lineChartData.dayBuyCountListData.thisMonthData.push(response.data[i].thisMonthData);
        }
      });
      queryDayMeterReadingCountListData().then(response => {
        for (var i = 0; i < response.data.length; i++) {
          lineChartData.dayMeterReadingListData.lastMonthData.push(response.data[i].lastMonthData);
          lineChartData.dayMeterReadingListData.thisMonthData.push(response.data[i].thisMonthData);
        }
      });
      queryOpenedMeterCountListData().then(response => {
        for (var i = 0; i < response.data.length; i++) {
          lineChartData.dayOpenUserListData.lastMonthData.push(response.data[i].lastMonthData);
          lineChartData.dayOpenUserListData.thisMonthData.push(response.data[i].thisMonthData);
        }
      });



  },
  methods: {
    handleSetLineChartData(type) {
      this.lineChartData = lineChartData[type]
    }
  }
}
</script>

<style lang="scss" scoped>
.dashboard-editor-container {
  padding: 32px;
  background-color: rgb(240, 242, 245);
  position: relative;

  .github-corner {
    position: absolute;
    top: 0px;
    border: 0;
    right: 0;
  }

  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }
}

@media (max-width:1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}
</style>

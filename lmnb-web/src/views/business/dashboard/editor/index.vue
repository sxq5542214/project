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
import GithubCorner from '@/components/GithubCorner'
import PanelGroup from './components/PanelGroup'
import LineChart from './components/LineChart'
import RaddarChart from './components/RaddarChart'
import PieChart from './components/PieChart'
import BarChart from './components/BarChart'
import TransactionTable from './components/TransactionTable'
import TodoList from './components/TodoList'
  import BoxCard from './components/BoxCard'
  import { queryDayBuyAmountAdminListData, queryDayBuyCountListData, queryDayMeterReadingCountListData, queryOpenedMeterCountListData  } from '@/api/dashboardManager'


  const lineChartData = {
    dayBuyAmountListData: {
      lastMonthData: [],
      thisMonthData: []
    },
    dayBuyCountListData: {
      lastMonthData: [],
      thisMonthData: []
    }
  }

export default {
  name: 'DashboardEditor',
  components: {
    GithubCorner,
    PanelGroup,
    LineChart,
     RaddarChart,
     PieChart,
     BarChart,
     TransactionTable,
     TodoList,
     BoxCard
  },
  data() {
    return {
      lineChartData: {
          dayBuyAmountListData: {
            lastMonthData: [],
            thisMonthData: []
          },
          dayBuyCountListData: {
            lastMonthData: [],
            thisMonthData: []
          }
        },
      serverData: {
        expectedData: [],
        actualData :[]
      }
      }
    },
    created() {
      var operatorid = window.localStorage.getItem('opid');

      queryDayBuyAmountAdminListData({ operatorid: operatorid, month: -1 }).then(response => {
        for (var i = 0; i < response.data.length; i++) {
          lineChartData.dayBuyAmountListData.lastMonthData.push(response.data[i].lastMonthData);
          lineChartData.dayBuyAmountListData.thisMonthData.push(response.data[i].thisMonthData);
        }
      });
      queryDayBuyCountListData({ operatorid: operatorid, month: 0 }).then(response => {
        for (var i = 0; i < response.data.length; i++) {
          lineChartData.dayBuyCountListData.lastMonthData.push(response.data[i].lastMonthData);
          lineChartData.dayBuyCountListData.thisMonthData.push(response.data[i].thisMonthData);
        }
      });
      //queryDayBuyAmountSumListOfMonth().then(response => { this.serverData.expectedData = response.data })
      //queryDayBuyAmountSumListOfMonth({ operatorid: 1, month: 0 }).then(response => { this.serverData.actualData = response.data })

      setTimeout(this.handleSetLineChartData('dayBuyAmountListData'), 2000)
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

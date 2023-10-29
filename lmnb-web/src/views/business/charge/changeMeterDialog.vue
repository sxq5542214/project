<template>
  <el-dialog title="换表操作"
             :visible.sync="dialogTableVisible"
             width="80%"
             @open="openDialog">
    <el-form :inline="true" class="demo-form-inline" label-position="right" label-width="100px"  
         ref="dataForm" :model="meter">
      <el-form-item label="户号">
        <el-input v-model="meter.id" placeholder="户号标识" :disabled="true" />
      </el-form-item>
      <el-form-item label="姓名">
        <el-input v-model="userName" placeholder="用户名称" :disabled="true" />
      </el-form-item>
      <el-form-item label="电话">
        <el-input v-model="meter.phone" placeholder="当前余额" :disabled="true" />
      </el-form-item>
      <el-form-item label="旧表表号">
        <el-input v-model="meterCode" placeholder="水表表号" :disabled="true" />
      </el-form-item>
      <el-form-item label="旧表余额">
        <el-input v-model="meter.balance" placeholder="当前余额" :disabled="true" />
      </el-form-item>
      <el-form-item label="最近抄表时间">
        <el-input v-model="meter.recentreadtime" placeholder="当前余额" :disabled="true" />
      </el-form-item>
      <el-form-item label="最近抄表读数">
        <el-input v-model="meter.recentreadnum" placeholder="当前余额" :disabled="true" />
      </el-form-item>

      <el-form-item label="换表费用">
        <el-input v-model="changeMeterCost" placeholder="请输入换表费用" />
      </el-form-item>
      <el-form-item label="新表表号"  prop="changeMeterCode" :rules="[{required:true,message:'请输入新表表号'}]">
        <el-input v-model="meter.changeMeterCode" placeholder="请输入新表表号"/>
      </el-form-item>
      <el-form-item label="旧表止码"  prop="oldReadNum">
        <el-input v-model="oldReadNum" placeholder="请输入旧表止码"/>
      </el-form-item>
      <el-form-item label="新表起码"  prop="changeReadNum">
        <el-input v-model="changeReadNum" placeholder="请输入新表起码"/>
      </el-form-item>
      <el-form-item label="维修工">
        <el-input v-model="changeFixUser" placeholder="请输入维修工姓名" />
      </el-form-item>
      <el-form-item label="备注">
        <el-input v-model="remark1" placeholder="请输入备注说明" />
      </el-form-item>

      <el-form-item label=" ">
        <el-button type="primary" @click="onSubmit">确定</el-button>
        <el-button type="primary" @click="onCancle">取消</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script>

  import { changeMeter  } from '@/api/meterManager'
  import { initAllDictionary, getDescByBeanAttrList } from '@/api/dictionaryManager'
  import waves from '@/directive/waves' // waves directive
  import { parseTime } from '@/utils'
  import Pagination from '@/components/Pagination' // secondary package based on el-pagination

  export default {
    components: { Pagination },
    directives: { waves },
    filters: {
    },
    props: {
      userid: {},
      meterCode: 0,
      meter: {}
    },
    data() {
      return {
        dialogTableVisible: false,
        userName: '用户',
        changeMeterCost: 0,
        changeMeterCode: '',
        oldReadNum: 0,
        changeReadNum: 0,
        changeFixUser: '',
        remark1: '',
        tableKey: 0,
        paperflagList: getDescByBeanAttrList('paymentModel', 'paperflag'),
        accountmodeList: getDescByBeanAttrList('paymentModel', 'accountmode'),
        total: 0,
        listLoading: true,
        showReviewer: false,
        downloadLoading: false
      }
    },

    created() {
      // this.getList();
    },
    methods: {
      openDialog() {
        this.$forceUpdate()
      },
      onSubmit() {

        this.meter.changeMeterCost = this.changeMeterCost;
        //this.meter.changeMeterCode = this.changeMeterCode;
        this.meter.oldReadNum = this.oldReadNum;
        this.meter.changeReadNum = this.changeReadNum;
        this.meter.changeFixUser = this.changeFixUser;
        this.meter.remark1 = this.remark1;

        console.log(this.meter);
        //if (this.meter.changeMeterCode)
        if (this.meter.changeMeterCode.trim() == '') {
          this.$notify({
            title: '提醒',
            message: '请填写新表表号!',
            type: 'error',
            duration: 2000
          })
        }

        this.$refs['dataForm'].validate(valid => {
          if (valid) {

            changeMeter(this.meter).then(resp => {
              this.$notify({
                title: '操作结果',
                message: '换表成功',
                type: 'success',
                duration: 2000
              });
            });

            this.dialogTableVisible = false
          } else {
            this.$notify({
              title: '提醒',
              message: '请填写必填项',
              type: 'error',
              duration: 2000
            });
          }
          
        });
        
      },
      onCancle() {
        this.dialogTableVisible = false
      },
      handlePrint(row) {
        alert('很抱歉，功能正在实现中，后续开放使用')
      }
    }
  }
</script>

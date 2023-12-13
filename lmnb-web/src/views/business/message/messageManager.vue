<template>
  <el-container style="border: 1px solid #eee;margin-left:10px;margin-right:10px;">
    <el-card class="box-card" style="width:40%">
      <div slot="header" class="clearfix">
        <span>地址列表</span>
      </div>
      <div class="tab-container">
        <el-tree
          ref="tree"
          node-key="id"
          :props="props"
          :load="loadNode"
          lazy
                   
          highlight-current
          show-checkbox
          :render-content="renderNextButton"
          @check-change="handleCheckChange"
        />
      </div>
    </el-card>
    <el-container style="margin-left:10px;margin-right:10px;">
      <el-card class="box-card" style="width:90%">
        <div slot="header" class="clearfix">
          <span>{{ headerLabel }}</span>
        </div>
        <div class="tab-container">
          <el-form ref="dataForm" :rules="rules" :model="dataForm" label-position="left" style="">
            <el-form-item label="短信内容" prop="content" >
              <el-input show-word-limit
                        maxlength="300" 
                        class="public-showWordLimit"
                        :autosize="{ minRows: 3, maxRows: 8 }"
                        type="textarea"
                        v-model="dataForm.content" />
              </el-form-item>

            <el-button ref="submitBTN" :type="buttonType" @click="submitSMS">{{ buttonLabel }}</el-button>
          </el-form>
        </div>
      </el-card>
    </el-container>
  </el-container>
</template>

<script>
  import { queryAddressList, addAddress, updateAddress, deleteAddress } from '@/api/addressManager'
  import { ajaxSendSMSByAddress } from '@/api/messageManager'
export default {
  data() {
    return {
      addrids: {},
      dataForm: { content: '' },
      headerLabel: '群发短信',
      buttonLabel: '提交群发',
		  buttonType: 'primary',
		  addressList: {},
		  address: { parentName: '', parentId: undefined, name: '', level: undefined, id: undefined },
      props: {
        label: 'name',
        children: 'zones'
      },
      rules: {
        content: [{ required: true, message: '请填写短信内容' }]
      }
    }
  },
  created() {
    // this.initAddressList();
  },
  methods: {
    handleCheckChange(data, checked, indeterminate) {
      //console.log(data, checked, indeterminate);

      //if (checked) {
      //  this.addrids.add(data.id);
      //} else {
      //  this.addrids.delete(data.id);
      //}
      //console.log("current addressids: ", this.addrids);

    },
    handleNodeClick(data, node) {
		  this.headerLabel = '修改地址'
		  this.buttonType = 'success'
      this.address.name = data.name
      this.address.level = data.level
      this.address.id = data.id
      this.address.parentId = data.parentId
      var parentData = node.parent.data
      if (parentData) {
        this.address.parentName = parentData.name
      }
	  },
	  handleAddNext(data, node) {
		  this.headerLabel = '添加地址'
		  this.buttonType = 'primary'
      this.address.name = ''
      this.address.level = data.level + 1
      this.address.id = undefined
      this.address.parentId = data.id
      this.address.parentName = data.name
	  },
      submitSMS() {
       this.addrids = new Set();
       var checkedNodes = this.$refs.tree.getCheckedNodes();

       //console.log(checkedNodes);
       for (var i = 0; i < checkedNodes.length; i++) {
         var cur = checkedNodes[i];
         this.addrids.add(cur.id);
       }

      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          var add = Array.from(this.addrids).toString()

          console.log(add);
          if (this.addrids.size > 0) {
            ajaxSendSMSByAddress({ content: this.dataForm.content, addressids: add }).then(response => {
              //curNode.data = this.address
              //curNode.name = this.address.name
              this.$notify({
                title: '成功',
                message: '提交成功，稍后请在报表中查询结果',
                type: 'success',
                duration: 2000
              })
            })
          } else {
              this.$notify({
                title: '错误',
                message: '请先选择地址！',
                type: 'error',
                duration: 2000
              })
          }
        }
      })
	  },
	  renderNextButton(h, { node, data, store }) {
      return (
        <span class='custom-tree-node'>
          <span on-click={ () => this.handleNodeClick(data, node) }>{node.label + data.id}</span>
          <span>
            {/*<el-button size='mini' type='text' style='margin-left:10px;' on-click={ () => this.handleAddNext(data, node) }>添加下级</el-button>*/}
          </span>
        </span>)
    },
    loadNode(node, resolve) {
      //    console.log(node);
      //   console.log(resolve);
      queryAddressList({ level: node.level + 1, parentId: node.key }).then(response => {
        resolve(response.data)
      })
    }
  }
}
</script>

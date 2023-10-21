<template>
  <el-container style="border: 1px solid #eee;margin-left:10px;margin-right:10px;">
    <el-card class="box-card">
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
          <el-form ref="dataForm" :rules="rules" :model="address" label-position="left" style="">
            <el-form-item label="上级地址" prop="parentName">
              <el-input v-model="address.parentName" placeholder="" disabled />
            </el-form-item>
            <el-form-item label="地址名称" prop="name">
              <el-input v-model="address.name" placeholder="请输入地址名称" />
            </el-form-item>
            <el-form-item label="地址级别" prop="level">
              <el-input v-model="address.level" placeholder="最上级" disabled />
            </el-form-item>

            <el-button ref="submitBTN" :type="buttonType" @click="submitAddress">{{ headerLabel }}</el-button>
            <el-button ref="deleteBTN" type="danger" @click="deleteAddress">删除地址</el-button>
          </el-form>
        </div>
      </el-card>
    </el-container>
  </el-container>
</template>

<script>
import { queryAddressList, addAddress, updateAddress, deleteAddress } from '@/api/addressManager'
export default {
  data() {
    return {
		  headerLabel: '添加地址',
		  buttonType: 'primary',
		  addressList: {},
		  address: { parentName: '', parentId: undefined, name: '', level: undefined, id: undefined },
      props: {
        label: 'name',
        children: 'zones'
      },
      rules: {
        name: [{ required: true, message: '请填写地址名称', trigger: 'change' }]
      }
    }
  },
  created() {
    // this.initAddressList();
  },
  methods: {
    handleCheckChange(data, checked, indeterminate) {
      console.log(data, checked, indeterminate)
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
	  submitAddress() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          var curNode = this.$refs.tree.getCurrentNode()
          if (this.address.id) {
            updateAddress(this.address).then(response => {
              curNode.data = this.address
              curNode.name = this.address.name
              this.$notify({
                title: '成功',
                message: '更新成功',
                type: 'success',
                duration: 2000
              })
            })
          } else {
            addAddress(this.address).then(response => {
              this.address.id = response.data.id

              this.$notify({
                title: '成功',
                message: '添加成功',
                type: 'success',
                duration: 2000
              })
              if (this.address.level) {
                this.$refs.tree.append(Object.assign({}, this.address), curNode)
              } else {
                location.reload()
              }
            })
          }
        }
      })
	  },
	  deleteAddress() {
		  if (this.address.id) {
        deleteAddress({ id: this.address.id }).then(response => {
          var curNode = this.$refs.tree.getCurrentNode()
          this.$refs.tree.remove(curNode)
          this.$notify({
            title: '成功',
            message: '删除成功',
            type: 'success',
            duration: 2000
          })
        })
		  } else {
			  this.$notify({
          title: '失败',
          message: '请选择地址后再点击删除！',
          type: 'error',
          duration: 2000
        })
		  }
	  },
	  renderNextButton(h, { node, data, store }) {
      return (
        <span class='custom-tree-node'>
          <span on-click={ () => this.handleNodeClick(data, node) }>{node.label}</span>
          <span>
            <el-button size='mini' type='text' style='margin-left:10px;' on-click={ () => this.handleAddNext(data, node) }>添加下级</el-button>
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

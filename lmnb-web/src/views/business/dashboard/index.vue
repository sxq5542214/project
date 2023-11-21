<template>
  <div class="dashboard-container">
    <component :is="currentRole" />
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import adminDashboard from './admin'
import editorDashboard from './editor'

export default {
  name: 'Dashboard',
  components: { adminDashboard, editorDashboard },
  data() {
    return {
      currentRole: 'editorDashboard'
    }
  },
  computed: {
    ...mapGetters([
      'roles'
    ])
  },
    created() {
      console.log("this.roles:", this.roles);
      if (this.roles.includes('admin') || this.roles.includes('系统管理员')) {
      this.currentRole = 'adminDashboard'
    }
  }
}
</script>

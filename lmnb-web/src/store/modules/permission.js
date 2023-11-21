import { asyncRoutes, constantRoutes } from '@/router'
import store from '@/store'
import Layout from '@/layout'
import request from '@/utils/request'

/**
 * Use meta.role to determine if the current user has permission
 * @param roles
 * @param route
 */
function hasPermission(roles, route) {
  if (route.meta && route.meta.roles) {
    return roles.some(role => route.meta.roles.includes(role))
  } else {
    return true
  }
}

/**
 * Filter asynchronous routing tables by recursion
 * @param routes asyncRoutes
 * @param roles
 */
export function filterAsyncRoutes(routes, roles) {
  const res = []

  routes.forEach(route => {
    const tmp = { ...route }
    if (hasPermission(roles, tmp)) {
      if (tmp.children) {
        tmp.children = filterAsyncRoutes(tmp.children, roles)
      }
      res.push(tmp)
    }
  })

  return res
}

const state = {
  routes: [],
  addRoutes: []
}

const mutations = {
  SET_ROUTES: (state, routes) => {
    state.addRoutes = routes
    state.routes = constantRoutes.concat(routes)
  }
}

const actions = {
  generateRoutes({ commit }, roles) {
    //return new Promise(resolve => {
    //  let accessedRoutes
    //  if (roles.includes('admin')) {
    //    accessedRoutes = asyncRoutes || []
    //  } else {
    //    accessedRoutes = filterAsyncRoutes(asyncRoutes, roles)
    //  }
    //  commit('SET_ROUTES', accessedRoutes)
    //  resolve(accessedRoutes)
    //})
    return new Promise(resolve => {
      let accessedRoutes
      if (roles.includes('admin')) {
        accessedRoutes = asyncRoutes || []
      } else {
        accessedRoutes = filterAsyncRoutes(asyncRoutes, roles)
      }
      //let isSuper = store.state.user.isSuper
      let isSuper = false;
      console.info('当前登录用户是否为超级管理员', isSuper)
      let userRoutes = []
      if (isSuper === false) {
        // 不是超级管理员
        userRoutes = filterAsyncRouter(store.state.user.menu)

        const menuEnd = {
          path: '*',
          redirect: '/404',
          hidden: true
        }
        userRoutes.push(menuEnd)
        commit('SET_ROUTES', userRoutes)
        resolve(userRoutes)
      } else {
        // 超级管理员，全部权限
        userRoutes = accessedRoutes
        commit('SET_ROUTES', userRoutes)
        resolve(userRoutes)
      }

    })

  }
}

/**
 * 将 用户菜单JSON信息 转换为 router 可识别的路由json信息
 * @param t 管理员菜单JSON数组
 * @returns {*}
 */
function filterAsyncRouter(t) {
  t.filter(index => {
    if (index.component === 'Layout') {
      index.component = Layout
    } else {
      // 不是路由菜单，转换对应 vue组件
      // @/views/sn-manage/dict/index
      let component = index.component
      //
      /**
       * 截取 示例 @/views/sn-manage/dict/index 截取出 @/viesw/ 以外的字符，因为 拼接会异常
       * 新逻辑： 创建页面的时候去掉 @/viesw/
       * @type {*|string}
       * let test = component.substr(find(component, '/', 1) + 1)
       */
      index.component = require(`@/views/${component}.vue`).default

    }
    // 递归子菜单
    if (index.children && index.children.length) {
      index.children = filterAsyncRouter(index.children)
    }
    return true
  })
  return t
}


export default {
  namespaced: true,
  state,
  mutations,
  actions
}

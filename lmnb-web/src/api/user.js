import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/login/loginByIOTWeb.do',
    method: 'post',
    params: data
  })
}

export function getInfo(token) {
  return request({
    url: '/admin/operator/findCurrentOperatorBySessionId.do',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/vue-element-admin/user/logout',
    method: 'post'
  })
}

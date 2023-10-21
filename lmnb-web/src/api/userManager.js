import request from '@/utils/request'

export function queryUserList(data) {
  return request({
    url: '/admin/user/ajaxQueryUserByCompany.do',
    method: 'get',
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

export function addUser(data) {
  return request({
    url: '/admin/user/ajaxAddOrUpdateUser.do',
    method: 'post',
    params: data
  })
}

export function updateUser(data) {
  return request({
    url: '/admin/user/ajaxAddOrUpdateUser.do',
    method: 'post',
    params: data
  })
}
export function deleteUser(data) {
  return request({
    url: '/admin/user/ajaxDeleteUser.do',
    method: 'post',
    params: data
  })
}

export function logout() {
  return request({
    url: '/vue-element-admin/user/logout',
    method: 'post'
  })
}

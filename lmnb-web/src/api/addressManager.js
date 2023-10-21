import request from '@/utils/request'

export function queryAddressList(data) {
  return request({
    url: '/admin/area/ajaxQueryAddressByParent.do',
    method: 'get',
    params: data
  })
}

export function addAddress(data) {
  return request({
    url: '/admin/area/ajaxAddAddressInfo.do',
    method: 'get',
    params: data
  })
}
export function updateAddress(data) {
  return request({
    url: '/admin/area/ajaxUpdateAddressInfo.do',
    method: 'get',
    params: data
  })
}
export function deleteAddress(data) {
  return request({
    url: '/admin/area/ajaxDeleteAddressInfo.do',
    method: 'get',
    params: data
  })
}

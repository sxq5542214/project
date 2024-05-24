import request from '@/utils/request'

export function queryBillList(data) {
  return request({
    url: '/admin/bill/ajaxQueryBillList.do',
    method: 'get',
    params: data
  })
}
export function ajaxQueryBillWaterList(data) {
  return request({
    url: '/admin/bill/ajaxQueryBillWaterList.do',
    method: 'get',
    params: data
  })
}




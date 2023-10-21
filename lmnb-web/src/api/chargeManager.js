import request from '@/utils/request'

export function queryChargeList(data) {
  return request({
    url: '/admin/charge/ajaxQueryChargeList.do',
    method: 'get',
    params: data
  })
}

export function chargeBalance(data) {
  return request({
    url: '/admin/charge/ajaxChargeBalance.do',
    method: 'get',
    params: data
  })
}


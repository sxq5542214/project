import request from '@/utils/request'

export function queryBillList(data) {
  return request({
    url: '/admin/bill/ajaxQueryBillList.do',
    method: 'get',
    params: data
  })
}


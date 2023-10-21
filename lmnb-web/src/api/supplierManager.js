import request from '@/utils/request'

export function querySupplierList(data) {
  return request({
    url: '/admin/supplier/ajaxQuerySupplierList.do',
    method: 'get',
    params: data
  })
}


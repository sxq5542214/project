import request from '@/utils/request'

export function queryProductList(data) {
  return request({
    url: '/admin/product/ajaxQueryProductList.do',
    method: 'get',
    params: data
  })
}


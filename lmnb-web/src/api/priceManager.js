import request from '@/utils/request'

export function queryPriceList(data) {
  return request({
    url: '/admin/price/ajaxQueryPriceByCompany.do',
    method: 'get',
    params: data
  })
}

export function queryPriceDetail(data) {
  return request({
    url: '/admin/price/ajaxQueryPriceDetail.do',
    method: 'get',
    params: data
  })
}

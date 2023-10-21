import request from '@/utils/request'

export function queryRecordList(data) {
  return request({
    url: '/admin/bill/ajaxQueryRecordList.do',
    method: 'get',
    params: data
  })
}

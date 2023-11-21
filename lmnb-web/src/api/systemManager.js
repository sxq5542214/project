import request from '@/utils/request'

export function ajaxQuerySystemMenuToShow(data) {
  return request({
    url: '/admin/system/ajaxQuerySystemMenuToShow.do',
    method: 'get',
    params: data
  })
}

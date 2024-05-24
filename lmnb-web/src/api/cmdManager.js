import request from '@/utils/request'

export function queryCMDLogList(data) {
  return request({
    url: '/admin/other/cmd/ajaxQueryCMDLogList.do',
    method: 'get',
    params: data
  })
}


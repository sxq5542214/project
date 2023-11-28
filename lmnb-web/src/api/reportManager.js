import request from '@/utils/request'

export function querySimpleReportList(data) {
  return request({
    url: '/admin/report/ajaxQuerySimpleReportList.do',
    method: 'get',
    params: data
  })
}

export function querySimpleReportDataView(data) {
  return request({
    url: '/admin/report/ajaxSimpleReportDataView.do',
    method: 'get',
    params: data
  })
}
export function querySimpleReportTableColumns(data) {
  return request({
    url: '/admin/report/ajaxSimpleReportTableColumns.do',
    method: 'get',
    params: data
  })
}

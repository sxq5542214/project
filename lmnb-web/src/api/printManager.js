import request from '@/utils/request'

export function queryPrintTemplateList(data) {
  return request({
    url: '/admin/print/ajaxQueryPrintTemplateList.do',
    method: 'get',
    params: data
  })
}

export function updatePrintTemplateStyle(data) {
  //return request.post('/admin/print/ajaxUpdatePrintTemplateStyle.do', data);

  return request({
    url: '/admin/print/ajaxUpdatePrintTemplateStyle.do',
    method: 'post',
    params: data
  })
}

import request from '@/utils/request'

export function ajaxSendSMSByAddress(data) {
  return request({
    url: '/admin/sms/ajaxSendSMSByAddress.do',
    method: 'post',
    params: data
  })
}

import request from '@/utils/request'

export function queryMonthBuyAmount(data) {
  return request({
    url: '/admin/chargeDetail/ajaxQueryMonthChargeAmoutSumByDashboard.do',
    method: 'get',
    params: data
  })
}

export function queryDayBuyAmountMeterCount(data) {
  return request({
    url: '/admin/chargeDetail/ajaxQueryDayChargeAmoutMeterCountByDashboard.do',
    method: 'get',
    params: data
  })
}
export function queryDayMeterReadingCount(data) {
  return request({
    url: '/admin/device/ajaxQueryDayMeterReadingCount.do',
    method: 'get',
    params: data
  })
}
export function queryOpenedMeterCount(data) {
  return request({
    url: '/admin/device/ajaxQueryOpenedMeterCount.do',
    method: 'get',
    params: data
  })
}

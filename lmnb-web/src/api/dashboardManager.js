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

export function queryDayBuyAmount(data) {
  return request({
    url: '/admin/chargeDetail/ajaxQueryDayChargeAmoutSumByDashboard.do',
    method: 'get',
    params: data
  })
}

export function queryMonthBuyAmountMeterCount(data) {
  return request({
    url: '/admin/chargeDetail/ajaxQueryMonthChargeAmoutMeterCountByDashboard.do',
    method: 'get',
    params: data
  })
}

export function queryDayBuyAmountSumListOfMonth(data) {
  return request({
    url: '/admin/chargeDetail/ajaxQueryDayBuyAmountSumListOfMonthByDashboard.do',
    method: 'get',
    params: data
  })
}


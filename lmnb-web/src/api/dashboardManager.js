import request from '@/utils/request'

export function queryMonthBuyAmount(data) {
  return request({
    url: '/admin/chargeDetail/ajaxQueryMonthChargeAmoutSumByDashboard.do',
    method: 'get',
    params: data
  })
}
export function queryDayBuyAmountAdminListData(data) {
  return request({
    url: '/admin/chargeDetail/ajaxQueryDayBuyAmountListDataByAdminDashboard.do',
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
export function queryDayBuyCountListData(data) {
  return request({
    url: '/admin/chargeDetail/ajaxQueryDayBuyCountListDataByDashboard.do',
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
export function queryDayMeterReadingCountListData(data) {
  return request({
    url: '/admin/device/ajaxQueryDayMeterReadingCountListDataByDashboard.do',
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
export function queryOpenedMeterCountListData(data) {
  return request({
    url: '/admin/device/ajaxQueryOpenedMeterCountListDataByDashboard.do',
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
    url: '/admin/chargeDetail/ajaxQueryDayBuyAmountSumListOfMonthDashboard.do',
    method: 'get',
    params: data
  })
}


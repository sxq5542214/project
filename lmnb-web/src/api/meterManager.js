import request from '@/utils/request'

export function addMeter(data) {
  return request({
    url: '/admin/device/ajaxAddOrUpdateMeter.do',
    method: 'get',
    params: data
  })
}
export function updateMeter(data) {
  return request({
    url: '/admin/device/ajaxAddOrUpdateMeter.do',
    method: 'get',
    params: data
  })
}
export function queryMeterList(data) {
  return request({
    url: '/admin/device/ajaxQueryMeterList.do',
    method: 'get',
    params: data
  })
}
export function findMeterByCode(data) {
  return request({
    url: '/admin/device/ajaxFindMeterByCode.do',
    method: 'get',
    params: data
  })
}
export function queryMeterAndUserList(data) {
  return request({
    url: '/admin/device/ajaxQueryMeterAndUserList.do',
    method: 'get',
    params: data
  })
}
export function openValveByCode(data) {
  return request({
    url: '/admin/device/ajaxOpenValveByCode.do',
    method: 'get',
    params: data
  })
}
export function closeValveByCode(data) {
  return request({
    url: '/admin/device/ajaxCloseValveByCode.do',
    method: 'get',
    params: data
  })
}

export function changeMeter(data) {
  return request({
    url: '/admin/device/changemeter/ajaxChangeMeter.do',
    method: 'get',
    params: data
  })
}
export function ajaxCheckDeviceStation(data) {
  return request({
    url: '/admin/device/ajaxCheckDeviceStation.do',
    method: 'get',
    params: data
  })
}


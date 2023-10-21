import request from '@/utils/request'
const stordeName = 'dictionaryMap'

export function initAllDictionary() {
  return request({
    url: '/dictionary/ajaxQueryAllDictionaryByCache.do',
    method: 'get'
  })
}

export function getDescByBeanAttrValue(beanname, attr, value) {
  var dictionaryCache = window.sessionStorage.getItem(stordeName)
  if (dictionaryCache) {
    dictionaryCache = JSON.parse(dictionaryCache)
    return dictionaryCache[beanname][attr][value]
  } else {
    console.log('dictionaryCache not found:' + beanname + '.' + attr + '.' + value)
    // initAllDictionary().then(res => {
    //   window.sessionStorage.setItem('dictionaryMap', JSON.stringify(res.data))
    // })
    // setTimeout(() => {
    //   return getDescByBeanAttrValue(beanname, attr, value)
    // }, 1 * 1000)
    return value
  }
}
export function getDescByBeanAttrList(beanname, attr, value) {
  var dictionaryCache = window.sessionStorage.getItem(stordeName)
  if (dictionaryCache) {
    dictionaryCache = JSON.parse(dictionaryCache)
    // alert(dictionaryCache[beanname][attr])
    console.log(dictionaryCache[beanname][attr])
    return dictionaryCache[beanname][attr]
  } else {
    console.log('dictionaryCacheList not foundList:' + beanname + '.' + attr)
    // initAllDictionary().then(res => {
    //   window.sessionStorage.setItem('dictionaryMap', JSON.stringify(res.data))
    // })
    // setTimeout(() => {
    //   return getDescByBeanAttrValue(beanname, attr, value)
    // }, 1 * 1000)
    return value
  }
}
export function queryDictionaryList(tableName, attribute) {
  return request({
    url: '/admin/dictionary/ajaxQueryDictionaryList.do',
    method: 'get',
    params: { tablename: tableName, attribute: attribute }
  })
}

export function queryDictionaryValue(tableName, attribute, value) {
  var result = this.queryDictionaryList(tableName, attribute)
  var dataList = result.data
  dataList.find
  return request({
    url: '/admin/dictionary/ajaxQueryDictionaryList.do',
    method: 'get',
    params: { tablename: tableName, attribute: attribute }
  })
}

export function queryMeterCaliberList(data) {
  return queryDictionaryList('meter', 'caliber')
}

export function queryMeterOpenedList(data) {
  return queryDictionaryList('meter', 'opened')
}

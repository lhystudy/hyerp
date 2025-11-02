import request from '@/utils/request'

export function getInventoryList(params) {
  return request({
    url: '/inventory',
    method: 'get',
    params
  })
}

export function getInventory(id) {
  return request({
    url: `/inventory/${id}`,
    method: 'get'
  })
}

export function createInventory(data) {
  return request({
    url: '/inventory',
    method: 'post',
    data
  })
}

export function updateInventory(id, data) {
  return request({
    url: `/inventory/${id}`,
    method: 'put',
    data
  })
}

export function deleteInventory(id) {
  return request({
    url: `/inventory/${id}`,
    method: 'delete'
  })
}

export function getInventoryByGoodsId(goodsId) {
  return request({
    url: `/inventory/goods/${goodsId}`,
    method: 'get'
  })
}


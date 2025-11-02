import request from '@/utils/request'

export function getOrderItemList() {
  return request({
    url: '/order-item',
    method: 'get'
  })
}

export function getOrderItem(id) {
  return request({
    url: `/order-item/${id}`,
    method: 'get'
  })
}

export function getOrderItemsByOrderId(orderId) {
  return request({
    url: `/order-item/order/${orderId}`,
    method: 'get'
  })
}

export function createOrderItem(data) {
  return request({
    url: '/order-item',
    method: 'post',
    data
  })
}

export function createOrderItems(data) {
  return request({
    url: '/order-item/batch',
    method: 'post',
    data
  })
}

export function updateOrderItem(id, data) {
  return request({
    url: `/order-item/${id}`,
    method: 'put',
    data
  })
}

export function deleteOrderItem(id) {
  return request({
    url: `/order-item/${id}`,
    method: 'delete'
  })
}

export function deleteOrderItemsByOrderId(orderId) {
  return request({
    url: `/order-item/order/${orderId}`,
    method: 'delete'
  })
}


import request from '@/utils/request'

export function getShipmentList(params) {
  return request({
    url: '/shipment',
    method: 'get',
    params
  })
}

export function getShipment(id) {
  return request({
    url: `/shipment/${id}`,
    method: 'get'
  })
}

export function createShipment(data) {
  return request({
    url: '/shipment',
    method: 'post',
    data
  })
}

export function updateShipment(id, data) {
  return request({
    url: `/shipment/${id}`,
    method: 'put',
    data
  })
}

export function deleteShipment(id) {
  return request({
    url: `/shipment/${id}`,
    method: 'delete'
  })
}

export function getShipmentByOrderId(orderId) {
  return request({
    url: `/shipment/order/${orderId}`,
    method: 'get'
  })
}


import request from '@/utils/request'

export function getCategoryList() {
  return request({
    url: '/category',
    method: 'get'
  })
}

export function getEnabledCategoryList() {
  return request({
    url: '/category/enabled',
    method: 'get'
  })
}

export function getCategory(id) {
  return request({
    url: `/category/${id}`,
    method: 'get'
  })
}

export function createCategory(data) {
  return request({
    url: '/category',
    method: 'post',
    data
  })
}

export function updateCategory(id, data) {
  return request({
    url: `/category/${id}`,
    method: 'put',
    data
  })
}

export function deleteCategory(id) {
  return request({
    url: `/category/${id}`,
    method: 'delete'
  })
}


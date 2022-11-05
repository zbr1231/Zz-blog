import request from '@/utils/request'

// 查询分类列表
export function listTag(query) {
  return request({
    url: '/tag/list',
    method: 'get',
    params: query
  })
}

// 查询分类列表
export function listAllTag() {
  return request({
    url: '/tag/listAllTag',
    method: 'get'
  })
}


﻿import request from '@/utils/request'

// 查询文章列表
export function articleList(query) {
    return request({
        url: '/article/articleList',
        method: 'get',
        headers: {
          isToken: false
        },
        params: query
    })
}

// 发表
export function addArticle(data) {
  return request({
    url: '/article',
    method: 'post',
    data: data
  })
}

//查询最热文章
export function hotArticleList() {
    return request({
        url: '/article/hotArticleList',
        headers: {
          isToken: false
        },
        method: 'get'
    })
}

//获取文章详情
export function getArticle(articleId) {
    return request({
        url: '/article/' + articleId,
        headers: {
          isToken: false
        },
        method: 'get'
    })
}

// 修改分类
export function updateArticle(data) {
  return request({
    url: '/article',
    method: 'put',
    data: data
  })
}

export function updateViewCount(articleId) {
    return request({
        url: '/article/updateViewCount/' + articleId,
        headers: {
          isToken: false
        },
        method: 'put'
    })
    
}

import request from '@/utils/request'

// 查询留言
export function messageList() {
    return request({
        url: '/message',
        method: 'get',
        headers: {
          isToken: false
        }
    })
}

// 发表留言
export function addMessage(type,articleId,rootId,toCommentId,toCommentUserId,content) {
    return request({
        url: '/message',
        method: 'post',
        data: {"articleId":articleId,"type":type,"rootId":rootId,"toCommentId":toCommentId,"toCommentUserId":toCommentUserId,"content":content}
    })
}

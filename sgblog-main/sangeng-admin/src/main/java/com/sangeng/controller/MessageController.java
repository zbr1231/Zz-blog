package com.sangeng.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.sangeng.common.ruoyi.AjaxResult;
import com.sangeng.common.ruoyi.BaseController;
import com.sangeng.common.ruoyi.TableDataInfo;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.Message;
import com.sangeng.service.MessageService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 留言管理Controller
 * 
 * @author zz
 * @date 2023-02-14
 */
@RestController
@RequestMapping("/content/message")
public class MessageController extends BaseController
{
    @Autowired
    private MessageService messageService;

    /**
     * 查询留言管理列表
     */
    @GetMapping("/list")
    public ResponseResult list(Message message)
    {
        startPage();
        List<Message> list = messageService.selectMessageList(message);
        return ResponseResult.okResult(getDataTable(list));
    }


    /**
     * 获取留言管理详细信息
     */
    @GetMapping(value = "/{id}")
    public ResponseResult getInfo(@PathVariable("id") Long id)
    {
        return ResponseResult.okResult(AjaxResult.success(messageService.selectMessageById(id)));
    }

    /**
     * 新增留言管理
     */
    @PostMapping
    public ResponseResult add(@RequestBody Message message)
    {
        return ResponseResult.okResult(toAjax(messageService.insertMessage(message)));
    }

    /**
     * 修改留言管理
     */
    @PutMapping
    public ResponseResult edit(@RequestBody Message message)
    {
        return ResponseResult.okResult(toAjax(messageService.updateMessage(message)));
    }

    /**
     * 删除留言管理
     */
	@DeleteMapping("/{ids}")
    public ResponseResult remove(@PathVariable Long[] ids)
    {
        return ResponseResult.okResult(toAjax(messageService.deleteMessageByIds(ids)));
    }
}

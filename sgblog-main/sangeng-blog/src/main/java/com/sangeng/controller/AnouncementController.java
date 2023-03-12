package com.sangeng.controller;

import com.sangeng.common.ruoyi.AjaxResult;
import com.sangeng.common.ruoyi.BaseController;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.Anouncement;
import com.sangeng.domain.vo.AnouncementVo;
import com.sangeng.service.IAnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * anouncementController
 * 
 * @author zz
 * @date 2023-02-14
 */
@RestController
@RequestMapping("/anouncement/anouncement")
public class AnouncementController extends BaseController
{
    @Autowired
    private IAnouncementService anouncementService;

    /**
     * 查询anouncement列表
     */
    @GetMapping("/list")
    public ResponseResult list(@Valid Anouncement anouncement)
    {
        startPage();
        List<AnouncementVo> list = anouncementService.selectAnouncementList(anouncement);
        return ResponseResult.okResult(getDataTable(list));
    }



    /**
     * 获取anouncement详细信息
     */
    @GetMapping(value = "/{id}")
    public ResponseResult getInfo(@PathVariable("id") Long id)
    {
        return ResponseResult.okResult(AjaxResult.success(anouncementService.selectAnouncementById(id)));
    }

    /**
     * 新增anouncement
     */
    @PostMapping
    public ResponseResult add(@RequestBody Anouncement anouncement)
    {
        return ResponseResult.okResult(toAjax(anouncementService.insertAnouncement(anouncement)));
    }

    /**
     * 修改anouncement
     */
    @PutMapping
    public ResponseResult edit(@RequestBody Anouncement anouncement)
    {
        return ResponseResult.okResult(toAjax(anouncementService.updateAnouncement(anouncement)));
    }

    /**
     * 删除anouncement
     */
	@DeleteMapping("/{ids}")
    public ResponseResult remove(@PathVariable Long[] ids)
    {
        return ResponseResult.okResult(toAjax(anouncementService.deleteAnouncementByIds(ids)));
    }

    /**
     * 修改用户状态
     */
    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody Map<String,String> map){
        String anouncementId = map.get("anouncementId");
        String status = map.get("status");
        boolean r = anouncementService.updateAnouncestatus(Long.parseLong(anouncementId), status);
        if(r){
            return ResponseResult.okResult();
        }
        return ResponseResult.errorResult(400,"更新失败");
    }
}

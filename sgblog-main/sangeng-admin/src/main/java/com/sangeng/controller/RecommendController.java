package com.sangeng.controller;

import com.sangeng.common.ruoyi.AjaxResult;
import com.sangeng.common.ruoyi.BaseController;
import com.sangeng.common.ruoyi.TableDataInfo;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.Recommend;
import com.sangeng.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 推荐管理Controller
 * 
 * @author zz
 * @date 2023-02-14
 */
@RestController
@RequestMapping("/recommend/recommend")
public class RecommendController extends BaseController
{
    @Autowired
    private RecommendService recommendService;

    /**
     * 查询推荐管理列表
     */
//    @PreAuthorize("@ss.hasPermi('recommend:recommend:list')")
    @GetMapping("/list")
    public ResponseResult list(Recommend recommend)
    {
        startPage();
        List<Recommend> list = recommendService.selectRecommendList(recommend);
        return ResponseResult.okResult(getDataTable(list));
    }



    /**
     * 获取推荐管理详细信息
     */
    @GetMapping(value = "/{id}")
    public ResponseResult getInfo(@PathVariable("id") Long id)
    {
        return ResponseResult.okResult(AjaxResult.success(recommendService.selectRecommendById(id)));
    }

    /**
     * 新增推荐管理
     */
    @PostMapping
    public ResponseResult add(@RequestBody Recommend recommend)
    {
        return ResponseResult.okResult(toAjax(recommendService.insertRecommend(recommend)));
    }

    /**
     * 修改推荐管理
     */
    @PutMapping
    public ResponseResult edit(@RequestBody Recommend recommend)
    {
        return ResponseResult.okResult(toAjax(recommendService.updateRecommend(recommend)));
    }

    /**
     * 删除推荐管理
     */
	@DeleteMapping("/{ids}")
    public ResponseResult remove(@PathVariable Long[] ids)
    {
        return ResponseResult.okResult(toAjax(recommendService.deleteRecommendByIds(ids)));
    }
}

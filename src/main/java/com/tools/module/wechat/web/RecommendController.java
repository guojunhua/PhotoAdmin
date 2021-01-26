package com.tools.module.wechat.web;

import com.tools.common.model.Result;
import com.tools.module.wechat.entity.Recommend;
import com.tools.module.wechat.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 今日推荐
 * @author 小柒2012
 */
@RestController
@RequestMapping("sys/recommend")
public class RecommendController {

    @Autowired
    private RecommendService recommendService;

    /**
     * 保存
     */
    @PostMapping("/save")
    public Result save(@RequestBody Recommend recommend){
        if(recommend.getId()!=null){
            return recommendService.update(recommend);
        }else{
            return recommendService.save(recommend);
        }
    }

    /**
     * 历史干货
     */
    @PostMapping("/list")
    public Result list(Recommend recommend){
        return recommendService.list(recommend);
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public Result delete(Long id){
        return recommendService.delete(id);
    }

    /**
     * 删除
     */
    @PostMapping("/hot")
    public Result hot(Long id){
        return recommendService.hot(id);
    }

    /**
     * 删除
     */
    @PostMapping("/removeAll")
    public Result removeAll(Short status){
        return recommendService.removeAll(status);
    }

}

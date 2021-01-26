package com.tools.module.${prefix}.web;

import com.tools.common.config.AbstractController;
import com.tools.common.model.Result;
import com.tools.module.${prefix}.entity.${entityName};
import com.tools.module.${prefix}.service.${entityName}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/${prefix}/${function}")
public class ${entityName}Controller extends AbstractController {

    @Autowired
    private ${entityName}Service ${function}Service;

    /**
     * 列表
     */
    @PostMapping("/list")
    public Result list(${entityName} ${function}){
        return ${function}Service.list(${function});
    }
    /**
     * 查询
     */
    @PostMapping("/get")
    public Result get(Long id){
        return ${function}Service.get(id);
    }
    /**
     * 保存
     */
    @PostMapping("/save")
    public Result save(@RequestBody ${entityName} ${function}){
        return ${function}Service.save(${function});
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public Result delete(Long id){
        return ${function}Service.delete(id);
    }
}

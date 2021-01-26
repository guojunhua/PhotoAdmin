package com.tools.module.app.web;

import com.tools.common.model.Result;
import com.tools.module.app.service.AppQueryService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 在线查询
 * @author 小柒2012
 */
@Api(tags ="在线查询")
@RestController
@RequestMapping("app/query")
public class QueryController {

    @Autowired
    private AppQueryService queryService;

    /**
     * 查询
     * @param sql
     * @return
     */
    @RequestMapping("crud")
    public Result crud(String sql) {
        return queryService.crud(sql);
    }
}

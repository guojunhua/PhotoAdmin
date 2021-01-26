package com.tools.module.wechat.service;


import com.tools.common.model.Result;
import com.tools.module.wechat.entity.Girl;

import java.io.File;

public interface GirlService {

    Result list(Integer pageSize, Integer pageNo);

    void upload(Girl image, File toPic, File fromPic);

    Result list(Girl girl);

    Result delete(Long id);

    Result removeAll(Short status);

    Result resume(Short status,Long id);
}


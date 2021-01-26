package com.tools.module.app.web;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONObject;
import com.tools.common.config.AbstractController;
import com.tools.common.constant.SystemConstant;
import com.tools.common.model.Result;
import com.tools.module.app.entity.AppArticle;
import com.tools.module.app.service.AppArticleService;
import com.tools.module.app.util.MinIoUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * 文章管理
 * 爪哇笔记：https://blog.52itstyle.vip
 * @author 小柒2012
 */
@Api(tags = "文章管理")
@RestController
@RequestMapping("/app/article")
public class ArticleController extends AbstractController {

    @Autowired
    private AppArticleService articleService;
    @Autowired
    private MinIoUtils minIoUtil;

    @Value("${file.path}")
    private String filePath;

    /**
     * 列表
     */
    @PostMapping("/list")
    public Result list(AppArticle article) {
        return articleService.list(article);
    }

    /**
     * 查询
     */
    @PostMapping("/get")
    public Result get(Long id) {
        return articleService.get(id);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public Result save(@RequestBody AppArticle article) {
        return articleService.save(article);
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public Result delete(Long id) {
        return articleService.delete(id);
    }


    /**
     * 文件上传
     */
    @RequestMapping("/upload")
    public JSONObject upload(@RequestParam(value = "editormd-image-file") MultipartFile file) {
        JSONObject res = new JSONObject();
        try {
            if (file != null) {
                File parentFile = createParentFile();
                String fileName = file.getOriginalFilename();
                String suffix = fileName.substring(fileName.lastIndexOf("."));
                String uuid = IdUtil.simpleUUID();
                fileName = uuid + suffix;
                File fromPic = new File(parentFile, fileName);
                FileUtil.writeFromStream(file.getInputStream(), fromPic);
                /**
                 * 入 minIo
                 */
                String fileDay = DateUtil.thisYear() + "/" + (DateUtil.thisMonth() + 1) + "/" + DateUtil.thisDayOfMonth();
                String minFile = parentFile.getPath() + SystemConstant.SF_FILE_SEPARATOR + fileName;
                minIoUtil.putObject(minIoUtil.getBucketName(), fileDay + "/" + fileName, minFile);
                res.put("url", minIoUtil.getEndpoint() + "/" + minIoUtil.getBucketName() + "/" + fileDay + "/" + fileName);
                res.put("success", 1);
                res.put("message", "upload success!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 创建多级文件夹
     *
     * @return
     */
    public File createParentFile() {
        File parentFile = new File(filePath + SystemConstant.SF_FILE_SEPARATOR + DateUtil.thisYear());
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        parentFile = new File(parentFile, (DateUtil.thisMonth() + 1) + "");
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        parentFile = new File(parentFile, DateUtil.thisDayOfMonth() + "");
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        return parentFile;
    }
}

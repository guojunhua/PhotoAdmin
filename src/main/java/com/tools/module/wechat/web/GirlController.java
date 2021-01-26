package com.tools.module.wechat.web;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONObject;
import com.tools.common.config.AbstractController;
import com.tools.common.constant.SystemConstant;
import com.tools.common.model.Result;
import com.tools.common.util.DateUtils;
import com.tools.common.util.FileUtils;
import com.tools.module.wechat.entity.Girl;
import com.tools.module.wechat.service.GirlService;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * 今日推荐
 * @author 小柒2012
 */
@RestController
@RequestMapping("sys/girl")
public class GirlController extends AbstractController {

    @Autowired
    private GirlService meiZiService;
    @Value("${file.path}")
    private String filePath;
    @Value("${aliyun.oss.url}")
    private String ossUrl;

    /**
     * 文件上传
     */
    @RequestMapping("/upload")
    public JSONObject upload(@RequestParam(value = "editormd-image-file")MultipartFile file,
                             HttpServletRequest request) {
        JSONObject res = new JSONObject();
        try {
            if(file!=null){
                String status = request.getParameter("status");
                File parentFile = FileUtils.createByDay(filePath);
                String fileName = file.getOriginalFilename();
                String suffix = fileName.substring(fileName.lastIndexOf("."));
                String uuid = IdUtil.simpleUUID();
                fileName = uuid + suffix;
                File fromPic = new File(parentFile,fileName);
                FileUtil.writeFromStream(file.getInputStream(), fromPic);
                /**
                 * 缩放
                 */
                fileName = "zoom_"+uuid + suffix;
                File toPic = new File(parentFile,fileName);
                /**
                 * scale 比例
                 * outputQuality 质量
                 */
                Thumbnails.of(fromPic).scale(0.5f).outputQuality(1f).toFile(toPic);
                String fileDay = DateUtil.thisYear()+"/"+(DateUtil.thisMonth()+1)+"/"+DateUtil.thisDayOfMonth();
                String imagePath = SystemConstant.FILE + "/" + fileDay+"/"+fileName;
                /**
                 * 入库
                 */
                Girl image = new Girl();
                image.setUuid(uuid);
                image.setUrl(imagePath);
                image.setOssUrl(fileDay+"/"+fileName);
                image.setGmtCreate(DateUtils.getTimestamp());
                image.setStatus(Short.parseShort(status));
                meiZiService.upload(image,toPic,fromPic);
                /**
                 * 默认返回的是阿里云OSS地址
                 * 如果不需要改成本地的就可以
                 */
                res.put("url",ossUrl+"/"+fileDay+"/"+fileName);
                res.put("success", 1);
                res.put("message", "upload success!");
            }
        } catch (Exception e) {
            logger.error("文件上传失败，{}",e.getMessage());
        }
        return res;
    }

    /**
     * 列表
     */
    @PostMapping("/list")
    public Result list(Girl girl){
        return meiZiService.list(girl);
    }

    /**
     * 删除
     * @return
     */
    @RequestMapping("delete")
    public Result delete(Long id) {
        return meiZiService.delete(id);
    }

    /**
     * 删除全部
     */
    @PostMapping("/removeAll")
    public Result removeAll(Short status){
        return meiZiService.removeAll(status);
    }

    /**
     * 启用恢复
     */
    @PostMapping("/resume")
    public Result resume(Short status,Long id){
        return meiZiService.resume(status,id);
    }
}

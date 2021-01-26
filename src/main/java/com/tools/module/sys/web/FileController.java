package com.tools.module.sys.web;

import cn.hutool.core.io.FileUtil;
import com.tools.common.constant.SystemConstant;
import com.tools.common.model.Result;
import com.tools.module.sys.model.SysFile;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件管理-在线修改代码
 * 爪哇笔记：https://blog.52itstyle.vip
 * @author 小柒2012
 */
@Api(tags ="文件管理")
@RestController
@RequestMapping(value = "/sys/file")
public class FileController {

    @Value("${spring.profiles.active}")
    private String active;

    /**
     * 列表
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public Result list() throws FileNotFoundException {
        String filePath = ResourceUtils.getURL("classpath:").getPath();
        List<SysFile> fileList = new ArrayList<>();
        getAllFilePaths(filePath,fileList,0,"");
        return Result.ok(fileList);
    }

    /**
     * 递归获取某目录下的所有子目录以及子文件
     * @param filePath
     * @param filePathList
     * @return
     */
    private static List<SysFile> getAllFilePaths(String filePath, List<SysFile> filePathList,
                                                 Integer level,String parentPath) {
        File[] files = new File(filePath).listFiles();
        if (files == null) {
            return filePathList;
        }
        for (File file : files) {
            int num = filePathList.size()+1;
            SysFile sysFile = new SysFile();
            sysFile.setName(file.getName());
            sysFile.setFileId(num);
            sysFile.setParentId(level);
            if (file.isDirectory()) {
                sysFile.setDirectory(true);
                if(level==0){
                    if(file.getName().equals("templates")||
                            file.getName().equals("static")){
                        filePathList.add(sysFile);
                        parentPath = SystemConstant.SF_FILE_SEPARATOR+file.getName();
                        getAllFilePaths(file.getAbsolutePath(), filePathList,num,parentPath);
                        num++;
                    }
                }else{
                    filePathList.add(sysFile);
                    String subParentPath = parentPath+SystemConstant.SF_FILE_SEPARATOR+file.getName();
                    getAllFilePaths(file.getAbsolutePath(), filePathList,num,subParentPath);
                    num++;
                }
            } else {
                if(level!=0){
                    sysFile.setDirectory(false);
                    sysFile.setParentPath(parentPath+SystemConstant.SF_FILE_SEPARATOR+file.getName());
                    filePathList.add(sysFile);
                    num++;
                }
            }
        }
        return filePathList;
    }

    /**
     * 获取内容
     * @return
     */
    @RequestMapping(value = "getContent", method = RequestMethod.POST)
    public Result getContent(String filePath) throws FileNotFoundException {
        String path = ResourceUtils.getURL("classpath:").getPath();
        String content = FileUtil.readUtf8String(path+filePath);
        return Result.ok(content);
    }
    /**
     * 保存内容
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Result save(String filePath, String content) throws FileNotFoundException {
        String path = ResourceUtils.getURL("classpath:").getPath();
        /**
         * 生产环境自行解除
         */
        if(active.equals("prod")){
            return Result.error("演示环境禁止插插插！！！");
        }else{
            File file = new File(path+filePath);
            long lastModified = file.lastModified();
            FileUtil.writeUtf8String(content,path+filePath);
            file.setLastModified(lastModified);
            return Result.ok();
        }
    }

}

package com.tools.module.document.web;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.tools.common.config.AbstractController;
import com.tools.common.constant.SystemConstant;
import com.tools.common.model.Result;
import com.tools.module.sys.model.SysFile;
import io.swagger.annotations.Api;
import org.jodconverter.core.DocumentConverter;
import org.jodconverter.core.office.OfficeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "文档管理")
@RestController
@RequestMapping("document")
public class ConverterController extends AbstractController {

    private final static Logger LOGGER = LoggerFactory.getLogger(ConverterController.class);

    @Value("${file.path}")
    private String filePath;

    @Autowired(required = false)
    private DocumentConverter documentConverter;

    /**
     * 文件上传
     */
    @RequestMapping("/upload")
    public Result upload(MultipartFile file) {
        try {
            if (file != null) {
                File parentFile = createParentFile();
                String fileName = file.getOriginalFilename();
                String suffix = fileName.substring(fileName.lastIndexOf("."));
                String uuid = IdUtil.simpleUUID();
                fileName = uuid + suffix;
                File imageFile = new File(parentFile, fileName);
                FileUtil.writeFromStream(file.getInputStream(), imageFile);
                /**
                 * 年月日目录
                 */
                String fileDay = DateUtil.thisYear() + "/" + (DateUtil.thisMonth() + 1) + "/"
                        + DateUtil.thisDayOfMonth();
                String imagePath = SystemConstant.FILE + "/document/" + fileDay + "/" + fileName;
                /**
                 * 实时转换
                 */
                File toFile = new File(parentFile, uuid + ".pdf");
                documentConverter.convert(imageFile).to(toFile).execute();
                return Result.ok(imagePath);
            } else {
                return Result.error();
            }
        } catch (Exception e) {
            LOGGER.error("转换异常{}",e);
            return Result.error();
        }
    }

    /**
     * 文件转换
     */
    @RequestMapping("converter")
    public Result converter() {
        try {
            documentConverter.convert(new File("F:\\前端.txt")).to(new File("E:\\home\\1.pdf")).execute();
        } catch (OfficeException e) {
            logger.error("转换失败{}", e);
            return Result.error();
        }
        return Result.ok();
    }

    /**
     * 列表
     *
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public Result list() {
        String document = filePath + SystemConstant.SF_FILE_SEPARATOR + "document";
        List<SysFile> fileList = new ArrayList<>();
        getAllFilePaths(document, fileList, 0, "");
        return Result.ok(fileList);
    }

    /**
     * 递归获取某目录下的所有子目录以及子文件
     *
     * @param filePath
     * @param filePathList
     * @return
     */
    private static List<SysFile> getAllFilePaths(String filePath, List<SysFile> filePathList, Integer level,
                                                 String parentPath) {
        File[] files = new File(filePath).listFiles();
        if (files == null) {
            return filePathList;
        }
        for (File file : files) {
            int num = filePathList.size() + 1;
            SysFile sysFile = new SysFile();
            sysFile.setName(file.getName());
            sysFile.setFileId(num);
            sysFile.setParentId(level);
            if (file.isDirectory()) {
                sysFile.setDirectory(true);
                if (level == 0) {
                    filePathList.add(sysFile);
                    parentPath = "/" + file.getName();
                    getAllFilePaths(file.getAbsolutePath(), filePathList, num, parentPath);
                    num++;
                } else {
                    filePathList.add(sysFile);
                    String subParentPath = parentPath + "/" + file.getName();
                    getAllFilePaths(file.getAbsolutePath(), filePathList, num, subParentPath);
                    num++;
                }
            } else {
                if (level != 0) {
                    sysFile.setDirectory(false);
                    sysFile.setParentPath(parentPath + "/" + file.getName());
                    filePathList.add(sysFile);
                    num++;
                }
            }
        }
        return filePathList;
    }

    /**
     * 创建多级文件夹
     *
     * @return
     */
    public File createParentFile() {
        File parentFile = new File(filePath + SystemConstant.SF_FILE_SEPARATOR + "document"
                + SystemConstant.SF_FILE_SEPARATOR + DateUtil.thisYear());
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

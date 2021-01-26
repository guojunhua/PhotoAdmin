package com.tools.module.document.util;

import org.jodconverter.core.office.OfficeException;
import org.jodconverter.core.office.OfficeUtils;
import org.jodconverter.local.JodConverter;
import org.jodconverter.local.office.LocalOfficeManager;

import java.io.File;


/**
 * OpenOffice 文件转换
 */
public class OpenOfficeService {

    static {
        LocalOfficeManager.builder()
                .portNumbers(2002)
                .build();
    }

    public static void main(String[] args) {
        LocalOfficeManager officeManager = LocalOfficeManager.install();
        try {
            officeManager.start();
            JodConverter
                    .convert(new File("F:\\前端.txt"))
                    .to(new File("E:\\home\\1.pdf"))
                    .execute();
        } catch (OfficeException e) {
            e.printStackTrace();
        } finally {
            OfficeUtils.stopQuietly(officeManager);
        }
    }
}
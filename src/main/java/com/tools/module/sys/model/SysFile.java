package com.tools.module.sys.model;

import lombok.Data;

@Data
public class SysFile {
    private Integer fileId;
    private String name;
    private Integer parentId;
    private String parentPath;
    private boolean directory;
}

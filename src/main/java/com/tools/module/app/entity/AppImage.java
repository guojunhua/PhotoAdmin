package com.tools.module.app.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tools.common.model.PageBean;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "app_image")
public class AppImage extends PageBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name="image_path")
    private String imagePath;

    @Column(name="original_name")
    private String OriginalName;

    @Column(name="image_size")
    private String imageSize;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name="gmt_create")
    private Timestamp gmtCreate;

    /**
     * 色情：1  正常 0 色情
     */
    @Column(name = "porn_status",length = 4)
    private Short pornStatus;

    @Column(name="file_md5")
    private String fileMd5;

    @Transient
    private String fileName;

}

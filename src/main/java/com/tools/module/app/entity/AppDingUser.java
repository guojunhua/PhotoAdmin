package com.tools.module.app.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tools.common.model.PageBean;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.GregorianCalendar;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 蘑菇钉用户信息
 */
@Data
@Entity
@Table(name = "app_ding_user")
public class AppDingUser  extends PageBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer userId;

    /**
     * 用户名
     */
    @Column(name = "username")
    private String username;

    /**
     * 密码
     */
    @Column(name = "password")
    private String password;

    /**
     * 国家
     */
    @Column(name = "country")
    private String country;

    /**
     * 地址
     */
    @Column(name = "address")
    private String address;

    /**
     * 省
     */
    @Column(name = "province")
    private String province;

    /**
     * 城市
     */
    @Column(name = "city")
    private String city;

    /**
     * 纬度
     */
    @Column(name = "latitude")
    private String latitude;

    /**
     * 发表的信息
     */
    @Column(name = "description")
    private String description;

    /**
     * 经度
     */
    @Column(name = "longitude")
    private String longitude;

    /**
     * 创建时间
     */
    @Column(name = "gmt_create")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp gmtCreate;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "gmt_modified")
    private Timestamp gmtModified;

    /**
     * START  上班  END 下班
     */
    @Transient
    private String type;

    public AppDingUser() {
        /**
         * 结果为“0”是上午 结果为“1”是下午
         */
        GregorianCalendar ca = new GregorianCalendar();
        if (ca.get(GregorianCalendar.AM_PM) == 0 || "0".equals(ca.get(GregorianCalendar.AM_PM))) {
            this.type = "START";
        } else {
            this.type = "END";
        }
    }

}

package com.tools.module.${prefix}.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tools.common.model.PageBean;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "${tableName}")
public class ${entityName} extends PageBean implements Serializable {
<#list list as entity>
   /**
    * ${entity.columnComment}
    */
<#if entity.columnExtra == "auto_increment">
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "${entity.columnName}", nullable = false, length = 20)
    private Long ${entity.entityColumnName};
<#else>
   <#if entity.dataType == "varchar">
    @Column(name = "${entity.columnName}",length = ${entity.columnLength})
    private String ${entity.entityColumnName};
   <#elseif entity.dataType == "text">
    @Lob
    @Column(name = "${entity.columnName}")
    private String ${entity.entityColumnName};
   <#elseif entity.dataType == "datetime">
    @Column(name = "${entity.columnName}")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp ${entity.entityColumnName};
   <#elseif entity.dataType == "timestamp">
    @Column(name = "${entity.columnName}")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp ${entity.entityColumnName};
   <#elseif entity.dataType == "int">
    @Column(name = "${entity.columnName}",length = 11)
    private Integer ${entity.entityColumnName};
   <#elseif entity.dataType == "tinyint">
    @Column(name = "${entity.columnName}",length = 4)
    private Short ${entity.entityColumnName};
   <#elseif entity.dataType == "bigint">
    @Column(name = "${entity.columnName}",length = 20)
    private Long ${entity.entityColumnName};
   <#else>
   </#if>
</#if>
</#list>
}
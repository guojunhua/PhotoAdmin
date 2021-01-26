package com.tools.module.app.service.impl;

import com.tools.common.dynamicquery.DynamicQuery;
import com.tools.common.model.PageBean;
import com.tools.common.model.Result;
import com.tools.module.app.model.AppGen;
import com.tools.module.app.service.AppGenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class AppGenServiceImpl implements AppGenService {

	@Autowired
	private DynamicQuery dynamicQuery;
    @Value("${table.schema}")
    private String schema;


    @Override
    @Transactional(readOnly = true)
	public Result list(AppGen gen){
	    String countSql = "SELECT COUNT(*) FROM information_schema.tables ";
        countSql +="WHERE table_schema=? AND table_name NOT LIKE 'qrtz_%'";
        Object[] params = new Object[]{schema};
	    Long totalCount = dynamicQuery.nativeQueryCount(countSql,params);
        PageBean<AppGen> data = new PageBean<>();
        if(totalCount>0){
            String nativeSql = "SELECT table_name as tableName,table_comment as tableComment ";
            nativeSql+="FROM information_schema.tables WHERE table_schema=? AND table_name NOT LIKE 'qrtz_%'";
            Pageable pageable = PageRequest.of(gen.getPageNo(),gen.getPageSize());
            List<AppGen> list = dynamicQuery.nativeQueryPagingListModel(AppGen.class,pageable, nativeSql,params);
            data = new PageBean<>(list, totalCount);
        }
        return Result.ok(data);
	}

    @Override
    @Transactional(readOnly = true)
    public List<AppGen> getByTable(AppGen gen) {
        String nativeSql = "SELECT COLUMN_NAME as columnName,COLUMN_COMMENT as columnComment,";
        nativeSql +="DATA_TYPE as dataType,Extra as columnExtra,CHARACTER_MAXIMUM_LENGTH as columnLength ";
        nativeSql +=" FROM information_schema.COLUMNS WHERE TABLE_NAME = ? and TABLE_SCHEMA=?";
        return dynamicQuery.nativeQueryListModel(AppGen.class,nativeSql,new Object[]{gen.getTableName(),schema});
    }
}

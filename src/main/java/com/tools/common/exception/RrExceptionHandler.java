package com.tools.common.exception;

import com.tools.common.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理器
 * 爪哇笔记 https://blog.52itstyle.vip
 * @author 小柒2012
 */
@RestControllerAdvice
public class RrExceptionHandler {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 自定义异常
	 */
	@ExceptionHandler(RrException.class)
	public Result handleRRException(RrException e){
		Result r = new Result();
		r.put("code", e.getCode());
		r.put("msg", e.getMessage());
		return r;
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public Result handleDuplicateKeyException(DuplicateKeyException e){
		logger.error(e.getMessage(), e);
		return Result.error("数据库中已存在该记录");
	}

    @ExceptionHandler(InvalidDataAccessResourceUsageException.class)
    public Result handleDuplicateKeyException(InvalidDataAccessResourceUsageException e){
        logger.error(e.getMessage(), e);
        return Result.error("演示环境禁止插插插");
    }

	@ExceptionHandler(Exception.class)
	public Result handleException(Exception e){
		logger.error(e.getMessage(), e);
		return Result.error();
	}

	/**
	 * 异常处理参数
	 */
	@ExceptionHandler(value = {BindException.class})
	public Result bindException(BindException e) {
		return Result.error(e.getAllErrors().toString());
	}
}

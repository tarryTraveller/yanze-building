
package com.yanze.building.service.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseService {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 打印普通日志
	 * 
	 * @param format
	 *            列子: 打印普通日志,id: {}, name: {}
	 * @param arguments
	 *            id,name
	 */
	protected void info(String format, Object... arguments) {
		logger.info(format, arguments);
	}

	/**
	 * 打印警告日志
	 * 
	 * @param format
	 *            列子: 打印警告日志,id: {}, name: {}
	 * @param arguments
	 *            id,name
	 */
	protected void warn(String format, Object... arguments) {
		logger.warn(format, arguments);
	}

	/**
	 * 打印错误日志
	 * 
	 * @param format
	 *            列子: 打印错误日志,id: {}, name: {}
	 * @param arguments
	 *            id,name
	 */
	protected void error(String format, Object... arguments) {
		logger.error(format, arguments);
	}
}

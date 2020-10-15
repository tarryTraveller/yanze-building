
package com.yanze.building.lang.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;

/**
 * json结果对象
 * 
 * @author sulei
 *
 */
@Setter
@Getter
public class JsonResult {

	private static final ObjectMapper MAPPER = new ObjectMapper();// 定义jackson对象
	private Integer status;// 状态码
	private String msg;// 消息描述
	private Object data;// 数据

	public static ObjectMapper getMAPPER() {
		return MAPPER;
	}

	public JsonResult(Integer status, String msg, Object data) {
		this.status = status;
		this.msg = msg;
		this.data = data;
	}

}

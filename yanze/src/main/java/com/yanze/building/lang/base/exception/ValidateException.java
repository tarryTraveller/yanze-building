
package com.yanze.building.lang.base.exception;

/**
 * 校验异常类
 * 
 * @author sulei
 *
 */
public class ValidateException extends BaseException {

	private static final long serialVersionUID = -5679286376502210180L;

	public ValidateException(Integer code) {
		super(code);
	}

	public ValidateException(Integer code, String message) {
		super(code, message);
	}
}
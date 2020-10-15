
package com.yanze.building.enums.common;


import com.yanze.building.enums.EnumInterface;

public enum YesNo implements EnumInterface {
	YES("是", "y"), NO("否", "n");

	public String name;
	public String value;

	private YesNo(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public static String getNameByCode(String value) {
		for (YesNo item : values()) {
			if (item.value.equals(value)) {
				return item.name;
			}
		}
		return "";
	}

	public static boolean isExists(String value) {
		for (YesNo item : values()) {
			if (item.value.equals(value)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public Integer getValueNum() {
		return null;
	}

}

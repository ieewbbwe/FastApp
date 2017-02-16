package com.android_mobile.core.enums;

public enum ResponseChartset {
	UTF_8("UTF-8"), GBK("GBK"), GB2312("GB2312");
	public String context;
	private ResponseChartset(String context) {
		this.context = context;
	}
}

package com.android_mobile.core.enums;

public enum CacheType {
	DEFAULT_NET(0x001), DEFAULT_CACHE_NET(0x002),DEFAULT_CACHE(0x003),
	BASIC_NET(0x001), BASIC_CACHE_NET(0x002),BASIC_CACHE(0x003);
	private int context=0x001;
	private CacheType(int context) {
		this.context=context;
	}
	public int getContext() {
		return context;
	}
}
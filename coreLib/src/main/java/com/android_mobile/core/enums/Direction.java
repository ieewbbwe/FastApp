package com.android_mobile.core.enums;

public enum Direction {
	LEFT(0x001), CENTER(0x002), RIGHT(0x003), TOP(
			0x004), BOTTOM(0x005);
	private int context = 0x001;

	private Direction(int context) {
		this.context = context;
	}

	public int getContext() {
		return context;
	}
}

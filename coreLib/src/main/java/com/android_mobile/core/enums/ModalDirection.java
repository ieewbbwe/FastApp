package com.android_mobile.core.enums;

public enum ModalDirection {
	LEFT(0x001), RIGHT(0x002),TOP(0x003),BOTTOM(0x004);
	private int context=0x001;
	private ModalDirection(int context) {
		this.context=context;
	}
	public int getContext() {
		return context;
	}
}
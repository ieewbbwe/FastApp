package com.android_mobile.core.event;

public class BasicEvent {
	public static String UPDATE_SKIN="update_skin";
	public static String ZXING_RESULT="ZXING_RESULT";
	public static String ZXING_WRITE="ZXING_WRITE";
	
	private String name;
	private Object data;
	public BasicEvent(String name){
		this.setName(name);
	}
	public BasicEvent(String name,Object data){
		this.setData(data);
		this.setName(name);
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}

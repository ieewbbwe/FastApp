package com.android_mobile.core.event;

import java.util.ArrayList;

public class BasicEventDispatcher {
	public interface IBasicListener {
		void handleEvent(BasicEvent event);
	}

	private static ArrayList<EventItem> array = new ArrayList<EventItem>();

	public static void addEventListenerSingle(String type, IBasicListener listener) {
		for (EventItem i : array) {
			if(i.type.equals(type)){
				return;
			}
		}
		EventItem item = new EventItem();
		item.type = type;
		item.listener = listener;
		array.add(item);
	}
	public static void addEventListener(String type, IBasicListener listener) {
//		Lg.print("add  "   +" type:"+type+" listener:"+listener);
		EventItem item = new EventItem();
		item.type = type;
		item.key="";
		item.listener = listener;
		array.add(item);
	}
	public static void addEventListener(String key,String type, IBasicListener listener) {
//		Lg.print("add  key:"+key+"   type:"+type+" listener:"+listener);
		EventItem item = new EventItem();
		item.type = type;
		item.key=key;
		item.listener = listener;
		array.add(item);
	}

	/**
	 * 移除监听
	 */
	public static void removeEventListener(String key ,String type) {
		for (int i = 0; i < array.size(); i++) {
			EventItem ei = array.get(i);
			if (ei.type.trim().equals(type)&&ei.key.trim().equals(key)) {
				array.remove(i);
			}
		}
	}
	public static void removeEventListenerByKey(String key) {
		for (int i = 0; i < array.size(); i++) {
			EventItem ei = array.get(i);
			
			if (ei!=null&&ei.key!=null&&ei.key.trim().equals(key)) {
				array.remove(i);
//				Lg.print("remove   key:"+ei.key+"   type:"+ei.type+" listener:"+ei.listener);
			}
		}
	}
	public static void dispatcher(BasicEvent e) {
		for (int i = 0; i < array.size(); i++) {
			EventItem ei = array.get(i);
			if (ei.type.trim().equals(e.getName())) {
//				Lg.print(e.getName()+"  "+ei.listener);
				ei.listener.handleEvent(e);
			}
		}
	}
}

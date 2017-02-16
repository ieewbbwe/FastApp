package com.android_mobile.core.net;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;


public class BasicParams {
	
	private List<BasicNameValuePair> params = null;
	
	public BasicParams()
	{
		params = new ArrayList<BasicNameValuePair>();
	}
	
	public void addParam(String name,String value)
	{
		BasicNameValuePair param = new BasicNameValuePair(name, value);
		params.add(param);
	}
	
	public List<BasicNameValuePair> getParams()
	{
		return params;
	}
}

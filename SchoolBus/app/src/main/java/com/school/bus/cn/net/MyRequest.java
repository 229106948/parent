package com.school.bus.cn.net;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import java.io.UnsupportedEncodingException;

/**
 * Created by zhangxingpei on 2017/3/29.
 */

public class MyRequest extends StringRequest {
    public MyRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    public MyRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsedResponse;
        try {
            parsedResponse=new String(response.data,"utf-8");
        } catch (UnsupportedEncodingException e) {
            parsedResponse=new String(response.data);
        }
        return Response.success(parsedResponse, HttpHeaderParser.parseCacheHeaders(response));
    }
}

package com.school.bus.cn.net;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.school.bus.cn.data.Leave;
import com.school.bus.cn.data.Notice;
import com.school.bus.cn.data.Parent;
import com.school.bus.cn.data.Student;
import com.school.bus.cn.listener.OnAddLeaveListener;
import com.school.bus.cn.listener.OnAddStudentListener;
import com.school.bus.cn.listener.OnDriverResponseListener;
import com.school.bus.cn.listener.OnLeaveaResponseListener;
import com.school.bus.cn.listener.OnLineResponseListener;
import com.school.bus.cn.listener.OnLocationResposeListener;
import com.school.bus.cn.listener.OnLoginResposeListener;
import com.school.bus.cn.listener.OnNoticeResposeListener;
import com.school.bus.cn.listener.OnRegisterResposeListener;
import com.school.bus.cn.listener.OnSiteResponseListener;
import com.school.bus.cn.listener.OnStudentResponseListener;
import com.school.bus.cn.listener.OnUpdateResposeListener;
import com.school.bus.cn.listener.OnUpdateStudentListener;
import com.school.bus.cn.util.GsonUtil;
import com.school.bus.main.MainApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangxingpei on 2017/3/29.
 */

public class NetworkProtocol {
    private static String url = "http://192.168.0.103:8080/SchoolBusServer/servlet";
    private static String TAG = "NetworkProtocol";
    private static OnNoticeResposeListener mNoticeListener;
    private static OnLocationResposeListener mLocationListener;
    private static OnLoginResposeListener mLoginListener;
    private static OnRegisterResposeListener mRegisterListener;
    private static OnUpdateResposeListener mUpdateListener;
    private static OnStudentResponseListener mStudentListener;
    private static OnDriverResponseListener mDriverListener;
    private static OnLineResponseListener mLineListener;
    private static OnSiteResponseListener mSiteListener;
    private static OnAddStudentListener mAddStudentListener;
    private static OnUpdateStudentListener mUpdateStudentListener;
    private static OnLeaveaResponseListener mLeaveaListener;
    private static OnAddLeaveListener mLeaveListener;
    public static void setOnResposeListener(OnNoticeResposeListener noticeListener) {
        mNoticeListener = noticeListener;
    }

    public static void setOnResposeListener(OnLocationResposeListener locationListener) {
        mLocationListener = locationListener;
    }

    public static void setOnResposeListener(OnLoginResposeListener loginListener) {
        mLoginListener = loginListener;
    }

    public static void setOnResposeListener(OnRegisterResposeListener registerListener) {
        mRegisterListener = registerListener;
    }

    public static void setOnResposeListener(OnUpdateResposeListener updateListener) {
        mUpdateListener = updateListener;
    }

    public static void setOnResposeListener(OnStudentResponseListener studentListener) {
        mStudentListener = studentListener;
    }

    public static void setOnResposeListener(OnLineResponseListener lineListener) {
        mLineListener = lineListener;
    }

    public static void setOnResposeListener(OnSiteResponseListener siteListener) {
        mSiteListener = siteListener;
    }

    public static void setOnResposeListener(OnDriverResponseListener driverListener) {
        mDriverListener = driverListener;
    }

    public static void setOnResposeListener(OnAddStudentListener studentListener) {
        mAddStudentListener = studentListener;
    }
    public static void setOnResposeListener(OnUpdateStudentListener studentListener) {
        mUpdateStudentListener = studentListener;
    }
    public static void setOnResposeListener(OnLeaveaResponseListener leaveaListener) {
        mLeaveaListener = leaveaListener;
    }
    public static void setOnResposeListener(OnAddLeaveListener leaveaListener) {
        mLeaveListener = leaveaListener;
    }
    public static void getNoticeData(final String parentId) {
        MyRequest request = new MyRequest(Request.Method.POST, url + "/notice", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d(TAG, s);
                if (mNoticeListener != null) {
                    mNoticeListener.onSuccess(s);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, volleyError.getMessage());
                if (mNoticeListener != null) {
                    mNoticeListener.onFail(volleyError.getMessage());
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("function", "showNotice");
                map.put("parentId", parentId);
                return map;
            }
        };
        MainApplication.getRequestQueue().add(request);
    }

    public static void getLocationData(final String driverId) {
        MyRequest request = new MyRequest(Request.Method.POST, url + "/location", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d(TAG, s);
                if (mLocationListener != null) {
                    mLocationListener.onSuccess(s);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, volleyError.getMessage());
                if (mLocationListener != null) {
                    mLocationListener.onFail(volleyError.getMessage());
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("function", "getLocation");
                map.put("driverId", driverId);
                return map;
            }
        };
        MainApplication.getRequestQueue().add(request);
    }

    public static void getDriverData(final String parentId) {
        MyRequest request = new MyRequest(Request.Method.POST, url + "/location", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d(TAG, s);
                if (mDriverListener != null) {
                    mDriverListener.onSuccess(s);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, volleyError.getMessage());
                if (mDriverListener != null) {
                    mDriverListener.onFail(volleyError.getMessage());
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("function", "getDriver");
                map.put("parentId", parentId);
                return map;
            }
        };
        MainApplication.getRequestQueue().add(request);
    }

    public static void getLoginResult(final String telephone, final String password) {
        MyRequest request = new MyRequest(Request.Method.POST, url + "/login", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d(TAG, s);
                if (mLoginListener != null) {
                    mLoginListener.onSuccess(s);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (mLoginListener != null) {
                    mLoginListener.onFail(volleyError.getMessage());
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("function", "login");
                map.put("telephone", telephone);
                map.put("password", password);
                return map;
            }
        };
        MainApplication.getRequestQueue().add(request);
    }

    public static void getUpdateResult(final String telephone, final String password) {
        MyRequest request = new MyRequest(Request.Method.POST, url + "/login", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d(TAG, s);
                if (mUpdateListener != null) {
                    mUpdateListener.onSuccess(s);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, volleyError.getMessage());
                if (mUpdateListener != null) {
                    mUpdateListener.onFail(volleyError.getMessage());
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("function", "password");
                map.put("telephone", telephone);
                map.put("password", password);
                return map;
            }
        };
        MainApplication.getRequestQueue().add(request);
    }

    public static void getRegisterResult(final String parent) {

        MyRequest request = new MyRequest(Request.Method.POST, url + "/login", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d(TAG, s);
                if (mRegisterListener != null) {
                    mRegisterListener.onSuccess(s);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, volleyError.getMessage());
                if (mRegisterListener != null) {
                    mRegisterListener.onFail(volleyError.getMessage());
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("function", "register");
                map.put("parent", parent);
                return map;
            }
        };
        MainApplication.getRequestQueue().add(request);
    }

    public static void getStudentByParent(final int parentId) {
        MyRequest request = new MyRequest(Request.Method.POST, url + "/student", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d(TAG, s);
                if (mStudentListener != null) {
                    mStudentListener.onSuccess(s);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, volleyError.getMessage());
                if (mStudentListener != null) {
                    mStudentListener.onFail(volleyError.getMessage());
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("function", "parentId");
                map.put("parentId", String.valueOf(parentId));
                return map;
            }
        };
        MainApplication.getRequestQueue().add(request);
    }

    public static void getLine() {
        MyRequest request = new MyRequest(Request.Method.POST, url + "/line", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d(TAG, s);
                if (mLineListener != null) {
                    mLineListener.onSuccess(s);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, volleyError.getMessage());
                if (mLineListener != null) {
                    mLineListener.onFail(volleyError.getMessage());
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("function", "getLine");
                return map;
            }
        };
        MainApplication.getRequestQueue().add(request);
    }
    public static void getLineByParent(final  int parentId) {
        MyRequest request = new MyRequest(Request.Method.POST, url + "/line", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d(TAG, s);
                if (mLineListener != null) {
                    mLineListener.onSuccess(s);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, volleyError.getMessage());
                if (mLineListener != null) {
                    mLineListener.onFail(volleyError.getMessage());
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("function", "parentId");
                map.put("parentId", String.valueOf(parentId));
                return map;
            }
        };
        MainApplication.getRequestQueue().add(request);
    }
    public static void getSite(final int lineId) {
        MyRequest request = new MyRequest(Request.Method.POST, url + "/line", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d(TAG, s);
                if (mSiteListener != null) {
                    mSiteListener.onSuccess(s);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, volleyError.getMessage());
                if (mSiteListener != null) {
                    mSiteListener.onFail(volleyError.getMessage());
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("function", "getSite");
                map.put("id", String.valueOf(lineId));
                return map;
            }
        };
        MainApplication.getRequestQueue().add(request);
    }

    public static void addChild(final Student student) {
        MyRequest request = new MyRequest(Request.Method.POST, url + "/student", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d(TAG, s);
                if (mAddStudentListener != null) {
                    mAddStudentListener.onSuccess(s);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, volleyError.getMessage());
                if (mAddStudentListener != null) {
                    mAddStudentListener.onFail(volleyError.getMessage());
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("function", "addChild");
                map.put("student", GsonUtil.changeStudentToJson(student));
                return map;
            }
        };
        MainApplication.getRequestQueue().add(request);
    }
    public static void updateChild(final String function, final String value,final int id,final String aboardSite,final String debusSite) {
        MyRequest request = new MyRequest(Request.Method.POST, url + "/student", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d(TAG, s);
                if (mUpdateStudentListener != null) {
                    mUpdateStudentListener.onSuccess(s);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (mUpdateStudentListener != null) {
                    mUpdateStudentListener.onFail(volleyError.getMessage());
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("function", function);
                map.put("data", value);
                map.put("id", String.valueOf(id));
                if(aboardSite!=null) {
                    map.put("aboardSite", aboardSite);
                }
                if(debusSite!=null) {
                    map.put("debusSite", debusSite);
                }
                return map;
            }
        };
        MainApplication.getRequestQueue().add(request);
    }
    public static void getLeaveData(final String parentId) {
        MyRequest request = new MyRequest(Request.Method.POST, url + "/leave", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d(TAG, s);
                if (mLeaveaListener != null) {
                    mLeaveaListener.onSuccess(s);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (mLeaveaListener != null) {
                    mLeaveaListener.onFail(volleyError.getMessage());
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("function", "showLeave");
                map.put("parentId", parentId);
                return map;
            }
        };
        MainApplication.getRequestQueue().add(request);
    }
    public static void addLeave(final String leave) {
        MyRequest request = new MyRequest(Request.Method.POST, url + "/leave", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d(TAG, s);
                if (mLeaveListener != null) {
                    mLeaveListener.onSuccess(s);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (mLeaveListener != null) {
                    mLeaveListener.onFail(volleyError.getMessage());
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("function", "addLeave");
                map.put("leave", leave);
                return map;
            }
        };
        MainApplication.getRequestQueue().add(request);
    }
}

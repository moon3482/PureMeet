package com.example.PureMeeting;


import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.request.StringRequest;


import java.util.HashMap;
import java.util.Map;

public class id_check extends StringRequest {


    //서버 URL 설정(php 파일 연동)
    final static private String URL = new ServerIP().http+"Id_Duplicate_Check.php";
    private Map<String, String> map;
    //private Map<String, String>parameters;

    public id_check(String UserEmail,Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("email", UserEmail);

    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}


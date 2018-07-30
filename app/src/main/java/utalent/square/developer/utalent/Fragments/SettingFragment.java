package utalent.square.developer.utalent.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import utalent.square.developer.utalent.R;
import utalent.square.developer.utalent.Utils.AlertUtils;

public class SettingFragment extends Fragment {
    android.support.v7.app.AlertDialog alertDialog;
    EditText etChangeUsername,etChangePasswd;
    Button btnChange;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        etChangeUsername = view.findViewById(R.id.etChangeUsername);
        etChangePasswd = view.findViewById(R.id.etChangepassword);
        btnChange = view.findViewById(R.id.btnChange);
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etChangeUsername.getText().toString().equals("")){
                   etChangeUsername.setError("enter your new username");
                }
                else if(etChangePasswd.getText().toString().equals("")){
                   etChangePasswd.setError("enter your new password");
                }
                else {
                    apiCall();
                }

            }
        });
        return view;
    }

    //api call for change password
    private void apiCall() {
        if (alertDialog == null)
            alertDialog = AlertUtils.createProgressDialog(getActivity());
        alertDialog.show();
        final String strNewUsername = etChangeUsername.getText().toString();
        final String strNewPasswd = etChangePasswd.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://chritmis.com/Utalent_Api/change_password.php"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                alertDialog.dismiss();
                if (response.contains("200")) {
                    alertDialog.dismiss();
                    SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#303F9F"));
                    pDialog.setTitleText("Username and password updated successfully");
                    pDialog.show();
                    Button btn = (Button) pDialog.findViewById(R.id.confirm_button);
                    btn.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.colorPrimary));
                } else {

                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded;charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", strNewUsername);
                params.put("password",strNewPasswd);
                return params;

            }
        };
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(stringRequest);

    }
}

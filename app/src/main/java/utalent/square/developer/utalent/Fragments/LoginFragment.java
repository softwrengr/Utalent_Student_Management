package utalent.square.developer.utalent.Fragments;

import android.graphics.Color;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import utalent.square.developer.utalent.R;
import utalent.square.developer.utalent.Utils.AlertUtils;


public class LoginFragment extends Fragment {
    android.support.v7.app.AlertDialog alertDialog;
    Button btnLogin;
    EditText etUserName,etUserPassword;
    String strUsername,strPassword;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        getActivity().setTitle("Utalent Student Management");
        etUserName = view.findViewById(R.id.etUsername);
        etUserPassword = view.findViewById(R.id.etUserpassword);
        btnLogin = view.findViewById(R.id.btnSignin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strUsername = etUserName.getText().toString().trim();
                strPassword = etUserPassword.getText().toString().trim();
                if(strUsername.equals("")){
                    etUserName.setError("enter your username");
                }
                else if(strPassword.equals("")){
                    etUserPassword.setError("enter your password");
                }
                else {
                    apiCall();
                }

            }
        });
        return view;
    }

    //api call for user Authenthication
    private void apiCall() {
        if (alertDialog == null)
            alertDialog = AlertUtils.createProgressDialog(getActivity());
        alertDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://chritmis.com/Utalent_Api/user_authenthication.php"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                alertDialog.dismiss();
                if (response.contains("200")) {
                    try {
                        alertDialog.dismiss();
                        JSONObject jsonObject = new JSONObject(response);
                        String message = jsonObject.getString("message");
                        if(message.contains("login successfully")){
                            Fragment fragment = new HomeFragment();
                            getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                        }
                        else {
                            SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE);
                            pDialog.getProgressHelper().setBarColor(Color.parseColor("#303F9F"));
                            pDialog.setTitleText("you got some error please try again");
                            pDialog.show();
                            Button btn = (Button) pDialog.findViewById(R.id.confirm_button);
                            btn.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.colorPrimary));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
                params.put("username", strUsername);
                params.put("password",strPassword);
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

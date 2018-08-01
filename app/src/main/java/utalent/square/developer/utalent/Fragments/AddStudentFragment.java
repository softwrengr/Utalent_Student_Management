package utalent.square.developer.utalent.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import utalent.square.developer.utalent.Models.AddStudentModel;
import utalent.square.developer.utalent.R;

public class AddStudentFragment extends Fragment {

    EditText etName,etStdTel,etParentTel,etAddress,etSubject,etRemark,etFee;
    ImageView ivAdd;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_student, container, false);
        etName = view.findViewById(R.id.etname);
        etAddress = view.findViewById(R.id.etAddress);
        etStdTel = view.findViewById(R.id.etStdTel);
        etParentTel = view.findViewById(R.id.etParentTel);
        etSubject = view.findViewById(R.id.etSubject);
        etRemark = view.findViewById(R.id.etRemark);
        etFee = view.findViewById(R.id.etFeetotal);
        ivAdd = view.findViewById(R.id.add);

        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apicallAddStd();
            }
        });
        return view;
    }


    private void apicallAddStd() {
        final String strName = etName.getText().toString();
        final String strTel = etStdTel.getText().toString();
        final String strParentTel = etParentTel.getText().toString();
        final String strAddress = etAddress.getText().toString();
        final String strSub = etSubject.getText().toString();
        final String strRemark = etRemark.getText().toString();
        final String strFee = etFee.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://chritmis.com/Utalent_Api/add_student.php"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response",response);
                Toast.makeText(getActivity(), "Student Added Successfully", Toast.LENGTH_SHORT).show();
                Fragment fragment = new HomeFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).addToBackStack("").commit();
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
                Map<String,String> params = new HashMap<>();
                params.put("name",strName);
                params.put("student_tel",strTel);
                params.put("parent_tel",strParentTel);
                params.put("address",strAddress);
                params.put("subject",strSub);
                params.put("remark",strRemark);
                params.put("fee_total",strFee);
                return params;
            }

        };
        RequestQueue mRequestQueue = Volley.newRequestQueue( getActivity());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(stringRequest);

    }

}

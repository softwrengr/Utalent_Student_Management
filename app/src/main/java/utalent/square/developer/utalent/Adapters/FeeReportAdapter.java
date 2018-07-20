package utalent.square.developer.utalent.Adapters;

import android.app.Fragment;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import utalent.square.developer.utalent.Fragments.HomeFragment;
import utalent.square.developer.utalent.Models.AddStudentModel;
import utalent.square.developer.utalent.Models.SpecificStudentModel;
import utalent.square.developer.utalent.R;

public class FeeReportAdapter  extends RecyclerView.Adapter<FeeReportAdapter.MyViewHolder> {
    android.support.v7.app.AlertDialog alertDialog;
    ArrayList<SpecificStudentModel> feeReportArrayList;
    Context context;

    public FeeReportAdapter(Context context, ArrayList<SpecificStudentModel> feeReportArrayList) {
        this.context = context;
        this.feeReportArrayList = feeReportArrayList;
    }

    @NonNull
    @Override
    public FeeReportAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cusotm_fee_report, parent, false);
        return new FeeReportAdapter.MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final SpecificStudentModel feeModel = feeReportArrayList.get(position);
        holder.tvStdName.setText(feeModel.getName());
        holder.tvFee.setText(feeModel.getFee());
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.checkBox.isChecked()){
                    String strID = feeModel.getId();
                    apicallDeleteStd(strID);
                    String strFeeCollect = feeModel.getFee();
                    apicallFeeCollect(strFeeCollect);
//
                }
                else {
                    Toast.makeText(context, "try again", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return feeReportArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvStdName,tvFee;
        CheckBox checkBox;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvStdName = itemView.findViewById(R.id.tvName);
            tvFee = itemView.findViewById(R.id.tvTotalFee);
            checkBox = itemView.findViewById(R.id.feeCheck);
        }
    }

    //api call for delete student

    private void apicallDeleteStd(String id) {
        final String stdId = id;
        Log.d("zma",stdId);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://chritmis.com/Utalent_Api/delete_student.php?"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response", response);
                if (response.contains("200")) {
                    Toast.makeText(context, "deleted", Toast.LENGTH_SHORT).show();
                    Fragment fragment = new HomeFragment();
                   ((AppCompatActivity)context).getFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();

                }  else {

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
                Map<String,String> params = new HashMap<>();
                params.put("id",stdId);
                return params;
            }

        };
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(stringRequest);

    }
    //end

    //api call for collecting fee

    private void apicallFeeCollect(String fee) {
        final String strFee = fee;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://chritmis.com/Utalent_Api/fee_collection.php"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response", response);

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
                params.put("fee",strFee);
                return params;
            }

        };
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(stringRequest);

    }

}


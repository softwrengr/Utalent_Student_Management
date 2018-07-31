package utalent.square.developer.utalent.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import utalent.square.developer.utalent.R;
import utalent.square.developer.utalent.Utils.AlertUtils;

public class AddStudentAdapter extends RecyclerView.Adapter<AddStudentAdapter.MyViewHolder> {
    android.support.v7.app.AlertDialog alertDialog;
    ArrayList<AddStudentModel> addStudentModelArrayList;
    Context context;
    Dialog dialog;

    public AddStudentAdapter(Context context, ArrayList<AddStudentModel> editLicensesModelArrayList) {
        this.context = context;
        this.addStudentModelArrayList = editLicensesModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_students_layout, parent, false);
        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final AddStudentModel editLicensesModel = addStudentModelArrayList.get(position);
        holder.tvStdName.setText(editLicensesModel.getStd_name());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_layout);
                final TextView tvDialogName = dialog.findViewById(R.id.tvDialogName);
                final TextView tvDialogAddress = dialog.findViewById(R.id.tvDialogAddress);
                final TextView tvDialogStdTel = dialog.findViewById(R.id.tvDialogStdTel);
                final TextView tvDialogParentTel = dialog.findViewById(R.id.tvDialogParentTel);
                final TextView tvDialogSubject = dialog.findViewById(R.id.tvDialogSubject);
                final TextView tvDialogRemark = dialog.findViewById(R.id.tvDialogRemark);
                final TextView tvDialogFee = dialog.findViewById(R.id.tvDialogFee);
                final Button btnDialogOk = dialog.findViewById(R.id.btnDialogOk);
                final Button btnDelete = dialog.findViewById(R.id.btnDeleteStd);
                final Button btnUpdate = dialog.findViewById(R.id.btnUpdateStd);
                tvDialogName.setText(editLicensesModel.getStd_name());
                tvDialogAddress.setText(editLicensesModel.getStd_address());
                tvDialogStdTel.setText(editLicensesModel.getStd_tel());
                tvDialogParentTel.setText(editLicensesModel.getParent_tel());
                tvDialogSubject.setText(editLicensesModel.getSubject());
                tvDialogRemark.setText(editLicensesModel.getRemarks());
                tvDialogFee.setText(editLicensesModel.getTotal_fee());
                btnDialogOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String strId = editLicensesModel.getStd_id();
                        apiCallDeleteStd(strId);
                    }
                });
                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog = new Dialog(context);
                        dialog.setContentView(R.layout.update_student_layout);
                        EditText etName = dialog.findViewById(R.id.etUpdatedname);
                        EditText etAddress = dialog.findViewById(R.id.etUpdatedAddress);
                        EditText etStdTel = dialog.findViewById(R.id.etUpdatedStdTel);
                        EditText etParentTel = dialog.findViewById(R.id.etUpdatedParentTel);
                        EditText etSubject = dialog.findViewById(R.id.etUpdatedSubject);
                        EditText etRemark = dialog.findViewById(R.id.etUpdatedRemark);
                        EditText etFee = dialog.findViewById(R.id.etUpdatedFeetotal);
                        Button update = dialog.findViewById(R.id.update);
                        final String id = editLicensesModel.getStd_id();
                        final String name = etName.getText().toString();
                        final String address = etAddress.getText().toString();
                        final String stdTel = etStdTel.getText().toString();
                        final String stdPaTel  =etParentTel.getText().toString();
                        final String subject = etSubject.getText().toString();
                        final String remark = etRemark.getText().toString();
                        final String totalFee = etFee.getText().toString();
                        update.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                apiCallUpdateStd(id,name,address,stdTel,stdPaTel,subject,remark,totalFee);
                            }
                        });

                        dialog.show();
                    }
                });
                dialog.show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return addStudentModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvStdName;
        LinearLayout linearLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvStdName = itemView.findViewById(R.id.tvStdName);
            linearLayout = itemView.findViewById(R.id.stdLinearLayout);
        }
    }

    //apiCall for delete student
    private void apiCallDeleteStd(final String id) {
        if (alertDialog == null)
            alertDialog = AlertUtils.createProgressDialog((Activity) context);
        alertDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://chritmis.com/Utalent_Api/delete_student.php"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                alertDialog.dismiss();
                if (response.contains("200")) {
                    try {
                        alertDialog.dismiss();
                        JSONObject jsonObject = new JSONObject(response);
                        String message = jsonObject.getString("data");
                        if(message.equals("student deleted successfully")){
                            dialog.dismiss();
                            Fragment fragment = new HomeFragment();
                            ((AppCompatActivity)context).getFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
                        }
                        else {
                            Toast.makeText(context, "you got some error!", Toast.LENGTH_SHORT).show();
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
                params.put("id", id);
                return params;

            }
        };
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(stringRequest);

    }

    //apiCall for update student
    private void apiCallUpdateStd( final String id,final String name,final String address,final String stdTel,final String stdPaTel,final String subject,final String remark,final String fee) {
        if (alertDialog == null)
            alertDialog = AlertUtils.createProgressDialog((Activity) context);
        alertDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://chritmis.com/Utalent_Api/update_student.php"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                alertDialog.dismiss();
                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                if (response.contains("200")) {
                    try {
                        alertDialog.dismiss();
                        dialog.dismiss();
                        JSONObject jsonObject = new JSONObject(response);
                        String message = jsonObject.getString("message");
                        if(message.equals("student deleted successfully")){
                            Fragment fragment = new HomeFragment();
                            ((AppCompatActivity)context).getFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
                        }
                        dialog.dismiss();
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
                Log.d("check",id);
                Log.d("check",name);
                Log.d("check",fee);
                params.put("id", id);
                params.put("name", name);
                params.put("address", address);
                params.put("student_tel", stdTel);
                params.put("parent_tel", stdPaTel);
                params.put("subject", subject);
                params.put("remark", remark);
                params.put("fee_total",fee);
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

package utalent.square.developer.utalent.Fragments;

import android.Manifest;
import android.app.Dialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utalent.square.developer.utalent.Adapters.AddStudentAdapter;
import utalent.square.developer.utalent.Adapters.FeeReportAdapter;
import utalent.square.developer.utalent.Models.AddStudentModel;
import utalent.square.developer.utalent.Models.SpecificStudentModel;
import utalent.square.developer.utalent.R;
import utalent.square.developer.utalent.Utils.AlertUtils;


public class HomeFragment extends Fragment {
    android.support.v7.app.AlertDialog alertDialog;
    Dialog dialog;
    RecyclerView rvTotalStudents,rvFeeReport;
    ArrayList<AddStudentModel> addStudentModelArrayList;
    ArrayList<SpecificStudentModel> feeReportModelArrayList;
    AddStudentAdapter addStudentAdapter;
    FeeReportAdapter feeReportAdapter;
    Button btnAddStudents,btnReport,btnSetting,btnHome;
    TextView tvTotalStd;
    ImageView ivSearchStd;
    String strSearchName;
    EditText etSearchStd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        getActivity().setTitle("Utalent Student Management");
        rvTotalStudents = view.findViewById(R.id.rvTotalStudents);
        rvFeeReport = view.findViewById(R.id.rvFeeReports);
        btnAddStudents = view.findViewById(R.id.btnStd);
        btnReport = view.findViewById(R.id.btnReport);
        btnSetting = view.findViewById(R.id.btnSetting);
        tvTotalStd = view.findViewById(R.id.tvTotalstudents);
        ivSearchStd = view.findViewById(R.id.etSearchStdList);
        etSearchStd = view.findViewById(R.id.etSearchStd);

        Dexter.withActivity(getActivity())
                .withPermissions(
                        Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_NETWORK_STATE
                ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

            }

        }).check();

        apicallTotalStd();
        apiSetUpFeeReport();
        apiSetUp();

        btnAddStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new AddStudentFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack("abc").commit();
            }
        });
        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ReportFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack("abc").commit();
            }
        });
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new SettingFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack("abc").commit();
            }
        });

        ivSearchStd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiCallStudentSearch();
            }
        });
        return view;
    }

    public void apiSetUp() {
        rvTotalStudents.setLayoutManager(new LinearLayoutManager(getActivity()));
        addStudentModelArrayList = new ArrayList<>();
        apicall();
        if (alertDialog == null)
            alertDialog = AlertUtils.createProgressDialog(getActivity());
        alertDialog.show();
        addStudentAdapter = new AddStudentAdapter(getActivity(), addStudentModelArrayList);
        rvTotalStudents.setAdapter(addStudentAdapter);

    }

    //api setup for fee report
    public void apiSetUpFeeReport() {
        rvFeeReport.setLayoutManager(new LinearLayoutManager(getActivity()));
        feeReportModelArrayList = new ArrayList<>();
        apiCallFeeReport();
        if (alertDialog == null)
            alertDialog = AlertUtils.createProgressDialog(getActivity());
        alertDialog.show();
        feeReportAdapter = new FeeReportAdapter(getActivity(), feeReportModelArrayList);
        rvFeeReport.setAdapter(feeReportAdapter);

    }
    //end

    private void apicall() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://chritmis.com/Utalent_Api/all_students.php"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                alertDialog.dismiss();
                Log.d("response", response);
                if (response.contains("200")) {
                    try {
                        if (alertDialog != null)
                            alertDialog.dismiss();

                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArr = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArr.length(); i++) {
                            JSONObject temp = jsonArr.getJSONObject(i);
                            AddStudentModel model = new AddStudentModel();
                            String std_id = temp.getString("id");
                            String name = temp.getString("name");
                            model.setStd_id(std_id);
                            model.setStd_name(name);
                            addStudentModelArrayList.add(model);
                        }
                        addStudentAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        if (alertDialog != null)
                            alertDialog.dismiss();
                    }


                } else {

                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (alertDialog != null)
                    alertDialog.dismiss();

            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded;charset=UTF-8";
            }

        };
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(stringRequest);

    }

    //api call for total number of students

    private void apicallTotalStd() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://chritmis.com/Utalent_Api/total_user.php"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                alertDialog.dismiss();
                Log.d("response", response);
                if (response.contains("200")) {
                    try {
                        if (alertDialog != null)
                            alertDialog.dismiss();

                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArr = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArr.length(); i++) {
                            JSONObject temp = jsonArr.getJSONObject(i);
                            String strTotalStd = temp.getString("count");
                            tvTotalStd.setText(strTotalStd+" Students");
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                        if (alertDialog != null)
                            alertDialog.dismiss();
                    }


                }  else {

                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (alertDialog != null)
                    alertDialog.dismiss();

            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded;charset=UTF-8";
            }

        };
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(stringRequest);

    }

    //api call for fee report
    private void apiCallFeeReport() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://chritmis.com/Utalent_Api/specific_student.php"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                alertDialog.dismiss();
                Log.d("response", response);
                if (response.contains("200")) {
                    try {
                        if (alertDialog != null)
                            alertDialog.dismiss();

                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArr = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArr.length(); i++) {
                            JSONObject temp = jsonArr.getJSONObject(i);
                            SpecificStudentModel model = new SpecificStudentModel();
                            String id = temp.getString("student_id");
                            String name = temp.getString("name");
                            String totalFee = temp.getString("fee");
                            model.setId(id);
                            model.setName(name);
                            model.setFee(totalFee);

                            feeReportModelArrayList.add(model);
                        }
                        feeReportAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        if (alertDialog != null)
                            alertDialog.dismiss();
                    }


                } else {

                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (alertDialog != null)
                    alertDialog.dismiss();

            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded;charset=UTF-8";
            }

        };
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(stringRequest);

    }

    //api call for student search
    private void apiCallStudentSearch() {
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_layout);
        final TextView tvDialogName = dialog.findViewById(R.id.tvDialogName);
        final TextView tvDialogFee = dialog.findViewById(R.id.tvDialogFee);
        final Button btnDialogOk = dialog.findViewById(R.id.btnDialogOk);
        strSearchName = etSearchStd.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://chritmis.com/Utalent_Api/student_search.php"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                alertDialog.dismiss();
                if (response.contains("200")) {
                    try {
                        if (alertDialog != null)
                            alertDialog.dismiss();

                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArr = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArr.length(); i++) {
                            JSONObject temp = jsonArr.getJSONObject(i);
                            String name = temp.getString("name");
                            String fee = temp.getString("fee_total");
                            tvDialogName.setText(name);
                            tvDialogFee.setText(fee);
                            dialog.show();
                            etSearchStd.setText("");
                            btnDialogOk.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        if (alertDialog != null)
                            alertDialog.dismiss();
                    }


                } else {

                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (alertDialog != null)
                    alertDialog.dismiss();

            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded;charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                Log.d("ser",strSearchName);
                params.put("name",strSearchName);
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

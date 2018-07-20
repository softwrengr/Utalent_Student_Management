package utalent.square.developer.utalent.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import utalent.square.developer.utalent.Adapters.FeeReportAdapter;
import utalent.square.developer.utalent.Adapters.MonthlyFeeAdapter;
import utalent.square.developer.utalent.Models.AddStudentModel;
import utalent.square.developer.utalent.Models.FeeReportModel;
import utalent.square.developer.utalent.Models.SpecificStudentModel;
import utalent.square.developer.utalent.R;
import utalent.square.developer.utalent.Utils.AlertUtils;

public class ReportFragment extends Fragment {
    android.support.v7.app.AlertDialog alertDialog;
    RecyclerView rvMonthReport;
    ArrayList<FeeReportModel> feeReportModelArrayList;
    MonthlyFeeAdapter monthlyFeeAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report, container, false);
        rvMonthReport = view.findViewById(R.id.rvMonthlyReport);
        apiSetMonthlyReport();
        return view;
    }

    public void apiSetMonthlyReport() {
        rvMonthReport.setLayoutManager(new LinearLayoutManager(getActivity()));
        feeReportModelArrayList = new ArrayList<>();
        apiCallFeeReport();
        if (alertDialog == null)
            alertDialog = AlertUtils.createProgressDialog(getActivity());
        alertDialog.show();
        monthlyFeeAdapter = new MonthlyFeeAdapter(getActivity(), feeReportModelArrayList);
        rvMonthReport.setAdapter(monthlyFeeAdapter);

    }

    private void apiCallFeeReport() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://chritmis.com/Utalent_Api/month_record.php"
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
                            FeeReportModel model = new FeeReportModel();

                            String monthName = temp.getString("collection_date");
                            String monthlyFee = temp.getString("SUM(fee_amount)");
                            String monthlyStd = temp.getString("count(*)");
                            model.setMonthName(monthName);
                            model.setMonthlyFee(monthlyFee);
                            model.setMonthlyTotalStudents(monthlyStd);

                            feeReportModelArrayList.add(model);
                        }
                        monthlyFeeAdapter.notifyDataSetChanged();

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
}

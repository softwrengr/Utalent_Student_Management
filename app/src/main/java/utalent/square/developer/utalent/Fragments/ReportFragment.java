package utalent.square.developer.utalent.Fragments;

import android.Manifest;
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
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import utalent.square.developer.utalent.Adapters.MonthlyFeeAdapter;
import utalent.square.developer.utalent.Models.FeeReportModel;
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
        getActivity().setTitle("Utalent Student Management");
        rvMonthReport = view.findViewById(R.id.rvMonthlyReport);
        apiSetMonthlyReport();

        Dexter.withActivity(getActivity())
                .withPermissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

            }

        }).check();
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
//    public void exportPdf(){
//        String jsonString = "{\"infile\": [{\"field1\": 11,\"field2\": 12,\"field3\": 13},{\"field1\": 21,\"field2\": 22,\"field3\": 23},{\"field1\": 31,\"field2\": 32,\"field3\": 33}]}";
//
//        JSONObject output;
//        try {
//            output = new JSONObject(jsonString);
//
//
//            JSONArray docs = output.getJSONArray("infile");
//
//            File file=new File("/tmp2/fromJSON.csv");
//            String csv = CDL.toString(docs);
//            FileUtils.writeStringToFile(file, csv);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
}

package utalent.square.developer.utalent.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import utalent.square.developer.utalent.Models.AddStudentModel;
import utalent.square.developer.utalent.Models.FeeReportModel;
import utalent.square.developer.utalent.R;

public class MonthlyFeeAdapter  extends RecyclerView.Adapter<MonthlyFeeAdapter.MyViewHolder> {
    ArrayList<FeeReportModel> feeReportModelArrayList;
    Context context;

    public MonthlyFeeAdapter(Context context, ArrayList<FeeReportModel> editLicensesModelArrayList) {
        this.context = context;
        this.feeReportModelArrayList = editLicensesModelArrayList;
    }

    @NonNull
    @Override
    public MonthlyFeeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.monthly_report_layout, parent, false);
        return new MonthlyFeeAdapter.MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
     FeeReportModel feeReportModel = feeReportModelArrayList.get(position);
     holder.tvmonthName.setText(feeReportModel.getMonthName());
     holder.tvmonthlyFee.setText(feeReportModel.getMonthlyFee());
     holder.tvMonthlystudent.setText(feeReportModel.getMonthlyTotalStudents());
    }


    @Override
    public int getItemCount() {
        return feeReportModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvmonthName,tvMonthlystudent;
        TextView tvmonthlyFee;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvmonthName = itemView.findViewById(R.id.tvMonthName);
            tvmonthlyFee = itemView.findViewById(R.id.tvMonthlyFee);
            tvMonthlystudent = itemView.findViewById(R.id.tvMonthlystudent);
        }
    }
}


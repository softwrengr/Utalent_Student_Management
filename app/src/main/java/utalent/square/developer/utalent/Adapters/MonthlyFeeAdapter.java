package utalent.square.developer.utalent.Adapters;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
     final FeeReportModel feeReportModel = feeReportModelArrayList.get(position);
     holder.tvmonthName.setText(feeReportModel.getMonthName());
     holder.tvmonthlyFee.setText("$"+feeReportModel.getMonthlyFee()+" Total");
     holder.tvMonthlystudent.setText(feeReportModel.getMonthlyTotalStudents()+" Students");
     holder.cardView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             String monthName = feeReportModel.getMonthName();
             String total_student = feeReportModel.getMonthlyTotalStudents();
             String total_fee = feeReportModel.getMonthlyFee();
             String body = monthName+total_student+total_fee;
             generateNoteOnSD(body);
         }
     });
    }


    @Override
    public int getItemCount() {
        return feeReportModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvmonthName,tvMonthlystudent;
        TextView tvmonthlyFee;
        CardView cardView;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvmonthName = itemView.findViewById(R.id.tvMonthName);
            tvmonthlyFee = itemView.findViewById(R.id.tvMonthlyFee);
            tvMonthlystudent = itemView.findViewById(R.id.tvMonthlystudent);
            cardView = itemView.findViewById(R.id.cvMonthlyReport);
        }
    }

    public void generateNoteOnSD(String sBody) {
        SimpleDateFormat formatter = new SimpleDateFormat("mm");
        Date now = new Date();
        String fileName = "abdullah" + ".xls";
        try {
            File root = new File(Environment.getExternalStorageDirectory() + File.separator + "Notes");
            if (!root.exists()) {
                root.mkdirs();
                Toast.makeText(context, "file created", Toast.LENGTH_SHORT).show();
            }

            File gpxfile = new File(root, fileName);


            FileWriter writer = new FileWriter(gpxfile, true);
            writer.append(sBody + "\n\n");
            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}


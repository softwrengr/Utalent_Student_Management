package utalent.square.developer.utalent.Adapters;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import utalent.square.developer.utalent.Models.AddStudentModel;
import utalent.square.developer.utalent.Models.FeeReportModel;
import utalent.square.developer.utalent.R;
import utalent.square.developer.utalent.Utils.ShareUtils;

public class MonthlyFeeAdapter extends RecyclerView.Adapter<MonthlyFeeAdapter.MyViewHolder> {
    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";
    FileWriter writer;
    File rootFile, CsvFile;
    ArrayList<FeeReportModel> feeReportModelArrayList;
    Context context;
    Boolean restriction;

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
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final FeeReportModel feeReportModel = feeReportModelArrayList.get(position);
        holder.tvmonthName.setText(feeReportModel.getMonthName());
        holder.tvmonthlyFee.setText("$" + feeReportModel.getMonthlyFee() + " Total");
        holder.tvMonthlystudent.setText(feeReportModel.getMonthlyTotalStudents() + " Students");
        holder.ivExcelExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String monthName = feeReportModel.getMonthName();
                String total_student = feeReportModel.getMonthlyTotalStudents();
                String total_fee = feeReportModel.getMonthlyFee();
                String body = monthName + total_student + total_fee;
                generateNoteOnSD(monthName, total_student, total_fee);
            }
        });
    }


    @Override
    public int getItemCount() {
        return feeReportModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvmonthName, tvMonthlystudent;
        TextView tvmonthlyFee;
        CardView cardView;
        ImageView ivExcelExport;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvmonthName = itemView.findViewById(R.id.tvMonthName);
            tvmonthlyFee = itemView.findViewById(R.id.tvMonthlyFee);
            tvMonthlystudent = itemView.findViewById(R.id.tvMonthlystudent);
            cardView = itemView.findViewById(R.id.cvMonthlyReport);
            ivExcelExport = itemView.findViewById(R.id.ivExcel);
        }
    }


    public void generateNoteOnSD(String monthName, String total_student, String total_fee) {
        Date currentTime = Calendar.getInstance().getTime();
        String dataTime = String.valueOf(currentTime);

        try {

            rootFile = new File(Environment.getExternalStorageDirectory(), "Utalent Folder");

            if (!rootFile.exists()) {
                rootFile.mkdirs();
                ShareUtils.putValueInEditor(context).putBoolean("title", true).commit();
            }
            CsvFile = new File(rootFile, "Utalent Fee Report" + ".CSV");
            if (!CsvFile.exists()) {
                ShareUtils.putValueInEditor(context).putBoolean("title", true).commit();
            }
            writer = new FileWriter(CsvFile, true);
            restriction = ShareUtils.getSharedPreferences(context).getBoolean("title", false);
            if (restriction) {
                writer.append("Month");
                writer.append(COMMA_DELIMITER);
                writer.append("Total Student");
                writer.append(COMMA_DELIMITER);
                writer.append(COMMA_DELIMITER);
                writer.append("Total Fee");
                writer.append(COMMA_DELIMITER);
                writer.append("Export Date");
                writer.append(COMMA_DELIMITER);
                writer.append(COMMA_DELIMITER);
                writer.append(NEW_LINE_SEPARATOR);
            }



            writer.append(monthName);
            writer.append(COMMA_DELIMITER);
            writer.append(total_student);
            writer.append(COMMA_DELIMITER);
            writer.append(COMMA_DELIMITER);
            writer.append("$"+total_fee);
            writer.append(COMMA_DELIMITER);
            writer.append(dataTime);
            writer.append(COMMA_DELIMITER);
            writer.append(NEW_LINE_SEPARATOR);


            writer.flush();
            writer.close();

            Toast.makeText(context, "Report Saved", Toast.LENGTH_SHORT).show();
            ShareUtils.putValueInEditor(context).putBoolean("title", false).commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


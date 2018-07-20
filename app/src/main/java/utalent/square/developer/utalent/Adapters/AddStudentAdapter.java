package utalent.square.developer.utalent.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import utalent.square.developer.utalent.Models.AddStudentModel;
import utalent.square.developer.utalent.R;

public class AddStudentAdapter  extends RecyclerView.Adapter<AddStudentAdapter.MyViewHolder> {
    ArrayList<AddStudentModel> addStudentModelArrayList;
    Context context;

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
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final AddStudentModel editLicensesModel = addStudentModelArrayList.get(position);
        holder.tvStdName.setText(editLicensesModel.getStd_name());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();
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
}

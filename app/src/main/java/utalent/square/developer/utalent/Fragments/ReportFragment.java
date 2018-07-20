package utalent.square.developer.utalent.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import utalent.square.developer.utalent.R;

public class ReportFragment extends Fragment {
    android.support.v7.app.AlertDialog alertDialog;
    RecyclerView rvReport;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report, container, false);
        return view;
    }
}

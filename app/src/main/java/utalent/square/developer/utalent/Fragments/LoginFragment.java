package utalent.square.developer.utalent.Fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import utalent.square.developer.utalent.R;


public class LoginFragment extends Fragment {
    Button btnLogin;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        btnLogin = view.findViewById(R.id.btnSignin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Fragment fragment = new HomeFragment();
             getFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            }
        });
        return view;
    }

}

package utalent.square.developer.utalent.Activities;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import utalent.square.developer.utalent.Fragments.LoginFragment;
import utalent.square.developer.utalent.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        Fragment fragment = new LoginFragment();
        getFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
    }


}

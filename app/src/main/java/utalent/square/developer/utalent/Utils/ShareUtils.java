package utalent.square.developer.utalent.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class ShareUtils {
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;



    public static SharedPreferences.Editor putValueInEditor(Context context) {
        sharedPreferences = getSharedPreferences(context);
        editor = sharedPreferences.edit();
        return editor;
    }

    public static SharedPreferences getSharedPreferences(Context context) {
        //sharedPreferences = context.getSharedPreferences(Configuration.MY_PREF, 0);
        return context.getSharedPreferences(Configuration.MY_PREF, 0);
    }

}

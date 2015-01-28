package srhdesigns.band;

import android.os.Bundle;
import android.preference.PreferenceActivity;


public class AppAbout extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.layout.activity_app_about);
    }

}

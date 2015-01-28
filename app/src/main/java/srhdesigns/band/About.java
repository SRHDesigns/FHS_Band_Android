package srhdesigns.band;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import srhdesigns.download.Download_HQ;


public class About extends ActionBarActivity {
    String Description = "";
    TextView Des = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Intent intent = getIntent();
        intent.getExtras().getString("Name");
        Des = (TextView) findViewById(R.id.kTextView);
        getDes();
    }

    public void getDes(){
        Download_HQ kDHQ = new Download_HQ();
        Des.setText(kDHQ.ReadFileWithKey("About.txt", this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent openNewActivity;

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            openNewActivity = new Intent(getApplicationContext(), AppAbout.class);
            startActivity(openNewActivity);
        }

        return super.onOptionsItemSelected(item);
    }


}

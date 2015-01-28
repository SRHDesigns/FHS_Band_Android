package srhdesigns.band;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.dd.plist.NSArray;
import com.dd.plist.NSDictionary;
import com.dd.plist.PropertyListParser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import srhdesigns.download.Download_HQ;


public class Main extends ActionBarActivity implements AdapterView.OnItemClickListener {
    static ArrayAdapter kArrayAdapter;
    static ArrayList kNameList = new ArrayList();
    static ArrayList kIDList = new ArrayList();
    boolean forceRun = false;
    boolean shouldRun = true;
    ListView kTableView;
    public static String kBase;

    @Override
    protected void onStop() {
        kNameList = new ArrayList();
        kIDList = new ArrayList();
        kArrayAdapter.notifyDataSetChanged();
        super.onStop();
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Download_HQ kDHQ = new Download_HQ();
        if (shouldRun) {
            kDHQ.DownloadAll(getString(R.string.kbaseurl), this);
        }
        GetData();

        kNameList = new ArrayList();
        kBase = getString(R.string.kbaseurl);
        kTableView = (ListView) findViewById(R.id.Table_View);
        kArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                kNameList);
        kTableView.setAdapter(kArrayAdapter);
        kTableView.setOnItemClickListener(this);
        GetData();

    }

    private void GetData() {
        Download_HQ kDHQ = new Download_HQ();
        System.out.println("Data: " + kDHQ.ReadFileWithKey("Identifiers.plist", this));
        Identifier(kDHQ.ReadFileWithKey("Identifiers.plist", this));
    }

    private void Identifier(String s) {
        System.out.println("String" + s);
        InputStream stream = new ByteArrayInputStream(s.getBytes());

        try {

            NSArray rootDict = (NSArray) PropertyListParser.parse(stream);
            System.out.println(rootDict.toJavaObject().getClass());
            for (int i = 0; i < rootDict.count(); i++) {
                NSDictionary indexDict = (NSDictionary) rootDict.objectAtIndex(i);
                if ((boolean) (indexDict.objectForKey("Show").toJavaObject())) {
                    kNameList.add(String.valueOf(indexDict.objectForKey("Header")));
                    kIDList.add(String.valueOf(indexDict.objectForKey("ID")));
                    System.out.println(indexDict.objectForKey("Header"));
                    System.out.println(indexDict.objectForKey("ID"));
                }
            }
            kArrayAdapter.notifyDataSetChanged();

        } catch (Exception e) {
            System.out.println("Failed" + e.toString());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String Name = kNameList.get(position).toString();
        String ID = kIDList.get(position).toString();
        Toast.makeText(getApplicationContext(), "You selected " + Name, Toast.LENGTH_SHORT).show();
        Intent openNewActivity;
        switch (ID) {
            case ("about"):
                openNewActivity = new Intent(getApplicationContext(), About.class);
                openNewActivity.putExtra("Name", "About.txt");
                startActivity(openNewActivity);

                break;
            case ("scales"):
                openNewActivity = new Intent(getApplicationContext(), Scales.class);
                startActivity(openNewActivity);
                break;

            case ("Contact"):
                openNewActivity = new Intent(getApplicationContext(), Directors.class);
                startActivity(openNewActivity);
                break;
            default:
                Toast.makeText(getApplicationContext(), "Unable to Change", Toast.LENGTH_SHORT).show();
                break;

        }

    }
}

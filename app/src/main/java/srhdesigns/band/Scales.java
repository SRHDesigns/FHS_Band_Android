package srhdesigns.band;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.dd.plist.NSArray;
import com.dd.plist.NSDictionary;
import com.dd.plist.PropertyListParser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import srhdesigns.download.Downloader;


public class Scales extends ActionBarActivity implements AdapterView.OnItemClickListener {
    ListView kList = null;
    static ArrayAdapter kArrayAdapter;
    static ArrayList kNameList = new ArrayList();
    static ArrayList kIDList = new ArrayList();
    boolean isVis = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scales);
        GetData();


        kList = (ListView)findViewById(R.id.kList);

        kArrayAdapter = new MyAdapter(this, kNameList);

        kList.setAdapter(kArrayAdapter);
        kList.setOnItemClickListener(this);
        kList.setVisibility(View.GONE);


    }
    private void GetData(){
        try {

            GetScales();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void GetScales() throws ExecutionException, InterruptedException {
        Downloader get = new Downloader();
        String g = get.execute(new String[] {Main.kBase + "Scales.plist"}).get();
        ProcessScales(g);
    }
    private void ProcessScales(String s) {
        System.out.println("String" +s);
        InputStream stream = new ByteArrayInputStream(s.getBytes());

        try {

            NSDictionary rootDict = (NSDictionary) PropertyListParser.parse(stream);
            NSArray indexDict = (NSArray) rootDict.objectForKey("Scales");

            for (int i = 0; i < indexDict.count(); i++) {
                NSDictionary dict = (NSDictionary)indexDict.objectAtIndex(i);
                kNameList.add(String.valueOf(dict.objectForKey("Description")));
                kIDList.add(String.valueOf(dict.objectForKey("Link")));
                System.out.println(dict.objectForKey("Description"));

            }
            kArrayAdapter.notifyDataSetChanged();

        } catch (Exception e) {
            System.out.println("Failed" + e.toString());
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scales, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else{
            kList.setVisibility(isVis ? View.GONE:View.VISIBLE);
            isVis = !isVis;
            item.setTitle(isVis?"Hide":"Show");

        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        System.out.println(kIDList.get(position));
    }
}

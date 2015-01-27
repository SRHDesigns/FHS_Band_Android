package srhdesigns.download;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by grandslam700 on 1/26/15.
 */
 public class Download_HQ {
    public String kBase;
    public Context kContext;
    ArrayList<String> kFileNames = new ArrayList<String>();
    public void DownloadAll(String gkbase, Context gkContext){
        PopFile();

        kBase = gkbase;
        kContext = gkContext;
        System.out.println(kBase);
        try {
            for(String url: kFileNames){
                D_String(url);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void PopFile(){
        kFileNames.add("About.txt");
        kFileNames.add("Contact.plist");
        kFileNames.add("Copyright.plist");
        kFileNames.add("Identifiers.plist");
        kFileNames.add("Scales.plist");
        kFileNames.add("Schools.plist");
    }
    public void WriteStringForKey(String kStr, String kKey){
        try {
            System.out.println(kStr);

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(kContext.openFileOutput(kKey, Context.MODE_PRIVATE));
            outputStreamWriter.write(kStr.toString());
            outputStreamWriter.close();
        }
        catch (IOException e){
            Log.e("Exception", "File write Failed: " + e.toString());
        }
    }
    public String ReadFileWithKey(String kStr, Context gContext){

        String ret = "";
        try{
            InputStream kinputStream = gContext.openFileInput(kStr);
            if(kinputStream!=null){
                InputStreamReader kinputStreamReader = new InputStreamReader(kinputStream);
                BufferedReader kbufferedReader = new BufferedReader(kinputStreamReader);
                String kReceievedString = "";
                StringBuilder kstringBuilder = new StringBuilder();
                while((kReceievedString = kbufferedReader.readLine())!=null){
                    kstringBuilder.append(kReceievedString);
                }
                kinputStream.close();
                ret = kstringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }
    public void D_String(String str) throws ExecutionException, InterruptedException {

        Downloader get = new Downloader();
        String g = get.execute(new String[] {kBase + str}).get();
        WriteStringForKey(g, str);
    }
}

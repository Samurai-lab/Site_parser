package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends Activity {

    public Elements content;
    public ArrayList<String> titleList = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.listView1);
        new NewThread().execute();
        adapter = new ArrayAdapter<>(this, R.layout.list_item, R.id.pro_item, titleList);
    }


    public class NewThread extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground (String... arg) {
            Document doc;
            try  {
                doc = Jsoup.connect("https://www.avito.ru/rossiya/vakansii?q=работник").get();
                content = doc.select(".iva-item-content-m2FiN");
                titleList.clear();
                for(Element contents: content) {
                    titleList.add(contents.text());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute (String result) {
            lv.setAdapter(adapter);
    }


    }

}
package io.github.ponnamkarthik.sample;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import io.github.ponnamkarthik.sample.adapters.URLAdapter;

public class ListActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = (ListView) findViewById(R.id.list);

        ArrayList<URL> urls = new ArrayList<>();

        try {
          //  urls.add(new URL("https://twitter.com"));

            urls.add(new URL("https://www.youtube.com/watch?v=aOmV5Wsa5Zc"));
            urls.add(new URL("https://www.amazon.com/"));
            urls.add(new URL("https://staging-www.communityx.tech/posts/0f638050-eece-11ea-8355-c9a0d6b13357"));
            urls.add(new URL("https://skype.com"));
            urls.add(new URL("https://www.flipkart.com/"));
            urls.add(new URL("https://google.com"));
            urls.add(new URL("https://skype.com"));
            urls.add(new URL("https://twitter.com"));
            urls.add(new URL("https://www.flipkart.com/"));
            urls.add(new URL("https://google.com"));
            urls.add(new URL("https://skype.com"));
            urls.add(new URL("https://twitter.com"));
            urls.add(new URL("https://www.flipkart.com/"));
            urls.add(new URL("https://google.com"));
            urls.add(new URL("https://skype.com"));
            urls.add(new URL("https://twitter.com"));

            URLAdapter urlAdapter = new URLAdapter(this, urls);

            listView.setAdapter(urlAdapter);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}

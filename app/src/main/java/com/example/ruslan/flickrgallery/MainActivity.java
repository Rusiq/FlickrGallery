package com.example.ruslan.flickrgallery;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.LruCache;
import android.text.TextUtils;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity {

    private final static String TAG = "MainActivity";
    private ListView listView = null;
    private RequestQueue requestQueue = null;
    private ImageLoader imageLoader = null;
    private LruCache<String, Bitmap> lruCache = null;
    private ImageAdapter adapter = null;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private FragmentManager supportFragmentManager;


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.i(TAG, "onCreate");
        verifyKey();
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.main_listview);
        initVolley();

        RetainFragment retainFragment = RetainFragment.findOrCreateRetainFragment(getSupportFragmentManager());
        lruCache = retainFragment.mRetainedCache;
        if (lruCache == null) {
            lruCache = new LruCache<String, Bitmap>(Config.MEMORY_CACHE_SIZE) {

            };
            retainFragment.mRetainedCache = lruCache;
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public FragmentManager getSupportFragmentManager() {
        return supportFragmentManager;
    }

    public void setSupportFragmentManager(FragmentManager supportFragmentManager) {
        this.supportFragmentManager = supportFragmentManager;
    }


    public static class RetainFragment extends Fragment {
        private static final String TAG = "RetainFragment";
        public LruCache<String, Bitmap> mRetainedCache;

        public RetainFragment() {
        }

        public static RetainFragment findOrCreateRetainFragment(FragmentManager fm) {
            RetainFragment fragment = (RetainFragment) fm.findFragmentByTag(TAG);
            if (fragment == null) {
                fragment = new RetainFragment();
                fm.beginTransaction().add(fragment, TAG).commit();
            }
            return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRetainInstance(true);
        }


   }


    private void verifyKey() {
        if (TextUtils.isEmpty(Config.FLICKR_KEY))
            throw new IllegalArgumentException("Please set flickr key");
    }

    private void initVolley() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        lruCache = new LruCache<String, Bitmap>(Config.MEMORY_CACHE_SIZE) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
        imageLoader = new ImageLoader(requestQueue, new ImageCache() {
            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                lruCache.put(url, bitmap);
            }

            @Override
            public Bitmap getBitmap(String url) {
                return lruCache.get(url);
            }
        });
        String api = String.format(Config.API_URL, Config.FETCH_IMAGE, Config.FLICKR_KEY);
        requestQueue.add(new StringRequest(api, new Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Logger.d(TAG, response);
                    JSONObject json = new JSONObject(response);
                    JSONObject photosJson = json.getJSONObject("photos");
                    adapter = new ImageAdapter(MainActivity.this, imageLoader, photosJson.getJSONArray("photo"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listView.setAdapter(adapter);
            }
        }, null));
    }

    @Override
    protected void onStop() {
        super.onStop();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.ruslan.flickrgallery/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        Logger.i(TAG, "onStop");
        requestQueue.cancelAll(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.disconnect();
    }

    @Override
    protected void onResume() {
        super.onResume();


        Logger.i(TAG, "onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.i(TAG, "onDestory");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Logger.i(TAG, "onPause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Logger.i(TAG, "onRestart");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Logger.i(TAG, "onRestoreInstanceState");

        //     lruCache = (LruCache<String, Bitmap>) savedInstanceState.getSerializable("lru");

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Logger.i(TAG, "onSaveInstanceState");


    }


    @Override
    protected void onStart() {
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Logger.i(TAG, "onStart");
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.ruslan.flickrgallery/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }




}

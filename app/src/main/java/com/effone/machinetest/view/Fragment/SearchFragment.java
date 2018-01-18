package com.effone.machinetest.view.Fragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.effone.machinetest.R;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

public class SearchFragment extends Fragment implements View.OnClickListener {
    private EditText editTextInput;
    private Button searchButton;
    private WebView webView;

    TextView textResult;

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_search, container, false);
        init(view);
        return view;
    }

    private void init(View root) {
        webView=(WebView)root.findViewById(R.id.webView);


        editTextInput = (EditText)root.findViewById(R.id.editTextInput);
        searchButton=(Button)root.findViewById(R.id.button1);
        searchButton.setOnClickListener(this);

    }

    private class CustomWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }


    @Override
    public void onClick(View view) {
        try {/*
            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
            String term = editTextInput.getText().toString();
            intent.putExtra(SearchManager.QUERY, term);
            startActivity(intent);*/
            String item=editTextInput.getText().toString();
            webView.setWebViewClient(new CustomWebViewClient());
            WebSettings webSetting = webView.getSettings();
            webSetting.setJavaScriptEnabled(true);
            webSetting.setDisplayZoomControls(true);
            webView.loadUrl("https://www.google.co.in/search?q="+item);

        } catch (Exception e) {
            // TODO: handle exception
        }
    }


}

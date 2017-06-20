package com.example.tourindia;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ourViewClient extends WebViewClient {

	@Override
	public boolean shouldOverrideUrlLoading(WebView v, String url) {
		// TODO Auto-generated method stub
		v.loadUrl(url);
		return true;
	}

}

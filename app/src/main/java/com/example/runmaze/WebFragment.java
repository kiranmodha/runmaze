package com.example.runmaze;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.runmaze.utils.Settings;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WebFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WebFragment extends Fragment {


    WebView mWebView;
    public String url = "";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WebFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WebFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WebFragment newInstance(String param1, String param2) {
        WebFragment fragment = new WebFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView =  inflater.inflate(R.layout.fragment_web, container, false);


        mWebView = (WebView) fragmentView.findViewById(R.id.webview);


        // Enable Javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        // Force links and redirects to open in the WebView instead of in a browser
        mWebView.setWebViewClient(new WebViewClient()
                                  {
//                                      public void onReceivedError(WebView view, WebResourceRequest request,
//                                                                  WebResourceError error) {
//                                          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                                              Toast.makeText(getActivity(),
//                                                      "WebView Error" + error.getDescription(),
//                                                      Toast.LENGTH_SHORT).show();
//                                          }
//                                          super.onReceivedError(view, request, error);
//                                      }
                                      public void onReceivedHttpError(WebView view,
                                                                      WebResourceRequest request, WebResourceResponse errorResponse) {

                                          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                                              Toast.makeText(getActivity(),
//                                                      "WebView Error " + errorResponse.getReasonPhrase(),
//                                                      Toast.LENGTH_SHORT).show();
                                          }
                                          super.onReceivedHttpError(view, request, errorResponse);
                                      }
                                  }
        );


        show();
        return fragmentView;

    }


    public void show()
    {
        mWebView.loadUrl(url);
    }
}
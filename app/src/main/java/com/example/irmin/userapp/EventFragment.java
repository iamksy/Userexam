package com.example.irmin.userapp;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class EventFragment extends Fragment implements View.OnClickListener{

    private static final String TAG_JSON = "webnautes";
//    private static final String TAG_NUM = "eventNum";
    private  static final String TAG_CATEGORY = "category";
    private static final String TAG_TEL = "userTel";
    private static final String TAG_AREA = "userArea";
    private static final String TAG_ADD = "userAddress";
    private static final String TAG_TITLE = "eventTitle";
    private static final String TAG_CONTENT = "eventContent";
    private static final String TAG_START = "startTime";
    private static final String TAG_CLOSE = "closeTime";
    private static final String TAG_AMOUNT = "amount";
//    private static final String TAG_IMG = "eventImg";

    long now = System.currentTimeMillis()+32400000;

    SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String strNow = sdfNow.format(new Date(now));

    ArrayList<HashMap<String, String>> eventList;

    ListView list;
    String myJSON;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        String userArea = getActivity().getTitle().toString();

        View view = inflater.inflate(R.layout.fragment_event, container, false);
        list = (ListView) view.findViewById(R.id.eventListView);
        eventList = new ArrayList<>();


        GetData task = new GetData();
        try {
            task.execute("http://irmin95.cafe24.com/EventListUser.php?userArea=" + URLEncoder.encode(userArea,"UTF-8") + "&now=" + URLEncoder.encode(strNow,"UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

private class GetData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                progressDialog = ProgressDialog.show(getContext(),
                        "Please Wait", null, true, true);
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            myJSON = result;
            showList();

        }

        @Override
        protected String doInBackground(String... params) {

            String uri = params[0];

            try {

                URL url = new URL(uri);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();
                int responseStatusCode = httpURLConnection.getResponseCode();

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                bufferedReader.close();
                return sb.toString().trim();
            } catch (Exception e) {
                errorString = e.toString();
                return null;
            }
        }
    }

    protected void showList() {

        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            JSONArray ja = jsonObj.getJSONArray(TAG_JSON);

            for (int i = 0; i < ja.length(); i++) {
                JSONObject c = ja.getJSONObject(i);

//                String num = c.optString(TAG_NUM);
                String cate = c.optString(TAG_CATEGORY);
                String tel = c.optString(TAG_TEL);
                String area = c.optString(TAG_AREA);
                String add = c.optString(TAG_ADD);
                String title = c.optString(TAG_TITLE);
                String content = c.optString(TAG_CONTENT);
                String start = c.optString(TAG_START);
                String close = c.optString(TAG_CLOSE);
                String amount = c.optString(TAG_AMOUNT);
//                String img = c.optString(TAG_IMG);

                HashMap<String, String> list = new HashMap<>();

//                list.put(TAG_NUM, num);
                list.put(TAG_CATEGORY, cate);
                list.put(TAG_TEL,tel);
                list.put(TAG_AREA, area);
                list.put(TAG_ADD,add);
                list.put(TAG_TITLE, title);
                list.put(TAG_CONTENT, content);
                list.put(TAG_START, start);
                list.put(TAG_CLOSE, close);
                list.put(TAG_AMOUNT, amount);
//                list.put(TAG_IMG, img);

                eventList.add(list);
            }

            ListAdapter adapter = new SimpleAdapter(
                    getActivity(), eventList, R.layout.event,
                    new String[]{ TAG_CATEGORY, TAG_ADD, TAG_TEL,TAG_AMOUNT, TAG_TITLE, TAG_CONTENT, TAG_START, TAG_CLOSE},
                    new int[]{ R.id.Category, R.id.Add, R.id.Tel ,R.id.amount, R.id.eventTitle, R.id.eventContent, R.id.startTime, R.id.closeTime}
            );


            list.setAdapter(adapter);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(getContext(), openEvent.class);

                    String category = eventList.get(position).get(TAG_CATEGORY);
                    String title = eventList.get(position).get(TAG_TITLE);
                    String content = eventList.get(position).get(TAG_CONTENT);
                    String amount = eventList.get(position).get(TAG_AMOUNT);
                    String add = eventList.get(position).get(TAG_ADD);
                    String tel = eventList.get(position).get(TAG_TEL);

                    intent.putExtra("cate2", category);
                    intent.putExtra("tel2", tel);
                    intent.putExtra("add2", add);
                    intent.putExtra("title2", title);
                    intent.putExtra("content2",content);
                    intent.putExtra("amount2", amount);
                    startActivity(intent);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
    }
}


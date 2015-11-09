package com.arshad.expense;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ShowListActivity extends ListActivity {

    private ProgressDialog pDialog;

    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> expenseList;

    private static String url_all_products = "";

    private static final String TAG_SUCCESS = "success";

    JSONArray expense = null;
    String filter;
    String expenseType, date, expenseName, expenseAmount;
    List<NameValuePair> params;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showlist);

        expenseList = new ArrayList<HashMap<String, String>>();

        filter = getIntent().getStringExtra("filter");
        if(filter.equals("type")){
            expenseType = getIntent().getStringExtra("expenseType");
            url_all_products = "http://kaspat.com/android/expense_type.php";
            params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("type", expenseType));
        }
        else if(filter.equals("date")){
            date = getIntent().getStringExtra("date");
            url_all_products = "http://kaspat.com/android/expense_date.php";
            params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("date", date));
        }
        else if(filter.equals("name")){
            expenseName = getIntent().getStringExtra("name");
            url_all_products = "http://kaspat.com/android/expense_name.php";
            params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("name", expenseName));
        }
        else if(filter.equals("amount")){
            expenseAmount = getIntent().getStringExtra("amount");
            url_all_products = "http://kaspat.com/android/expense_amount.php";
            params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("amount", expenseAmount));
        }
        else if(filter.equals("all")){
            url_all_products = "http://kaspat.com/android/expense.php";
            params = new ArrayList<NameValuePair>();
        }

        new LoadAllExpense().execute();

        ListView lv = getListView();

        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 100) {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }

    }

    class LoadAllExpense extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ShowListActivity.this);
            pDialog.setMessage("Loading Expense. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {

            JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);

            Log.d("Expense: ", json.toString());

            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    expense = json.getJSONArray("expense");
                    for (int i = 0; i < expense.length(); i++) {
                        JSONObject c = expense.getJSONObject(i);
                        Log.d("Expense2: ", expense.toString());

                        int id = c.getInt("id");
                        String type = c.getString("type");
                        String date = c.getString("date");
                        String name = c.getString("name");
                        String amount = c.getString("amount");

                        HashMap<String, String> map = new HashMap<String, String>();

                        map.put("id", id+"");
                        map.put("type", type);
                        map.put("date", date);
                        map.put("name", name);
                        map.put("amount", amount);

                        expenseList.add(map);
                    }
                } else {
                    Intent i = new Intent(getApplicationContext(), ViewActivity.class);
                    startActivity(i);
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    ListAdapter adapter = new SimpleAdapter(
                            ShowListActivity.this, expenseList,
                            R.layout.list_expense, new String[] { "id",
                            "type", "date", "name", "amount"},
                            new int[] { R.id.id, R.id.type, R.id.date, R.id.name, R.id.amount });
                    setListAdapter(adapter);
                }
            });

        }

    }
}
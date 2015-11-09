package com.arshad.expense;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


public class AddExpenseActivity extends Activity {

    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();
    String type, date, name, amount;

    private static String url_create_product = "http://kaspat.com/android/insert_expense.php";

    private static final String TAG_SUCCESS = "success";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_expense);

        type = getIntent().getStringExtra("type");
        date = getIntent().getStringExtra("date");
        name = getIntent().getStringExtra("name");
        amount = getIntent().getStringExtra("amount");

        new AddNewExpense().execute();
    }

    class AddNewExpense extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AddExpenseActivity.this);
            pDialog.setMessage("Adding Expense..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... args) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("type", type));
            params.add(new BasicNameValuePair("date", date));
            params.add(new BasicNameValuePair("name", name));
            params.add(new BasicNameValuePair("amount", amount));

            JSONObject json = jsonParser.makeHttpRequest(url_create_product,
                    "GET", params);

            Log.d("Create Response", json.toString());

            try {
                int success = json.getInt(TAG_SUCCESS);
                if(success == 1)
                    Toast.makeText(getApplicationContext(), "Expense added successfully", Toast.LENGTH_SHORT).show();

                if (success == 1) {
                    Intent i = new Intent(getApplicationContext(), FirstActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Expense adding failed", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
        }

    }
}

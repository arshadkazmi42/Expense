package com.arshad.expense;

import android.content.Intent;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;


public class MainActivity extends Activity implements OnItemSelectedListener{

    String expenseType;
    String d, n, amt;
    EditText date, name, amount;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        date = (EditText)findViewById(R.id.date);
        name = (EditText)findViewById(R.id.name);
        amount = (EditText)findViewById(R.id.amount);
        submit = (Button)findViewById(R.id.submit);

        spinner.setOnItemSelectedListener(this);

        List <String> categories = new ArrayList<String>();
//        categories.add("Expense Type");
        categories.add("Travel");
        categories.add("Entertainment");
        categories.add("Food");
        categories.add("Marketing");
        categories.add("Advertisement");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);

        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                d = date.getText().toString();
                n = name.getText().toString();
                amt = amount.getText().toString();
                if(d.equals("") || n.equals("") || amt.equals("") || expenseType.equals(""))
                    Toast.makeText(getApplicationContext(), "All fields are mandatory", Toast.LENGTH_SHORT).show();
                else {
                    Intent i = new Intent(getApplicationContext(), AddExpenseActivity.class);
                    i.putExtra("type", expenseType);
                    i.putExtra("date", d);
                    i.putExtra("name", n);
                    i.putExtra("amount", amt);
                    startActivity(i);
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String item = parent.getItemAtPosition(position).toString();
        expenseType = item;
        //Toast.makeText(getApplicationContext(),expenseType,Toast.LENGTH_SHORT).show();

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

}

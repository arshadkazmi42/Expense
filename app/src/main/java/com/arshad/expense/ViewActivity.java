package com.arshad.expense;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import android.widget.AdapterView.OnItemSelectedListener;

public class ViewActivity extends Activity implements OnItemSelectedListener {

    String expenseType = "";
    EditText date, name, amount;
    Button searchType, searchDate, searchName, searchAmount, viewAll;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        date = (EditText)findViewById(R.id.date);
        name = (EditText)findViewById(R.id.name);
        amount = (EditText)findViewById(R.id.amount);
        searchType = (Button)findViewById(R.id.searchType);
        searchDate = (Button)findViewById(R.id.searchDate);
        searchName = (Button)findViewById(R.id.searchName);
        searchAmount = (Button)findViewById(R.id.searchAmount);
        viewAll = (Button)findViewById(R.id.viewAll);

        spinner.setOnItemSelectedListener(this);

        List<String> categories = new ArrayList<String>();
        categories.add("Expense Type");
        categories.add("Travel");
        categories.add("Entertainment");
        categories.add("Food");
        categories.add("Marketing");
        categories.add("Advertisement");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        searchType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expenseType.equals(""))
                    Toast.makeText(getApplicationContext(), "Select Expense Type", Toast.LENGTH_SHORT).show();
                else {
                    Intent i = new Intent(getApplicationContext(), ShowListActivity.class);
                    i.putExtra("filter", "type");
                    i.putExtra("expenseType", expenseType);
                    startActivity(i);
                }
            }
        });

        searchDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String d;
                d = date.getText().toString();
                if(d.equals(""))
                    Toast.makeText(getApplicationContext(), "Enter date range", Toast.LENGTH_SHORT).show();
                else{
                    Intent i = new Intent(getApplicationContext(), ShowListActivity.class);
                    i.putExtra("filter", "date");
                    i.putExtra("date", d);
                    startActivity(i);
                }
            }
        });

        searchName.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String n = name.getText().toString();
                if(n.equals(""))
                    Toast.makeText(getApplicationContext(), "Enter name", Toast.LENGTH_SHORT).show();
                else{
                    Intent i = new Intent(getApplicationContext(), ShowListActivity.class);
                    i.putExtra("filter", "name");
                    i.putExtra("name", n);
                    startActivity(i);
                }
            }
        });

        searchAmount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String amt = amount.getText().toString();
                if(amt.equals(""))
                    Toast.makeText(getApplicationContext(), "Enter some amount", Toast.LENGTH_SHORT).show();
                else{
                    Intent i = new Intent(getApplicationContext(), ShowListActivity.class);
                    i.putExtra("filter", "amount");
                    i.putExtra("amount", amt);
                    startActivity(i);
                }
            }
        });

        viewAll.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ShowListActivity.class);
                i.putExtra("filter","all");
                startActivity(i);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String item = parent.getItemAtPosition(position).toString();

        if(!item.equals("Expense Type"))
            expenseType = item;

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

}

package com.example.currencyconverter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText amountInput;
    private Spinner inputSpinner;
    private Spinner outputSpinner;
    private TextView resultText;
    private TextView dateText;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        amountInput = findViewById(R.id.amount);
        amountInput.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        inputSpinner = (Spinner) findViewById(R.id.input);
        outputSpinner = (Spinner) findViewById(R.id.output);
        resultText = findViewById(R.id.result_text);
        dateText = findViewById(R.id.date_text);
        //input spinner
        ArrayAdapter<CharSequence> Adapter = ArrayAdapter.createFromResource(this, R.array.currencies, android.R.layout.simple_spinner_item);
        Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputSpinner.setAdapter(Adapter);

        //output spinner
        Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        outputSpinner.setAdapter(Adapter);

        inputSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //do nothing
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //do nothing
            }
        });

        outputSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                convertCurrency();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing
            }
        });

    }
    protected double any_to_dollar(String input, Double amt, HashMap<String, Double> map)
    {
        Double val=map.get(input);
        assert val != null;
        return (1/val)*amt;
    }
    protected static double dollar_to_any(String output, Double amt, HashMap<String, Double> map)
    {
        Double val=map.get(output);
        return val*(amt);
    }
    private double currencyConverter(String inputCurrency, String outputCurrency, Double amount, HashMap<String,Double> rates){
        Log.d("check input",inputCurrency);
        Log.d("check output",outputCurrency);
        if(inputCurrency.equals(outputCurrency)){
            return amount;
        }
        double indollar=any_to_dollar(inputCurrency, amount,rates);
        double dollarto=dollar_to_any(outputCurrency, indollar, rates);
        return dollarto;
    }

    private void convertCurrency(){
        String inputCurrency=inputSpinner.getSelectedItem().toString();
        String outputCurrency=outputSpinner.getSelectedItem().toString();
        String amountText=amountInput.getText().toString();

        if (!amountText.isEmpty()) {
            double amount = Double.parseDouble(amountText);

            if (amount > 0) {
                ExchangeRateApiClient.load(new ExchangeRateApiClient.LoadCallback() {
                    @Override
                    public void onLoaded(HashMap<String, Double> rates, String date) {
                        if (rates != null && date != null) {
                            double result = currencyConverter(inputCurrency, outputCurrency, amount, rates);
                            if (result != -1) {
                                runOnUiThread(() -> {
                                    resultText.setText(result + "" + outputCurrency);
                                    dateText.setText("Last Updated: "+date);
                                });
                            } else {
                                runOnUiThread(() -> resultText.setText("Invalid input"));
                            }
                        } else {
                            runOnUiThread(() -> Toast.makeText(MainActivity.this, "Failed to fetch exchange rates!", Toast.LENGTH_SHORT).show());
                        }
                    }
                });
            }
            else {
                Toast.makeText(this, "Amount must be greater than 0", Toast.LENGTH_SHORT).show();
            }
        }

    }

}

//Maryna Yesakova Calculator
package com.example.dexat.calculator;

import java.math.BigDecimal;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import java.math.RoundingMode;

public class MarinaCalculator extends AppCompatActivity {

    private final static String LOGTAG = "MarinaCalculator";
    //constants for Bundle class to retrieve the date
    private final static String KEY_CURRENT_ENTRY = "currentEntry";
    private final static String KEY_ACCUMULATOR = "accumulator";
    private final static String KEY_OPCODE = "opcode";

    private TextView currentEntry;
    private TextView accumulator;
    private TextView opcode;
    private boolean pressedEqualSign = false;

    //initialize MarinaCalculator
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //calling super constructor
        super.onCreate(savedInstanceState);
        //shows the activity_main layout
        setContentView(R.layout.activity_main);
        Log.d(LOGTAG, "...onCreate...");
        //find in layout widget currentEntry
        currentEntry = (TextView)findViewById(R.id.currentEntry);
        //find in layout widget accumulator
        accumulator = (TextView)findViewById(R.id.accumulator);
        //find in layout widget opcode
        opcode = (TextView)findViewById(R.id.opcode);
        // if the savedInstanceState bundle contains any data, then reinstate the text field of the EditText widget
        if (savedInstanceState != null) {
            String savedString = "";
            savedString = savedInstanceState.getString(KEY_CURRENT_ENTRY, "<default>");
            currentEntry.setText(savedString);
            savedString = savedInstanceState.getString(KEY_ACCUMULATOR, "<default>");
            accumulator.setText(savedString);
            savedString = savedInstanceState.getString(KEY_OPCODE, "<default>");
            opcode.setText(savedString);
        }
    }

    // onSaveInstanceState methods to save the contents of the EditText widget
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        String stringToSave = currentEntry.getText().toString();
        savedInstanceState.putString(KEY_CURRENT_ENTRY, stringToSave);
        stringToSave = accumulator.getText().toString();
        savedInstanceState.putString(KEY_ACCUMULATOR, stringToSave);
        stringToSave = opcode.getText().toString();
        savedInstanceState.putString(KEY_OPCODE, stringToSave);
    }

    // onClick gets called when the button is clicked on. Log.d call in the onClick handler so that it outputs a string to logcat
    protected void onClick(View v) {
        Log.d(LOGTAG, "...onButtonClick...");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOGTAG, "...onStart...");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOGTAG, "...onResume...");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOGTAG, "...onPause...");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOGTAG, "...onStop...");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOGTAG, "...onDestroy...");
    }

    //display numbers
    protected void onNumericClickHandler(View v) {
        int num = 0;
        int id = v.getId();
        if (pressedEqualSign == true){
            currentEntry.setText("0");
            pressedEqualSign = false;
        }
        switch (id)
        {
            case R.id.button_9:
                num = 9;
                break;
            case R.id.button_8:
                num = 8;
                break;
            case R.id.button_7:
                num = 7;
                break;
            case R.id.button_6:
                num = 6;
                break;
            case R.id.button_5:
                num = 5;
                break;
            case R.id.button_4:
                num = 4;
                break;
            case R.id.button_3:
                num = 3;
                break;
            case R.id.button_2:
                num = 2;
                break;
            case R.id.button_1:
                num = 1;
                break;
            case R.id.button_0:
                num = 0;
                break;
            default:
                throw new IllegalArgumentException("Invalid button: "+ id);
        }
        Log.d(LOGTAG, "onNumericClickHandler: " + num);
        String currentText = currentEntry.getText().toString();
        //if the number is following by default 0 set currentText to ""
        if (currentText.equals("0")){
            currentText = ("");
        }
        //display current number which was pressed
        currentText = currentText + num;
        currentEntry.setText(currentText);
    }

    //Ce set all fields to 0
    protected void onCeClickHandler(View v) {
        Log.d(LOGTAG, "onCeClickHandler");
        accumulator.setText("");
        currentEntry.setText("0");
        opcode.setText("");
    }

    //Clr set currentEntry to 0
    protected void onClrClickHandler(View v) {
        Log.d(LOGTAG, "onClrClickHandler");
        currentEntry.setText("0");
    }

    //Delete
    protected void onDelClickHandler(View v) {
        Log.d(LOGTAG, "onDelClickHandler");
        String currentString = currentEntry.getText().toString();
        if (!currentString.isEmpty()){
        //display number without default 0
        currentString = currentString.substring(0, currentString.length()-1);
        currentEntry.setText(currentString);
        }
    }

    //opcode
    protected void onOpcodeClickHandler(View v) {
        //hold opcode
        String op;
        int id = v.getId();
        switch (id)
        {
            case R.id.button_divide:
                op = "/";
                break;
            case R.id.button_multiply:
                op = "*";
                break;
            case R.id.button_minus:
                op = "-";
                break;
            case R.id.button_plus:
                op = "+";
                break;
            default:
                throw new IllegalArgumentException("Invalid button: "+ id);
        }
        Log.d(LOGTAG, "onOpcodeClickHandler: " + op);
        //calling calculate method
        calculate();
        opcode.setText(op);
        //display 0 after pressing button of any operation
        currentEntry.setText("0");
    }

    //equal
    protected void onEqualClickHandler(View v) {
        Log.d(LOGTAG, "onEqualClickHandler");
        calculate();
        String currentString = accumulator.getText().toString();
        currentEntry.setText(currentString);
        //when displayed the answer after pressing equal sign set opcode and accum to ""
        opcode.setText("");
        accumulator.setText("");
        pressedEqualSign = true;
    }

    //calculations
    private void calculate() {
        //get currentEntryString
        String currentEntryString = currentEntry.getText().toString();
        //get currentAccum
        String currentAccum = accumulator.getText().toString();
        //get currentOpcode
        String currentOpcode = opcode.getText().toString();
        Log.d(LOGTAG, "calculate()" +"currentEntryString"+ currentEntryString+"currentAccum" + currentAccum +"currentOpcode"+currentOpcode);
        //hold the the result of opearations
        BigDecimal result = BigDecimal.ZERO;
        //check that accum and current entry is not equal null
        if (!currentAccum.isEmpty() && !currentEntryString.isEmpty()) {
            //calculations
            switch (currentOpcode) {
                case "-":
                    result = new BigDecimal(currentAccum).subtract(new BigDecimal(currentEntryString));
                    break;
                case "+":
                    result = new BigDecimal(currentAccum).add(new BigDecimal(currentEntryString));
                    break;
                case "/":
                    if(!currentEntryString.equals("0"))
                    result = new BigDecimal(currentAccum).divide(new BigDecimal(currentEntryString),2, RoundingMode.HALF_UP);
                    break;
                case "*":
                    result = new BigDecimal(currentAccum).multiply(new BigDecimal(currentEntryString));
                    break;
                default:
                    throw new IllegalArgumentException("Invalid op: " + currentOpcode);
            }
            //set result into accum
            accumulator.setText(result.toString());
        }
        else{
            //set current entry into accum
            accumulator.setText(currentEntryString);
        }
    }

    //decimal
    protected void onDecimalPointClickHandler(View v) {
        Log.d(LOGTAG, "onDecimalPointClickHandler");
        String currentText = currentEntry.getText().toString();
        if (!currentText.contains(".") ){
            currentText = currentText + ".";
            currentEntry.setText(currentText);
        }
    }
}
package pl.tkaczyk.examplecalculator;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText result;
    private EditText newNumber;
    private TextView displayOperation;

    private Double operand1 = null;
    private String pendingOperation = "=";

    private final String pendingOperationSave = "=";
    private final  String operand1Save = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = findViewById(R.id.result);
        newNumber = findViewById(R.id.newNumber);
        displayOperation = findViewById(R.id.operation);

        Button button0 = findViewById(R.id.button0);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);
        Button button7 = findViewById(R.id.button7);
        Button button8 = findViewById(R.id.button8);
        Button button9 = findViewById(R.id.button9);

        Button buttonDot = findViewById(R.id.buttonDot);
        Button buttonEquals = findViewById(R.id.buttonEquals);
        Button buttonDivide = findViewById(R.id.buttonDivide);
        Button buttonPlus = findViewById(R.id.buttonPlus);
        Button buttonMultiply = findViewById(R.id.buttonMultiply);
        Button buttonMinus = findViewById(R.id.buttonMinus);

        View.OnClickListener listener = v -> {
            Button b = (Button) v;
            newNumber.append(b.getText().toString());
        };
        button0.setOnClickListener(listener);
        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);
        button5.setOnClickListener(listener);
        button6.setOnClickListener(listener);
        button7.setOnClickListener(listener);
        button8.setOnClickListener(listener);
        button9.setOnClickListener(listener);
        buttonDot.setOnClickListener(listener);

        View.OnClickListener opListener = v -> {
            Button b = (Button) v;
            String op = b.getText().toString();
            String value = newNumber.getText().toString();
            try {
                Double doubleValue = Double.valueOf(value);
                performOperation(doubleValue, op);
            } catch (NumberFormatException e) {
                newNumber.setText("");
            }
            pendingOperation = op;
            displayOperation.setText(pendingOperation);
        };

        buttonEquals.setOnClickListener(opListener);
        buttonPlus.setOnClickListener(opListener);
        buttonMinus.setOnClickListener(opListener);
        buttonDivide.setOnClickListener(opListener);
        buttonMultiply.setOnClickListener(opListener);

        Button buttonNeg = findViewById(R.id.buttonNeg);
        buttonNeg.setOnClickListener(v -> {
            String value = newNumber.getText().toString();
            if(value.length() == 0){
                newNumber.setText("-");
            }else{
                try{
                    Double doubleValue = Double.valueOf(value);
                    doubleValue *= -1;
                    newNumber.setText(doubleValue.toString());
                } catch (NumberFormatException e){
                    // newNumber to - lub .
                    newNumber.setText("");
                }
            }
        });

        Button buttonClear = findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener(v ->{
            result.append("");
            newNumber.append("");
            displayOperation.setText("C");
            
        });

    }

    private void performOperation(Double value, String op) {
        if (operand1 == null) {
            operand1 = value;
        } else {
            if (pendingOperation.equals("=")) {
                pendingOperation = op;
            }
            switch (pendingOperation) {
                case "=":
                    operand1 = value;
                    break;
                case "/":
                    if (value == 0) {
                        operand1 = 0.0;
                    } else {
                        operand1 /= value;
                    }
                    break;
                case "*":
                    operand1 *= value;
                    break;
                case "+":
                    operand1 += value;
                    break;
                case "-":
                    operand1 -= value;
                    break;
            }
        }
        result.setText(operand1.toString());
        newNumber.setText("");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        pendingOperation = savedInstanceState.getString(pendingOperationSave);
        operand1 = savedInstanceState.getDouble(operand1Save);
        displayOperation.setText(pendingOperation);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(pendingOperationSave, pendingOperation);
        if(operand1 != null){
            outState.putDouble(operand1Save, operand1);
        }
        super.onSaveInstanceState(outState);
    }
}
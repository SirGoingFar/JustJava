package com.eemf.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import static android.R.attr.name;
import static android.R.attr.order;

//This app displays an order form to order coffee
public class MainActivity extends AppCompatActivity {

    int qty = 1;
    CheckBox whippedCreamCheckBox;
    CheckBox chocolateCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        whippedCreamCheckBox = (CheckBox) findViewById(R.id.whippedcream_checkbox);
        chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
    }

    //This method is called when the + button is clicked
    public void increment(View view){
        qty = qty + 1;
        displayQuantity(qty);
    }

   //This method is called when the + button is clicked
    public void decrement(View view){
        //check if the quantity is less than 1 before decrementing
        if(qty > 1){
            qty = qty - 1;
            displayQuantity(qty);
        }else {
            //show a toast
            Toast message = Toast.makeText(this, "You cannot order less than a cup of coffee", Toast.LENGTH_SHORT);
            message.show();
        }

        displayQuantity(qty);


    }

    //This method is called when the order button is clicked
    public void submitOrder(View view){

        String[] checkBoxesOption = {"NO", "NO"};

        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        //check if the checkbox is checked or not; checked implies YES, unchecked implies NO
        checkBoxesOption[0] = (whippedCreamCheckBox.isChecked() ? "YES" : "NO");
        checkBoxesOption[1] = (chocolateCheckBox.isChecked() ? "YES" : "NO");

        int price = calculatePrice(checkBoxesOption);

        String message = createOrderSummary(price, checkBoxesOption, name);

        if(name.isEmpty()){
            //show a toast
            Toast toastMessage = Toast.makeText(this, "Enter your name first", Toast.LENGTH_SHORT);
            toastMessage.show();
        }else{
            Intent mailIntent = new Intent(Intent.ACTION_SENDTO);
                   mailIntent.setData(Uri.parse("mailto: akintunde_olanrewaju@yahoo.co.uk"));
                   mailIntent.putExtra(Intent.EXTRA_SUBJECT, "Coffee Ordering App of " + name);
                   mailIntent.putExtra(Intent.EXTRA_TEXT, message);

                   if(mailIntent.resolveActivity(getPackageManager()) != null){
                       startActivity(mailIntent);
                   }
        }
    }

    private int calculatePrice(String[] hasTopping) {
        //initial price of coffee without toppings
        int price = 5;

        //if whipped cream is to be added as topping
        if(hasTopping[0].equals("YES"))
            price += 1;

        //if chocolate is to be added as topping
        if(hasTopping[1].equals("YES"))
            price += 2;

        //total price of the entire quantity(ies) ordered
        return (qty * price);
    }

    private String createOrderSummary(int orderPrice, String[] hasToppings, String Name){

        return "Name: " + (Name.isEmpty() ? "(Error) Enter your name" : Name) + "\n\nAdd Whipped Cream: " + hasToppings[0] + "\n\nAdd Chocolate: " + hasToppings[1] + "\n\nQuantity: " + qty + " \n\nPrice: $" + orderPrice + " \n\nThank you!";
    }

    //This method is called when the order button is clicked
    public void resetFields(View view) {
        whippedCreamCheckBox.setChecked(false);
        chocolateCheckBox.setChecked(false);
        qty = 1;
        displayQuantity(qty);
    }

    //This method updates the quantity text view
    public void displayQuantity(int number){
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
                 quantityTextView.setText(""+number);
    }

}































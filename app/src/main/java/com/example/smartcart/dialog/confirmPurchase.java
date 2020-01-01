package com.example.smartcart.dialog;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.smartcart.R;
import com.kusu.loadingbutton.LoadingButton;

import java.util.List;

public class confirmPurchase extends Dialog implements  android.view.View.OnClickListener {

    public Activity c;
    private String address , total;
    private Button buy, back;

    public confirmPurchase(Activity a) {
        super(a);
        this.c = a;
    }

    public void setValues(String name, String address, String total){
        this.address = address;
        this.total = total;

        EditText txt_address = findViewById(R.id.address);
        TextView txt_money = findViewById(R.id.money);
        TextView txt_name = findViewById(R.id.name);

        txt_name.setText(name+"");
        txt_address.setText(address +"");
        txt_money.setText(total+"");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_purchase);

        buy = findViewById(R.id.buy);
        back = findViewById(R.id.back);

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> data, Menu menu, int deviceId) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void disableButtons() {
        back.setEnabled(false);
        buy.setEnabled(false);
        LoadingButton loadingButton = (LoadingButton) buy;
        loadingButton.showLoading();


    }

    public String getAddress() {
        EditText t = findViewById(R.id.address);
        return t.getText().toString();
    }
}

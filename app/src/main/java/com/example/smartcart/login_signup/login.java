package com.example.smartcart.login_signup;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcart.MainActivity;
import com.example.smartcart.R;
import com.example.smartcart.controller;
import com.example.smartcart.userBoard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.kusu.loadingbutton.LoadingButton;

import static android.content.Context.MODE_PRIVATE;


public class login extends Fragment {
    View root;
    private FirebaseAuth mAuth;
    Button login_button;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        root = inflater.inflate(R.layout.fragment_login, container, false);
        login_button = (Button)root.findViewById(R.id.login_button);
        login_button.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {

                        EditText txt_email = root.findViewById(R.id.email);
                        EditText txt_password = root.findViewById(R.id.password);

                        String email = txt_email.getText().toString().trim();
                        String password = txt_password.getText().toString().trim();

                        if (email.length() == 0) {controller.toast(getContext(),"Enter your email"); txt_email.requestFocus(); showKeyboard(txt_email);}
                        else if (password.length() == 0) {controller.toast(getContext(), "Enter your password"); txt_password.requestFocus(); showKeyboard(txt_password);}
                        else{
                            LoadingButton loadingButton = (LoadingButton) login_button; loadingButton.showLoading();
                            login_button.setEnabled(false);

                            connect(email, password);
                        }
                    }
                });

        return root;
    }


     public void connect(final String email, String password) {


         mAuth.signInWithEmailAndPassword(email, password)
                 .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {
                         if (task.isSuccessful()) {
                             FirebaseUser user = mAuth.getCurrentUser();
                             final String E = email;
                         /*    controller c = new controller(new MainActivity());
                             c.getUserInfo(); // download user's info*/
                             DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                             mAuth = FirebaseAuth.getInstance();

                /*             SharedPreferences mPrefs =  MainActivity.getContextOfApplication().getSharedPreferences("myUser", getContext().MODE_PRIVATE);
                             SharedPreferences.Editor prefsEditor = mPrefs.edit();
                             Gson gson = new Gson();
                             String json = gson.toJson(new userBoard("0", "fhjg", "fghgf", "Ffff"));
                             prefsEditor.putString("myUser", json);
                             prefsEditor.commit();
*/


                             controller.toast(getContext(), "Welcome back " + E);
                             getActivity().finish();
                         } else {
                             // If sign in fails, display a message to the user.
                          controller.toast(getContext(), "Password/email is wrong");
                          login_button.setEnabled(true);

                          LoadingButton loadingButton = (LoadingButton) login_button; loadingButton.hideLoading();


                         }

                     }
                 });

    }

    private void showKeyboard(View what) {
        what.requestFocus();
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(what, InputMethodManager.SHOW_IMPLICIT);
    }
}

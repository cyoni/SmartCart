package com.example.smartcart.login_signup;

import android.content.Context;
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
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcart.R;
import com.example.smartcart.controller;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class signup extends Fragment {
    private FirebaseAuth mAuth;
    View root;
     Button signup_button;

    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_signup, container, false);
        mAuth = FirebaseAuth.getInstance();

         signup_button = (Button)root.findViewById(R.id.signup_button);

        signup_button.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        EditText txt_name = root.findViewById(R.id.name);
                        EditText txt_email = root.findViewById(R.id.email);
                        EditText txt_password = root.findViewById(R.id.password);
                        EditText txt_address = root.findViewById(R.id.address);

                        String username = txt_name.getText().toString().trim();
                        String email = txt_email.getText().toString().trim();
                        String password = txt_password.getText().toString().trim();
                        String address = txt_address.getText().toString().trim();


                        if (username.length() == 0) {controller.toast(getContext(),"Enter your name");txt_name.requestFocus();  showKeyboard(txt_name);}
                        else if (email.length() == 0) {controller.toast(getContext(),"Enter your email"); txt_email.requestFocus();showKeyboard(txt_email);}
                        else if (password.length() == 0) {controller.toast(getContext(), "Choose a password"); txt_password.requestFocus();showKeyboard(txt_password);}
                        else if (address.length() == 0) {controller.toast(getContext(), "Enter your address"); showKeyboard(txt_address);}
                        else{

                        signup_button.setEnabled(false);
                        signup_button.setText("SIGNING UP..");


                        createAccount(email, password);


                        }
                    }
                });
        return root;
    }

    private void createAccount(final String email, String password) {


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                        //    Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            controller.toast(getContext(), "Welcome " + email + "!");
                            getActivity().finish();
                        } else {
                            Log.w(getTag(), "createUserWithEmail:failure", task.getException());
                        controller.toast(getContext(), "Sorry. We could not create your account right now");
                       signup_button.setText("Sign up");
                       signup_button.setEnabled(true);
                        }

                        // ...
                    }
                });
    }

    private void showKeyboard(View what) {
        what.requestFocus();
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(what, InputMethodManager.SHOW_IMPLICIT);
    }


}

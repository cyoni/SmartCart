package com.example.smartcart.manager_login_signup;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.smartcart.R;
import com.example.smartcart.controller;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


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
                            login_button.setEnabled(false);
                            login_button.setText("Logging in..");
                            connect(email, password);
                        }
                    }
                });

        return root;
    }


    public void connect(final String email, String password) {
/// CHECK IF THIS ACCOUNT IS CLASSIFIED AS ADMIN

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            final String E = email;
                            controller.toast(getContext(), "Welcome back " + E);
                            getActivity().finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            controller.toast(getContext(), "Password/email is wrong");
                            login_button.setEnabled(true);
                            login_button.setText("Log in");
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

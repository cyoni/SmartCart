package com.example.smartcart.login_signup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

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

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


public class signup extends Fragment {
    private FirebaseAuth mAuth;
    private View root;
    private Button signup_button;
    private userBoard _user;
    private String username="",email="",password="",address="";

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

                         username = txt_name.getText().toString().trim();
                         email = txt_email.getText().toString().trim();
                         password = txt_password.getText().toString().trim();
                         address = txt_address.getText().toString().trim();


                        if (username.length() == 0) {controller.toast(getContext(),"Enter your name");txt_name.requestFocus();  showKeyboard(txt_name);}
                        else if (email.length() == 0) {controller.toast(getContext(),"Enter your email"); txt_email.requestFocus();showKeyboard(txt_email);}
                        else if (password.length() == 0) {controller.toast(getContext(), "Choose a password"); txt_password.requestFocus();showKeyboard(txt_password);}
                        else if (address.length() == 0) {controller.toast(getContext(), "Enter your address"); showKeyboard(txt_address);}
                        else{
                        signup_button.setEnabled(false);
                        LoadingButton loadingButton = (LoadingButton)signup_button; loadingButton.showLoading();
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
                            setAccount(); // update user's profile
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

                            mDatabase.child(String.format("users")).child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    _user = dataSnapshot.getValue(userBoard.class);

                                    Gson gson = new Gson();
                                    String metaData = gson.toJson(_user); // convert metaData to JSON

                                    setData(metaData);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });

                        } else {
                            Log.w(getTag(), "createUserWithEmail:failure", task.getException());

                        controller.toast(getContext(), "Sorry. We could not create your account right now");
                       signup_button.setText("Sign up");
                       signup_button.setEnabled(true);
                       LoadingButton loadingButton = (LoadingButton)signup_button; loadingButton.hideLoading();

                        }
                    }
                });
    }

    private void setData(String s){


        controller.toast(getContext(), "Welcome " + email + "!");
        Intent a = new Intent(getActivity() , MainActivity.class);
        a.putExtra("userMetaData", s);
        startActivity(a);

        getActivity().finish();

    }

    private void setAccount() {
        FirebaseUser user = mAuth.getCurrentUser();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> User = new HashMap<>();
        User.put("name", username);
        User.put("email", email);
        User.put("address", address);
        User.put("accountType", "0");
        mDatabase.child("users").child(user.getUid()).setValue(User);
        new userBoard( "0",username,  email,  address);
         }

    private void showKeyboard(View what) {
        what.requestFocus();
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(what, InputMethodManager.SHOW_IMPLICIT);
    }
}

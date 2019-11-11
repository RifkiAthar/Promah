package com.arosyadi.promah.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.arosyadi.promah.MainActivity;
import com.arosyadi.promah.R;
import com.arosyadi.promah.SplashActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    public static final String PREFS_NAME = "PrefFile";
    private static final String TAG = "RegisterActivity";

    ProgressBar progressBar;
    TextView tvLogin;
    EditText edtUsername, edtEmail, edtPassword, edtConfirmPass;
    Spinner spUnit;
    Button btnRegister;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    Object unit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtUsername = findViewById(R.id.et_name);
        edtEmail = findViewById(R.id.et_email);
        edtPassword = findViewById(R.id.et_password);
        edtConfirmPass = findViewById(R.id.et_confirm);

        spUnit = findViewById(R.id.spinner_unit);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.unit));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spUnit.setAdapter(adapter);
        spUnit.setOnItemSelectedListener(this);


        btnRegister = findViewById(R.id.btn_register);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        tvLogin = findViewById(R.id.tv_login);
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);

        btnRegister.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null){

        }
    }

    private void registerUser() {
        final String name = edtUsername.getText().toString().trim();
        final String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String confirmPass = edtConfirmPass.getText().toString().trim();

        if (name.isEmpty()) {
            edtUsername.setError("please enter your name!");
            edtUsername.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            edtEmail.setError("please enter your email!");
            edtEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail.setError("please enter a valid email!");
            edtEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            edtEmail.setError("please enter your password!");
            edtEmail.requestFocus();
            return;
        }

        if (password.length() < 6) {
            edtPassword.setError("too weak!");
            edtPassword.requestFocus();
            return;
        }

        if (confirmPass.isEmpty()) {
            edtEmail.setError("please confirm your password!");
            edtEmail.requestFocus();
            return;
        }

        if (!confirmPass.equals(password)) {
            edtConfirmPass.setError("password not match");
            edtConfirmPass.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        finish();
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {

                            UserModel user = new UserModel(name, unit, email);
                            String uid =   FirebaseAuth.getInstance().getCurrentUser().getUid();
                            db.collection("users")
                                    .document(uid)
                                    .set(user)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "DocumentSnapshot successfully written!");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG, "Error writing document", e);
                                        }
                                    });
                        } else {
                            Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                registerUser();
                break;
            case R.id.tv_login:
                startActivity(new Intent(RegisterActivity.this, SplashActivity.class));
                break;
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        unit = adapterView.getItemAtPosition(i);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

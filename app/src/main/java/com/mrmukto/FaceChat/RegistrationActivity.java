package com.mrmukto.FaceChat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


public class RegistrationActivity extends AppCompatActivity {
    TextView textView_goto_login;

    private EditText editTextName, editTextEmail, editTextPassword;
    private Button buttonRegisterr;
    private ProgressDialog progressDialog;
    private FirebaseFirestore database;

    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registration is progressing !!!!");
        auth =(FirebaseAuth) FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        editTextName =(EditText) findViewById(R.id.reg_name);
        editTextEmail=(EditText) findViewById(R.id.reg_email);
        editTextPassword=(EditText) findViewById(R.id.reg_password);
        buttonRegisterr=(Button) findViewById(R.id.btn_register);

        textView_goto_login = (TextView) findViewById(R.id.txt_go_to_login_page);


        buttonRegisterr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
              String name = editTextName.getText().toString();
              String email = editTextEmail.getText().toString();
              String password = editTextPassword.getText().toString();
                User user = new User();
                user.setName(name);
                user.setEmail(email);
                user.setPassword(password);


              auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                  @Override
                  public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful() ){
                            database.collection("User").document()
                                    .set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    progressDialog.dismiss();
                                    Toast.makeText(RegistrationActivity.this,"Registration is succesfull",Toast.LENGTH_SHORT).show();

                                    startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                                }
                            });

                       }

                  }
              });


            }
        });
        textView_goto_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, MainActivity.class));

            }
        });

    }
}
package com.its.camcar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.its.camcar.helper.FireAuthHelper;
import com.its.camcar.model.User;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtPhone;
    private EditText edtPassword;
    private EditText edtFullname;
    private RadioGroup rgRole;
    private Button btnRegister;

    private FireAuthHelper fireAuthHelper ;
    User user = new User();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        FirebaseApp.initializeApp(this);
        fireAuthHelper = new FireAuthHelper(this);

        edtPhone = findViewById(R.id.edt_phone);
        edtPassword = findViewById(R.id.edt_password);
        btnRegister = findViewById(R.id.btn_next);
        rgRole = findViewById(R.id.rg_role);
        edtFullname = findViewById(R.id.edt_full_name);

        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == btnRegister.getId()){

            user.setFullName(edtFullname.getText().toString());
            user.setPhone(edtPhone.getText().toString());
            user.setPassword(edtPassword.getText().toString());

            int selectedRoleId = rgRole.getCheckedRadioButtonId();
            RadioButton selectRole = findViewById(selectedRoleId);

            user.setRole(selectRole.getText().toString());
//            Create user and save to firestore
            fireAuthHelper.createAccount(user);
        }
    }


}

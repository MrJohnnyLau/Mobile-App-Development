package com.example.assignment_workshop_app.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.assignment_workshop_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddInfoActivity extends AppCompatActivity {

    EditText name, email, phone;
    Toolbar toolbar;
    Button addInfoBtn;

    FirebaseFirestore firestore;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_info);

        toolbar=findViewById(R.id.add_info_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        name = findViewById(R.id.ad_name);
        email = findViewById(R.id.ad_email);
        phone = findViewById(R.id.ad_phone);
        addInfoBtn = findViewById(R.id.ad_add_info);

        addInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = name.getText().toString();
                String userEmail = email.getText().toString();
                String userPhone = phone.getText().toString();

                String final_info = "";

                if (!userName.isEmpty()) {
                    final_info+=userName;
                }
                if (!userEmail.isEmpty()) {
                    final_info+=userEmail;
                }
                if (!userPhone.isEmpty()) {
                    final_info+=userPhone;
                }
                if (!userName.isEmpty() && !userEmail.isEmpty() && !userPhone.isEmpty()) {
                    Map<String,String> map = new HashMap<>();
                    map.put("userInfo", final_info);

                    firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                            .collection("Information").add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Toast.makeText(AddInfoActivity.this, "Information Added", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(AddInfoActivity.this, "Please Fill All Field", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
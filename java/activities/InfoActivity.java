package com.example.assignment_workshop_app.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.assignment_workshop_app.R;
import com.example.assignment_workshop_app.adapters.InfoAdapter;
import com.example.assignment_workshop_app.models.InfoModel;
import com.example.assignment_workshop_app.models.MyCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class InfoActivity extends AppCompatActivity implements InfoAdapter.SelectedInfo {

    Button addInfo;
    RecyclerView recyclerView;
    private InfoAdapter infoAdapter;
    private List<InfoModel> infoModelList;

    FirebaseFirestore firestore;
    FirebaseAuth auth;

    Button addInfoBtn, orderBtn;
    Toolbar toolbar;

    String mInfo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        toolbar = findViewById(R.id.info_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.info_recycler);
        orderBtn = findViewById(R.id.order_btn);
        addInfoBtn = findViewById(R.id.add_info_btn);

        addInfo = findViewById(R.id.add_info_btn);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        infoModelList = new ArrayList<>();
        infoAdapter = new InfoAdapter(infoModelList, getApplicationContext(), this);

        recyclerView.setAdapter(infoAdapter);

        firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("Information").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot doc :task.getResult().getDocuments()) {
                        InfoModel infoModel = doc.toObject(InfoModel.class);
                        infoModelList.add(infoModel);
                        infoAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InfoActivity.this, MainActivity.class));
                Toast.makeText(InfoActivity.this, "Order is Completed", Toast.LENGTH_SHORT).show();
            }
        });

        addInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InfoActivity.this, AddInfoActivity.class));
            }
        });
    }

    @Override
    public void setInfo(String info) {
        mInfo = info;
    }
}
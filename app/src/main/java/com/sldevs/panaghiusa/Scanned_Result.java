package com.sldevs.panaghiusa;

import static androidx.camera.core.CameraX.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Scanned_Result extends AppCompatActivity {

    TextView tvPoints,tvID,tvEmail;
    EditText etKilo;
    Button btnAddPoint,btnCancel;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    String currentPoints;
    double convertedCurrentPoints;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanned_result);
        tvPoints = findViewById(R.id.tvPoints);
        tvID = findViewById(R.id.tvID);
        tvEmail = findViewById(R.id.tvEmail);
        etKilo = findViewById(R.id.etKilo);
        btnAddPoint = findViewById(R.id.btnAddPoints);
        btnCancel = findViewById(R.id.btnCancel);

        getData();

        btnAddPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etKilo.getText().toString().isEmpty()){
                    etKilo.setError("Please provide an amount");
                    etKilo.requestFocus();
                    return;
                }else{
                    updateUser();
                }
            }
        });



    }
    public void getData(){
        String getData = getIntent().getStringExtra("QR_DATA");
        String uid = getData.substring(9,37).trim();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users/" + uid);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String id = snapshot.child("id").getValue(String.class);
                String email = snapshot.child("email").getValue(String.class);
                String points = snapshot.child("points").getValue(String.class);

                tvID.setText("User ID: " + id);
                tvEmail.setText("Email: " + email);
                tvPoints.setText(points);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Scanned_Result.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
//    public void getPoints(){
//        String getData = getIntent().getStringExtra("QR_DATA");
//        String uid = getData.substring(9,37).trim();
//        firebaseDatabase = FirebaseDatabase.getInstance();
//        databaseReference = firebaseDatabase.getReference("Users/" + uid);
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                currentPoints = snapshot.child("points").getValue(String.class);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(Scanned_Result.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
//            }
//        });
//        convertedCurrentPoints = Double.parseDouble(currentPoints);
//    }
    public void updateUser(){
        String getData = getIntent().getStringExtra("QR_DATA");
        String uid = getData.substring(9,37).trim();
        String kilo =  etKilo.getText().toString();
        double currentPoints = Double.parseDouble(tvPoints.getText().toString());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        if(kilo.equalsIgnoreCase("1")){
            currentPoints = currentPoints + 1.2;
            databaseReference.child("Users/" + uid+"/points").setValue(String.valueOf(currentPoints));
        }else{

        }

    }
}
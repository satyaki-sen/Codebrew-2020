package com.satyaki.medicos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    TextInputEditText editEmail,editPass,editConPass,editOccp,editName,editPhn;
    String email,conPass,pass,name,phnNo,occupatio;
    FirebaseFirestore db;
    boolean check=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth=FirebaseAuth.getInstance();
        editEmail=findViewById(R.id.editEmailID);
        editConPass=findViewById(R.id.editconPass);
        editPass=findViewById(R.id.editPass);
        editOccp=findViewById(R.id.editOccupation);
        editName=findViewById(R.id.editName);
        editPhn=findViewById(R.id.editPhoneNo);
        db = FirebaseFirestore.getInstance();
    }

    public void onRegisterUser(View view){

        email=editEmail.getText().toString();
        conPass=editConPass.getText().toString();
        pass=editConPass.getText().toString();
        name=editName.getText().toString();
        phnNo=editPhn.getText().toString();
        occupatio=editOccp.getText().toString();

        if(TextUtils.isEmpty(email)){

            editEmail.setError("This field is compulsory");
            check=false;
        }
        if(TextUtils.isEmpty(conPass)){

            editConPass.setError("This field is compulsory");
            check=false;
        }
        if(TextUtils.isEmpty(pass)){

            editPass.setError("This field is compulsory");
            check=false;
        }
        if(pass.length()<6){

            editPass.setError("Password should have atleast 6 characters.");
        }
        if(!conPass.equals(pass)){

            Toast.makeText(this, "Please check your password..", Toast.LENGTH_SHORT).show();
            check=false;
        }
        if(conPass.equals(pass) && check) {

            Toast.makeText(this, "OKK", Toast.LENGTH_SHORT).show();
            final Map<String,Object> hashMap=new HashMap<>();
            hashMap.put("Name",name);
            hashMap.put("Occupation",occupatio);
            hashMap.put("Phone Number",phnNo);
            hashMap.put("Email",email);
            //hashMap.put("UID",firebaseAuth.getCurrentUser().getUid());

            firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){

                        hashMap.put("UID",firebaseAuth.getCurrentUser().getUid());
                        //Log.i("OK",firebaseAuth.getCurrentUser().getUid());
                        db.collection("Users").document(firebaseAuth.getCurrentUser().getUid()).set(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Toast.makeText(MainActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(MainActivity.this,DoctorsActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                    }
                    else{

                        Log.w("ok check", task.getException());
                        Toast.makeText(MainActivity.this, "Error occured..", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }

    public void onClickLog(View view){

        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
}

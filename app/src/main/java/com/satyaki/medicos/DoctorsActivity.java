package com.satyaki.medicos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;

public class DoctorsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    RecyclerView recyclerView;
    FirebaseAuth auth;
    FirebaseFirestore dB;
    DoctorAdapter doctorAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors);
        drawerLayout=findViewById(R.id.drawer_Layout);
        navigationView=findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        auth=FirebaseAuth.getInstance();
        dB=FirebaseFirestore.getInstance();
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        recyclerView=findViewById(R.id.recycler_Doctor);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setRecyclerView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        doctorAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        doctorAdapter.stopListening();
    }

    public void setRecyclerView(){

        Log.i("Check","Ok");
        Query query=FirebaseFirestore.getInstance().collection("Doctors").orderBy("Rating");
        Log.i("Query",query.toString());
        FirestoreRecyclerOptions<DoctorsClass> firestoreRecyclerOptions=new FirestoreRecyclerOptions.Builder<DoctorsClass>()
                .setQuery(query,DoctorsClass.class)
                .build();

        doctorAdapter=new DoctorAdapter(firestoreRecyclerOptions);
            recyclerView.setAdapter(doctorAdapter);
            doctorAdapter.notifyDataSetChanged();


            doctorAdapter.setOnlink_Click(new DoctorAdapter.link_Click() {
                @Override
                public void onlinkClick(DocumentSnapshot documentSnapshot, int pos) {

                    String link= (String) documentSnapshot.get("Find_out_more");
                    Intent intent=new Intent(DoctorsActivity.this,WebDisplayActivity.class);
                    intent.putExtra("Link",link);
                    startActivity(intent);
                    finish();
                }
            });
    }

    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){

            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.nav_home:
                break;
            case R.id.nav_account:
                Toast.makeText(this, "Account", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_sign_out:
                auth.signOut();
                Intent intent=new Intent(DoctorsActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


}

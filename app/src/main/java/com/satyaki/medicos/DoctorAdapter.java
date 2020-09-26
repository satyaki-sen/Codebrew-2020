package com.satyaki.medicos;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;


public class DoctorAdapter extends FirestoreRecyclerAdapter<DoctorsClass,DoctorAdapter.ViewHolder>
{
    link_Click linkClick;

    public DoctorAdapter(@NonNull FirestoreRecyclerOptions<DoctorsClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull DoctorsClass doctorsClass) {

        viewHolder.name.setText(doctorsClass.getName());
        viewHolder.rating.setText(doctorsClass.getRating()+" Star Rating");
        viewHolder.mail.setText(doctorsClass.getAddress());
        viewHolder.des.setText(doctorsClass.getSpecialization());
        viewHolder.url.setText(doctorsClass.getFind_out_more());

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.doctors_recycler_layout,parent,false);
        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name,des,mail,rating,url;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            url=itemView.findViewById(R.id.text_Doc_URL);
            name=itemView.findViewById(R.id.text_Doc_Name);
            des=itemView.findViewById(R.id.text_Doc_Designation);
            mail=itemView.findViewById(R.id.text_Doc_Email);
            rating=itemView.findViewById(R.id.text_Doc_Rating);

            url.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=getAdapterPosition();
                    linkClick.onlinkClick(getSnapshots().getSnapshot(pos),pos);

                }
            });
        }
    }

    public interface link_Click{

        void onlinkClick(DocumentSnapshot documentSnapshot,int pos);
    }

    public void setOnlink_Click(link_Click linkClick){

        this.linkClick=linkClick;
    }

}

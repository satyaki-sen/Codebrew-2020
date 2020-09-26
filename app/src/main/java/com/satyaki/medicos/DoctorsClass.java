package com.satyaki.medicos;

import com.google.firebase.firestore.PropertyName;

public class DoctorsClass {

   String Name,Address,Specialization,Find_out_more;
   String Rating;

    public DoctorsClass(){

    }

    public DoctorsClass(String name,String Address,String Specialization,String Find_out_more,String Rating) {
        this.Name = name;
        this.Address=Address;
        this.Find_out_more=Find_out_more;
        this.Rating=Rating;
        this.Specialization=Specialization;
    }

    public String getName() {
        return Name;
    }

    public String getAddress() {
        return Address;
    }

    public String getSpecialization() {
        return Specialization;
    }

    public String getFind_out_more() {
        return Find_out_more;
    }


    public String getRating() {
        return Rating;
    }
}

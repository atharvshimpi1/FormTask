package com.example.form;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


    Context context;
    ArrayList<Applications> list;

    public MyAdapter(Context context, ArrayList<Applications> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.items,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Applications applications= list.get(position);
        holder.firstName.setText(applications.getFirstName());
        holder.lastName.setText(applications.getLastName());
        holder.email.setText(applications.getEmail());
        holder.address.setText(applications.getAddress());
        holder.city.setText(applications.getCity());
        holder.phone.setText(applications.getPhone());
        holder.country.setText(applications.getCountry());
        holder.postalCode.setText(applications.getPostalCode());
        holder.region.setText(applications.getRegion());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView firstName,lastName,email,address,city,phone,region,country,postalCode;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            firstName=itemView.findViewById(R.id.AfirstName);
            lastName=itemView.findViewById(R.id.AlastName);
            email=itemView.findViewById(R.id.Aemail);
            address=itemView.findViewById(R.id.Aaddress);
            city=itemView.findViewById(R.id.Acity);
            phone=itemView.findViewById(R.id.Aphone);
            region=itemView.findViewById(R.id.Aregion);
            country=itemView.findViewById(R.id.Acountry);
            postalCode=itemView.findViewById(R.id.Apostal);

        }
    }
}

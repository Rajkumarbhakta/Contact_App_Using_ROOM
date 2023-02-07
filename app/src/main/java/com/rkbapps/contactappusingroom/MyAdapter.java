package com.rkbapps.contactappusingroom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rkbapps.contactappusingroom.db.Contact;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    List<Contact> contactList = new ArrayList<>();
    final String [] colorList = {"ffbe0b","fb5607","ff006e","8338ec","3a86ff","ffadad","FFD6A5","CAFFBF","9BF6FF","A0C4FF","BDB2FF","FFC6FF"};


    public MyAdapter(Context context, List<Contact> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_view,parent,false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        holder.name.setText(contactList.get(position).getName());
        holder.phNumber.setText(contactList.get(position).getMobileNumber());
        String name = contactList.get(position).getName();
        char ch[]=name.toCharArray();
        holder.firstLetter.setText(""+ch[0]);
        Random random = new Random();
        int x= random.nextInt(colorList.length);
        holder.cardView.setCardBackgroundColor(Color.parseColor("#"+colorList[x]));

    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name,phNumber,firstLetter;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            phNumber=itemView.findViewById(R.id.phNumber);
            firstLetter=itemView.findViewById(R.id.fristLetter);
            cardView=itemView.findViewById(R.id.cardView);
        }
    }
}

package com.mayurkakade.test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {
    private Context context;
    private List<UserModel> uList;

    public UsersAdapter(Context context, List<UserModel> uList) {
        this.context = context;
        this.uList = uList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UsersAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.user_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setViews(uList.get(position));
    }

    @Override
    public int getItemCount() {
        return uList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,username,email,street,city,zipcode;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            username = itemView.findViewById(R.id.tv_username);
            email = itemView.findViewById(R.id.tv_email);
            street = itemView.findViewById(R.id.tv_street);
            city = itemView.findViewById(R.id.tv_city);
            zipcode = itemView.findViewById(R.id.tv_zipcode);

        }

        @SuppressLint("SetTextI18n")
        public void setViews(UserModel userModel) {
            name.setText(userModel.getName());
            username.setText("Username : " + userModel.getUsername());
            email.setText("Email : " + userModel.getEmail());
            street.setText("Street : " + userModel.getAddress().getStreet());
            city.setText("City : "+userModel.getAddress().getCity());
            zipcode.setText("Zipcode : "+userModel.getAddress().getZipcode());
        }
    }
}

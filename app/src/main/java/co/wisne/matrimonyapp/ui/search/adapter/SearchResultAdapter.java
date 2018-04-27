package co.wisne.matrimonyapp.ui.search.adapter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

import co.wisne.matrimonyapp.R;
import co.wisne.matrimonyapp.databinding.RecyclerSearchResultItemBinding;
import co.wisne.matrimonyapp.ui.profile.UserProfileActivity;
import co.wisne.matrimonyapp.ui.search.model.SearchResult;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder>{

    private ArrayList<SearchResult> results = new ArrayList<>();

    ViewGroup viewGroup;

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView height;
        TextView subCaste;
        TextView maritalStatus;
        TextView age;
        TextView salary;
        ImageView profilePicture;

        String UUID;

        public ViewHolder(RecyclerSearchResultItemBinding binding){
            super(binding.getRoot());
            name = binding.textResultName;
            height = binding.textViewHeightValue;
            subCaste = binding.textViewSubCasteValue;
            maritalStatus = binding.textViewMaritalStatusValue;
            age = binding.textViewAgeValue;
            salary = binding.textViewSalaryValue;
            profilePicture = binding.imageViewProfilePicture;


            binding.getRoot().setOnClickListener(view -> {
                Intent userProfileIntent = new Intent(binding.getRoot().getContext(), UserProfileActivity.class);
                userProfileIntent.putExtra("profileUUID",getUUID());
                binding.getRoot().getContext().startActivity(userProfileIntent);
            });
        }

        public void setUUID(String UUID) {
            this.UUID = UUID;
        }

        public String getUUID() {
            return UUID;
        }
    }

    public SearchResultAdapter(@NonNull ArrayList<SearchResult> results){
        this.results = results;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        viewGroup = parent;

        RecyclerSearchResultItemBinding binding = RecyclerSearchResultItemBinding.inflate(inflater,parent,false);

        ViewHolder vh = new ViewHolder(binding);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(results.get(position).getFirstName()+' '+results.get(position).getLastName());
        holder.height.setText(results.get(position).getHeight());
        holder.maritalStatus.setText(results.get(position).getMaritalStatus());
        holder.salary.setText(results.get(position).getSalary());
        holder.setUUID(results.get(position).getUUID());
        holder.age.setText(results.get(position).getAge());

        new Thread(new Runnable() {
            @Override
            public void run() {
                FirebaseStorage.getInstance().getReference().child(
                        results.get(position).getUUID()+"/images/profile.jpg"
                ).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(viewGroup.getContext())
                                .load(uri)
                                .into(holder.profilePicture);
                    }
                });
            }
        }).run();
    }

    @Override
    public int getItemCount() {
        return results.size();
    }


}

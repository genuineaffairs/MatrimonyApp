package co.wisne.matrimonyapp.ui.main.adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Map;

import co.wisne.matrimonyapp.databinding.BookmarkListItemBinding;
import co.wisne.matrimonyapp.ui.main.model.Bookmarks;
import co.wisne.matrimonyapp.ui.profile.UserProfileActivity;

public class BookmarksAdapter extends RecyclerView.Adapter<BookmarksAdapter.ViewHolder>{

   private ArrayList<Bookmarks> bookmarks;


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;

        ImageView profilePicture;

        String UUID;

        public ViewHolder(BookmarkListItemBinding binding) {

            super(binding.getRoot());

            name = binding.textFullName;

            profilePicture = binding.imageViewProfilePicture;

            binding.getRoot().setOnClickListener((view)->
                    {
                        Intent profileIntent = new Intent(binding.getRoot().getContext(), UserProfileActivity.class);
                        profileIntent.putExtra("profileUUID",UUID);
                        binding.getRoot().getContext().startActivity(profileIntent);
                    }
            );
        }

        public void setUUID(String UUID) {
            this.UUID = UUID;
        }
    }

    public BookmarksAdapter(ArrayList<Bookmarks> bookmarks){

        this.bookmarks = bookmarks;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        BookmarkListItemBinding binding = BookmarkListItemBinding.inflate(inflater,parent,false);

        ViewHolder viewHolder = new ViewHolder(binding);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.setUUID(bookmarks.get(position).getUUID());

        FirebaseFirestore.getInstance().collection("users")
                .document(bookmarks.get(position).getUUID())
                .get()
                .addOnSuccessListener(
                        new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    holder.name.setText(
                                            documentSnapshot.get("name.first").toString()
                                            + " " + documentSnapshot.get("name.last").toString()
                                    );
                            }
                        }
                );

        FirebaseStorage.getInstance().getReference(bookmarks.get(position).getUUID())
                .child("images/profile.jpg").getDownloadUrl().addOnSuccessListener(
                new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        if(uri!= null){
                            Glide.with(holder.itemView.getContext().getApplicationContext())
                                    .load(uri)
                                    .into(holder.profilePicture);
                        }

                    }
                }
        );

    }

    @Override
    public int getItemCount() {
        return bookmarks.size();
    }


}

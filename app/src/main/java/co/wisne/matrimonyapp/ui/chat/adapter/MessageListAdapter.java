package co.wisne.matrimonyapp.ui.chat.adapter;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import co.wisne.matrimonyapp.databinding.ChatMessageItemBinding;
import co.wisne.matrimonyapp.ui.chat.ChatActivityViewModel;
import co.wisne.matrimonyapp.ui.chat.model.ChatMessage;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder> {

    ArrayList<ChatMessage> messages;

    String otherUserUID;

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView leftMessage;
        TextView rightMessage;

        public ViewHolder(ChatMessageItemBinding binding) {
            super(binding.getRoot());
            leftMessage = binding.textLeftMessage;
            rightMessage = binding.textRightMessage;
        }
    }

    public MessageListAdapter(ChatActivityViewModel viewModel){
        this.messages = viewModel.getMessages();
        this.otherUserUID = viewModel.getUserUUID();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =  LayoutInflater.from(parent.getContext());

        ChatMessageItemBinding binding = ChatMessageItemBinding.inflate(inflater,parent,false);

        ViewHolder vh = new ViewHolder(binding);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(messages.get(position).getFrom().equals(otherUserUID)){
            holder.leftMessage.setText(messages.get(position).getMessage());
        }else {
            holder.rightMessage.setText(messages.get(position).getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}

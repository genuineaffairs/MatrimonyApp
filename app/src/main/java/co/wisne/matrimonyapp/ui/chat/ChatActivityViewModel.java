package co.wisne.matrimonyapp.ui.chat;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import co.wisne.matrimonyapp.ui.chat.model.ChatMessage;


public class ChatActivityViewModel extends ViewModel {

    ArrayList<ChatMessage> messages = new ArrayList<>();


    FirebaseFirestore db = FirebaseFirestore.getInstance();

    FirebaseUser meUser = FirebaseAuth.getInstance().getCurrentUser();

    String userUUID;

    MutableLiveData<String> message;

    MutableLiveData<Boolean> updateMessages;

    MutableLiveData<Boolean> addedMessage;


    public String getUserUUID() {
        return userUUID;
    }

    public void setUserUUID(String userUUID) {
        this.userUUID = userUUID;
        listenCollection();
        loadMessages();
    }

    public MutableLiveData<String> getMessage() {
        if(message == null){
            message = new MutableLiveData<>();
        }
        return message;
    }

    public ArrayList<ChatMessage> getMessages() {
        if(messages==null){
            messages = new ArrayList<>();
        }
        return messages;
    }

    public MutableLiveData<Boolean> getUpdateMessages() {
        if(updateMessages == null){
            updateMessages = new MutableLiveData<>();
        }
        return updateMessages;
    }

    public MutableLiveData<Boolean> getAddedMessage() {
        if(addedMessage == null){
            addedMessage = new MutableLiveData<>();
        }
        return addedMessage;
    }

    public void loadMessages(){
        db.collection("users")
                .document(meUser.getUid())
                .collection("chats")
                .document(userUUID)
                .collection("messages")
                .orderBy("time")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot d: queryDocumentSnapshots) {
                            ChatMessage message = new ChatMessage();
                            message.setFrom(d.get("from").toString());
                            message.setDate(d.getDate("date"));
                            message.setTo(d.get("to").toString());
                            message.setMessage(d.getString("message"));
                            messages.add(message);
                        }
                    }
                });
        getUpdateMessages().postValue(true);
    }

    public void sendMessage(){

        Map<String, Object> message = new HashMap<>();
        message.put("time",new Date());
        message.put("from",meUser.getUid());
        message.put("to",userUUID);
        message.put("message",this.message.getValue());

        db.collection("users")
                .document(meUser.getUid())
                .collection("chats")
                .document(userUUID)
                .collection("messages")
                .document()
                .set(message);




    }

    public void listenCollection(){


        db.collection("users")
                .document(meUser.getUid())
                .collection("chats")
                .document(userUUID)
                .collection("messages")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {


                        for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {

                            switch (dc.getType()) {

                                case ADDED:

                                    ChatMessage message = new ChatMessage();
                                    message.setFrom(dc.getDocument().get("from").toString());
                                    message.setDate(dc.getDocument().getDate("date"));
                                    message.setTo(dc.getDocument().get("to").toString());
                                    message.setMessage(dc.getDocument().getString("message"));
                                    messages.add(message);

                                    getAddedMessage().postValue(true);

                                    getMessage().postValue(null);

                                    break;
                                case MODIFIED:
                                    Log.d("D", "Modified city: " + dc.getDocument().getData());
                                    break;
                                case REMOVED:
                                    Log.d("D", "Removed city: " + dc.getDocument().getData());
                                    break;
                            }
                        }




                    }
                });

    }
}

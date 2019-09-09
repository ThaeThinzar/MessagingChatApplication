package com.example.messagingchatapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    String mmessage;
    int mtype;

    public ChatListAdapter(Context context) {
        mContext = context;
    }
    public ChatListAdapter(Context context, String message, int type) {
        mContext = context;
        mmessage = message;
        mtype = type;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if(mtype == 1){
            final View v = layoutInflater.inflate(R.layout.sender_message, parent, false);
            return new SenderMessage(v);
        } else{

        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof SenderMessage){

            SenderMessage senderViewHolder = (SenderMessage) viewHolder;
            ((SenderMessage) viewHolder).mMessage.setText(mmessage);
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
class SenderMessage extends RecyclerView.ViewHolder{

    @BindView(R.id.tv_message_layout)
    TextView mMessage;
    public SenderMessage(@NonNull View itemView) {

        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
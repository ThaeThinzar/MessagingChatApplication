package com.example.messagingchatapplication;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatActivity extends AppCompatActivity {
    @BindView(R.id.layout1)
    LinearLayout rvChatList;

    @BindView(R.id.layout2)
    RelativeLayout layout2;

    @BindView(R.id.scrollView)
    ScrollView scrollView;

    @BindView(R.id.messageArea)
    EditText mMessageArea;

    @BindView(R.id.sendButton)
    ImageView mSend;

    Firebase reference1, reference2;
    ChatListAdapter chatListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this, this);

        Firebase.setAndroidContext(this);
        reference1 = new Firebase("https://androidchatapp-76776.firebaseio.com/messages/" + UserDetail.username + "_" + UserDetail.chatWith);
        reference2 = new Firebase("https://androidchatapp-76776.firebaseio.com/messages/" + UserDetail.chatWith + "_" + UserDetail.username);
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messageText = mMessageArea.getText().toString();

                if(!messageText.equals("")){
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("message", messageText);
                    map.put("user", UserDetail.username);
                    reference1.push().setValue(map);
                    reference2.push().setValue(map);
                    mMessageArea.setText("");
                }
            }
        });
        mMessageArea.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager inputMethodManager = (InputMethodManager)
                            getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    // Hide the soft keyboard
                    inputMethodManager.hideSoftInputFromWindow(mMessageArea.getWindowToken(),0);

                    String messageText = mMessageArea.getText().toString();

                    if(!messageText.equals("")){
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("message", messageText);
                        map.put("user", UserDetail.username);
                        reference1.push().setValue(map);
                        reference2.push().setValue(map);
                        mMessageArea.setText("");
                    }
                    return  true;
                }
                return false;
            }
        });
        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);
                String message = map.get("message").toString();
                String userName = map.get("user").toString();

                if(userName.equals(UserDetail.username)){
                    addMessage( message, 1);
                }
                else{
                    addMessage(UserDetail.chatWith + ":-\n" + message, 2);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });



    }
    /*public void addMessageBox(String message, int type) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        int msgWidth = width / 2;
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        lp2.weight = 1.0f;
        lp2.setMargins(0, 0, 0, 32);

        LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.chat_text_item, null);
        CardView cardView = v.findViewById(R.id.card_view_chat_item);
        TextView chatMessage = v.findViewById(R.id.tv_chat_item);

        if (type == 1) {
            lp2.gravity = Gravity.LEFT;
            //cardView.setCardBackgroundColor(getResources().getColor(android.R.color.white));
            cardView.setBackgroundResource(R.drawable.ic_chat_bg);
        } else {
            lp2.gravity = Gravity.RIGHT;
           // cardView.setCardBackgroundColor(getResources().getColor(R.color.colorAccent));
            cardView.setBackgroundResource(R.drawable.ic_chat_receive);
        }

        cardView.setLayoutParams(lp2);

        chatMessage.setText(message);
        chatMessage.setMaxWidth(msgWidth);

        rvChatList.addView(v);

        v.setFocusable(true);
        v.setFocusableInTouchMode(true);
        v.requestFocus();

        mMessageArea.requestFocus();
    }
*/

    private void  addMessage(String message, int i) {
        LayoutInflater messageInflater = (LayoutInflater) getApplicationContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if( i == 2) {
            View view = messageInflater.inflate(R.layout.sender_message,null);
            TextView tvSendMsg = view.findViewById(R.id.message_body);
            tvSendMsg.setText(message);
            rvChatList.addView(view);
        } else {
            View view = messageInflater.inflate(R.layout.receive_message,null);
            TextView tvReceiveMsg = view.findViewById(R.id.tv_receive_message);
            tvReceiveMsg.setText(message);
            rvChatList.addView(view);
        }
        scrollView.fullScroll(View.FOCUS_DOWN);
    }
    private void addMessageBox(String message, int i) {
        LayoutInflater messageInflater = (LayoutInflater) getApplicationContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if(i == 1) {
            View view = messageInflater.inflate(R.layout.sender_message,null);
            TextView tvMessage = view.findViewById(R.id.message_body);
            tvMessage.setText(message);
            rvChatList.addView(view);
        }
        TextView textView = new TextView(ChatActivity.this);
        textView.setText(message);

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.weight = 1.0f;

        if(i == 1) {
            lp2.gravity = Gravity.LEFT;
            textView.setBackgroundResource(R.drawable.chat_bubble_bg);
        }
        else{
            lp2.gravity = Gravity.RIGHT;
            textView.setBackgroundResource(R.drawable.chat_bubble_bg);
        }
        textView.setLayoutParams(lp2);
        textView.setPadding(24,24,24,24);
        textView.setLayoutParams(lp2);
        //rvChatList.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }
}

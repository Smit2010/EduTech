package com.example.smit.edutech;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class FragmentTwo extends Fragment {
    DatabaseReference mDataBase;
    FirebaseAuth mAuth;
    EditText Msg;
    FloatingActionButton send;
    TextView Chat;
    private FirebaseListAdapter<ChatMessage> adapter;

    static int counter = 1;
    private GoogleApiClient mGoogleApi;

    private static int count = 0;
    public FragmentTwo() {
        // Required empty public constructor
        //not useful
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_fragment_two, container, false);

        mDataBase = FirebaseDatabase.getInstance().getReference();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();
        mGoogleApi = new GoogleApiClient.Builder(getActivity()).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();
        mGoogleApi.connect();
        final GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = mAuth.getCurrentUser();

        Msg = view.findViewById(R.id.input);
        send = view.findViewById(R.id.fab);

        final DatabaseReference mRef = mDataBase.child("Discussion");


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(Msg.getText().toString())) {
                    mRef
                            .push()
                            .setValue(new ChatMessage(Msg.getText().toString(),
                                    FirebaseAuth.getInstance()
                                            .getCurrentUser()
                                            .getDisplayName())
                            );

                    // Clear the input
                    Msg.setText("");
                }
            }
        });

        ListView listOfMessages = (ListView)view.findViewById(R.id.list_of_messages);

        adapter = new FirebaseListAdapter<ChatMessage>(getActivity(), ChatMessage.class,R.layout.message, mRef) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                // Get references to the views of message.xml
                TextView messageText = (TextView)v.findViewById(R.id.message_text);
                TextView messageUser = (TextView)v.findViewById(R.id.message_user);

                // Set their text
                    messageText.setText(model.getMessageText());
                    messageUser.setText(model.getMessageUser());
                // Format the date before showing it
            }
        };

        listOfMessages.setAdapter(adapter);
        return view;
    }

    @Override
    public void onStart() {
        mGoogleApi.connect();
        super.onStart();
    }

}
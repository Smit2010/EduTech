package com.example.smit.edutech;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import io.grpc.okhttp.internal.Util;

import static android.content.Context.MODE_PRIVATE;


public class FragmentOne extends Fragment {

    private EditText editText;
    int i = 0;
    DatabaseReference myRef;
    FirebaseDatabase database;
    String TAG = "FRAGMENTONE";
    Handler handler = new Handler();
    long delay = 1000; // 1 seconds after user stops typing
    long last_text_edit = 0;

    private static int count = 0;

    public FragmentOne() {
        // Required empty public constructor
        //not useful
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_one, container, false);

        editText = view.findViewById(R.id.editext);
        editText.setSelection(0);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("TextEditor");

        final Runnable input_finish_checker = new Runnable() {
            public void run() {
                if (System.currentTimeMillis() > (last_text_edit + delay - 500)) {
                    // TODO: do what you need here
                    for (int i = 0; i < 2; i++) {
                        myRef.setValue(editText.getText().toString());
                    }

                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            editText.setText(dataSnapshot.getValue(String.class));
                            editText.setSelection(dataSnapshot.getValue(String.class).length());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        };

        editText.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence s, int start, int count,
                                                                          int after) {

                                            }

                                            @Override
                                            public void onTextChanged(final CharSequence s, int start, int before,
                                                                      int count) {
                                                //You need to remove this to run only once
                                                handler.removeCallbacks(input_finish_checker);

                                            }

                                            @Override
                                            public void afterTextChanged(final Editable s) {
                                                //avoid triggering event when text is empty
                                                if (s.length() > 0) {
                                                    last_text_edit = System.currentTimeMillis();
                                                    handler.postDelayed(input_finish_checker, delay);
                                                } else {

                                                }
                                            }
                                        }

        );

        return view;
    }

}
package com.example.smit.edutech;

import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import jp.wasabeef.blurry.Blurry;

public class user_profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);


        TextView name,emailid;
        ImageView profile;
        FirebaseUser acct = FirebaseAuth.getInstance().getCurrentUser();
        String personName = acct.getDisplayName();
        //String personGivenName = acct.getGivenName();
        //String personFamilyName = acct.getFamilyName();
        String personEmail = acct.getEmail();
        //String personId = acct.getId();
        Uri personPhoto = acct.getPhotoUrl();
        name=(TextView) findViewById(R.id.name);
        emailid=(TextView) findViewById(R.id.email);
        profile=(ImageView)findViewById(R.id.profile_pic);


        Picasso.with(this).load(personPhoto).into(profile);



        name.setText(personName);
        emailid.setText(personEmail);



    }
}

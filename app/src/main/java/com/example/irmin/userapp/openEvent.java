package com.example.irmin.userapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.media.tv.TvContentRating;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class openEvent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_open_event2);

        Intent intent = getIntent();

        TextView title = (TextView) findViewById(R.id.title2);
        TextView content = (TextView) findViewById(R.id.content2);
        final TextView amount = (TextView) findViewById(R.id.amount2);
        TextView cate = (TextView) findViewById(R.id.category2);
        TextView tel = (TextView) findViewById(R.id.tel2);
        TextView add = (TextView) findViewById(R.id.add2);

        title.setText(intent.getStringExtra("title2"));
        content.setText(intent.getStringExtra("content2"));
        amount.setText(intent.getStringExtra("amount2"));
        cate.setText(intent.getStringExtra("cate2"));
        tel.setText(intent.getStringExtra("tel2"));
        add.setText(intent.getStringExtra("add2"));

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(amount.getText().toString()!=null) {

                    ImageView img = (ImageView) findViewById(R.id.imgView);

                    if (img.getVisibility() == GONE) {
                        img.setVisibility(VISIBLE);

                    }
                }
                Log.e("test", amount.getText().toString());

            }
        });

    }

}

package com.example.projectprototype;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Views
        final TextView tv = findViewById(R.id.tv);
        final Button btn = findViewById(R.id.sendMsg);
        btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //Send message that was typed in tv
                try{
                    JDABuilder.createDefault(TOKEN) //Token goes here
                            //.addEventListeners(listener) // some other listeners/settings
                            .addEventListeners(new ListenerAdapter() {
                                @Override public void onReady(ReadyEvent event) {
                                    new EventChannel(event.getJDA()).sendGeneral(tv.getText().toString()); // starts your channel with the ready event
                                }
                            }).build();
                } catch(LoginException e){
                    e.printStackTrace();
                }
            }
        });
    }
}

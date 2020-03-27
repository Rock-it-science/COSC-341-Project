package com.example.projectprototype.ui.home;

import android.speech.tts.Voice;
import android.util.Log;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;
import net.dv8tion.jda.api.audio.AudioSendHandler;

import net.dv8tion.jda.api.requests.restaction.pagination.ReactionPaginationAction;
import com.example.projectprototype.music.musicMain;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;
import com.sedmelluq.discord.lavaplayer.track.playback.MutableAudioFrame;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.projectprototype.EventChannel;
import com.example.projectprototype.MainActivity;
import com.example.projectprototype.R;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

//  Information on fragments:
//  https://guides.codepath.com/android/Creating-and-Using-Fragments
public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;

    TextView songName;
    String token;
    String songNameText = " - ";
    Spinner spinner;
    String[] channels;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);
        token = getLastToken();


        //Spinner   (broken)
        try {
            JDABuilder.createDefault(token).addEventListeners(new ListenerAdapter() {
                @Override public void onReady(ReadyEvent event) {
                    try {
                        channels = new EventChannel(JDABuilder.createDefault(token).build().awaitReady()).getVoice();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (LoginException e) {
                        e.printStackTrace();
                    }
                }
            }
            ).build();
        } catch (LoginException e) {
            e.printStackTrace();
        }

        //System.out.println(channels[0]);   // Should print a channel name but just crashes despite this being initialized.

        /*
        Spinner spinner = v.findViewById(R.id.spinnerChannels);
        ArrayAdapter<String> adapter = new ArrayAdapter(this.getActivity(), android.R.layout.simple_spinner_item, channels);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spinner.setAdapter(adapter);
        */
        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //Views
        songName = getView().findViewById(R.id.textViewCurrent);
        updatePlaying(songNameText);
        final TextView tv = getView().findViewById(R.id.tv2);
        final Button btn = getView().findViewById(R.id.sendMsg2);

        token = getLastToken();

        //Button
        btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //Send message that was typed in tv
                try{
                    JDABuilder.createDefault(token) //Token goes here
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

    //  adds song to queue
    void addToQueue()
    {

    }



    //  updates the currently playing song
    void updatePlaying(String songNameTextIn)
    {
        songNameText = songNameTextIn;
        songName.setText(songNameTextIn);
    }



    //  returns saved token from token.txt
    String getLastToken() {
        //  OPs                         :   Return value
        // read token and return it     :   token
        // return a placeholder         :   ""
        // error                        :   "error at getLastToken"

        String token;
        try (BufferedReader br = new BufferedReader(new FileReader(getContext().getFilesDir()+"/token.txt"))) {
            token = br.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "error at getLastToken";
        }
        return token;
    }
}

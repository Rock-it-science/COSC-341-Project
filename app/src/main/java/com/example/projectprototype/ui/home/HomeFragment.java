package com.example.projectprototype.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import javax.security.auth.login.LoginException;

//  Information on fragments:
//  https://guides.codepath.com/android/Creating-and-Using-Fragments
public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        return root;
    }



    String token;
    String songName;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //Views
        final TextView tv = view.findViewById(R.id.tv2);
        final Button btn = view.findViewById(R.id.sendMsg2);
        //Token
        token = getLastToken();
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



    void updatePlaying(String songName)
    {
        this.songName = songName;
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

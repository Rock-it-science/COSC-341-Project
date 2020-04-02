package com.example.projectprototype.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.projectprototype.EventChannel;
import com.example.projectprototype.MainActivity;
import com.example.projectprototype.MainNav;
import com.example.projectprototype.R;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import javax.security.auth.login.LoginException;

//  Information on fragments:
//  https://guides.codepath.com/android/Creating-and-Using-Fragments

public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;

    TextView songName;
    String token;
    String songNameText = " - ";
    Spinner spinner, sp;
    String[] channels;
    static EventChannel eve;
    List<Guild> server;


    public static EventChannel getEvent(){
        return eve;
    }

    public EventChannel connect(String token) throws LoginException, InterruptedException {
        JDA api = JDABuilder.create(token,GatewayIntent.GUILD_BANS,GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_EMOJIS,GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGE_REACTIONS, GatewayIntent.GUILD_EMOJIS)
                .setEnabledCacheFlags(EnumSet.of(CacheFlag.EMOTE, CacheFlag.ACTIVITY))
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .setDisabledCacheFlags(EnumSet.of(CacheFlag.VOICE_STATE,CacheFlag.ACTIVITY,CacheFlag.CLIENT_STATUS))
                .build();
        api.awaitReady();
        return new EventChannel(api);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);
        token = getLastToken();


        //Spinner
        String[] ser = {};
        while(eve == null) {

            try {

                eve = connect(token);

                System.out.println(ser);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Could not connect to bot, retrying", Toast.LENGTH_LONG).show();
                eve = null;
            }
            if(eve.getServers().toString() == "[]")
                eve = null;
        }

        server = eve.getServers();
        ser = new String[server.size()];
        for(int i = 0 ; i < server.size() ; i++)
        {
            ser[i] =  server.get(i).getName();
        }

        sp = (Spinner)v.findViewById(R.id.spinnerChannels);
        ArrayAdapter<String> adapter = new ArrayAdapter(this.getActivity(), android.R.layout.simple_spinner_item, ser);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        sp.setAdapter(adapter);

        Button btn2 = (Button) v.findViewById(R.id.button6);
        ((Button)v.findViewById(R.id.button7)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eve.newUsers();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ser = (sp.getSelectedItem().toString());
                eve.setServer(ser);
            }
        });

        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //Views
        songName = getView().findViewById(R.id.textViewCurrent);
        final Button btn = getView().findViewById(R.id.button3);

        token = getLastToken();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eve.setupServer();
            }
        });
    }

    //  adds song to queue
    void addToQueue()
    {

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

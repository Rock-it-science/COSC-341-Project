package com.example.projectprototype.ui.home;

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
import com.example.projectprototype.R;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
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
    Spinner spinner, sp;
    String[] channels;
    static EventChannel eve;


    public static EventChannel getEvent(){ //throws Exception {
        //if(eve == null)
            //throw new Exception("AAAAAAAAHHHHHHHHHHHHHHHHHHHHHHH eventchannel was not created yet : ");
        return eve;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);
        token = getLastToken();

        //Spinner
        if(eve == null) {
            try {
                JDA api = JDABuilder.createDefault(token).build();
                Thread.sleep(1000);
                if (eve == null)
                    eve = new EventChannel(api);
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        eve.setPoolText("");




        List<Guild> server = (eve.getServers());
        String[] ser = new String[server.size()];
        for(int i = 0 ; i < server.size() ; i++)
            ser[i] = server.get(i).getName();

        sp = (Spinner)v.findViewById(R.id.spinnerChannels);
        ArrayAdapter<String> adapter = new ArrayAdapter(this.getActivity(), android.R.layout.simple_spinner_item, server);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        sp.setAdapter(adapter);

        Button btn2 = (Button) v.findViewById(R.id.button6);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eve.setServer(sp.getSelectedItem().toString());
            }
        });




        /*
        channels = eve.getVoice();
        Spinner spinner = v.findViewById(R.id.spinnerChannels);
        ArrayAdapter<String> adapter = new ArrayAdapter(this.getActivity(), android.R.layout.simple_spinner_item, channels);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                eve.setMusicChannel(channels[position]);   //  Crashes here
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
        */

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

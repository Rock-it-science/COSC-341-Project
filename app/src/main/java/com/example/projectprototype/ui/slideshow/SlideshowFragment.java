package com.example.projectprototype.ui.slideshow;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.security.auth.login.LoginException;

public class SlideshowFragment extends Fragment {
    private SlideshowViewModel slideshowViewModel;

    //  Event Channel
    static EventChannel eve;
    //  Token
    String token;
    //  Views
    Button banButton, kickButton;
    TextView userRole;      //  Displays the role of the selected user
    Spinner spinner;        //  Displays all the names of users
    String[] users;         //  Holds all the names of users on the server

    PopupWindow popUp;
    boolean click = true;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        token = getLastToken();
        /*
        try {
            JDA api = JDABuilder.createDefault(token).build();
            Thread.sleep(1000);
            eve = new EventChannel(api);
        } catch (Exception e) {
            e.printStackTrace();
        }
        */


        //  Spinner
        /*
        users = eve.getMembers();
        spinner = v.findViewById(R.id.spinnerChannels);
        ArrayAdapter<String> adapter = new ArrayAdapter(this.getActivity(), android.R.layout.simple_spinner_item, users);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //eve.setMusicChannel(channels[position]);   //  Crashes here

                //set userRole to display selected user's role.
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
        */


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayout layout = new LinearLayout(getActivity());

        //  Ban Button
        banButton = view.findViewById(R.id.buttonBan);
        banButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //open warning dialogue.
            }
        });



        //  Kick Button
        banButton = view.findViewById(R.id.buttonBan);
        banButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //open warning dialogue.
            }
        });
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

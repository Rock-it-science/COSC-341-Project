package com.example.projectprototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import javax.security.auth.login.LoginException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Views
        final TextView tv = findViewById(R.id.editTextToken);
        tv.setText(getLastToken()); //  Set tv to last used token if available.

        final Button btn = findViewById(R.id.buttonConnect);
        btn.setOnClickListener(v -> {
            if (tv.getText().toString().equals("")       //  Invalid inputs
                    || tv.getText().toString().length() < 15) {
                Toast.makeText(getApplicationContext(), "Invalid Input", Toast.LENGTH_SHORT).show();
            } else {

                //  Save token to local file
                if (updateTokens(tv.getText().toString()) == 0) {
                    Toast.makeText(getApplicationContext(), "Token Saved", Toast.LENGTH_SHORT).show();
                    // Save token to XML (or any other way to save locally)
                }
                //  Move on to MainNav activity
                Intent myIntent = new Intent(MainActivity.this, MainNav.class);
                myIntent.putExtra("ser", "0");
                startActivity(myIntent);

            }
        });
    }


    //  Information on files:
    //  https://www.dev2qa.com/android-read-write-internal-storage-file-example/

    //  Takes token and returns success states as int
    int updateTokens(String token) {
        //  OPs                         :   Return value
        // save token,                  :   0
        // FileNotFoundException        :   -1
        // IOException                  :   -2

        try (FileOutputStream out = new FileOutputStream(new File(getFilesDir(), "token.txt"))) {
            out.write(token.getBytes());    //  Write token to token.txt
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return -1;
        } catch (IOException e) {
            e.printStackTrace();
            return -2;
        }
        return 0;
    }




    //  returns saved token from token.txt
    String getLastToken() {
        //  OPs                         :   Return value
        // read token and return it     :   token
        // return a placeholder         :   ""
        // error                        :   "error at getLastToken"

        String token;
        try (BufferedReader br = new BufferedReader(new FileReader(getFilesDir()+"/token.txt"))) {
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

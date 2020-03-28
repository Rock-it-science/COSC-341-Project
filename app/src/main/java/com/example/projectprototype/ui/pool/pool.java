package com.example.projectprototype.ui.pool;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projectprototype.EventChannel;
import com.example.projectprototype.R;
import com.example.projectprototype.ui.Results.Results;
import com.example.projectprototype.ui.home.HomeFragment;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class pool extends Fragment
{

    public boolean isEmpty()
    {
        if(!(title.getText().toString().length() > 0) ||
                !(message.getText().toString().length() > 0) ||
                !(o1.getText().toString().length() > 0) ||
                !(o2.getText().toString().length() > 0))
            return false;

        return true;
    }

    private PoolViewModel mViewModel;

    public static pool newInstance() {
        return new pool();
    }

    private View view1, view2;

    EditText title;
    EditText message;
    EditText o1;
    EditText o2;
    EditText o3;
    EditText o4;
    EditText o5;

    Button b;

    public void showResults()
    {
       /* FragmentTransaction fragmentTransaction = getActivity()
                .getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_pool, new Results())
                .commit();*/
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View root  = inflater.inflate(R.layout.fragment_pool, container, false);

        title = root.findViewById(R.id.editText);
        message = root.findViewById(R.id.editText4);
        o1 = root.findViewById(R.id.editText5);
        o2 = root.findViewById(R.id.editText6);
        o3 = root.findViewById(R.id.editText7);
        o4 = root.findViewById(R.id.editText8);
        o5 = root.findViewById(R.id.editText2);

        b = root.findViewById(R.id.button);

        b.setOnClickListener(v ->
        {
            if(isEmpty()) {
                String msg = "*" +
                        title.getText().toString() + "*```\n\n" +
                        message.getText().toString() + "\n\n" +
                        "1) " + o1.getText().toString() + "\n" +
                        "2) " + o2.getText().toString() + "\n" +
                        "3) " + o3.getText().toString() + "\n" +
                        "4) " + o4.getText().toString() + "\n" +
                        "5) " + o5.getText().toString() + "\n" +
                        " \n```";


                EventChannel e = HomeFragment.getEvent();

                e.inThePoolWithTheBoys(msg);

                e.setPoolText(title.getText().toString() + " ," +
                        message.getText().toString() + " ," +
                        o1.getText().toString() + " ," +
                        o2.getText().toString() + " ," +
                        o3.getText().toString() + " ," +
                        o4.getText().toString() + " ," +
                        o5.getText().toString() + " ");

                showResults();
            }
            else
            {
                Toast toast = Toast.makeText(root.getContext(), "Fill the Missing fields", Toast.LENGTH_LONG);
                toast.show();
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PoolViewModel.class);
        // TODO: Use the ViewModel


    }

}

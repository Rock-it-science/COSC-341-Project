package com.example.projectprototype.ui.Results;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectprototype.EventChannel;
import com.example.projectprototype.R;
import com.example.projectprototype.ui.home.HomeFragment;


import net.dv8tion.jda.api.events.Event;

import java.util.ArrayList;

public class Results extends Fragment {

    EventChannel e;

    TextView t[];
    TextView percent[];
    Button b;

    void update()
    {
        ArrayList<Integer> r = e.results5();

        percent[0].setText( r.get(0)+ "%");
        percent[1].setText( r.get(1)+ "%");
        percent[2].setText( r.get(2)+ "%");
        percent[3].setText( r.get(3)+ "%");
        percent[4].setText( r.get(4)+ "%");

    }

    private ResultsViewModel mViewModel;

    public static Results newInstance() {
        return new Results();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {

        View root  = inflater.inflate(R.layout.results_fragment, container, false);
        e = HomeFragment.getEvent();

        if(e == null){}
        else if(e.getPoolText().length() > 0)
        {
            t = new TextView[7];
            t[0] = root.findViewById(R.id.textView13);
            t[1] = root.findViewById(R.id.textView16);
            t[2] = root.findViewById(R.id.textView22);
            t[3] = root.findViewById(R.id.textView24);
            t[4] = root.findViewById(R.id.textView29);
            t[5] = root.findViewById(R.id.textView30);
            t[6] = root.findViewById(R.id.textView31);

            percent = new TextView[5];
            percent[0] = root.findViewById(R.id.textView23);
            percent[1] = root.findViewById(R.id.textView25);
            percent[2] = root.findViewById(R.id.textView26);
            percent[3] = root.findViewById(R.id.textView27);
            percent[4] = root.findViewById(R.id.textView28);

            String pool[] = e.getPoolText().split(",");
            t[0].setText(pool[0]);
            t[1].setText(pool[1]);
            t[2].setText(pool[2]);
            t[3].setText(pool[3]);
            t[4].setText(pool[4]);
            t[5].setText(pool[5]);
            t[6].setText(pool[6]);

            update();

            b = root.findViewById(R.id.button2);

            b.setOnClickListener(v ->
            {
                update();
            });

        }
        else
        {
            Toast toast = Toast.makeText(root.getContext(), "The are no current polls up", Toast.LENGTH_LONG);
            toast.show();
        }

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ResultsViewModel.class);
        // TODO: Use the ViewModel
    }

}

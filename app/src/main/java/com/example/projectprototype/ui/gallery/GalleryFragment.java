package com.example.projectprototype.ui.gallery;

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

import com.example.projectprototype.R;
import com.example.projectprototype.ui.home.HomeFragment;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;

    Spinner sp1, sp2;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        String[] roles = HomeFragment.getEvent().getRoles();
        String[] users = HomeFragment.getEvent().getMembers();

        sp1 = root.findViewById(R.id.spinnerRoles);
        ArrayAdapter<String> adapter = new ArrayAdapter(this.getActivity(), android.R.layout.simple_spinner_item, roles);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        sp1.setAdapter(adapter);

        sp2 = root.findViewById(R.id.spinnerUsers2);
        ArrayAdapter<String> adapter2 = new ArrayAdapter(this.getActivity(), android.R.layout.simple_spinner_item, users);
        adapter2.setDropDownViewResource(android.R.layout.simple_list_item_1);
        sp2.setAdapter(adapter2);

        Button btn = (Button) root.findViewById(R.id.roleAdd);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment.getEvent().setRole(sp1.getSelectedItem().toString(), sp2.getSelectedItem().toString());
            }
        });

        return root;
    }
}

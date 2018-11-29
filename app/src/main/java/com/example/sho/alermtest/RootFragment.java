package com.example.sho.alermtest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * TODO クラス説明
 * <p>
 * Created by sho on 2017/08/29.
 */

public class RootFragment extends Fragment {

    public RootFragment() {
    }

    public static RootFragment newInstance(int position) {
        return new RootFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.root_fragment, container, false);
        Button setTimeBtn = v.findViewById(R.id.timeSetting);
        setTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTimePicker();
            }
        });
        return v;
    }

    private void openTimePicker() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        TimePick2 tp2 = TimePick2.newInstance(0);
        ft.replace(R.id.fragment_container, tp2);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}

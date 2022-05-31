package kr.kjca.project_greentopia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Nav_settings extends Fragment {

    ImageView imageView;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.nav_settings, container, false);

        //menu_light화면
        imageView = rootView.findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingController.fragmentTransaction(getActivity(), "Menu_light");
            }
        });

        //menu_water화면
        imageView2 = rootView.findViewById(R.id.imageView2);
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingController.fragmentTransaction(getActivity(), "Menu_water");
            }
        });

        //menu_culture화면
        imageView3 = rootView.findViewById(R.id.imageView3);
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingController.fragmentTransaction(getActivity(), "Menu_culture");
            }
        });

        //menu_insect화면
        imageView4 = rootView.findViewById(R.id.imageView4);
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingController.fragmentTransaction(getActivity(), "Menu_insect");
            }
        });

        imageView5 = rootView.findViewById(R.id.imageView5);
        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //menu_cooling화면
                SettingController.fragmentTransaction(getActivity(), "Menu_cooling");
            }
        });
        return rootView;
    }
}

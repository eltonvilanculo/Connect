package com.example.connect.adapter;



import com.example.connect.fragment.ChatsFragment;
import com.example.connect.fragment.ContactosFragment;
import com.example.connect.fragment.PerfilFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position ==0){
            fragment = new ChatsFragment();
        }
        if (position == 1){
            fragment = new ContactosFragment();
        }
        if (position ==2){
            fragment = new PerfilFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    public CharSequence getPageTitle(int position){
        String name = null;
        if (position==0){
            name = "Chats";
        }
        if (position == 1){
            name = "Contactos";
        }
        if (position == 2){
            name = "Perfil";
        }
        return name;
    }
}

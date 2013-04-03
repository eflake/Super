package com.lavaspark.adapter;

import com.lavaspark.ssf4.CharacterFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

	public SectionsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int i) {
		Fragment fragment = new CharacterFragment();
        Bundle args = new Bundle();
        args.putInt("position", i + 1);
        fragment.setArguments(args);
        return fragment;	
        }

	@Override
	public int getCount() {
		return 39;
	}
	
	 @Override
     public CharSequence getPageTitle(int position) {
         switch (position) {
             case 0: return "A";
             case 1: return "B";
             case 2: return "C";
         }
         return null;
     }

}

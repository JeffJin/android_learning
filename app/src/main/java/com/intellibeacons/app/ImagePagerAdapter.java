package com.intellibeacons.app;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by jeffjin on 2014-07-24.
 */
public class ImagePagerAdapter extends FragmentPagerAdapter {


    private final FragmentManager fm;
    private final Context context;

    public ImagePagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.fm = fm;
        this.context = context;
    }

    @Override
    public Fragment getItem(int i) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}

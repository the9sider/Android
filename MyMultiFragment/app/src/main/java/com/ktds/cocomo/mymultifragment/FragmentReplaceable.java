package com.ktds.cocomo.mymultifragment;

import android.support.v4.app.Fragment;

import java.io.Serializable;

/**
 * Created by 206-032 on 2016-06-16.
 */
public interface FragmentReplaceable extends Serializable {

    public void replaceFragment(int fragmentId);
    public void replaceFragment(Fragment fragment);

}

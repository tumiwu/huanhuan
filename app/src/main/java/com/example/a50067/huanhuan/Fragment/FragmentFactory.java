package com.example.a50067.huanhuan.Fragment;

import java.util.HashMap;

/**
 * Created by 50067 on 2018/5/21.
 */

public class FragmentFactory {
    private static HashMap<Integer,BaseFragment> mBaseFragments=new HashMap<Integer, BaseFragment>();

    public static BaseFragment createFragment(int pos){
        BaseFragment baseFragment=mBaseFragments.get(pos);
        if(baseFragment==null){
            switch (pos){
                case 0:
                    baseFragment=new MainFragment();
                    break;
                case 1:
                    baseFragment=new ChatFragment();
                    break;
                case 2:
                    baseFragment=new StarFragment();
                    break;
                case 3:
                    baseFragment=new PersonnalFragment();
                    break;
            }
            mBaseFragments.put(pos,baseFragment);
        }
        return baseFragment;
    }
}

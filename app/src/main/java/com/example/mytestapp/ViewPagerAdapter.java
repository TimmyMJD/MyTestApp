package com.example.mytestapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

import static androidx.recyclerview.widget.RecyclerView.NO_ID;
import static androidx.viewpager.widget.PagerAdapter.POSITION_NONE;

public class ViewPagerAdapter extends FragmentStateAdapter {

    private List<Integer> fragmentList = new ArrayList();
    private ContentFragment.Callback callback;

    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, @NonNull ContentFragment.Callback callback) {
        super(fragmentManager, lifecycle);
        this.callback = callback;
    }

    public void setFragmentList(List<Integer> fragmentList) {
        this.fragmentList = fragmentList;
    }

    public List<Integer> getFragmentList() {
        return fragmentList;
    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }

    @Override
    public Fragment createFragment(int position) {
        ContentFragment fragment = ContentFragment.newInstance(position != 0, fragmentList.get(position));
        fragment.clickCallback = callback;
        return fragment;
    }

    @Override
    public long getItemId(int position) {
        return fragmentList.get(position);
    }
}



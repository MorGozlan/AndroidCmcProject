package com.morgozlan.cryptoproject.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.morgozlan.cryptoproject.R;

/**
 * Created by Mor Gozlan on 23/04/2018.
 */

public class PortfolioFragment extends Fragment{
    View root;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        root = LayoutInflater.from(getActivity()).inflate(R.layout.activity_portfolio, null);
        return root;
    }
}
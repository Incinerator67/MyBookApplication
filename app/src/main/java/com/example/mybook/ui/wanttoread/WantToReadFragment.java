package com.example.mybook.ui.wanttoread;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mybook.databinding.FragmentWantreadBinding;

public class WantToReadFragment extends Fragment {

    private FragmentWantreadBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        WantToReadViewModel slideshowViewModel = new ViewModelProvider(this).get(WantToReadViewModel.class);

        binding = FragmentWantreadBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textWantToRead;
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
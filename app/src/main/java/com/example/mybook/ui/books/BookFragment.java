package com.example.mybook.ui.books;


import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mybook.PdfModel.PdfAdapter;
import com.example.mybook.PdfModel.PdfFile;
import com.example.mybook.databinding.FragmentBookBinding;

import java.io.File;
import java.util.ArrayList;

public class BookFragment extends Fragment {
    private ListView listView;
    private FragmentBookBinding binding;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private ArrayList list = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        BookViewModel homeViewModel = new ViewModelProvider(this).get(BookViewModel.class);

        binding = FragmentBookBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textBook;
        listView = binding.listView;   listView.setVisibility(View.GONE);
        final ImageButton imageButton = binding.displayFiles;

        if (checkPermission()) {

            imageButton.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            initViews();
        } else {
            homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    requestPermission();
                } // Request Permission
            });
        }
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult( ActivityResult result ) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                    Toast.makeText(requireContext(), "You Denied the permission", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (Environment.isExternalStorageManager())
                        Toast.makeText(requireContext(),"We Have Permission",Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(requireContext(), "You Denied the permission", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        } else {
            int readCheck = ContextCompat.checkSelfPermission(getContext(), READ_EXTERNAL_STORAGE);
            int writeCheck = ContextCompat.checkSelfPermission(getContext(), WRITE_EXTERNAL_STORAGE);
            return readCheck == PackageManager.PERMISSION_GRANTED && writeCheck == PackageManager.PERMISSION_GRANTED;
        }
    }
    private String[] permissions = {READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE};
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            new AlertDialog.Builder(requireActivity())
                    .setTitle("Permission")
                    .setMessage("Please give the Storage permission")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick( DialogInterface dialog, int which ) {
                            try {
                                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                                intent.addCategory("android.intent.category.DEFAULT");
                                intent.setData(Uri.parse(String.format("package:%s", new Object[]{requireContext().getApplicationContext().getPackageName()})));
                                activityResultLauncher.launch(intent);
                            } catch (Exception e) {
                                Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                                activityResultLauncher.launch(intent);
                            }
                        }
                    })
                    .setCancelable(false)
                    .show();
        } else {ActivityCompat.requestPermissions(requireActivity(), permissions, 30);}
    }
    private void initViews() {
        // получаем путь до внешнего хранилища
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        initList(path);
        // устанавливаем адаптер в ListView
        listView.setAdapter(new PdfAdapter(requireContext(), list));
    }
    private void initList(String path) {
        try {
            File file = new File(path);
            File[] fileList = file.listFiles();
            String fileName;
            for (File f : fileList) {
                if (f.isDirectory()) {
                    initList(f.getAbsolutePath());
                } else {
                    fileName = f.getName();
                    if (fileName.endsWith(".pdf")) {
                        list.add(new PdfFile(fileName, f.getAbsolutePath(),false,false));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
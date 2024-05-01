package com.example.mybook.PdfModel;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mybook.R;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

public class PdfActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pdf);

        String pdfFilePath = getIntent().getStringExtra("pdfFilePath");
        Uri pdfUri = Uri.fromFile(new File(pdfFilePath));

        PDFView pdfView = findViewById(R.id.pdfView);

        pdfView.fromUri(pdfUri)
                .defaultPage(0)
                .spacing(10)
                .load();
    }
}
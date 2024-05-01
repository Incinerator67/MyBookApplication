package com.example.mybook.PdfModel;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mybook.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class PdfAdapter extends BaseAdapter {
    private Context mContext;
    private List<PdfFile> mPdfFiles;
    private LayoutInflater mInflater;

    public PdfAdapter(Context context, List<PdfFile> pdfFiles) {
        mContext = context;
        mPdfFiles = pdfFiles;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mPdfFiles.size();
    }

    @Override
    public PdfFile getItem(int position) {
        return mPdfFiles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_pdf, parent, false);
            holder = new ViewHolder();
            holder.fileNameTextView = convertView.findViewById(R.id.textFileName);
            holder.ImagePdfRead = convertView.findViewById(R.id.image_pdf_for_read);
            holder.favoriteButton = convertView.findViewById(R.id.buttonFavorite);
            holder.readLaterButton = convertView.findViewById(R.id.buttonReadLater);
            holder.shareButton = convertView.findViewById(R.id.buttonShare);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        PdfFile pdfFile = getItem(position);
        holder.fileNameTextView.setText(pdfFile.getFileName());
        // Установите обработчики кнопок и их состояния в соответствии с PdfFile
        holder.ImagePdfRead.setOnClickListener(v -> {
            Context context = v.getContext();

            // Создаем Intent для перехода в PdfActivity и передачи данных
            Intent intent = new Intent(context, PdfActivity.class);
            intent.putExtra("pdfFilePath", pdfFile.getFilePath()); // Передаем путь к файлу
            context.startActivity(intent);
        });
        holder.favoriteButton.setOnClickListener(v -> {

        });
        holder.readLaterButton.setOnClickListener(v -> {

        });
        holder.shareButton.setOnClickListener(v -> {
            Context context = v.getContext();
            // Создаем URI для временного файла в каталоге Cache приложения
            File pdfTempFile = new File(context.getCacheDir(), pdfFile.getFileName());
            try {
                // Копируем содержимое PDF файла во временный файл
                FileInputStream inputStream = new FileInputStream(pdfFile.getFilePath());
                FileOutputStream outputStream = new FileOutputStream(pdfTempFile);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
                outputStream.flush();
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // Создаем Intent для действия отправки
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("application/pdf");
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(pdfFile.getFilePath()));
            context.startActivity(Intent.createChooser(shareIntent, "Поделиться файлом"));
        });
        return convertView;
    }

    static class ViewHolder {
        TextView fileNameTextView;
        ImageView ImagePdfRead;
        ImageButton favoriteButton;
        ImageButton readLaterButton;
        ImageButton shareButton;
    }

}

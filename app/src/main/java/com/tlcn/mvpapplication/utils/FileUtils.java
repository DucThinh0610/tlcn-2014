package com.tlcn.mvpapplication.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import com.tlcn.mvpapplication.BuildConfig;
import com.tlcn.mvpapplication.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class FileUtils {
    public static final String JPEG_FILE_PREFIX = "IMG_";
    public static final String JPEG_FILE_SUFFIX = ".jpg";

    public static File convertUriToFile(Context context, Uri uri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        if (isMediaDocument(uri)) {
            final String docId = DocumentsContract.getDocumentId(uri);
            String[] slip = docId.split(":");
            final String type = slip[0];

            Uri contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

            final String selection = "_id=?";
            final String[] selectionArgs = new String[]{slip[1]};

            String filePath = getDataColumn(context, contentUri, selection, selectionArgs);

            if (filePath != null)
                return new File(filePath);
            else
                return null;
        } else {
            try {
                Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
                assert cursor != null;
                int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                File file = new File(cursor.getString(index));
                cursor.close();
                return file;

            } catch (Exception e) {
                return new File(uri.getPath());
            }
        }
    }

    public static MultipartBody.Part createMultipartBodyPart(Context context, File file) {
        return MultipartBody.Part.createFormData("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
    }

    private static String getDataColumn(Context context, Uri uri,
                                        String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static File startCameraScreen(Activity activity, int requestCaptureImage) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file;
        try {
            file = createTempFile(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    activity.getResources().getString(R.string.app_name),
                    JPEG_FILE_PREFIX,
                    JPEG_FILE_SUFFIX
            );
            if (file == null) {
                return null;
            }
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, getUriFromFile(activity, file));
            activity.startActivityForResult(takePictureIntent, requestCaptureImage);
        } catch (IOException e) {
            e.printStackTrace();
            file = null;
        }
        return file;
    }

    public static File createTempFile(File rootDir, String dirName, String prefix, String suffix) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String fileName = prefix + timeStamp + "_";
        File storageDir = getStorageDir(rootDir, dirName);
        if (storageDir == null) {
            return null;
        }
        return File.createTempFile(fileName, suffix, storageDir);
    }

    private static File getStorageDir(File dir, String name) {
        File storageDir = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            storageDir = new File(dir, name);
            if (!storageDir.mkdirs()) {
                if (!storageDir.exists()) {
                    return null;
                }
            }
        }
        return storageDir;
    }
    public static Uri getUriFromFile(Context context, File file) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file);
        }
        return Uri.fromFile(file);
    }
}

package com.tlcn.mvpapplication.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by ducthinh on 22/09/2017.
 */

public class FileUtils {
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
}

package com.ghostFood.util;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ghostFood.callback.ProfileCallback;

import java.io.File;

/**
 * Created by android1 on 6/6/2017.
 */

public class ProfileImage {
    public static final int SELECT_PICTURE = 1;
    public static final int CAMERA_REQUEST = 1888;

    public void openGallery(Activity activity) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(Intent.createChooser(intent, "Select Picture"), ProfileImage.SELECT_PICTURE);

    }

    public String paymentcameraIntent(Activity activity) {
        String imagePath1 = "";
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        {
            Uri fileUri = getOutputMediaFileUri(activity);
            imagePath1 = fileUri.getPath();
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            activity.startActivityForResult(intent, ProfileImage.CAMERA_REQUEST);
        }

        return imagePath1;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void ProcessProfileImage(Context context, String imagePath1,
                                    int requestCode, int resultCode, Intent data,
                                    int RESULT_OK, final SimpleDraweeView iv_profilepicture) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedMediaUri = data.getData();


                if (data.getData() != null) {
                    Uri selectedImageUri = data.getData();
                    if (null != selectedImageUri) {

                        imagePath1 = getPath1(context, selectedImageUri);
                        CommonFunctions.getInstance().UploadProfile(context, imagePath1, new ProfileCallback.ImageListener() {
                            @Override
                            public void onSuccess(String profileImageUrl) {
                                CommonFunctions.getInstance().SetProfilePicture(iv_profilepicture);
                            }

                            @Override
                            public void onFailure(String profileImageUrl) {

                            }
                        });
                    }
                }


            } else if (requestCode == CAMERA_REQUEST) {


                int currentapiVersion = Build.VERSION.SDK_INT;

                Uri ss = null;

                if (currentapiVersion > 23) {
                    ss = Uri.parse(imagePath1);
                    String fileName = ss.getLastPathSegment();
                    imagePath1 = "/storage/emulated/0/Pictures/Saddid/" + fileName;
                }
                File imgFile = new File(imagePath1);
                if (imgFile.exists()) {
                    CommonFunctions.getInstance().UploadProfile(context, imagePath1, new ProfileCallback.ImageListener() {
                        @Override
                        public void onSuccess(String profileImageUrl) {
                            CommonFunctions.getInstance().SetProfilePicture(iv_profilepicture);
                        }

                        @Override
                        public void onFailure(String profileImageUrl) {

                        }
                    });
                }


            }
        }
    }

    private Uri getOutputMediaFileUri(Context context) {
        Uri photoURI = null;
        int currentapiVersion = Build.VERSION.SDK_INT;
        if (currentapiVersion <= 23) {
            photoURI = Uri.fromFile(getOutputMediaFile());
        } else {
            photoURI = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", getOutputMediaFile());
        }


        return photoURI;
    }

    private static File getOutputMediaFile() {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Saddid");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("Saddid", "failed to create directory");
                return null;
            }
        }

        File mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                "img_" + "1" + ".jpg");

        if (mediaFile.exists())
            mediaFile.delete();

        return mediaFile.getAbsoluteFile();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getPath1(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }
}

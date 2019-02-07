package com.ghostFood.callback;

/**
 * Created by android1 on 6/6/2017.
 */

public class ProfileCallback {
    public interface ImageListener {
        public void onSuccess(String profileImageUrl);
        public void onFailure(String profileImageUrl);
    }

    private ProfileCallback.ImageListener listener;
}
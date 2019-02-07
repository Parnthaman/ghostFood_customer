package com.ghostFood.callback;

/**
 * Created by android1 on 6/6/2017.
 */

public class CommonCallback {
    public interface Listener {
        public void onSuccess();
        public void onFailure();
    }

    private Listener listener;
}

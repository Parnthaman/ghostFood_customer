package com.ghostFood.callback;

import com.ghostFood.util.CommissionDetails;

/**
 * Created by android1 on 6/6/2017.
 */

public class CommissionCallback {
    public interface CommissionListener {
        public void onSuccess(CommissionDetails commissionDetails);
        public void onFailure(CommissionDetails commissionDetails);
    }

    private CommissionListener listener;
}

package com.ghostFood.util;

import android.content.Context;
import android.view.Window;
import android.widget.LinearLayout;

import com.ghostFood.R;
import com.ghostFood.acitivity.MyApplication;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class CustomProgressDialog {
    private static CustomProgressDialog ourInstance = new CustomProgressDialog();
    ACProgressFlower dialog;
    // SimpleArcDialog mDialog;
    //ArcConfiguration mArcConfig;

//    Dialog dialog;

    public static CustomProgressDialog getInstance() {
        return ourInstance;
    }

    private CustomProgressDialog() {

    }

    public void show(Context mContext) {
//        if (dialog != null)
//            dialog.dismiss();
        /*mDialog = new SimpleArcDialog(mContext);
        mArcConfig = new ArcConfiguration(mContext);
        mDialog.setCancelable(false);
        mDialog.setConfiguration(mArcConfig);
        mDialog.show();*/

//        dialog = new ACProgressFlower(mContext);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(false);
//        dialog.setContentView(R.layout.dialogss);
//        LinearLayout mkLoader=dialog.findViewById(R.id.llView);
//        mkLoader.setBackgroundColor(mContext.getResources().getColor(android.R.color.transparent));
//        dialog.show();

        dialog = new ACProgressFlower.Builder(mContext)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(MyApplication.context.getResources().getColor(R.color.white))
                .bgColor(MyApplication.context.getResources().getColor(R.color.colorPrimary))
                .fadeColor(MyApplication.context.getResources().getColor(R.color.colorPrimary)).build();
        dialog.show();


    }

    public void dismiss() {
//        if (dialog != null)
//            dialog.dismiss();
        try {
            if (dialog != null)
                dialog.dismiss();
        } catch (Exception p) {
            p.printStackTrace();
        } catch (Error e) {
            e.printStackTrace();
        }
    }

    public boolean isShowing() {
        if (dialog != null && dialog.isShowing())
            return true;
        else return false;
    }


}

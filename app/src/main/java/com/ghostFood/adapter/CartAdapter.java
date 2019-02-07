package com.ghostFood.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ghostFood.R;
import com.ghostFood.acitivity.Home;
import com.ghostFood.callback.CommonCallback;
import com.ghostFood.events.EBRefreshCart;
import com.ghostFood.model.CartItem;
import com.ghostFood.util.CartDB;
import com.ghostFood.util.CommonFunctions;
import com.ghostFood.util.Constants;
import com.ghostFood.util.ConstantsInternal;
import com.ghostFood.util.MyActivity;
import com.ghostFood.util.SessionManager;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by android1 on 5/12/2016.
 */

public class CartAdapter extends BaseAdapter {
    Context mContext;
    List<CartItem> mCartItemList;
    private static LayoutInflater inflater = null;
    private int lastPosition = -1;
    Typeface mTypeface;

    public CartAdapter(Context context, List<CartItem> mCartItemList) {
        this.mContext = context;
        this.mCartItemList = mCartItemList;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public int getCount() {
        return mCartItemList.size();
    }

    @Override
    public CartItem getItem(int position) {
        return mCartItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder mHolder = new ViewHolder();
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.adapter_cart, parent, false);
            mHolder.tvItemName = (TextView) convertView.findViewById(R.id.tvItemName);
            mHolder.tvModifierName = (TextView) convertView.findViewById(R.id.tvModifierName);
            mHolder.tvIngrdientsName = (TextView) convertView.findViewById(R.id.tvIngrdientsName);
            mHolder.tvSpecialOffers = (TextView) convertView.findViewById(R.id.tvSpecialOffers);
            mHolder.tvQuantity = (TextView) convertView.findViewById(R.id.tvQuantity);
            mHolder.tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);

            mHolder.ivIncrease = (ImageView) convertView.findViewById(R.id.ivIncrease);
            mHolder.ivDecrease = (ImageView) convertView.findViewById(R.id.ivDecrease);
            mHolder.ivDelete = (ImageView) convertView.findViewById(R.id.ivDelete);
            mHolder.llDeleteCart = (LinearLayout) convertView.findViewById(R.id.llDeleteCart);

            mHolder.btnPrice = (TextView) convertView.findViewById(R.id.btnPrice);
            mHolder.ivItemImage = (SimpleDraweeView) convertView.findViewById(R.id.ivItemImage);

            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        mHolder.tvItemName.setText(getItem(position).getItemName());
        mHolder.tvModifierName.setText("");
        mHolder.tvIngrdientsName.setText("");
        mHolder.tvSpecialOffers.setText("");

        mHolder.tvModifierName.setVisibility(View.GONE);
        mHolder.tvIngrdientsName.setVisibility(View.INVISIBLE);
        mHolder.tvSpecialOffers.setVisibility(View.GONE);

        if (getItem(position).getModifierList() != null && !getItem(position).getModifierList().trim().isEmpty()) {
            mHolder.tvModifierName.setVisibility(View.VISIBLE);
            mHolder.tvModifierName.setText(CommonFunctions.getInstance().fromHtml(getItem(position).getModifierList()));
        }

        if (getItem(position).getIngrdientsList() != null && !getItem(position).getIngrdientsList().trim().isEmpty()) {
            mHolder.tvIngrdientsName.setVisibility(View.VISIBLE);
            mHolder.tvIngrdientsName.setText(CommonFunctions.getInstance().fromHtml(getItem(position).getIngrdientsList()));
        }

        if (getItem(position).getSpecialOffers() != null && !getItem(position).getSpecialOffers().trim().isEmpty()) {
            mHolder.tvSpecialOffers.setVisibility(View.VISIBLE);
            mHolder.tvSpecialOffers.setText(CommonFunctions.getInstance().fromHtml(getItem(position).getSpecialOffers()));
        }

        mHolder.tvQuantity.setText(String.valueOf(getItem(position).getQuantity()));
        mHolder.tvPrice.setText("= " + CommonFunctions.DeciamlDigitsAfterDotValue.getWithCurrencyCode(getItem(position).getTotalPrice()));
        mHolder.btnPrice.setText(CommonFunctions.DeciamlDigitsAfterDotValue.getWithCurrencyCodeOriginal(getItem(position).getTotalPrice()));
        CommonFunctions.getInstance().LoadImageByFrescoOld(mHolder.ivItemImage, getItem(position).getItemImage());

        mHolder.ivDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mCartItemList.get(position).getItemType() == ConstantsInternal.ItemType.Item) {
                    if (CartDB.getInstance().GetItems().get(position).getQuantity() - 1 <= 0) {
                        final Dialog dialog = new Dialog(mContext);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.dialog_alerts);
                        TextView tvAlertTxt = dialog.findViewById(R.id.tvAlertTxt);
                        TextView tvAlertDescription = dialog.findViewById(R.id.tvAlertDescription);
                        TextView tvCancel = dialog.findViewById(R.id.tvCancel);
                        TextView tvYes = dialog.findViewById(R.id.tvYes);
                        tvCancel.setText(Constants.Cancel);
                        tvYes.setText(Constants.Yes + "," + " " + Constants.DeleteIt);
                        tvAlertDescription.setText(Constants.thisItemwillBeClearFromCart);
                        tvAlertTxt.setText(Constants.areYouSure);
                        tvYes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                CartDB.getInstance().Delete(mCartItemList.get(position).getsNo(), new CommonCallback.Listener() {
                                    @Override
                                    public void onSuccess() {
                                        EventBus.getDefault().post(new EBRefreshCart());
                                    }

                                    @Override
                                    public void onFailure() {

                                    }
                                });

                            }
                        });
                        tvCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        Window window = dialog.getWindow();
                        WindowManager.LayoutParams wlp = window.getAttributes();
                        wlp.gravity = Gravity.CENTER;
                        window.setAttributes(wlp);
                        dialog.show();
                    } else {
                        CartDB.getInstance().DecreaseItem(mCartItemList.get(position).getsNo(), new CommonCallback.Listener() {
                            @Override
                            public void onSuccess() {
                                EventBus.getDefault().post(new EBRefreshCart());
                            }

                            @Override
                            public void onFailure() {
                                CommonFunctions.getInstance().ShowSnackBar(mContext, Constants.ErrorDecrease);
                            }
                        });
                    }


                } else if (mCartItemList.get(position).getItemType() == ConstantsInternal.ItemType.SpecialItem) {
                    CartDB.getInstance().DecreaseSpecialOffer(mCartItemList.get(position).getItemKey(), new CommonCallback.Listener() {
                        @Override
                        public void onSuccess() {
                            EventBus.getDefault().post(new EBRefreshCart());
                        }

                        @Override
                        public void onFailure() {
                            CommonFunctions.getInstance().ShowSnackBar(mContext, Constants.ErrorDecrease);
                        }
                    });
                }
            }
        });

        mHolder.llDeleteCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog;
                dialog = new Dialog(new ContextThemeWrapper(mContext, R.style.DialogSlideAnim));
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_alerts);
                TextView tvAlertTxt = dialog.findViewById(R.id.tvAlertTxt);
                TextView tvAlertDescription = dialog.findViewById(R.id.tvAlertDescription);
                TextView tvCancel = dialog.findViewById(R.id.tvCancel);
                TextView tvYes = dialog.findViewById(R.id.tvYes);
                tvCancel.setText(Constants.Cancel);
                tvYes.setText(Constants.Yes + "," + " " + Constants.DeleteIt);
                tvAlertDescription.setText(Constants.thisItemwillBeClearFromCart);
                tvAlertTxt.setText(Constants.areYouSure);
                tvYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        CartDB.getInstance().Delete(mCartItemList.get(position).getsNo(), new CommonCallback.Listener() {
                            @Override
                            public void onSuccess() {
                                EventBus.getDefault().post(new EBRefreshCart());
                            }

                            @Override
                            public void onFailure() {

                            }
                        });

                    }
                });

                tvCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.CENTER;
                window.setGravity(Gravity.CENTER);
                window.setAttributes(wlp);
                dialog.show();
            }
        });

        mHolder.ivIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mCurrentQty = mCartItemList.get(position).getQuantity();
                if (mCurrentQty < 100) {
                    if (mCartItemList.get(position).getItemType() == ConstantsInternal.ItemType.Item) {
                        CartDB.getInstance().IncreaseItem(mCartItemList.get(position).getsNo(), new CommonCallback.Listener() {
                            @Override
                            public void onSuccess() {
                                EventBus.getDefault().post(new EBRefreshCart());
                            }

                            @Override
                            public void onFailure() {
                                CommonFunctions.getInstance().ShowSnackBar(mContext, Constants.ErrorIncrease);
                            }
                        });
                    } else if (mCartItemList.get(position).getItemType() == ConstantsInternal.ItemType.SpecialItem) {
                        CartDB.getInstance().IncreaseSpecialOffer(mCartItemList.get(position).getItemKey(), new CommonCallback.Listener() {
                            @Override
                            public void onSuccess() {
                                EventBus.getDefault().post(new EBRefreshCart());
                            }

                            @Override
                            public void onFailure() {
                                CommonFunctions.getInstance().ShowSnackBar(mContext, Constants.ErrorIncrease);
                            }
                        });
                    }
                }
            }
        });


        mHolder.ivIncrease.setVisibility(View.VISIBLE);
        mHolder.ivDecrease.setVisibility(View.VISIBLE);
        if (getItem(position).getUserScope() == ConstantsInternal.CouponScope.User.getValue()) {
            mHolder.ivIncrease.setVisibility(View.GONE);
        }

        return convertView;
    }

    public class ViewHolder {
        TextView tvItemName, tvModifierName, tvIngrdientsName, tvQuantity, tvSpecialOffers, tvPrice;
        TextView btnPrice;
        ImageView ivIncrease, ivDecrease, ivDelete;
        SimpleDraweeView ivItemImage;
        LinearLayout llDeleteCart;

    }

    public void notifyDataSet(List<CartItem> mCartItemList) {
        this.mCartItemList = mCartItemList;
        notifyDataSetChanged();
    }
}
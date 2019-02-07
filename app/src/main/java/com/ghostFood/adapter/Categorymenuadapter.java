package com.ghostFood.adapter;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ghostFood.R;
import com.ghostFood.api.ItemAPI;
import com.ghostFood.callback.CommonCallback;
import com.ghostFood.util.CartDB;
import com.ghostFood.util.CommonFunctions;
import com.ghostFood.util.Constants;
import com.ghostFood.util.FontFunctions;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tech on 4/19/2018.
 */

public class Categorymenuadapter extends RecyclerView.Adapter<Categorymenuadapter.MyViewHolder> {

    private final AppCompatActivity context;
    private final List<ItemAPI.Ingredient> ingredient;
    private final TextView tvCartCount;
    private final ItemAPI.Data itemData;
    public ItemAPI.ItemIngredient itemModifier;
    public ArrayList<ItemAPI.ItemIngredient> itemIngredients = new ArrayList<>();

    public Categorymenuadapter(AppCompatActivity context, List<ItemAPI.Ingredient> ingredient, TextView tvCartCount, ItemAPI.Data itemData) {
        this.context = context;
        this.ingredient = ingredient;
        this.tvCartCount=tvCartCount;
        this.itemData=itemData;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_categorymenuadapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        FontFunctions.getInstance().FontBalooBhaijaan(context, holder.txt_price);
        FontFunctions.getInstance().FontBalooBhaijaan(context, holder.txtingrident);
        holder.txt_price.setText(CommonFunctions.DeciamlDigitsAfterDotValue.getWithCurrencyCodeOriginal(ingredient.get(position).getPrice()) + ingredient.get(position).getPrice() + "");
        holder.txtingrident.setText(ingredient.get(position).getDetails().getIngredientName());


    }


    @Override
    public int getItemCount() {
        return ingredient.size();
    }
    private void AddToCart() {

        Gson gson = new GsonBuilder().create();

        ArrayList<ItemAPI.Ingredient> modifiers = new ArrayList<>();


        if (itemModifier != null) {
            for (ItemAPI.Ingredient ingredient : itemModifier.getIngredients()) {
                if (!ingredient.getSelected()) {
                    ingredient.setGroupKey(itemModifier.getItemIngredientGroupKey());
                    modifiers.add(ingredient);
                }
            }
        }


        String modifiersJSON = gson.toJsonTree(modifiers).toString();


        ArrayList<ItemAPI.Ingredient> ingredients = new ArrayList<>();
        if (itemIngredients != null) {
            for (ItemAPI.ItemIngredient itemIngredient : itemIngredients) {

                Integer minimum = itemIngredient.getMinimum();
                Integer maximum = itemIngredient.getMaximum();

                Integer itemIngredientCount = 0;
                for (ItemAPI.Ingredient ingredient : itemIngredient.getIngredients()) {
                    if (ingredient.getSelected() && ingredient.getDetails() != null) {
                        ingredient.setGroupKey(itemIngredient.getItemIngredientGroupKey());
                        ingredients.add(ingredient);
                        itemIngredientCount++;
                    }
                }

                if (itemIngredientCount < minimum) {
                    CommonFunctions.getInstance().ShowSnackBar(context, MessageFormat.format(Constants.PleaseSelectMinimumIngredients, minimum.toString(), itemIngredient.getGroupName()));
                    return;
                } else if (itemIngredientCount > maximum) {
                    CommonFunctions.getInstance().ShowSnackBar(context, MessageFormat.format(Constants.PleaseSelectMaximumIngredients, maximum.toString(), itemIngredient.getGroupName()));
                    return;
                }
            }
        }
        String ingredientsJSON = gson.toJsonTree(ingredients).toString();


        String imagePath = itemData.getItemImage().size() > 0 ? itemData.getItemImage().get(0).getItemImagePath() : "";


        // as we are showing special offers as seperate item we are making "specialOffersJSON" as empty string to avoid any calcualtion issues
        CartDB.getInstance().Add(itemData.getItemKey(), itemData.getItemName(), modifiersJSON, ingredientsJSON, "", itemData.getItemPrice(), imagePath, new CommonCallback.Listener() {
            @Override
            public void onSuccess() {
                CommonFunctions.getInstance().ShowCartCount(tvCartCount);
                //CommonFunctions.getInstance().FinishActivityWithDelay(context);
            }

            @Override
            public void onFailure() {
                CommonFunctions.getInstance().ShowCartCount(tvCartCount);
            }
        });

        /*for(SpecialOffer specialOffer : specialOfferAdapter.specialOffer) {
            if(specialOffer.getSelected()) {
                CartDB.getInstance().AddSpecialOffer(specialOffer.getOfferKey(), specialOffer.getOffername(), specialOffer.getOfferPrice(), specialOffer.getItemImage());
            }
        }*/

        CommonFunctions.getInstance().ShowCartCount(tvCartCount);
        CommonFunctions.getInstance().ShowSnackBar(context, Constants.AddedToCart);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout rlyout_parent;
        ImageView img_ingredient;
        TextView txt_price, txtingrident;
        CheckBox checkbox;
        RelativeLayout lay_ingredient;

        MyViewHolder(View itemView) {
            super(itemView);

            img_ingredient = (ImageView) itemView.findViewById(R.id.img_ingredient);
            txt_price = (TextView) itemView.findViewById(R.id.txt_price);
            txtingrident = (TextView) itemView.findViewById(R.id.txtingrident);
            checkbox = (CheckBox) itemView.findViewById(R.id.checkbox);
            lay_ingredient = (RelativeLayout) itemView.findViewById(R.id.lay_ingredient);

        }
    }


}
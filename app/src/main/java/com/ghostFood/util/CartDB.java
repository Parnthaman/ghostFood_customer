package com.ghostFood.util;

import android.app.Dialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.ghostFood.R;
import com.ghostFood.acitivity.MyApplication;
import com.ghostFood.api.ItemAPI;
import com.ghostFood.callback.CommonCallback;
import com.ghostFood.db.Items;
import com.ghostFood.db.SpecialItems;
import com.ghostFood.events.EBRefreshCart;
import com.ghostFood.model.CheckoutExtras;
import com.ghostFood.model.CheckoutItemDetails;
import com.ghostFood.model.SpecialOffer;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by android1 on 7/3/2017.
 */

public class CartDB {

    private static CartDB ourInstance = new CartDB();

    public static CartDB getInstance() {
        return ourInstance;
    }

    public ArrayList<CheckoutItemDetails> GetItems() {
        Gson gson = new GsonBuilder().create();

        Realm realm = Realm.getDefaultInstance();
        RealmResults<Items> items = realm.where(Items.class).findAll();
        ArrayList<CheckoutItemDetails> checkoutItemDetails = new ArrayList<>();


        for (Items item : items) {
            CheckoutItemDetails checkoutItem = new CheckoutItemDetails();
            checkoutItem.setItem_key(item.getItemKey());
            checkoutItem.setQuantity(item.getQuantity());
            checkoutItem.setItem_price(item.getPrice());

            ArrayList<CheckoutExtras> checkoutModifiers = new ArrayList<>();
            ArrayList<ItemAPI.Ingredient> modifiers = gson.fromJson(item.getModifier(), new TypeToken<List<ItemAPI.Ingredient>>() {
            }.getType());
            for (ItemAPI.Ingredient modifier : modifiers) {

                CheckoutExtras checkoutIngredient = new CheckoutExtras();
                checkoutIngredient.setKey(modifier.getDetails().getIngredientKey());
                checkoutIngredient.setPrice(modifier.getPrice());
                checkoutIngredient.setGroup_key(modifier.getGroupKey());
                checkoutModifiers.add(checkoutIngredient);

            }
            checkoutItem.setModifiers(checkoutModifiers);

            ArrayList<CheckoutExtras> checkoutIngredients = new ArrayList<>();
            ArrayList<ItemAPI.Ingredient> ingredients = gson.fromJson(item.getIngredients(), new TypeToken<List<ItemAPI.Ingredient>>() {
            }.getType());
            for (ItemAPI.Ingredient ingredient : ingredients) {
                CheckoutExtras checkoutIngredient = new CheckoutExtras();
                checkoutIngredient.setKey(ingredient.getDetails().getIngredientKey());
                checkoutIngredient.setPrice(ingredient.getPrice());
                checkoutIngredient.setGroup_key(ingredient.getGroupKey());
                checkoutIngredient.setIs_selected_drink(ingredient.getSelectedDrink());
                checkoutIngredients.add(checkoutIngredient);

            }
            checkoutItem.setIngrdients(checkoutIngredients);

            ArrayList<CheckoutExtras> checkoutSpecialOffers = new ArrayList<>();
            if (!item.getSpecialOffer().trim().isEmpty()) {
                ArrayList<SpecialOffer> specialOffers = gson.fromJson(item.getSpecialOffer(), new TypeToken<List<SpecialOffer>>() {
                }.getType());
                for (SpecialOffer specialOffer : specialOffers) {
                    CheckoutExtras checkoutSpecialOffer = new CheckoutExtras();
                    checkoutSpecialOffer.setKey(specialOffer.getOfferKey());
                    checkoutSpecialOffer.setPrice(specialOffer.getOfferPrice());
                    checkoutSpecialOffers.add(checkoutSpecialOffer);
                }
            }
            checkoutItem.setSpecialOffers(checkoutSpecialOffers);
            checkoutItem.setQuantity(item.getQuantity());
            checkoutItemDetails.add(checkoutItem);
        }
        return checkoutItemDetails;
    }

    public ArrayList<CheckoutItemDetails> GetOffers() {

        Realm realm = Realm.getDefaultInstance();
        RealmResults<SpecialItems> items = realm.where(SpecialItems.class).findAll();
        ArrayList<CheckoutItemDetails> checkoutItemDetails = new ArrayList<>();


        for (SpecialItems item : items) {
            CheckoutItemDetails checkoutItem = new CheckoutItemDetails();
            checkoutItem.setItem_key(item.getSpecialOfferKey());
            checkoutItem.setQuantity(item.getQuantity());
            checkoutItem.setItem_price(item.getPrice());

            checkoutItem.setModifiers(null);
            checkoutItem.setIngrdients(null);
            checkoutItem.setSpecialOffers(null);

            checkoutItem.setQuantity(item.getQuantity());
            checkoutItemDetails.add(checkoutItem);
        }
        return checkoutItemDetails;
    }

    public ArrayList<CheckoutItemDetails> GetSpecialOffers() {

        Realm realm = Realm.getDefaultInstance();
        RealmResults<SpecialItems> items = realm.where(SpecialItems.class).equalTo("offerType", ConstantsInternal.OfferType.SpecialOffer.getValue()).findAll();
        ArrayList<CheckoutItemDetails> checkoutItemDetails = new ArrayList<>();


        for (SpecialItems item : items) {
            CheckoutItemDetails checkoutItem = new CheckoutItemDetails();
            checkoutItem.setItem_key(item.getSpecialOfferKey());
            checkoutItem.setQuantity(item.getQuantity());
            checkoutItem.setItem_price(item.getPrice());

            checkoutItem.setModifiers(null);
            checkoutItem.setIngrdients(null);
            checkoutItem.setSpecialOffers(null);

            checkoutItem.setQuantity(item.getQuantity());
            checkoutItemDetails.add(checkoutItem);
        }
        return checkoutItemDetails;
    }

    public ArrayList<CheckoutItemDetails> GetSpecialItems() {

        Realm realm = Realm.getDefaultInstance();
        RealmResults<SpecialItems> items = realm.where(SpecialItems.class).equalTo("offerType", ConstantsInternal.OfferType.SpecialItem.getValue()).findAll();
        ArrayList<CheckoutItemDetails> checkoutItemDetails = new ArrayList<>();


        for (SpecialItems item : items) {
            CheckoutItemDetails checkoutItem = new CheckoutItemDetails();
            checkoutItem.setItem_key(item.getSpecialOfferKey());
            checkoutItem.setQuantity(item.getQuantity());
            checkoutItem.setItem_price(item.getPrice());

            checkoutItem.setModifiers(null);
            checkoutItem.setIngrdients(null);
            checkoutItem.setSpecialOffers(null);

            checkoutItem.setQuantity(item.getQuantity());
            checkoutItemDetails.add(checkoutItem);
        }
        return checkoutItemDetails;
    }

    public ArrayList<CheckoutItemDetails> GetCouponItems() {

        Realm realm = Realm.getDefaultInstance();
        RealmResults<SpecialItems> items = realm.where(SpecialItems.class).equalTo("offerType", ConstantsInternal.OfferType.CouponItem.getValue()).findAll();
        ArrayList<CheckoutItemDetails> checkoutItemDetails = new ArrayList<>();


        for (SpecialItems item : items) {
            CheckoutItemDetails checkoutItem = new CheckoutItemDetails();
            checkoutItem.setItem_key(item.getSpecialOfferKey());
            checkoutItem.setQuantity(item.getQuantity());
            checkoutItem.setItem_price(item.getPrice());

            checkoutItem.setModifiers(null);
            checkoutItem.setIngrdients(null);
            checkoutItem.setSpecialOffers(null);

            checkoutItem.setQuantity(item.getQuantity());
            checkoutItemDetails.add(checkoutItem);
        }
        return checkoutItemDetails;
    }

    public void Add(final String itemKey, final String itemName, final String modifier, final String ingredients, final String specialOffer, final Double price, final String itemImagePath, final CommonCallback.Listener callback) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
                                          @Override
                                          public void execute(Realm realm) {
                                              Number currentSNo = realm.where(Items.class).max("sNo");
                                              int nextId;
                                              if (currentSNo == null) {
                                                  nextId = 1;
                                              } else {
                                                  nextId = currentSNo.intValue() + 1;
                                              }

                                              Integer itemQuantity = 1;
                                              RealmResults<Items> items = realm.where(Items.class)
                                                      .equalTo("itemKey", itemKey.trim())
                                                      .findAll();

                                              Boolean isUpdated = false;
                                              for (Items item : items) {
                                                  Boolean isModifiersMatching = false;
                                                  Boolean isIngredientsMatching = false;
                                                  Boolean isSpecialOfferMatching = false;

                                                  JsonParser parser = new JsonParser();
                                                  JsonElement modifierJsonLocal = parser.parse(item.getModifier());
                                                  JsonElement modifierJsonDB = parser.parse(modifier);

                                                  if (modifierJsonLocal.equals(modifierJsonDB)) {
                                                      isModifiersMatching = true;
                                                  }


                                                  JsonElement ingredientsJsonLocal = parser.parse(item.getIngredients());
                                                  JsonElement ingredientsJsonDB = parser.parse(ingredients);

                                                  if (ingredientsJsonLocal.equals(ingredientsJsonDB)) {
                                                      isIngredientsMatching = true;
                                                  }

                                                  JsonElement specialOfferJsonLocal = parser.parse(item.getSpecialOffer());
                                                  JsonElement specialOfferJsonDB = parser.parse(specialOffer);

                                                  if (specialOfferJsonDB.equals(specialOfferJsonLocal)) {
                                                      isSpecialOfferMatching = true;
                                                  }

                                                  if (isModifiersMatching && isIngredientsMatching && isSpecialOfferMatching) {
                                                      item.setQuantity(item.getQuantity() + 1);
                                                      item.setPrice(price);
                                                      realm.insertOrUpdate(item);

                                                      isUpdated = true;
                                                      break;
                                                  }
                                              }

                                              if (!isUpdated) {
                                                  Items itemDetails = new Items(nextId, itemKey, itemName, modifier, ingredients, specialOffer, itemQuantity, price, itemImagePath, ConstantsInternal.CouponScope.None.getValue());
                                                  realm.insertOrUpdate(itemDetails);
                                              }

                                          }
                                      }, new Realm.Transaction.OnSuccess() {
                                          @Override
                                          public void onSuccess() {
                                              callback.onSuccess();
                                          }
                                      }, new Realm.Transaction.OnError() {
                                          @Override
                                          public void onError(Throwable error) {
                                              callback.onFailure();
                                          }
                                      }
        );
    }

    public void AddSpecialOffer(final String specialOfferKey, final Integer OfferType, final String specialOfferName, final Double price, final String specialOfferImagePath, final Integer couponScope, final CommonCallback.Listener callback) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
                                          @Override
                                          public void execute(Realm realm) {
                                              Integer itemQuantity = 1;
                                              SpecialItems item = realm.where(SpecialItems.class)
                                                      .equalTo("specialOfferKey", specialOfferKey.trim())
                                                      .findFirst();

                                              if (item != null) {

                                                  item.setQuantity(item.getQuantity() + 1);
                                                  item.setPrice(price);
                                                  item.setOfferType(OfferType);
                                                  item.setSpecialOfferName(specialOfferName);
                                                  realm.insertOrUpdate(item);
                                                  item.setUserScope(couponScope);
                                                  return;
                                              }

                                              SpecialItems items = new SpecialItems(specialOfferKey, OfferType, specialOfferName, itemQuantity, price, specialOfferImagePath, couponScope);
                                              realm.insertOrUpdate(items);

                                          }
                                      }, new Realm.Transaction.OnSuccess() {
                                          @Override
                                          public void onSuccess() {
                                              System.out.println("Success");
                                              callback.onSuccess();
                                          }
                                      }, new Realm.Transaction.OnError() {
                                          @Override
                                          public void onError(Throwable error) {
                                              System.out.println("Failure");
                                              callback.onFailure();
                                          }
                                      }
        );
    }

    public void AddCouponItems(final String couponKey, final Integer couponType, final String couponName, final Double price, final String couponImagePath, final Integer isUserScope, final CommonCallback.Listener listener) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
                                          @Override
                                          public void execute(Realm realm) {
                                              Integer itemQuantity = 1;
                                              SpecialItems item = realm.where(SpecialItems.class)
                                                      .equalTo("specialOfferKey", couponKey.trim())
                                                      .findFirst();

                                              if (item != null) {
                                                  if (item.getUserScope() != ConstantsInternal.CouponScope.User.getValue()) {
                                                      item.setQuantity(item.getQuantity() + 1);
                                                      item.setPrice(price);
                                                      item.setOfferType(couponType);
                                                      item.setSpecialOfferName(couponName);
                                                      item.setUserScope(isUserScope);
                                                      realm.insertOrUpdate(item);
                                                      listener.onSuccess();
                                                      return;
                                                  } else {
                                                      listener.onFailure();
                                                      return;
                                                  }
                                              }

                                              SpecialItems items = new SpecialItems(couponKey, couponType, couponName, itemQuantity, price, couponImagePath, isUserScope);
                                              realm.insertOrUpdate(items);
                                              listener.onSuccess();
                                              return;

                                          }
                                      }, new Realm.Transaction.OnSuccess() {
                                          @Override
                                          public void onSuccess() {

                                          }
                                      }, new Realm.Transaction.OnError() {
                                          @Override
                                          public void onError(Throwable error) {

                                          }
                                      }
        );
    }

    public void IncreaseItem(final Integer sNo, final CommonCallback.Listener callback) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
                                          @Override
                                          public void execute(Realm realm) {
                                              Items item = realm.where(Items.class)
                                                      .equalTo("sNo", sNo)
                                                      .findFirst();

                                              if (item != null) {
                                                  if (item.getUserScope() != ConstantsInternal.CouponScope.User.getValue()) {
                                                      item.setQuantity(item.getQuantity() + 1);
                                                      realm.copyToRealm(item);
                                                  }
                                              }
                                          }
                                      }, new Realm.Transaction.OnSuccess() {
                                          @Override
                                          public void onSuccess() {
                                              callback.onSuccess();
                                          }
                                      }, new Realm.Transaction.OnError() {
                                          @Override
                                          public void onError(Throwable error) {
                                              callback.onFailure();
                                          }
                                      }
        );
    }

    public void DecreaseItem(final Integer sNo, final CommonCallback.Listener callback) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
                                          @Override
                                          public void execute(Realm realm) {
                                              final Items item = realm.where(Items.class)
                                                      .equalTo("sNo", sNo)
                                                      .findFirst();

                                              if (item != null) {
                                                  if (item.getQuantity() - 1 <= 0) {
                                                      item.deleteFromRealm();
                                                  } else {
                                                      item.setQuantity(item.getQuantity() - 1);
                                                      realm.copyToRealmOrUpdate(item);
                                                  }

                                              }
                                          }
                                      }, new Realm.Transaction.OnSuccess() {
                                          @Override
                                          public void onSuccess() {
                                              callback.onSuccess();
                                          }
                                      }, new Realm.Transaction.OnError() {
                                          @Override
                                          public void onError(Throwable error) {
                                              callback.onFailure();
                                          }
                                      }
        );
    }

    public void IncreaseSpecialOffer(final String specialOfferKey, final CommonCallback.Listener callback) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
                                          @Override
                                          public void execute(Realm realm) {
                                              SpecialItems item = realm.where(SpecialItems.class)
                                                      .equalTo("specialOfferKey", specialOfferKey)
                                                      .findFirst();

                                              if (item != null) {
                                                  if (item.getUserScope() != ConstantsInternal.CouponScope.User.getValue()) {
                                                      item.setQuantity(item.getQuantity() + 1);
                                                      realm.copyToRealm(item);
                                                  }
                                              }
                                          }
                                      }, new Realm.Transaction.OnSuccess() {
                                          @Override
                                          public void onSuccess() {
                                              callback.onSuccess();
                                          }
                                      }, new Realm.Transaction.OnError() {
                                          @Override
                                          public void onError(Throwable error) {
                                              callback.onFailure();
                                          }
                                      }
        );
    }

    public void DecreaseSpecialOffer(final String specialOfferKey, final CommonCallback.Listener callback) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
                                          @Override
                                          public void execute(Realm realm) {
                                              SpecialItems item = realm.where(SpecialItems.class)
                                                      .equalTo("specialOfferKey", specialOfferKey)
                                                      .findFirst();

                                              if (item != null) {
                                                  if (item.getQuantity() - 1 <= 0) {
                                                      item.deleteFromRealm();
                                                  } else {
                                                      item.setQuantity(item.getQuantity() - 1);
                                                      realm.copyToRealmOrUpdate(item);
                                                  }

                                              }
                                          }
                                      }, new Realm.Transaction.OnSuccess() {
                                          @Override
                                          public void onSuccess() {
                                              callback.onSuccess();
                                          }
                                      }, new Realm.Transaction.OnError() {
                                          @Override
                                          public void onError(Throwable error) {
                                              callback.onFailure();
                                          }
                                      }
        );
    }

    public void Delete(final Integer sNo, final CommonCallback.Listener callback) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
                                          @Override
                                          public void execute(Realm realm) {
                                              Items item = realm.where(Items.class)
                                                      .equalTo("sNo", sNo)
                                                      .findFirst();

                                              if (item != null) {
                                                  item.deleteFromRealm();
                                              }
                                          }
                                      }, new Realm.Transaction.OnSuccess() {
                                          @Override
                                          public void onSuccess() {
                                              callback.onSuccess();
                                          }
                                      }, new Realm.Transaction.OnError() {
                                          @Override
                                          public void onError(Throwable error) {
                                              callback.onFailure();
                                          }
                                      }
        );

    }
}

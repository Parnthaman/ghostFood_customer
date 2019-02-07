package com.ghostFood.util;

import com.ghostFood.model.Category;
import com.ghostFood.model.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by android1 on 5/25/2017.
 */

public class ConstantsInternal {

    public static String DBName = "canadianpizza";
    public static String CurrencyZero = "0.00";
    public static String CurrencyCode = "€";
    public static String DeviceTypeId = "1";

    public static Integer DeciamlDigitsBeforeDot = 10;
    public static Integer DeciamlDigitsAfterDot = 2;
    public static String TwitterConsumerKey = "6uLtuI9JZAM3Pb3e9bWiAQUBR";
    public static String TwitterConsumerSecret = "kxMEO7nSqghNm7aV0MiA73ClqyA2crDua8j4gGSaN3C2tNExbk";

    public static int mCurrentPosition = 0;
    public static ArrayList<Category> mCategory;
    public static HashMap<Category, List<Item>> mItems;

    public static boolean isEnableDummyImages = true;
    public static String mDummyImage = "http://www.paramfood.in/wp-content/uploads/2017/03/W8-300x300.jpg";

    public enum Address {
        Add("Add", 1),
        Update("Update", 2);

        private String stringValue;
        private int intValue;

        private Address(String toString, int value) {
            stringValue = toString;
            intValue = value;
        }

        public static Address fromInteger(int x) {
            switch (x) {
                case 1:
                    return Add;
                case 2:
                    return Update;
            }
            return null;
        }

        public int getValue() {
            return intValue;
        }

        @Override
        public String toString() {
            return stringValue;
        }
    }

    public enum CouponType {
        None(Constants.None, 0),
        OnlineItem(Constants.Online, 1),
        OnlineFlatOfferCoupon(Constants.Online, 2),
        OnlinePercentOfferCoupon(Constants.Online, 3),
        PhysicalWithoutCode(Constants.InStore, 4),
        PhysicalWithCode(Constants.InStore, 5);

        private String stringValue;
        private int intValue;

        private CouponType(String toString, int value) {
            stringValue = toString;
            intValue = value;
        }

        public static CouponType fromInteger(int x) {
            switch (x) {
                case 0:
                    return None;
                case 1:
                    return OnlineItem;
                case 2:
                    return OnlineFlatOfferCoupon;
                case 3:
                    return OnlinePercentOfferCoupon;
                case 4:
                    return PhysicalWithoutCode;
                case 5:
                    return PhysicalWithCode;
            }
            return null;
        }

        public int getValue() {
            return intValue;
        }

        @Override
        public String toString() {
            return stringValue;
        }
    }

    public enum Cart {
        AddItem("Add", 1),
        RemoveItem("RemoveItem", 2);

        private String stringValue;
        private int intValue;

        private Cart(String toString, int value) {
            stringValue = toString;
            intValue = value;
        }

        public static Cart fromInteger(int x) {
            switch (x) {
                case 1:
                    return AddItem;
                case 2:
                    return RemoveItem;
            }
            return null;
        }

        public int getValue() {
            return intValue;
        }

        @Override
        public String toString() {
            return stringValue;
        }
    }

    public enum IngredientType {
        Modifier("Modifier", 1),
        Others("Others", 2);

        private String stringValue;
        private int intValue;

        private IngredientType(String toString, int value) {
            stringValue = toString;
            intValue = value;
        }

        public static IngredientType fromInteger(int x) {
            switch (x) {
                case 1:
                    return Modifier;
                case 2:
                    return Others;
            }
            return null;
        }

        public int getValue() {
            return intValue;
        }

        @Override
        public String toString() {
            return stringValue;
        }
    }

    public enum ItemType {
        Item("Item", 1),
        SpecialItem("SpecialItem", 2),
        CouponItem("CouponItem", 3);

        private String stringValue;
        private int intValue;

        private ItemType(String toString, int value) {
            stringValue = toString;
            intValue = value;
        }

        public static ItemType fromInteger(int x) {
            switch (x) {
                case 1:
                    return Item;
                case 2:
                    return SpecialItem;
                case 3:
                    return CouponItem;
            }
            return null;
        }

        public int getValue() {
            return intValue;
        }

        @Override
        public String toString() {
            return stringValue;
        }
    }

    public enum OfferType {
        SpecialOffer("SpecialOffer", 1),
        SpecialItem("SpecialItem", 2),
        CouponItem("CouponItem", 3);

        private String stringValue;
        private int intValue;

        private OfferType(String toString, int value) {
            stringValue = toString;
            intValue = value;
        }

        public static OfferType fromInteger(int x) {
            switch (x) {
                case 1:
                    return SpecialOffer;
                case 2:
                    return SpecialItem;
                case 3:
                    return CouponItem;
            }
            return null;
        }

        public int getValue() {
            return intValue;
        }

        @Override
        public String toString() {
            return stringValue;
        }
    }

    public enum OrderType {
        Delivery("Delivery", 1),
        Pickup("Pickup", 2);

        private String stringValue;
        private int intValue;

        private OrderType(String toString, int value) {
            stringValue = toString;
            intValue = value;
        }

        public static OrderType fromInteger(int x) {
            switch (x) {
                case 1:
                    return Delivery;
                case 2:
                    return Pickup;
            }
            return null;
        }

        public int getValue() {
            return intValue;
        }

        @Override
        public String toString() {
            return stringValue;
        }
    }

    public enum AddressAdapterType {
        AddressBook("AddressBook", 1),
        Checkout("Checkout", 2),
        ChooseAddress("ChooseAddress", 3),
        ChooseAddressDialog("ChooseAddressDialog", 4);


        private String stringValue;
        private int intValue;

        private AddressAdapterType(String toString, int value) {
            stringValue = toString;
            intValue = value;
        }

        public static AddressAdapterType fromInteger(int x) {
            switch (x) {
                case 1:
                    return AddressBook;
                case 2:
                    return Checkout;
                case 3:
                    return ChooseAddress;
                case 4:
                    return ChooseAddressDialog;
            }
            return null;
        }

        public int getValue() {
            return intValue;
        }

        @Override
        public String toString() {
            return stringValue;
        }
    }

    public enum DeliveryOption {
        ASAP("ASAP", 1),
        Later("Later", 2);

        private String stringValue;
        private int intValue;

        private DeliveryOption(String toString, int value) {
            stringValue = toString;
            intValue = value;
        }

        public static DeliveryOption fromInteger(int x) {
            switch (x) {
                case 1:
                    return ASAP;
                case 2:
                    return Later;
            }
            return null;
        }

        public int getValue() {
            return intValue;
        }

        @Override
        public String toString() {
            return stringValue;
        }
    }

    public enum PaymentOption {
        COD("COD", 1),
        Online("Online", 2),
        Both("Both", 3);

        private String stringValue;
        private int intValue;

        private PaymentOption(String toString, int value) {
            stringValue = toString;
            intValue = value;
        }

        public static PaymentOption fromInteger(int x) {
            switch (x) {
                case 1:
                    return COD;
                case 2:
                    return Online;
                case 3:
                    return Both;
            }
            return null;
        }

        public int getValue() {
            return intValue;
        }

        @Override
        public String toString() {
            return stringValue;
        }
    }

    public enum PaymentOptionSelected {
        COD("COD", 0),
        Online("Online", 1);

        private String stringValue;
        private int intValue;

        private PaymentOptionSelected(String toString, int value) {
            stringValue = toString;
            intValue = value;
        }

        public static PaymentOptionSelected fromInteger(int x) {
            switch (x) {
                case 0:
                    return COD;
                case 1:
                    return Online;
            }
            return null;
        }

        public int getValue() {
            return intValue;
        }

        @Override
        public String toString() {
            return stringValue;
        }
    }

    public enum ListType {
        PICKUP("Pickup", 1),
        LOCATION("Location", 2);

        private String stringValue;
        private int intValue;

        private ListType(String toString, int value) {
            stringValue = toString;
            intValue = value;
        }

        public static ListType fromInteger(int x) {
            switch (x) {
                case 1:
                    return PICKUP;
                case 2:
                    return LOCATION;
            }
            return null;
        }

        public int getValue() {
            return intValue;
        }

        @Override
        public String toString() {
            return stringValue;
        }
    }


    public enum AppDirection {
        LTR("LTR", 0),
        RTL("RTL", 1);

        private String stringValue;
        private int intValue;

        private AppDirection(String toString, int value) {
            stringValue = toString;
            intValue = value;
        }

        public static AppDirection fromInteger(int x) {
            switch (x) {
                case 0:
                    return LTR;
                case 1:
                    return RTL;
            }
            return null;
        }

        public int getValue() {
            return intValue;
        }

        @Override
        public String toString() {
            return stringValue;
        }
    }

    public enum DefaultPaymentReceiveIn {
        WALLET("Wallet", 0),
        BANK("Bank", 1);

        private String stringValue;
        private int intValue;

        private DefaultPaymentReceiveIn(String toString, int value) {
            stringValue = toString;
            intValue = value;
        }

        public int getValue() {
            return intValue;
        }

        @Override
        public String toString() {
            return stringValue;
        }
    }

    public enum OrderStatus {
        Pending(Constants.Pending, 1),
        Accepted(Constants.Accepted, 2),
        Prepared(Constants.Prepared, 3),
        OnTheWay(Constants.OnTheWay, 4),
        Delivered(Constants.Delivered, 5),
        Rejected(Constants.Rejected, 6),
        DeliveryBoyAssigned(Constants.DeliveryBoyAssigned, 7),
        DeliveryBoyAccepted(Constants.DeliveryBoyAccepted, 8),
        DeliveryBoyRejected(Constants.DeliveryBoyRejected, 9);

        private String stringValue;
        private int intValue;

        private OrderStatus(String toString, int value) {
            stringValue = toString;
            intValue = value;
        }

        public static OrderStatus fromInteger(int x) {
            switch (x) {
                case 1:
                    return Pending;
                case 2:
                    return Accepted;
                case 3:
                    return Prepared;
                case 4:
                    return OnTheWay;
                case 5:
                    return Delivered;
                case 6:
                    return Rejected;
                case 7:
                    return DeliveryBoyAssigned;
                case 8:
                    return DeliveryBoyAccepted;
                case 9:
                    return DeliveryBoyRejected;
            }
            return null;
        }

        public int getValue() {
            return intValue;
        }

        @Override
        public String toString() {
            return stringValue;
        }
    }

    public enum Confirmation {
        MobileNumer("MobileNumer", 0),
        EMail("EMail", 1);

        private String stringValue;
        private int intValue;

        private Confirmation(String toString, int value) {
            stringValue = toString;
            intValue = value;
        }

        public static Confirmation fromInteger(int x) {
            switch (x) {
                case 0:
                    return MobileNumer;
                case 1:
                    return EMail;
            }
            return null;
        }

        public int getValue() {
            return intValue;
        }

        @Override
        public String toString() {
            return stringValue;
        }
    }


    public enum FavouriteStatus {
        None("None", 0),
        Favourite("Favourite", 1);

        private String stringValue;
        private int intValue;

        private FavouriteStatus(String toString, int value) {
            stringValue = toString;
            intValue = value;
        }

        public static FavouriteStatus fromInteger(int x) {
            switch (x) {
                case 0:
                    return None;
                case 1:
                    return Favourite;
            }
            return null;
        }

        public int getValue() {
            return intValue;
        }

        @Override
        public String toString() {
            return stringValue;
        }
    }

    public enum RegistrationType {
        Normal("Normal", 1),
        Facebook("Facebook", 2),
        Twitter("Twitter", 3),
        GooglePlus("GooglePlus", 4);

        private String stringValue;
        private int intValue;

        private RegistrationType(String toString, int value) {
            stringValue = toString;
            intValue = value;
        }

        public static RegistrationType fromInteger(int x) {
            switch (x) {
                case 1:
                    return Normal;
                case 2:
                    return Facebook;
                case 3:
                    return Twitter;
                case 4:
                    return GooglePlus;
            }
            return null;
        }

        public int getValue() {
            return intValue;
        }

        @Override
        public String toString() {
            return stringValue;
        }
    }

    public enum CouponScope {
        None("None", 0),
        Global("Global", 1),
        User("User", 2);

        private String stringValue;
        private int intValue;

        private CouponScope(String toString, int value) {
            stringValue = toString;
            intValue = value;
        }

        public static CouponScope fromInteger(int x) {
            switch (x) {
                case 0:
                    return None;
                case 1:
                    return Global;
                case 2:
                    return User;
            }
            return null;
        }

        public int getValue() {
            return intValue;
        }

        @Override
        public String toString() {
            return stringValue;
        }
    }

    public static class Variables {
        private static Variables ourInstance = new Variables();

        public static Variables getInstance() {
            return ourInstance;
        }

        public String RedeemAmount = "$$redeemamount$$";
    }

    public enum AppMode {
        Dev("Dev", 1),
        Staging("Staging", 2),
        Prod("Prod", 3);

        private String stringValue;
        private int intValue;

        private AppMode(String toString, int value) {
            stringValue = toString;
            intValue = value;
        }

        public static AppMode fromInteger(int x) {
            switch (x) {
                case 1:
                    return Dev;
                case 2:
                    return Staging;
                case 3:
                    return Prod;
            }
            return null;
        }

        public int getValue() {
            return intValue;
        }

        @Override
        public String toString() {
            return stringValue;
        }
    }
}

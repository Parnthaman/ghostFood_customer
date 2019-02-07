package com.ghostFood.util;

import java.util.ArrayList;

/**
 * Created by android1 on 5/30/2017.
 */

public class CommissionDetails {
    private static CommissionDetails ourInstance = new CommissionDetails();

    public static CommissionDetails getInstance() {
        return ourInstance;
    }

    public Boolean getEnabledStatus() {
        return EnabledStatus;
    }

    public void setEnabledStatus(Boolean enabledStatus) {
        EnabledStatus = enabledStatus;
    }

    public Double getFixedCommissionValue() {
        return FixedCommissionValue;
    }

    public void setFixedCommissionValue(Double fixedCommissionValue) {
        FixedCommissionValue = fixedCommissionValue;
    }

    public ArrayList<SlabCommission> getSlabCommissionValue() {
        return SlabCommissionValue;
    }

    public void setSlabCommissionValue(ArrayList<SlabCommission> slabCommissionValue) {
        SlabCommissionValue = slabCommissionValue;
    }



    public CommissionDetails.CommissionType getCommissionType() {
        return CommissionType;
    }

    public void setCommissionType(CommissionDetails.CommissionType commissionType) {
        CommissionType = commissionType;
    }

    private Boolean EnabledStatus;
    private CommissionType CommissionType;
    private Double FixedCommissionValue;
    private ArrayList<SlabCommission> SlabCommissionValue;

    public enum CommissionType {
        Fixed,
        Slab
    }

    public enum SlabCommissionType {
        Fixed("Fixed", 1),
        Percentage("Percentage", 2);

        private String stringValue;
        private int intValue;
        private SlabCommissionType(String toString, int value) {
            stringValue = toString;
            intValue = value;
        }

        public static SlabCommissionType fromInteger(int x) {
            switch(x) {
                case 1:
                    return Fixed;
                case 2:
                    return Percentage;
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

    public static class SlabCommission {
        public Double getMaxAmount() {
            return MaxAmount;
        }

        public void setMaxAmount(Double maxAmount) {
            MaxAmount = maxAmount;
        }

        public CommissionDetails.SlabCommissionType getCommissionType() {
            return CommissionType;
        }

        public void setCommissionType(CommissionDetails.SlabCommissionType commissionType) {
            CommissionType = commissionType;
        }

        public Double getCommissionValue() {
            return CommissionValue;
        }

        public void setCommissionValue(Double commissionValue) {
            CommissionValue = commissionValue;
        }

        private Double MaxAmount;
        private SlabCommissionType CommissionType;
        private Double CommissionValue;
    }
}
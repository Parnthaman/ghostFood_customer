package com.ghostFood.util;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by android1 on 5/26/2017.
 */

public class DecimalDigitsInputFilter implements InputFilter {

    Pattern mPattern;

    public DecimalDigitsInputFilter() {
        mPattern=Pattern.compile("[0-9]{0," + (ConstantsInternal.DeciamlDigitsBeforeDot - 1) + "}+((\\.[0-9]{0," + (ConstantsInternal.DeciamlDigitsAfterDot - 1) + "})?)||(\\.)?");
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

        Matcher matcher=mPattern.matcher(dest);
        if(!matcher.matches())
            return "";
        return null;
    }

}
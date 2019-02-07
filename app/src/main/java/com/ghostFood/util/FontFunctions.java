package com.ghostFood.util;

import android.content.Context;
import android.widget.TextView;

public class FontFunctions {

    public static String CMMidNightfontPath = "fonts/montser_ratregular.ttf";
    public static String BerlinfontPath = "fonts/montser_ratregular.ttf";
    public static String BerlinboldfontPath = "fonts/montser_ratregular.ttf";
    public static String SketchFont = "fonts/montser_ratregular.ttf";
    public static String LatoFont = "fonts/montser_ratregular.ttf";
    public static String BalooBhaijaan = "fonts/montser_ratregular.ttf";
    public static String KGSecondSketch = "fonts/montser_ratregular.ttf";
    public static String BabeNeueBold = "fonts/Montserrat-SemiBold.ttf";
    public static String Calibri = "fonts/montser_ratregular.ttf";
    public static String ArialNovaLight = "fonts/montser_ratregular.ttf";
    public static String Pristina = "fonts/montser_ratregular.ttf";
    public static String Pristinasss = "fonts/appfont.otf";

    private static FontFunctions ourInstance = new FontFunctions();

    public static FontFunctions getInstance() {

        if (SessionManager.AppProperties.getInstance().getAppDirection() == ConstantsInternal.AppDirection.RTL) {

        } else {

        }

        return ourInstance;
    }

    private FontFunctions() {
    }

    public void FontCMMidNight(Context mContext, TextView view) {
        /*Typeface face = Typeface.createFromAsset(mContext.getAssets(), FontFunctions.CMMidNightfontPath);
        view.setTypeface(face);*/
    }

    public void fontAppFont(Context mContext, TextView view) {
        /*Typeface face = Typeface.createFromAsset(mContext.getAssets(), FontFunctions.Pristinasss);
        view.setTypeface(face);*/
    }

    public void FontBerlin(Context mContext, TextView view) {
        /*Typeface face = Typeface.createFromAsset(mContext.getAssets(), FontFunctions.BerlinfontPath);
        view.setTypeface(face);*/
    }

    public void FontBerlinBold(Context mContext, TextView view) {
       /* Typeface face = Typeface.createFromAsset(mContext.getAssets(), FontFunctions.BerlinboldfontPath);
        view.setTypeface(face);*/
    }

    public void FontSketchBold(Context mContext, TextView view) {
        /*Typeface face = Typeface.createFromAsset(mContext.getAssets(), FontFunctions.SketchFont);
        view.setTypeface(face);*/
    }

    public void FontSketchBoldExtra(Context mContext, TextView view) {
        /*Typeface face = Typeface.createFromAsset(mContext.getAssets(), FontFunctions.SketchFont);
        view.setTypeface(face, Typeface.BOLD);*/
    }

    public void FontLatoFont(Context mContext, TextView view) {
        /*Typeface face = Typeface.createFromAsset(mContext.getAssets(), FontFunctions.LatoFont);
        view.setTypeface(face);*/
    }

    public void FontBalooBhaijaan(Context mContext, TextView view) {
        /*Typeface face = Typeface.createFromAsset(mContext.getAssets(), FontFunctions.BalooBhaijaan);
        view.setTypeface(face);
        if (SessionManager.AppProperties.getInstance().getAppDirection() == ConstantsInternal.AppDirection.RTL)
            view.setTypeface(face, Typeface.BOLD);*/
    }

    public void FontKGSecondSketch(Context mContext, TextView view) {
        /*Typeface face = Typeface.createFromAsset(mContext.getAssets(), FontFunctions.KGSecondSketch);
        view.setTypeface(face);
        if (SessionManager.AppProperties.getInstance().getAppDirection() == ConstantsInternal.AppDirection.RTL)
            view.setTypeface(face, Typeface.BOLD);*/
    }

    public void FontKGSecondSketchBold(Context mContext, TextView view) {
        /*Typeface face = Typeface.createFromAsset(mContext.getAssets(), FontFunctions.KGSecondSketch);
        view.setTypeface(face, Typeface.BOLD);*/

    }

    public void FontBabeNeueBold(Context mContext, TextView view) {
        /*Typeface face = Typeface.createFromAsset(mContext.getAssets(), FontFunctions.BabeNeueBold);
        view.setTypeface(face);
        if (SessionManager.AppProperties.getInstance().getAppDirection() == ConstantsInternal.AppDirection.RTL)
            view.setTypeface(face, Typeface.BOLD);*/
    }

    public void FontBabeNeueExtraBold(Context mContext, TextView view) {
        /*Typeface face = Typeface.createFromAsset(mContext.getAssets(), FontFunctions.BabeNeueBold);
        view.setTypeface(face);
        view.setTypeface(face, Typeface.BOLD);*/
    }


    public void FontSegoeSemiBold(Context mContext, TextView view) {
        /*Typeface face = Typeface.createFromAsset(mContext.getAssets(), FontFunctions.Calibri); //Needs to Change this font.
        view.setTypeface(face);*/
    }

    public void FontCalibri(Context mContext, TextView view) {
        /*Typeface face = Typeface.createFromAsset(mContext.getAssets(), FontFunctions.Calibri); //Needs to Change this font.
        view.setTypeface(face);*/
    }

    public void FontArialNovaLight(Context mContext, TextView view) {
        /*Typeface face = Typeface.createFromAsset(mContext.getAssets(), FontFunctions.ArialNovaLight); //Needs to Change this font.
        view.setTypeface(face);*/
    }

    public void FontArialNovaLightBold(Context mContext, TextView view) {
        /*Typeface face = Typeface.createFromAsset(mContext.getAssets(), FontFunctions.ArialNovaLight); //Needs to Change this font.
        view.setTypeface(face, Typeface.BOLD);*/
    }

    public void FontPristina(Context mContext, TextView view) {
        /*Typeface face = Typeface.createFromAsset(mContext.getAssets(), FontFunctions.Pristina); //Needs to Change this font.
        view.setTypeface(face);*/
    }
}
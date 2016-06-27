package com.guangzhou.weiwong.accountbook.utils;

import android.content.Context;
import android.view.View;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.guangzhou.weiwong.accountbook.R;

/**
 * Created by Tower on 2016/6/20.
 */
public class DialogUtil {
    public static final String FADEIN = "Fadein";
    public static final String SLIDER_RIGHT = "Slideright";
    public static final String SLIDER_LEFT = "Slideleft";
    public static final String SLIDER_TOP = "Slidetop";
    public static final String SLIDER_BOTTOM = "SlideBottom";
    public static final String NEWS_PAGER = "Newspager";
    public static final String FALL = "Fall";
    public static final String SIDE_FILL = "Sidefill";
    public static final String FLIPH = "Fliph";
    public static final String FLIPV = "Flipv";
    public static final String ROTATE_BOTTOM = "RotateBottom";
    public static final String ROTATE_LEFT = "RotateLeft";
    public static final String SLIT = "Slit";
    public static final String SHAKE = "Shake";

    private static Effectstype effect;
    public static void dialogShow(Context context, String stype, String msg, final DialogClickListener listener){
        switch (stype) {
            case "Fadein": effect= Effectstype.Fadein;break;
            case "Slideright": effect=Effectstype.Slideright;break;
            case "Slideleft": effect=Effectstype.Slideleft;break;
            case "Slidetop": effect=Effectstype.Slidetop;break;
            case "SlideBottom": effect=Effectstype.SlideBottom;break;
            case "Newspager": effect=Effectstype.Newspager;break;
            case "Fall": effect=Effectstype.Fall;break;
            case "Sidefill": effect=Effectstype.Sidefill;break;
            case "Fliph": effect=Effectstype.Fliph;break;
            case "Flipv": effect=Effectstype.Flipv;break;
            case "RotateBottom": effect=Effectstype.RotateBottom;break;
            case "RotateLeft": effect=Effectstype.RotateLeft;break;
            case "Slit": effect=Effectstype.Slit;break;
            case "Shake": effect=Effectstype.Shake;break;
            default:  stype = "Fadein"; effect=Effectstype.Fadein; break;
        }

        final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(context);
        dialogBuilder
                .withTitle("提示")                                  //.withTitle(null)  no title "Modal Dialog"
                .withTitleColor("#FFFFFF")                                  //def
                .withDividerColor("#11000000")                              //def
                .withMessage("\n" + msg + "\n")                     //.withMessage(null)  no Msg
                .withMessageColor("#FFFFFFFF")                              //def  | withMessageColor(int resid)
                .withDialogColor("#FF009688")                               //def  | withDialogColor(int resid)                               //def
                .withIcon(context.getResources().getDrawable(R.drawable.ic_launcher_m))
                .isCancelableOnTouchOutside(true)                           //def    | isCancelable(true)
                .withDuration(400)                                          //def 700
                .withEffect(effect)                                         //def Effectstype.Slidetop
                .withButton1Text("确定")                                      //def gone
                .withButton2Text("取消")                                  //def gone
//                .setCustomView(R.layout.custom_view, this)         //.setCustomView(View or ResId,context)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                        listener.onClick(v);
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                    }
                })
                .show();
    }

    public interface DialogClickListener extends View.OnClickListener {
        @Override
        void onClick(View v);
    }
}

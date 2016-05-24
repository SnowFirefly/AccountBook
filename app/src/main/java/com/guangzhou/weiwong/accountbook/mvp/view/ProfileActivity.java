package com.guangzhou.weiwong.accountbook.mvp.view;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.guangzhou.weiwong.accountbook.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {
    @Bind(R.id.collapsing_toolbar) CollapsingToolbarLayout collapsingToolbar;
    @Bind(R.id.btn_login_out) Button mBtnLoginOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setLogo(getResources().getDrawable(R.drawable.btg_icon_assistivebutton_submit_pressed));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
//        collapsingToolbar.setTitle("Title");

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        mBtnLoginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogShow(effectType[count%14]);
                count++;
            }
        });
    }

    int count = 0;
    private String[] effectType = {"Fadein", "Slideright", "Slideleft", "Slidetop", "SlideBottom",
    "Newspager", "Fall", "Sidefill", "Fliph", "Flipv", "RotateBottom", "RotateLeft", "Slit", "Shake"};
    private Effectstype effect;
    // test dialog
    private void dialogShow(String stype){
        switch (stype) {
            case "Fadein": effect=Effectstype.Fadein;break;
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

        NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(this);
        dialogBuilder
                .withTitle("Modal Dialog")                                  //.withTitle(null)  no title
                .withTitleColor("#FFFFFF")                                  //def
                .withDividerColor("#11000000")                              //def
                .withMessage(stype)                     //.withMessage(null)  no Msg
                .withMessageColor("#FFFFFFFF")                              //def  | withMessageColor(int resid)
                .withDialogColor("#FFE74C3C")                               //def  | withDialogColor(int resid)                               //def
                .withIcon(getResources().getDrawable(R.drawable.ic_launcher))
                .isCancelableOnTouchOutside(true)                           //def    | isCancelable(true)
                .withDuration(400)                                          //def 700
                .withEffect(effect)                                         //def Effectstype.Slidetop
                .withButton1Text("OK")                                      //def gone
                .withButton2Text("Cancel")                                  //def gone
                .setCustomView(R.layout.custom_view, this)         //.setCustomView(View or ResId,context)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(), "i'm btn1", Toast.LENGTH_SHORT).show();
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(), "i'm btn2", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
        }
        return super.onOptionsItemSelected(item);
    }
}

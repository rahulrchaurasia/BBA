package com.beldara.bba.splash;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.beldara.bba.R;
import com.beldara.bba.login.loginActivity;
import com.beldara.bba.utility.Constants;

public class SplashScreenActivity extends AppCompatActivity {

    TextView txtGroup;
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);
        txtGroup = (TextView) findViewById(R.id.txtGroup);


        verify();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(SplashScreenActivity.this, loginActivity.class));

//                if (loginFacade.getUser() != null) {
//                    startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
//                } else {
//                    startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
//                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }


    public void verify() {
        if (!Constants.checkInternetStatus(SplashScreenActivity.this)) {

            Snackbar snackbar = Snackbar.make(txtGroup, "No internet connection!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            verify();
                        }
                    });

            // Changing message text color
            snackbar.setActionTextColor(Color.RED);

            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.CYAN);

            snackbar.show();
        } /*else {


            if (prefManager.IsProductMasterUpdate()) {
                new ProductController(this).getProductMaster(this);
                new ProductController(this).getRTOLocationMaster(this);

            }

            new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    startActivity(new Intent(SplashScreenActivity.this, loginActivity.class));
                }
            }, Constants.SPLASH_DISPLAY_LENGTH);
        }*/

    }
}

package com.simple.guide.gfx.v1;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;


public class splash extends AppCompatActivity {

    String info_user , name_user;

    @Override
    protected void onCreate(Bundle Splash){
        super.onCreate(Splash);
        setContentView(R.layout.splash);
        Splash();
    }

    public void Splash(){
        Thread splash = new Thread(){
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while ((waited < 2000)) {
                        sleep(4000);
                        waited += 4000;
                    }
                } catch (InterruptedException e) {
                    e.toString();
                } finally {

                    startActivity(new Intent(getApplicationContext(), Home.class));
                    finish();
                }
            }

        };
        splash.start();
        }

}

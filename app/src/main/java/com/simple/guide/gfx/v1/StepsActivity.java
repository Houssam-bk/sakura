package com.simple.guide.gfx.v1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class StepsActivity extends AppCompatActivity {

    static String tips ;
    int position ;

    RelativeLayout tips1 , tips2 , tips3 , tips4 ,
                   tips5 , tips6 , tips7 , tips8 ;

    ImageView back ;
    RelativeLayout relativeAdView ;
    com.facebook.ads.AdView mAdViewFacebook ;
    com.facebook.ads.InterstitialAd mInterstitialFacebook ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.steps);
        InitializeTips();
        AudienceNetworkAds.initialize(this);


        position = getIntent().getIntExtra(tips , 0);
       // Toast.makeText(getApplicationContext(), ""+position, Toast.LENGTH_SHORT).show();
        loadTips();
        mAdViewFacebook = new com.facebook.ads.AdView(this, getResources().getString(R.string.Banner_AdMob), com.facebook.ads.AdSize.BANNER_HEIGHT_50);
        mInterstitialFacebook = new com.facebook.ads.InterstitialAd(getApplicationContext(),  getResources().getString(R.string.Interstitial_AdMob));
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);
        adContainer.addView(mAdViewFacebook);
        mAdViewFacebook.loadAd();
        mAdViewFacebook.setAdListener(new com.facebook.ads.AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                Log.d("motya" , "Banner Facebook on Failed Loaded");
            }

            @Override
            public void onAdLoaded(Ad ad) {
                Log.d("motya" , "Banner Facebook on Loaded");
            }

            @Override
            public void onAdClicked(Ad ad) {}
            @Override
            public void onLoggingImpression(Ad ad) {}
        });

        // Request an ad

        mInterstitialFacebook.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad){}
            @Override
            public void onInterstitialDismissed(Ad ad){
                mInterstitialFacebook.loadAd();
            }
            @Override
            public void onError(Ad ad, AdError adError){
                Log.d("motya" , "Interstitial Facebook on Failed Loaded");
            }
            @Override
            public void onAdClicked(Ad ad){}
            @Override
            public void onLoggingImpression(Ad ad){}
            @Override
            public void onAdLoaded(Ad ad) {
                Log.d("motya" , "Interstitial Facebook Loaded");
                //  mInterstitialFacebook.show();
            }
        });
        mInterstitialFacebook.loadAd();








        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInterstitial();
                finish();
            }
        });




    }

    private void InitializeTips(){
        tips1 = findViewById(R.id.relative_tips1);
        tips2 = findViewById(R.id.relative_tips2);
        tips3 = findViewById(R.id.relative_tips3);
        tips4 = findViewById(R.id.relative_tips4);

        tips5 = findViewById(R.id.relative_tips5);
        tips6 = findViewById(R.id.relative_tips6);

        back= findViewById(R.id.back);

    }


    private void loadTips(){
        if (position == 0){
            setTips(tips1);
        }else if (position == 1){
            setTips(tips2);
        }else if (position == 2){
            setTips(tips3);
        }else if (position == 3){
            setTips(tips4);
        }else if (position == 4){
            setTips(tips5);
        }else if (position == 5){
            setTips(tips6);
        }else if (position == 6){
            setTips(tips7);
        }
    }

    private void setTips(RelativeLayout tips){
        tips.setVisibility(View.VISIBLE);
    }


    public void showInterstitial(){
        // Show Interstitial Facebook After Loading
        if (mInterstitialFacebook != null && mInterstitialFacebook.isAdLoaded()) {
            mInterstitialFacebook.show();
        }
    }


    @Override
    public void onBackPressed() {
       showInterstitial();
        super.onBackPressed();

    }



    @Override
    public void onDestroy() {

        if (mAdViewFacebook != null) {
            mAdViewFacebook.destroy();
        }
        super.onDestroy();

    }


}

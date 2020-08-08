package com.simple.guide.gfx.v1;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.ads.*;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.List;

public class Home extends AppCompatActivity {

    TextView start, share, rate, privacy;
    String Tag = "ads done";
    Context context;
    com.facebook.ads.InterstitialAd mInterstitialFacebook;


    @Override
    protected void onCreate(Bundle home) {
        super.onCreate(home);
        setContentView(R.layout.home);

        AudienceNetworkAds.initialize(this);

        start = findViewById(R.id.start_tips);
        share = findViewById(R.id.share);
        rate = findViewById(R.id.rate);
        privacy = findViewById(R.id.privacy_policy);
        mInterstitialFacebook = new com.facebook.ads.InterstitialAd(getApplicationContext(), getResources().getString(R.string.Interstitial_AdMob));


        mInterstitialFacebook.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
                startActivity(new Intent(getApplicationContext(), list_tips.class));

            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
                // Show the ad
                mInterstitialFacebook.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
                startActivity(new Intent(getApplicationContext(), list_tips.class));

            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback

            }
        });

        // For auto play video ads, it's recommended to load the ad
        // at least 30 seconds before it is shown


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mInterstitialFacebook.loadAd();
                startActivity(new Intent(getApplicationContext(), list_tips.class));


            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                share();
            }
        });
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Rate();
            }
        });

        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                privacy();
            }
        });
    }


    public void Rate() {
        try {
            Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + getPackageName()));
            startActivity(localIntent);
        } catch (ActivityNotFoundException localActivityNotFoundException) {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
        }
    }

    public void share() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.app_name) + " ** Install from this link : " + "https://play.google.com/store/apps/details?id=" + getPackageName());
        sendIntent.setType("text/plain");
        if (isAvailable(sendIntent)) {
            startActivity(sendIntent);
        } else {
            Toast.makeText(getApplicationContext(), "There is no app available for this task", Toast.LENGTH_SHORT).show();
        }
    }

    public void privacy() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.privacy_link)));
        if (isAvailable(intent)) {
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "There is no app available for this task", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isAvailable(Intent mIntent) {
        final PackageManager mgr = getPackageManager();
        List<ResolveInfo> list =
                mgr.queryIntentActivities(mIntent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    @Override
    protected void onDestroy() {
        if (mInterstitialFacebook != null) {
            mInterstitialFacebook.destroy();
        }
        super.onDestroy();
    }

}

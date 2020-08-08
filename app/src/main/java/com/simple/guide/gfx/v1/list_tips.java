package com.simple.guide.gfx.v1;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.facebook.ads.*;

public class list_tips extends AppCompatActivity {

    ImageView back;
    RecyclerView mRecyclerView;
    ContentAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    RelativeLayout relativeAdView;
    com.facebook.ads.AdView mAdViewFacebook;
    com.facebook.ads.InterstitialAd mInterstitialFacebook;
    String TAG;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.items);
        loadItems();
        AudienceNetworkAds.initialize(this);
        back = findViewById(R.id.back);


        mAdViewFacebook = new com.facebook.ads.AdView(this, getResources().getString(R.string.Banner_AdMob), com.facebook.ads.AdSize.BANNER_HEIGHT_50);

        mInterstitialFacebook = new com.facebook.ads.InterstitialAd(getApplicationContext(), getResources().getString(R.string.Interstitial_AdMob));
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);
        adContainer.addView(mAdViewFacebook);


        mAdViewFacebook.setAdListener(new com.facebook.ads.AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                Log.d("motya", "Banner Facebook on Failed Loaded");
            }

            @Override
            public void onAdLoaded(Ad ad) {
                Log.d("motya", "Banner Facebook on Loaded");
            }

            @Override
            public void onAdClicked(Ad ad) {
            }

            @Override
            public void onLoggingImpression(Ad ad) {
            }
        });

         mAdViewFacebook.loadAd();

        // Request an ad

        mInterstitialFacebook.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {

            }

            @Override
            public void onError(Ad ad, AdError adError) {
                Log.d("motya", "Interstitial Facebook on Failed Loaded");
            }

            @Override
            public void onAdClicked(Ad ad) {
            }

            @Override
            public void onLoggingImpression(Ad ad) {
            }

            @Override
            public void onAdLoaded(Ad ad) {
                Log.d("motya", "Interstitial Facebook Loaded");
                mInterstitialFacebook.show();
            }
        });
        mInterstitialFacebook.loadAd();

        // For auto play video ads, it's recommended to load the ad
        // at least 30 seconds before it is shown


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInterstitial();
                finish();
            }
        });

    }

    public void showInterstitial() {
        // Show Interstitial Facebook After Loading
        if (mInterstitialFacebook != null && mInterstitialFacebook.isAdLoaded()) {
            mInterstitialFacebook.show();
        }
    }

    private void loadItems() {
        mRecyclerView = findViewById(R.id.recyclerView);
        layoutManager = new GridLayoutManager(this, 1);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
        adapter = new ContentAdapter(mRecyclerView.getContext());
        mRecyclerView.setAdapter(adapter);
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView preview_img;
        TextView title;

        ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.cadre_view, parent, false));

            preview_img = itemView.findViewById(R.id.img_preview);
            title = itemView.findViewById(R.id.title_preview);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, StepsActivity.class);
                    intent.putExtra(StepsActivity.tips, getAdapterPosition());
                    context.startActivity(intent);
                    showInterstitial();
                }
            });

        }

    }

    public class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        // Set numbers of Card in RecyclerView.
        private static final int LENGTH = 8;

        private final String[] Title_Array;
        private final Drawable[] ImgPreview_Array;


        ContentAdapter(Context context) {

            Resources resources = context.getResources();
            Title_Array = resources.getStringArray(R.array.title);
            TypedArray typedArray = resources.obtainTypedArray(R.array.images_previews);


            ImgPreview_Array = new Drawable[typedArray.length()];
            for (int i = 0; i < ImgPreview_Array.length; i++) {
                ImgPreview_Array[i] = typedArray.getDrawable(i);
            }
            typedArray.recycle();

        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.preview_img.setImageDrawable(ImgPreview_Array[position % ImgPreview_Array.length]);
            holder.title.setText(Title_Array[position % Title_Array.length]);

        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }
    }


    @Override
    public void onBackPressed() {
        showInterstitial();
        super.onBackPressed();


    }


    @Override
    public void onDestroy() {

        if (mInterstitialFacebook != null) {
            mInterstitialFacebook.destroy();
        }
        super.onDestroy();

    }

}

package com.sepidehmiller.bakingapp;


import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.sepidehmiller.bakingapp.data.Step;


// This fragment is called by the RecipeActivity in tablet mode and by RecipeDetailActivity
// on phones.

public class PlayerFragment extends Fragment {
  private static final String TAG = "PlayerFragment";
  private SimpleExoPlayerView mExoPlayerView;
  private SimpleExoPlayer mExoPlayer;
  private TextView mStepTextView;
  private Step mStep;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_player, container, false);
    mExoPlayerView = rootView.findViewById(R.id.player_view);
    mStepTextView = rootView.findViewById(R.id.step_description_text_view);
    mStep = getArguments().getParcelable(RecipeActivity.STEP);

    if (mStep != null) {
      String url1 = mStep.getVideoURL();
      String url2 = mStep.getThumbnailURL();

      if (!url1.isEmpty()) {
        mExoPlayerView.setVisibility(View.VISIBLE);
        initializePlayer(Uri.parse(url1));
      } else if (!url2.isEmpty()) {
        mExoPlayerView.setVisibility(View.VISIBLE);
        initializePlayer(Uri.parse(url2));
      } else {
        mExoPlayerView.setVisibility(View.GONE);
      }
    }

    mStepTextView.setText(mStep.getDescription());

    return rootView;
  }


  /* Since we are having the manifest deal with configuration changes so the video does not
     restart on rotation, we need to override onConfigurationChanged to change the view when it
     is in a horizontal layout on phones.

     https://developer.android.com/guide/topics/resources/runtime-changes

  */

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);

    if (newConfig.smallestScreenWidthDp < 600) {
      if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
        mStepTextView.setVisibility(View.VISIBLE);
        mStepTextView.setText(mStep.getDescription());
      } else {
        if (mExoPlayerView.getVisibility() != View.GONE) {
          mStepTextView.setVisibility(View.GONE);
          mExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT);
        }
      }
    }
  }

  private void initializePlayer(Uri uri) {

    if (mExoPlayer == null) {
      Context context = getContext();
      TrackSelector trackSelector = new DefaultTrackSelector();
      LoadControl loadControl = new DefaultLoadControl();
      mExoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadControl);
      mExoPlayerView.setPlayer(mExoPlayer);
      String userAgent = Util.getUserAgent(context, "BakingApp");
      MediaSource mediaSource = new ExtractorMediaSource(uri,
          new DefaultDataSourceFactory(context, userAgent), new DefaultExtractorsFactory(),
          null, null);
      mExoPlayer.prepare(mediaSource);
      mExoPlayer.setPlayWhenReady(true);
    }
  }


  // When do we release the player?
  // https://codelabs.developers.google.com/codelabs/exoplayer-intro/#2

  @Override
  public void onPause() {
    super.onPause();
    if (Util.SDK_INT <= 23) {
      releasePlayer();
    }
  }

  @Override
  public void onStop() {
    super.onStop();
    if (Util.SDK_INT > 23) {
      releasePlayer();
    }
  }

  private void releasePlayer() {
    if (mExoPlayer != null) {
      mExoPlayer.stop();
      mExoPlayer.release();
      mExoPlayer = null;
    }
  }

}
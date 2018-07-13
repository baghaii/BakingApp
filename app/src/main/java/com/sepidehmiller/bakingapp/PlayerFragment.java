package com.sepidehmiller.bakingapp;


import android.content.Context;
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
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.sepidehmiller.bakingapp.data.Step;

import java.util.ArrayList;

public class PlayerFragment extends Fragment {
  private static final String TAG = "PlayerFragment";
  private SimpleExoPlayerView mExoPlayerView;
  private SimpleExoPlayer mExoPlayer;
  private TextView mStepTextView;
  private ArrayList<Step> mSteps;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    //TODO Have the descriptions update appropriately.
    View rootView = inflater.inflate(R.layout.fragment_player, container, false);
    mExoPlayerView = rootView.findViewById(R.id.player_view);
    mStepTextView = rootView.findViewById(R.id.step_description_text_view);
    mSteps = getArguments().getParcelableArrayList(RecipeAdapter.STEPS);
    if (mSteps != null && mSteps.size() != 0) {
      initializePlayer(Uri.parse(mSteps.get(0).getVideoURL()));
    }
    mStepTextView.setText(mSteps.get(0).getDescription());

    return rootView;
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
    mExoPlayer.stop();
    mExoPlayer.release();
    mExoPlayer = null;
  }

}
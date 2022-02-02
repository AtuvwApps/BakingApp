package com.atuvwapps.bakingapp.fragments;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import com.atuvwapps.bakingapp.R;
import com.atuvwapps.bakingapp.data.Recipe;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

public class StepDetailFragment extends Fragment {

    private Recipe chosenRecipe;
    private int chosenStep;
    private TextView instruction;
    private Button next;
    private Button previous;
    private SimpleExoPlayer player;
    private PlayerView playerView;
    private Uri videoUrl;
    private ConstraintLayout.LayoutParams params;
    private ActionBar actionBar;

    public StepDetailFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflator, ViewGroup container, Bundle SavedInstanceState){
        View rootView = inflator.inflate(R.layout.fragment_step_detail, container, false);
        playerView = rootView.findViewById(R.id.player);
        videoUrl = Uri.parse(chosenRecipe.getSteps().get(chosenStep).getVideoURL());
        player = new SimpleExoPlayer.Builder(getContext()).build();
        playerView.setPlayer(player);
        instruction = rootView.findViewById(R.id.instruction_tv);
        next = rootView.findViewById(R.id.next_btn);
        previous = rootView.findViewById(R.id.previous_btn);
        setupStepDetails();
        params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT);
        actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chosenStep++;
                if(player.isPlaying()){
                    player.stop();
                    player.seekTo(0);
                    player.setPlayWhenReady(true);
                }
                setupStepDetails();
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chosenStep--;
                if(player.isPlaying()){
                    player.stop();
                    player.seekTo(0);
                    player.setPlayWhenReady(true);
                }
                setupStepDetails();
            }
        });
        return rootView;
    }

    private void setupStepDetails(){
        instruction.setText(chosenRecipe.getSteps().get(chosenStep).getDescription());
        if(videoTutorialAvailable()){
            playerView.setVisibility(View.VISIBLE);
            initializePlayer(videoUrl);
        } else{
            playerView.setVisibility(View.GONE);
        }
        checkStepPosition();
    }

    private boolean videoTutorialAvailable(){
        boolean videoAvailable = false;
        if(!chosenRecipe.getSteps().get(chosenStep).getVideoURL().equals("")){
            videoUrl = Uri.parse(chosenRecipe.getSteps().get(chosenStep).getVideoURL());
            videoAvailable = true;
        } else if(!chosenRecipe.getSteps().get(chosenStep).getThumbnailURL().equals("")){
            videoUrl = Uri.parse(chosenRecipe.getSteps().get(chosenStep).getThumbnailURL());
            videoAvailable = true;
        } else{
            videoAvailable = false;
        }
        return videoAvailable;
    }

    private void initializePlayer(Uri mediaUri){
        MediaItem mediaItem = MediaItem.fromUri(mediaUri);
        player.setMediaItem(mediaItem);
        player.prepare();
        player.play();
    }

    private void checkStepPosition(){
        if(chosenStep == 0){
            previous.setVisibility(View.INVISIBLE);
        } else{
            previous.setVisibility(View.VISIBLE);
        }

        if(chosenStep == chosenRecipe.getSteps().size()-1){
            next.setVisibility(View.INVISIBLE);
        } else{
            next.setVisibility(View.VISIBLE);
        }
    }

    public void setChosenStep(int step){
        chosenStep = step;
    }

    public void setChosenRecipe(Recipe recipe) {
        chosenRecipe = recipe;
    }

    public void releaseVideo(){
        if(player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        releaseVideo();
    }

    @Override
    public void onPause(){
        super.onPause();
        if(player != null && player.isPlaying()){
            player.pause();
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if(player != null){
            player.play();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Check the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            params.height = ConstraintLayout.LayoutParams.MATCH_PARENT;
            params.width = ConstraintLayout.LayoutParams.MATCH_PARENT;
            setFullscreen(true);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            params.height = ConstraintLayout.LayoutParams.WRAP_CONTENT;
            setFullscreen(false);
        }
    }

    public void setFullscreen(boolean fullscreen){
        if(fullscreen){
            instruction.setVisibility(View.INVISIBLE);
            next.setVisibility(View.INVISIBLE);
            previous.setVisibility(View.INVISIBLE);
            actionBar.hide();
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else{
            instruction.setVisibility(View.VISIBLE);
            next.setVisibility(View.VISIBLE);
            previous.setVisibility(View.VISIBLE);
            actionBar.show();
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        playerView.setLayoutParams(params);
    }
}

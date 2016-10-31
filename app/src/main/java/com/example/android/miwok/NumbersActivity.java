package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {
    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);
         mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<com.example.android.miwok.Word> words = new ArrayList<com.example.android.miwok.Word>();
        words.add(new com.example.android.miwok.Word("one", "lutti", R.drawable.number_one, R.raw.number_one));
        words.add(new com.example.android.miwok.Word("two", "otiiko", R.drawable.number_two, R.raw.number_two));
        words.add(new com.example.android.miwok.Word("three", "tolookosu", R.drawable.number_three, R.raw.number_three));
        words.add(new com.example.android.miwok.Word("four", "oyyisa", R.drawable.number_four, R.raw.number_four));
        words.add(new com.example.android.miwok.Word("five", "massokka", R.drawable.number_five, R.raw.number_five));
        words.add(new com.example.android.miwok.Word("six", "temmokka", R.drawable.number_six, R.raw.number_six));
        words.add(new com.example.android.miwok.Word("seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven));
        words.add(new com.example.android.miwok.Word("eight", "kawinta", R.drawable.number_eight, R.raw.number_eight));
        words.add(new com.example.android.miwok.Word("nine", "wo’e", R.drawable.number_nine, R.raw.number_nine));
        words.add(new com.example.android.miwok.Word("ten", "na’aacha", R.drawable.number_ten, R.raw.number_ten));
        com.example.android.miwok.WordAdapter adapter = new com.example.android.miwok.WordAdapter(this, words,R.color.category_numbers);

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                releaseMediaPlayer();
                com.example.android.miwok.Word  word  = words.get(position);
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {


                    Log.v("NumbersActivity", "Current word: " + word);
                    mMediaPlayer = MediaPlayer.create(NumbersActivity.this, word.getAudioResourceId());
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);

                }

            }
        });
    }
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}

//        LinearLayout rootView = (LinearLayout) findViewById(R.id.rootView);
//        for (int index = 0; index < words.size(); index++) {
//            TextView wordView = new TextView(this);
//            wordView.setText(words.get(index));
//            rootView.addView(wordView);
//        }

// just for testing
//        int listsize = words.size();
//        for (int i=0;i<listsize;i++){
//            Log.v("NumbersActivity", "word at index:" + i + "\t"+  words.get(i));
//        }
//
//          Log.v("NumbersActivity", "Word at index 9: " + words[9]);


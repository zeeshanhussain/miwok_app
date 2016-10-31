package com.example.android.miwok;


import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<com.example.android.miwok.Word> {
    private int mColorResourceId;
    public WordAdapter(Context context, ArrayList<com.example.android.miwok.Word> words, int colorResourceId) {
        super(context, 0, words);
        mColorResourceId = colorResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        com.example.android.miwok.Word currentWord = getItem(position);

        TextView miwokTextView = (TextView) listItemView.findViewById(R.id.view1);
        miwokTextView.setText(currentWord.getMiwokTranslationId());

        TextView defaultTextView = (TextView) listItemView.findViewById(R.id.view2);
        defaultTextView.setText(currentWord.getDefaultTranslationId());

        ImageView imageImageView = (ImageView)  listItemView.findViewById(R.id.wew);
        if (currentWord.hasImage()){
            imageImageView.setImageResource(currentWord.getImageResourceId());
            imageImageView.setVisibility(View.VISIBLE);

        }
        else {
            imageImageView.setVisibility(View.GONE);
        }
        View textContainer = listItemView.findViewById(R.id.text_container);
        // Find the color that the resource ID maps to
        int color = ContextCompat.getColor(getContext(), mColorResourceId);
        // Set the background color of the text container View
        textContainer.setBackgroundColor(color);


        return listItemView;
    }
}

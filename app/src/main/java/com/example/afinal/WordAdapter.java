package com.example.afinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class WordAdapter extends ArrayAdapter<Word> {
    private int resourceId;

    public WordAdapter(Context context, int textViewResourceId, List<Word> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Word word = getItem(position);
        View view;
        ViewHolder holder;

        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.word_item, parent, false);
            holder = new ViewHolder();
            holder.wordText = view.findViewById(R.id.word);
            holder.pronText = view.findViewById(R.id.pt);
            holder.definitionText = view.findViewById(R.id.word_definition);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }

        if (word != null) {
            holder.wordText.setText(word.getWord());
            holder.pronText.setText(word.getPron());
            holder.definitionText.setText(word.getInterpret());
        }

        return view;
    }

    private static class ViewHolder {
        TextView wordText;
        TextView pronText;
        TextView definitionText;
    }
}

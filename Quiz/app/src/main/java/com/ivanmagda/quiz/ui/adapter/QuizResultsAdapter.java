package com.ivanmagda.quiz.ui.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ivanmagda.quiz.R;
import com.ivanmagda.quiz.model.Question;

import java.util.ArrayList;
import java.util.HashMap;

public class QuizResultsAdapter extends BaseAdapter {

    private static class ViewHolder {
        public TextView titleTextView;
        public ImageView thumbnailImageView;
    }

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Question> mDataSource;
    private HashMap<Integer, Boolean> mAnswers;

    public QuizResultsAdapter(Context context, ArrayList<Question> items, HashMap<Integer, Boolean> answers) {
        mContext = context;
        mDataSource = items;
        mAnswers = answers;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_answer, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.titleTextView = (TextView) convertView.findViewById(R.id.answer_list_title);
            viewHolder.thumbnailImageView = (ImageView) convertView.findViewById(R.id.answer_list_image_view);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Question question = (Question) getItem(position);
        viewHolder.titleTextView.setText(position + 1 + ". " + question.getTitle());

        Boolean isCorrect = mAnswers.get(position);

        ImageView imageView = viewHolder.thumbnailImageView;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageView.setImageDrawable(mContext.getResources().getDrawable((isCorrect ? R.drawable.checked : R.drawable.cancel), null));
        } else {
            imageView.setImageDrawable(mContext.getResources().getDrawable((isCorrect ? R.drawable.checked : R.drawable.cancel)));
        }

        return convertView;
    }

}

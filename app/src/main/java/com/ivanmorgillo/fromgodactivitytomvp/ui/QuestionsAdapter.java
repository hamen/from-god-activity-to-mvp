package com.ivanmorgillo.fromgodactivitytomvp.ui;

import com.ivanmorgillo.fromgodactivitytomvp.R;
import com.ivanmorgillo.fromgodactivitytomvp.api.models.Question;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.ViewHolder> {

    private List<Question> questions;

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView avatar;

        public TextView title;

        public TextView author;

        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.firstLine);
            author = (TextView) v.findViewById(R.id.secondLine);
            avatar = (ImageView) v.findViewById(R.id.icon);
        }
    }

    public void add(int position, Question item) {
        questions.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(Question item) {
        int position = questions.indexOf(item);
        questions.remove(position);
        notifyItemRemoved(position);
    }

    public QuestionsAdapter(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public QuestionsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
        int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.question, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Question question = questions.get(position);
        holder.title.setText(question.getTitle());
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(question);
            }
        });

        holder.author.setText(question.getOwner().getDisplayName());

        ImageLoader.getInstance().displayImage(question.getOwner().getProfileImage(), holder.avatar);
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

}

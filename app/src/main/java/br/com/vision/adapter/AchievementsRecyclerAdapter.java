package br.com.vision.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.vision.R;
import br.com.vision.adapter.AchievementsRecyclerAdapter.AchievementsViewHolder;
import br.com.vision.model.Achievement;

public class AchievementsRecyclerAdapter extends RecyclerView.Adapter<AchievementsViewHolder> {

    static OnItemClickListener mItemClickListener;
    static OnItemLongClickListener mItemLongClickListener;

    List<Achievement> achievements;

    public AchievementsRecyclerAdapter(List<Achievement> achievements) {
        this.achievements = achievements;
    }

    @Override
    public AchievementsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.achievement_list_item, parent, false);

        return new AchievementsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AchievementsViewHolder holder, int position) {
        Achievement achievement = achievements.get(position);
        holder.tvGoal.setText(achievement.getGoal());
        holder.tvPrize.setText(achievement.getPrize());
        holder.tvStatus.setText(achievement.getStatus());
        if (achievement.isCompleted())
            holder.ivGoal.setImageResource(R.drawable.vd_completed_goal);
        else
            holder.ivGoal.setImageResource(R.drawable.vd_uncompleted_goal);
    }

    public static class AchievementsViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnLongClickListener {
        TextView tvGoal, tvPrize, tvStatus;
        ImageView ivGoal;

        AchievementsViewHolder(View itemView) {
            super(itemView);
            tvGoal = itemView.findViewById(R.id.tvGoal);
            tvPrize = itemView.findViewById(R.id.tvPrize);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            ivGoal = itemView.findViewById(R.id.ivGoal);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (mItemLongClickListener != null) {
                mItemLongClickListener.onItemLongClick(v, getAdapterPosition());
            }
            return true;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public void setOnItemLongClickListener(final OnItemLongClickListener mItemLongClickListener) {
        this.mItemLongClickListener = mItemLongClickListener;
    }

    @Override
    public int getItemCount() {
        return achievements.size();
    }
}
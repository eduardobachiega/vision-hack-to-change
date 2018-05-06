package br.com.vision.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import br.com.vision.R;
import br.com.vision.model.Chat;

public class ChatRecyclerAdapter extends RecyclerView.Adapter<ChatRecyclerAdapter.GenericViewHolder> {

    static OnItemClickListener mItemClickListener;
    static OnItemLongClickListener mItemLongClickListener;
    int TYPE_1 = 0;
    int TYPE_2 = 1;
    List<Chat> chatList;

    public ChatRecyclerAdapter(List<Chat> chatList) {
        this.chatList = chatList;
    }

    @Override
    public GenericViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if (viewType == TYPE_1) {
            itemView = LayoutInflater.
                    from(parent.getContext()).
                    inflate(R.layout.item_chat_me, parent, false);
            return new MeViewHolder(itemView);
        } else {
            itemView = LayoutInflater.
                    from(parent.getContext()).
                    inflate(R.layout.item_chat_system, parent, false);
            return new BotViewHolder(itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (!chatList.get(position).isBot())
            return TYPE_1;
        else
            return TYPE_2;
    }

    @Override
    public void onBindViewHolder(GenericViewHolder holder, int position) {
        holder.setDataOnView(chatList.get(position));
    }

    public static class MeViewHolder extends GenericViewHolder implements
            View.OnClickListener, View.OnLongClickListener {
        TextView tvMessage;

        MeViewHolder(View itemView) {
            super(itemView);
            tvMessage = itemView.findViewById(R.id.tvMessage);
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

        @Override
        public void setDataOnView(Chat chat) {
            tvMessage.setText(chat.getMessage());
        }
    }

    public static class BotViewHolder extends GenericViewHolder implements
            View.OnClickListener, View.OnLongClickListener {
        TextView tvMessage;

        BotViewHolder(View itemView) {
            super(itemView);
            tvMessage = itemView.findViewById(R.id.tvMessage);
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

        @Override
        public void setDataOnView(Chat chat) {
            tvMessage.setText(chat.getMessage());
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
        return chatList.size();
    }

    public static abstract class GenericViewHolder extends RecyclerView.ViewHolder {

        public GenericViewHolder(View itemView) {
            super(itemView);
        }

        public abstract void setDataOnView(Chat chat);
    }
}
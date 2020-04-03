package com.personal.demo;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onClick(int position);
        void onDeleteClickListener(int position);
        void onLongClick(View view, int position);
    }

    private Context mContext;
    private OnItemClickListener mListener;
    private List<Model> mDatas = new ArrayList<>();

    public ItemAdapter(Context context, List<Model> datas) {
        mContext = context;
        mDatas = datas;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onClick(position);
                }
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onDeleteClickListener(position);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                Animation rotate = AnimationUtils.loadAnimation(mContext, R.anim.rotate);
                view.startAnimation(rotate);
                holder.delete.setVisibility(View.VISIBLE);
                holder.mask.setVisibility(View.VISIBLE);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        view.clearAnimation();
                    }
                }, 1 * 1000);

                if (mListener != null) {
                    mListener.onLongClick(view, position);
                }
                return true;
            }
        });

        Boolean isDelete = mDatas.get(position).isDetele;
        holder.textView.setText(String.valueOf(mDatas.get(position).i));
        if (isDelete) {
            holder.delete.setVisibility(View.VISIBLE);
            holder.mask.setVisibility(View.VISIBLE);
        }else {
            holder.delete.setVisibility(View.INVISIBLE);
            holder.mask.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout backgound;
        RelativeLayout mask;
        TextView textView;
        ImageButton delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            backgound = itemView.findViewById(R.id.icone_item);
            textView = itemView.findViewById(R.id.item_text);
            mask = itemView.findViewById(R.id.icone_item_mask);
            delete = itemView.findViewById(R.id.icone_item_delete);
        }
    }
}

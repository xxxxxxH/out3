package net.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.HistoryEntity;
import net.basicmodel.R;
import net.utils.OnItemChildClickListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    ArrayList<HistoryEntity> data;
    Context context;
    OnItemChildClickListener listener;

    public HistoryAdapter(ArrayList<HistoryEntity> data, Context context) {
        this.data = data;
        this.context = context;
    }

    public void setListener(OnItemChildClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_history, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull final ViewHolder holder, int position) {
        holder.type.setText(data.get(position).getType());
        holder.content.setText(data.get(position).getContent());
        holder.time.setText(data.get(position).getTime());
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    int pos = holder.getLayoutPosition();
                    listener.onItemChildClick(holder.share, pos, "");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public ArrayList<HistoryEntity> getData() {
        return data;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView type;
        TextView content;
        TextView time;
        ImageView share;

        public ViewHolder(View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.item_type);
            content = itemView.findViewById(R.id.item_content);
            time = itemView.findViewById(R.id.item_time);
            share = itemView.findViewById(R.id.item_share);
        }
    }
}

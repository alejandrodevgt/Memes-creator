package dev.ale.android.memescreator;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Norma on 24/05/2018.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<ListItem> listItems;
    private Context mContext;

    //To click an element from recycler

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public MyAdapter(List<ListItem> listItems, Context context) {
        this.listItems = listItems;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final ListItem listItem = listItems.get(position);
        //holder.tvId.setText(listItem.getId());
        holder.tvName.setText(listItem.getDesc());
        Picasso.get().load(listItem.getImageUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName, tvId;
        public ImageView imageView;

        public ViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.txtViewDesc);
            imageView = (ImageView) itemView.findViewById(R.id.imgvMeme);
            tvId = (TextView) itemView.findViewById(R.id.tvId);

            // To click one item of the recycler
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.OnItemClick(position);
                        }
                    }

                }
            });

        }

    }
}
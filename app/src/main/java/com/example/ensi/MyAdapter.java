package com.example.ensi;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    public ArrayList<Class> Classes;
    private mClickListener listener;

    public MyAdapter(ArrayList<Class> classes, mClickListener listener) {
        this.Classes = classes;
        this.listener = listener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTextView;
        public ImageView mTextMenu;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.textview);
            mTextMenu = itemView.findViewById(R.id.OptionsMenu);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            listener.monClick(view, getAdapterPosition());

        }
    }






    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Class obj = Classes.get(position);
        holder.mTextView.setText(obj.getClasse_name());
        holder.mTextMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                PopupMenu popupMenu = new PopupMenu(view.getContext(), holder.mTextMenu);
                popupMenu.inflate(R.menu.cardview_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){

                            case R.id.item_delete :
                                Classes.remove(position);
                                notifyDataSetChanged();
                                Toast.makeText(view.getContext(), "deleted", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                break;

                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return Classes.size();
    }

    public interface mClickListener {
        void monClick(View v, int position);
    }


}
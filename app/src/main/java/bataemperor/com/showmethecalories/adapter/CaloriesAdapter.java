package bataemperor.com.showmethecalories.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import bataemperor.com.showmethecalories.R;
import bataemperor.com.showmethecalories.model.FoodWrapper;
import bataemperor.com.showmethecalories.model.Hit;
import bataemperor.com.showmethecalories.model.Item;

/**
 * Created by aleksandar on 30.12.16..
 */

public class CaloriesAdapter extends RecyclerView.Adapter<CaloriesAdapter.CaloriesHolder> {
    private List<FoodWrapper> items;
    private LayoutInflater inflater;

    public CaloriesAdapter(List<FoodWrapper> items, Context context) {
        this.items = items;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public CaloriesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CaloriesHolder(inflater.inflate(R.layout.layout_food_item, parent, false));
    }

    @Override
    public void onBindViewHolder(CaloriesHolder holder, int position) {
        FoodWrapper foodWrapper = items.get(position);
        holder.tvFood.setText(String.valueOf(foodWrapper.getFood()));
        holder.tvCal.setText(String.valueOf(foodWrapper.getCalorie()));
        if (position == 0) {
            holder.tvCal.setTypeface(null, Typeface.BOLD);
            holder.tvFood.setTypeface(null, Typeface.BOLD);
        } else {
            holder.tvCal.setTypeface(null, Typeface.NORMAL);
            holder.tvFood.setTypeface(null, Typeface.NORMAL);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public void addList(List<FoodWrapper> list) {
        items.clear();
        items.addAll(list);
        notifyDataSetChanged();
    }

    public class CaloriesHolder extends RecyclerView.ViewHolder {
        TextView tvFood, tvCal;

        public CaloriesHolder(View itemView) {
            super(itemView);
            tvFood = (TextView) itemView.findViewById(R.id.tvFood);
            tvCal = (TextView) itemView.findViewById(R.id.tvCalorie);
        }
    }
}

package co.wisne.matrimonyapp.ui.search.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.wisne.matrimonyapp.R;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {

    private ArrayList<String> results = new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;

        public ViewHolder(View view){
            super(view);
            name = view.findViewById(R.id.textResultName);

        }
    }

    public SearchResultAdapter(@NonNull List<String> results){
        this.results.addAll(results);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_search_result_item, parent, false);

        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(results.get(position));
    }

    @Override
    public int getItemCount() {
        return results.size();
    }


}

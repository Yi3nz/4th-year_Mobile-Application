package sarah.nci.ie.reminder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * The layout of the main recycler list item
 */

class CAAdapter extends RecyclerView.Adapter<CAAdapter.ViewHolder> {

    List<CA> cas;

    public CAAdapter(List<CA> cas) {
        this.cas = cas;
    }

    @Override
    public CAAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_adapter_view ,parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CAAdapter.ViewHolder holder, int position) {
        holder.tvSubject.setText(cas.get(position).getSubject());
        holder.tvCATitle.setText(cas.get(position).getCATitle());
        holder.tvDate.setText(cas.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return cas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvCATitle;
        public TextView tvSubject;
        public TextView tvDate;

        public ViewHolder(View itemView) {
            super(itemView);
            tvCATitle = itemView.findViewById(R.id.tvCATitle);
            tvSubject = itemView.findViewById(R.id.tvSubject);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}

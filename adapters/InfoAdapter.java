package com.example.assignment_workshop_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_workshop_app.R;
import com.example.assignment_workshop_app.activities.InfoActivity;
import com.example.assignment_workshop_app.models.InfoModel;

import org.w3c.dom.Text;

import java.util.List;

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.ViewHolder> {

    List<InfoModel> infoModelList;
    SelectedInfo selectedInfo;
    Context context;

    private RadioButton selectedRadioBtn;

    public InfoAdapter(List<InfoModel> infoModelList, Context context, SelectedInfo selectedInfo) {
        this.infoModelList = infoModelList;
        this.context = context;
        this.selectedInfo = selectedInfo;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.info_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.info.setText(infoModelList.get(position).getUserInfo());
        holder.radioButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                for (InfoModel info:infoModelList) {
                    info.setSelected(false);
                }
                infoModelList.get(position).setSelected(true);

                if (selectedRadioBtn!=null) {
                    selectedRadioBtn.setChecked(false);
                }
                selectedRadioBtn = (RadioButton) v;
                selectedRadioBtn.setChecked(true);
                selectedInfo.setInfo(infoModelList.get(position).getUserInfo());
            }
        });
    }

    @Override
    public int getItemCount() {
        return infoModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView info;
        RadioButton radioButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            info = itemView.findViewById(R.id.info_add);
            radioButton = itemView.findViewById(R.id.select_info);
        }
    }

    public interface SelectedInfo {
        void setInfo(String info);
    }
}

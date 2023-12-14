package com.example.babycare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SiestasAdapter extends RecyclerView.Adapter<SiestasAdapter.SiestasAdapterHolder>{
    Context context;
    ArrayList<ListSiestas> siestas;

    public SiestasAdapter(Context context, ArrayList<ListSiestas> siestas) {
        this.context = context;
        this.siestas = siestas;
    }

    @NonNull
    @Override
    public SiestasAdapter.SiestasAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.items_siestas,parent,false);
        return new SiestasAdapterHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SiestasAdapter.SiestasAdapterHolder holder, int position) {
        ListSiestas siesta = siestas.get(position);
        holder.dia.setText(siesta.getDia());
        holder.horaI.setText(siesta.getHora_Inicio());
        holder.horaF.setText(siesta.getHora_Fin());
    }

    @Override
    public int getItemCount() {
        return siestas.size();
    }

    public static class SiestasAdapterHolder extends RecyclerView.ViewHolder{
        TextView dia, horaI, horaF;
        public SiestasAdapterHolder(@NonNull View itemView) {
            super(itemView);
            dia = itemView.findViewById(R.id.dia);
            horaI = itemView.findViewById(R.id.horaI);
            horaF = itemView.findViewById(R.id.horaF);
        }
    }
}

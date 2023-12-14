package com.example.babycare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ComidasAdapter extends RecyclerView.Adapter<ComidasAdapter.ComidasAdapterHolder>{
    Context context;
    ArrayList<ListComidas> comidas;

    public ComidasAdapter(Context context, ArrayList<ListComidas> comidas) {
        this.context = context;
        this.comidas = comidas;
    }

    @NonNull
    @Override
    public ComidasAdapter.ComidasAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.items_comidas,parent,false);
        return new ComidasAdapterHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ComidasAdapter.ComidasAdapterHolder holder, int position) {
        ListComidas comida = comidas.get(position);
        holder.dia.setText(comida.getDia());
        holder.hora.setText(comida.getHora());
        holder.fuente.setText(comida.getFuente());
    }

    @Override
    public int getItemCount() {
        return comidas.size();
    }

    public static class ComidasAdapterHolder extends RecyclerView.ViewHolder{
        TextView dia, hora, fuente;
        public ComidasAdapterHolder(@NonNull View itemView) {
            super(itemView);
            dia = itemView.findViewById(R.id.dia);
            hora = itemView.findViewById(R.id.hora);
            fuente = itemView.findViewById(R.id.fuente);
        }
    }
}

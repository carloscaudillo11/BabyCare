package com.example.babycare;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class ConsejosAdapter extends RecyclerView.Adapter<ConsejosAdapter.ConsejosAdapterHolder> {
    Context context;
    ArrayList<ListConsejos> consejos;

    public ConsejosAdapter(Context context, ArrayList<ListConsejos> consejos) {
        this.context = context;
        this.consejos = consejos;
    }

    @NonNull
    @Override
    public ConsejosAdapter.ConsejosAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.items_consejos,parent,false);
        return new ConsejosAdapterHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ConsejosAdapter.ConsejosAdapterHolder holder, int position) {
        ListConsejos consejo = consejos.get(position);
        holder.autor.setText(consejo.getAutor());
        holder.titulo.setText(consejo.getTitulo());
        Glide.with(context).load(consejo.getImg()).centerCrop().into(holder.imagen);
        holder.fecha.setText(consejo.getFechaDePublicacion());
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(holder.itemView.getContext(), contenido.class);
            intent.putExtra("itemDetail", consejo);
            holder.itemView.getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return consejos.size();
    }

    public static class ConsejosAdapterHolder extends RecyclerView.ViewHolder{
        TextView autor, titulo, fecha;
        ImageView imagen;
        public ConsejosAdapterHolder(@NonNull View itemView) {
            super(itemView);
            autor= itemView.findViewById(R.id.autor);
            titulo= itemView.findViewById(R.id.titulo);
            imagen = itemView.findViewById(R.id.imagenes);
            fecha = itemView.findViewById(R.id.fechaPub);
        }
    }
}

package com.ADAenseatech.adaptcurso;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ADAenseatech.R;
import com.ADAenseatech.modelos.Nota;
import java.util.List;

public class AdaptNotas extends RecyclerView.Adapter<AdaptNotas.NotaViewHolder> {

    private List<Nota> listaNotas;

    public AdaptNotas(List<Nota> listaNotas) {
        this.listaNotas = listaNotas;
    }

    @NonNull
    @Override
    public NotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_nota, parent, false);
        return new NotaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotaViewHolder holder, int position) {
        Nota nota = listaNotas.get(position);
        holder.bind(nota);
    }

    @Override
    public int getItemCount() {
        return listaNotas.size();
    }

    static class NotaViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTituloNota, tvContenidoNota, tvCursoNota, tvFechaNota;

        public NotaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTituloNota = itemView.findViewById(R.id.tvTituloNota);
            tvContenidoNota = itemView.findViewById(R.id.tvContenidoNota);
            tvCursoNota = itemView.findViewById(R.id.tvCursoNota);
            tvFechaNota = itemView.findViewById(R.id.tvFechaNota);
        }

        public void bind(Nota nota) {
            tvTituloNota.setText(nota.getTitulo());
            tvContenidoNota.setText(nota.getContenido());
            tvCursoNota.setText("Curso: " + nota.getCurso());
            tvFechaNota.setText(nota.getFecha());
        }
    }
}

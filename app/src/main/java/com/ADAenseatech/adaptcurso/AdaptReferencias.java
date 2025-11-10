package com.ADAenseatech.adaptcurso;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ADAenseatech.R;
import com.ADAenseatech.modelos.Referencia;
import java.util.List;

public class AdaptReferencias extends RecyclerView.Adapter<AdaptReferencias.ReferenciaViewHolder> {

    private List<Referencia> listaReferencias;

    public AdaptReferencias(List<Referencia> listaReferencias) {
        this.listaReferencias = listaReferencias;
    }

    @NonNull
    @Override
    public ReferenciaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_referencia, parent, false);
        return new ReferenciaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReferenciaViewHolder holder, int position) {
        Referencia referencia = listaReferencias.get(position);
        holder.bind(referencia);
    }

    @Override
    public int getItemCount() {
        return listaReferencias.size();
    }

    static class ReferenciaViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTituloReferencia, tvTipoReferencia;

        public ReferenciaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTituloReferencia = itemView.findViewById(R.id.tvTituloReferencia);
            tvTipoReferencia = itemView.findViewById(R.id.tvTipoReferencia);
        }

        public void bind(Referencia referencia) {
            tvTituloReferencia.setText(referencia.getTitulo());
            tvTipoReferencia.setText("Tipo: " + referencia.getTipo());
        }
    }
}

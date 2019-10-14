package pruebas.roomdatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.RoomDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TareasViewModel tareasViewModel;
    TareasAdapter tareasAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tareasViewModel = ViewModelProviders.of(this).get(TareasViewModel.class);

        findViewById(R.id.nuevaTarea).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, NuevaTareaActivity.class));
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_tareas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(tareasAdapter = new TareasAdapter());

        tareasViewModel.getTareasDetalle().observe(this, new Observer<List<TareaDetalle>>() {
            @Override
            public void onChanged(List<TareaDetalle> queryResult) {
                tareasAdapter.setList(queryResult);
            }
        });
    }

    class TareaViewHolder extends RecyclerView.ViewHolder {
        TextView descripcion, fecha, prioridad;
        ImageView trash;

        public TareaViewHolder(@NonNull View itemView) {
            super(itemView);
            descripcion = itemView.findViewById(R.id.tarea_descripcion);
            fecha = itemView.findViewById(R.id.tarea_fecha);
            prioridad = itemView.findViewById(R.id.tarea_prioridad);
            trash = itemView.findViewById(R.id.trash);
        }
    }

    class TareasAdapter extends RecyclerView.Adapter<TareaViewHolder>{

        List<TareaDetalle> tareaDetalles;

        @NonNull
        @Override
        public TareaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new TareaViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_tarea, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull TareaViewHolder holder, int position) {
            final TareaDetalle tareaDetalle = tareaDetalles.get(position);

            holder.descripcion.setText(tareaDetalle.descripcion);
            holder.fecha.setText(tareaDetalle.fecha);
            holder.prioridad.setText(tareaDetalle.prioridad);

            holder.trash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tareasViewModel.deleteTarea(tareaDetalle.id);
                }
            });
        }

        @Override
        public int getItemCount() {
            return tareaDetalles != null ? tareaDetalles.size() : 0;
        }

        public void setList(List<TareaDetalle> list){
            tareaDetalles = list;
            notifyDataSetChanged();
        }
    }
}
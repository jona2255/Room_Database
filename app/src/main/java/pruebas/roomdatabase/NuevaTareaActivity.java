package pruebas.roomdatabase;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class NuevaTareaActivity extends AppCompatActivity {

    TareasViewModel tareasViewModel;
    EditText editTextDescripcion;
    Spinner spinnerPrioridades;

    int prioridadId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_tarea);

        editTextDescripcion = findViewById(R.id.tarea_descripcion);
        spinnerPrioridades = findViewById(R.id.tarea_prioridad);

        tareasViewModel = ViewModelProviders.of(this).get(TareasViewModel.class);


        findViewById(R.id.crear_tarea).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if(editTextDescripcion.getText().toString().isEmpty()){
                    editTextDescripcion.setError("Introduzca la descripción");
                    return;
                }
                tareasViewModel.insertarTarea(new Tarea(editTextDescripcion.getText().toString(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), prioridadId));
                finish();
            }
        });

        tareasViewModel.getPrioridades().observe(this, new Observer<List<Prioridad>>() {
            @Override
            public void onChanged(final List<Prioridad> prioridads) {
                spinnerPrioridades.setAdapter(new ArrayAdapter<>(NuevaTareaActivity.this, R.layout.support_simple_spinner_dropdown_item, prioridads));
                spinnerPrioridades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        prioridadId = prioridads.get(i).id;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {}
                });
            }
        });
    }
}
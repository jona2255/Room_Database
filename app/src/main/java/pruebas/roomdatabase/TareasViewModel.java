package pruebas.roomdatabase;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TareasViewModel extends AndroidViewModel {
    TareasDao tareasDao;

    public TareasViewModel(@NonNull Application application) {
        super(application);

        tareasDao = TareasDatabase.getInstance(application).tareasDao();
    }

    public LiveData<List<Prioridad>> getPrioridades(){
        return tareasDao.getPrioridades();
    }

    public LiveData<List<TareaDetalle>> getTareasDetalle(){
        return tareasDao.getTareasDetalle();
    }

    public void insertarTarea(final Tarea tarea){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                tareasDao.insertarTarea(tarea);
            }
        });
    }

    public void deleteTarea(final int id){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                tareasDao.deleteTarea(id);
            }
        });
    }
}
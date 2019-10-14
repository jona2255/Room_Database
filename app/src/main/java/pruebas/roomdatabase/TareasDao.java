package pruebas.roomdatabase;

import pruebas.roomdatabase.Prioridad;
import pruebas.roomdatabase.Tarea;
import pruebas.roomdatabase.TareaDetalle;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public abstract class TareasDao {

    @Insert
    public abstract void insertarPrioridad(Prioridad prioridad);

    @Query("SELECT * FROM Prioridad")
    public abstract LiveData<List<Prioridad>> getPrioridades();



    @Insert
    public abstract void insertarTarea(Tarea tarea);

    @Query("DELETE FROM Tarea WHERE id=:id")
    public abstract void deleteTarea(int id);

    @Query("SELECT * FROM TareaDetalle")
    public abstract LiveData<List<TareaDetalle>> getTareasDetalle();
}
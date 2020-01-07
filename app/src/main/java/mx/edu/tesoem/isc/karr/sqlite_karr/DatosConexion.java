package mx.edu.tesoem.isc.karr.sqlite_karr;



import android.content.ContentValues;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import mx.edu.tesoem.isc.karr.sqlite_karr.DatosHelper.*;

public class DatosConexion {

    private SQLiteDatabase basedatos;
    private DatosHelper datosHelper;

    public DatosConexion(Context context)
    {
        datosHelper = DatosHelper.getInstance(context);
    }

    public void open()
    {
        basedatos= datosHelper.getWritableDatabase();
    }

    public void close()
    {
        basedatos.close();
    }

    public String[] llenagridview()
    {
        String[] datos;
        int columnas=4;
        Cursor cursor = basedatos.rawQuery("select * from " + tabladatos.TABLA, null);
        if (cursor.getCount()<=0)
        {
            datos = new String[4];

            datos[0]=tabladatos.COLUMNA_ID;
            datos[1]=tabladatos.COLUMNA_NOMBRE;
            datos[2]=tabladatos.COLUMNA_EDAD;
            datos[3]=tabladatos.COLUMNA_CORREO;
        }
        else
        {
            datos = new String[(cursor.getCount()*4)+4];
            datos[0]=tabladatos.COLUMNA_ID;
            datos[1]=tabladatos.COLUMNA_NOMBRE;
            datos[2]=tabladatos.COLUMNA_EDAD;
            datos[3]=tabladatos.COLUMNA_CORREO;
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                datos[columnas]=String.valueOf(cursor.getInt(0));
                datos[columnas+1]=cursor.getString(1);
                datos[columnas+2]=String.valueOf(cursor.getInt(2));
                datos[columnas+3]=cursor.getString(3);
                columnas+=4;
                cursor.moveToNext();
            }
        }
        return datos;
    }

    public boolean insertar (ContentValues contentValues)
    {
        boolean estado = true;
        basedatos.isOpen();
        int resultadoconsulta =(int) basedatos.insert(tabladatos.TABLA, null, contentValues);
        if (!(resultadoconsulta == 1)) estado = false;
        basedatos.close();

        return estado;

    }

    public boolean actualizar (ContentValues contentValues, String[] condicion)
    {
        boolean esatdo = true;
        basedatos.isOpen();
        int resultadoconsulta = (int)basedatos.update(tabladatos.TABLA,contentValues,tabladatos.COLUMNA_ID + "=?",condicion);
        if (!(resultadoconsulta == 1)) esatdo= false;
        basedatos.close();
        return esatdo;

    }

    public boolean eliminar (String[] condicion)
    {
        boolean estado = true;
        basedatos.isOpen();
        int resultado= (int)basedatos.delete(tabladatos.TABLA,tabladatos.COLUMNA_ID + "=?",condicion);

        if (!(resultado == 1)) estado= false;
        basedatos.close();
        return estado;
    }

}

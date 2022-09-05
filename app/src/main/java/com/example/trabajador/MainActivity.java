package com.example.trabajador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText etcodigo, etnombre, etpago;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etcodigo = (EditText) findViewById(R.id.txt_codigo);
        etnombre = (EditText) findViewById(R.id.txt_nombre);
        etpago  = (EditText) findViewById(R.id.txt_pago);
    }

    //METODOS PADA REGISTRAR AL TRABAJADOR
    public void Registrar(View view){
        SQLiteDB admin = new SQLiteDB(this, "administracion",null,1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String codigo = etcodigo.getText().toString();
        String nombre = etnombre.getText().toString();
        String pago   = etpago.getText().toString();

        if(!codigo.isEmpty() && !nombre.isEmpty() && !pago.isEmpty()){
            ContentValues registro = new ContentValues();

            registro.put("codigo", codigo);
            registro.put("nombre", nombre);
            registro.put("pago", pago);

            BaseDeDatos.insert("trabajador", null, registro);

            BaseDeDatos.close();
            etcodigo.setText("");
            etnombre.setText("");
            etpago.setText("");

            Toast.makeText(this,"Registro exitoso", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

        //METODO PARA CONSULTA
        public void Buscar(View view){
            SQLiteDB admin = new SQLiteDB(this, "administracion",null,1);
            SQLiteDatabase BaseDatabase  = admin.getWritableDatabase();

            String codigo = etcodigo.getText().toString();

            if(!codigo.isEmpty()){
            Cursor fila = BaseDatabase.rawQuery("select nombre, pago from trabajador where codigo =" + codigo, null);

            if(fila.moveToFirst()){
                etnombre.setText(fila.getString(0));
                etpago.setText(fila.getString(1));
                BaseDatabase.close();
                }else{
                Toast.makeText(this,"No existe este registro", Toast.LENGTH_SHORT).show();
                BaseDatabase.close();
            }
            }else{
                Toast.makeText(this,"Introdusca codigo trabajador", Toast.LENGTH_SHORT).show();
            }
        }

        //METODO MODIFICAR

        public void Modificar(View view) {
            SQLiteDB admin = new SQLiteDB(this, "administracion", null, 1);
            SQLiteDatabase BaseDatabase = admin.getWritableDatabase();

            String codigo = etcodigo.getText().toString();
            String nombre = etnombre.getText().toString();
            String pago = etpago.getText().toString();

            if (!codigo.isEmpty() && !nombre.isEmpty() && !pago.isEmpty()) {
                ContentValues registro = new ContentValues();
                registro.put("codigo", codigo);
                registro.put("nombre", nombre);
                registro.put("pago", pago);

                int cantidad = BaseDatabase.update("trabajador", registro, "codigo=" + codigo, null);
                BaseDatabase.close();

                if (cantidad == 1) {
                    Toast.makeText(this, "Datos modificados", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Registro no existente", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Debe llenar los campos", Toast.LENGTH_SHORT).show();
            }
        }

        //METODO ELIMINAR
        public void Eliminar(View view) {
            SQLiteDB admin = new SQLiteDB(this, "administracion", null, 1);
            SQLiteDatabase BaseDatabase = admin.getWritableDatabase();

            String codigo = etcodigo.getText().toString();

            if (!codigo.isEmpty()) {
                int cantidad = BaseDatabase.delete("trabajador", "codigo=" + codigo, null);
                BaseDatabase.close();

                etcodigo.setText("");
                etnombre.setText("");
                etpago.setText("");

                if (cantidad == 1) {
                    Toast.makeText(this, "Registro eliminado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Registro no existe", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Introdusca codigo de trabajador", Toast.LENGTH_SHORT).show();
            }
        }

}
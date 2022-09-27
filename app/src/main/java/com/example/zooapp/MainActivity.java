package com.example.zooapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase bancoDados;
    ListView listView;
    Button btnAdd;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listAnimal);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        
        criarBancoDados();
        inserirDadosTemp();
        
    }


    private void openConnection() {
        this.bancoDados = openOrCreateDatabase("zoo", MODE_PRIVATE, null);
    }

    public void criarBancoDados() {
        try {
            openConnection();
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS animal (" + " id INTEGER PRIMARY KEY AUTOINCREMENT" + ", especie VARCHAR, cela VARCHAR)");
            bancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    };
    public void inserirDadosTemp() {
        try {
            openConnection();
            String sql = "INSERT INTO animal (especie,cela) VALUES (?,?)";
            SQLiteStatement stmt = bancoDados.compileStatement(sql);

            stmt.bindString(1, "Ema");
            stmt.bindString(2, "Cela 1");
            stmt.executeInsert();

            stmt.bindString(1, "Jumento");
            stmt.bindString(2, "Cela 2");
            stmt.executeInsert();

            stmt.bindString(1, "Cavalo");
            stmt.bindString(2, "Cela 3");
            stmt.executeInsert();

            bancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    };

    public void listarDados() {
        openConnection();
        Cursor meuCursos = bancoDados.rawQuery("SELECT id, especie, cela FROM animal", null);
        ArrayList<String> linhas = new ArrayList<String>();
        ArrayAdapter meuAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                linhas
        );
        listView.setAdapter(meuAdapter);
    }

}
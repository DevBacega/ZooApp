package com.example.zooapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    FloatingActionButton btnAdd;
    private SQLiteDatabase bancoDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        btnAdd = (FloatingActionButton) findViewById(R.id.btnAdd);

        criarBancoDados();
        // inserirDadosTemp();
        listarDados();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirTelaCadastro();
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        listarDados();
    }

    public void criarBancoDados(){
        try {
            bancoDados = openOrCreateDatabase("zoo", MODE_PRIVATE, null);
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS animal(" +
                    " id INTEGER PRIMARY KEY AUTOINCREMENT" +
                    " , nome VARCHAR)");
            //bancoDados.execSQL("DELETE FROM animal");
            bancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void listarDados(){
        try {

            bancoDados = openOrCreateDatabase("zoo", MODE_PRIVATE, null);
            Cursor meuCursor = bancoDados.rawQuery("SELECT id, nome FROM animal", null);
            ArrayList<String> linhas = new ArrayList<String>();
            ArrayAdapter meuAdapter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    linhas
            );
            listView.setAdapter(meuAdapter);

            meuCursor.moveToFirst();
            do {
                linhas.add(meuCursor.getString(0) + " - " + meuCursor.getString(1));
            } while(meuCursor.moveToNext());

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void inserirDadosTemp(){
        try{
            bancoDados = openOrCreateDatabase("zoo", MODE_PRIVATE, null);
            String sql = "INSERT INTO animal (nome) VALUES (?)";
            SQLiteStatement stmt = bancoDados.compileStatement(sql);

            stmt.bindString(1,"Ema");
            stmt.executeInsert();

            stmt.bindString(1,"Zebra");
            stmt.executeInsert();

            stmt.bindString(1,"Gorila");
            stmt.executeInsert();

            bancoDados.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void abrirTelaCadastro(){
        Intent intent = new Intent(this,AddActivity.class);
        startActivity(intent);
    }
}
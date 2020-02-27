package com.codevelopers.launcher;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ssasa.core.Launcher;
import com.ssasa.core.LauncherListener;
import com.ssasa.core.pojo.LauncherException;
import com.ssasa.core.pojo.Persona;


public class MainActivity extends AppCompatActivity {

    //Objeto que nos sirve para lanzar o mandar a llamar al aplicativo del Lector
    private Launcher launcher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Instanciamos el objeto launcher enviando los siguientes paramtros
        //Activity o Fragment: Este activity o Fragment es el activity o Fragment en donde se va a invocar el app del lector
        //SecretKey: Este parametro es un string proporciano por SSASA, sin este string el app de Lector no se podra invocar
        //LauncherListener: Este parametro es un listener que nos permite saber si se obtuvo la información de forma correcta o en
        //Caso que haya ocurrido un error
        launcher = new Launcher(this, "735458xxxxxxxxx7b4afb24", new LauncherListener() {

            //Metodo lanzado por la interface cuando no ocurrio problemas al obtener la información
            @Override
            public void onSuccess(Persona persona) {

                //El objeto persona contiene toda la información capturada en el DPI
                //el metodo getFoto retorna foto en 64bits

                byte[] fotobytes = Base64.decode(persona.getFoto(), Base64.DEFAULT);
            }

            //Metodo lanzado para informarnos que ocurrió un error

            //Los errores que pueden ocurrir son los siguientes:
            //Error Code|Message                                            |Momento en que ocurre
            //1         | "The activity is null"                            | Cuando la actividad proporcionada es nula
            //2         | "Invalid Secret Key"                              | Cuando se envia una llave vacia o nula
            //3         | "No has Launcher app"                             | Cuando no se encuentra el app instalada
            //4         | "The user cancel capture or Secret key is invalid"| Cuando el usuario cancelo la lectura o se envio una llave invalida
            //5         | "Not capturing data"                              | cuando se hizo la Lectura pero no se obtuvieron datos

            @Override
            public void onError(LauncherException error) {

                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    //Debemos de sobreescribir el "OnActivityResult" de la actividad o Fragment que se envió como parametro en el constructor del objeto Launcher
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //se debe de invocar el metodo onActivityResult del objeto Launcher
        launcher.onActivityResult(requestCode, resultCode, data);
    }

    public void getDPI(View view) {

        //El metodo lauch del objeto Launcher es quien manda a invocar el app de Lector
        launcher.launch();
    }
}

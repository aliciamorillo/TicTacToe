package com.example.tictactoe;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends Activity {

    private int jugadores;
    private int[] casillas;
    private Partida partida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Iniciamos el array casillas que identifica + almacena

        casillas = new int[9];

        casillas[0] = R.id.a1;
        casillas[1] = R.id.a2;
        casillas[2] = R.id.a3;
        casillas[3] = R.id.b1;
        casillas[4] = R.id.b2;
        casillas[5] = R.id.b3;
        casillas[6] = R.id.c1;
        casillas[7] = R.id.c2;
        casillas[8] = R.id.c3;

        findViewById(R.id.unJugador).setOnClickListener(this::aJugar);
        findViewById(R.id.dosJugadores).setOnClickListener(this::aJugar);
    }


    private void aJugar (View vista) {

        ImageView imagen;

        for(int cadaCasilla:casillas){
            imagen = findViewById(cadaCasilla);
            imagen.setImageResource(R.drawable.casilla);
        }

        jugadores = 1;

        if (vista.getId() == R.id.dosJugadores){
            jugadores = 2;

        }

        RadioGroup configurarDificultad = findViewById(R.id.configDificultad);

        int id = configurarDificultad.getCheckedRadioButtonId();

        //Fácil:
        int dificultad = 0;

        if (id == R.id.normal){
            dificultad = 1;
        } else if(id == R.id.dificil){
            dificultad = 2;
        }

        partida = new Partida(dificultad);

        findViewById(R.id.unJugador).setEnabled(false);

        findViewById(R.id.configDificultad).setAlpha(0);

        findViewById(R.id.dosJugadores).setEnabled(false);
    }


    public void toque (View vista){

        if(partida == null){
            return;
        }

        int casilla = 0;

        for (int i=0;i<9;i++){
            if(casillas[i] == vista.getId()){
                casilla = i;
                break;
            }
        }

        /*Toast toast = Toast.makeText(this,"Has pulsado la Casilla " + casilla, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();*/

        if(!partida.compruebaCasilla(casilla)){
            return;
        }

        marca(casilla);
        //Después de marcar, cambia de turno
        partida.turno();

        casilla = partida.inteligenciaArtificial();

        while(partida.compruebaCasilla(casilla) != true){
            casilla = partida.inteligenciaArtificial();
        }

        marca(casilla);

        partida.turno();
    }


    private void marca (int casilla){
        ImageView imagen;
        imagen = findViewById(casillas[casilla]);

        if(partida.jugador == 1){
            imagen.setImageResource(R.drawable.circulo);
        } else {
            imagen.setImageResource(R.drawable.cruz);
        }
    }

}
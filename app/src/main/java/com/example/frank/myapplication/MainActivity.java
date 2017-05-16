package com.example.frank.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String[] PYP = {"4 - 5", "6 - 7", "8 - 9","0 - 1", "2 - 3"};
    private static final Integer[] FESTIVOS = {1, 9, 79, 103, 104, 121, 149, 170, 177, 184, 201, 219, 233, 289, 310, 317, 342, 359};
   // private static final Integer [] LISTA ={-1,4,6,8,0,2,-1,-1,6,8,0,2,4,-1,-1,8,0,2,4,6,-1,-1,0,2,4,6,8,-1,-1,2,4,6,8,0,-1};

    TextView tv1,tv_placa,tv_proximo_dia,tx_fecha,tx4;
    private ImageView iv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String picoYplacaHoy;
        tv1=(TextView)findViewById(R.id.tv1);
        tv_placa=(TextView)findViewById(R.id.tv_placa);
        tv_proximo_dia=(TextView)findViewById(R.id.tv_proximo_dia);
        tx_fecha=(TextView)findViewById(R.id.tx_fecha);
        tx4=(TextView)findViewById(R.id.tx4);
        iv1=(ImageView)findViewById(R.id.iv1);

        SharedPreferences prefe=getSharedPreferences("datos", Context.MODE_PRIVATE);
        tv_placa.setText(prefe.getString("placa","0-1"));

        Calendar localCalendar = Calendar.getInstance();
        int diaAnio = localCalendar.get(Calendar.DAY_OF_YEAR);
        int diaSemana = localCalendar.get(Calendar.DAY_OF_WEEK);
        if(Arrays.asList(FESTIVOS).contains(diaAnio) || diaSemana == 1 || diaSemana == 7) {// si es festivo o sabado o domingo
            picoYplacaHoy="NO APLICA";
        }else{
            int j=diaAnio;
            while(j>7){
                j=localCalendar.get(Calendar.DAY_OF_WEEK)==Calendar.FRIDAY?j-11:j-6;//si es viernes resta 11 y si es otro dia resta 6
                localCalendar.set(Calendar.DAY_OF_YEAR, j);// cambia la fecha a la fecha restada
            }
            picoYplacaHoy=PYP[localCalendar.get(Calendar.DAY_OF_YEAR)-2];//se resta dos porque lunes comenzo el dia 2
        }
        tv1.setText(" " + picoYplacaHoy + " ");
        //próximo pico y placa ... solo cuando el mismo dia corresponde con el pico y placa
        localCalendar.set(Calendar.DAY_OF_YEAR, diaAnio);//vuelve al dia actual


        si_no();
        imagen();

        long hoy = System.currentTimeMillis();
        Date fecha = new Date(hoy);

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String f = df.format(fecha);

        tx_fecha.setText(f);

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefe=getSharedPreferences("datos", Context.MODE_PRIVATE);
        tv_placa.setText(prefe.getString("placa","0-1"));
        Date dia = new Date();
        Calendar localCalendar = Calendar.getInstance();
        int diaAnio = localCalendar.get(Calendar.DAY_OF_YEAR);

        boolean enc1 = false;
        int pos1 = 2;
        for (int i = 0; i < PYP.length && !enc1; i++) {
            if (PYP[i].equals(tv_placa.getText())) {
                enc1 = true;
                pos1 = pos1 + i;
            }
        }

        while (pos1 <= diaAnio) {
            localCalendar.set(Calendar.DAY_OF_YEAR, pos1);
            pos1= localCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY ? pos1 + 11 : pos1 + 6;
            localCalendar.set(Calendar.DAY_OF_YEAR, pos1);
            if (Arrays.asList(FESTIVOS).contains(pos1)) {
                localCalendar.set(Calendar.DAY_OF_YEAR, pos1);
                pos1= localCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY ? pos1 + 11 : pos1 + 6;
                localCalendar.set(Calendar.DAY_OF_YEAR, pos1);
            }
        }

        dia = localCalendar.getTime();
        SimpleDateFormat fech = new SimpleDateFormat("dd-MM-yyyy");
        String fech1 = fech.format(dia);
       // tv_proximo_dia.setText("En " + (pos1 - diaAnio) + " dias, " + fech1);
        tv_proximo_dia.setText(fech1);

        if(tv1.getText().toString().equals(" "+tv_placa.getText().toString()+" ")){
            tx4.setText(R.string.HOY_SI);
            tx4.setTextColor(Color.RED);
        }else {
            tx4.setText(R.string.HOY_NO);
            tx4.setTextColor(Color.GREEN);
        }


    }

    public void configuracion(View v)
    {
        Intent i = new Intent(this, ConfigActivity.class );
        startActivity(i);
    }

    public void acercaDe(View v)
    {
        Intent i = new Intent(this, AcercaDe.class );
        startActivity(i);
    }

    public void si_no(){

        if(tv1.getText().toString().equals(" "+tv_placa.getText().toString()+" ")){
            tx4.setText(R.string.HOY_SI);
            tx4.setTextColor(Color.RED);
        }else {
            tx4.setText(R.string.HOY_NO);
            tx4.setTextColor(Color.GREEN);
        }
    }

    public  void imagen(){
        // tv_proximo_dia.setText("-"+tv1.getText().toString()+"-");

        if(tv1.getText().equals(" 0 - 1 ")) {
            iv1.setImageResource(R.drawable.a);
        }

        if(tv1.getText().equals(" 2 - 3 ")) {
            iv1.setImageResource(R.drawable.b);
        }

        if(tv1.getText().equals(" 4 - 5 ")) {
            iv1.setImageResource(R.drawable.c);
        }

        if(tv1.getText().equals(" 6 - 7 ")) {
            iv1.setImageResource(R.drawable.d);
        }

        if(tv1.getText().equals(" 8 - 9 ")) {
            iv1.setImageResource(R.drawable.e);
        }

        if(tv1.getText().equals(" NO APLICA ")) {
            iv1.setImageResource(R.drawable.no);
        }
    }
}

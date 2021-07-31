package com.americadigital.libupapi.Utils;

import static java.lang.Thread.sleep;

public class Timmers {

    public void startTimer(){
        int nuSeg=10;//El Contador de de segundos
        try {//si ocurre un error al dormir el proceso(sleep(999))
            for (; ;){//inicio del for infinito
                if(nuSeg!=0) {//si no es el ultimo segundo
                    nuSeg--;  //decremento el numero de segundos
                } else{
                    break;
                }

                System.out.println(nuSeg);//Muestro en pantalla el temporizador
                sleep(998);//Duermo el hilo durante 999 milisegundos(casi un segundo, quintandole el tiempo de proceso)
            }
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }//Fin try
    }
}

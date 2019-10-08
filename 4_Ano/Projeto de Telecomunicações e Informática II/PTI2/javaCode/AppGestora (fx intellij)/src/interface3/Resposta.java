/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interface3;

/**
 *
 * @author andre
 */
public class Resposta {
    String respostas = null;
    String anterior = null;

        private static Resposta instance;

        public static Resposta getInstance( ){
            if (instance==null){
                instance = new Resposta();
            }
            return instance;

        }
    
    public void putresposta (String respost){
        this.respostas=respost;
    }
    public String getresposta(){
        //String new_resp = this.respostas;
        return this.respostas;
    }
    
    public void putrespostaanterior (String anterior){
        this.anterior=anterior;
    }
    
    public String getrespostaanterior (){
        return this.anterior;
    }
}

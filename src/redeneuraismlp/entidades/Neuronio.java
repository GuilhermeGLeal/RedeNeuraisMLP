package redeneuraismlp.entidades;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Neuronio {
      
    private float net;
    private float saida;
    private float erro;
    private List<Float> pesos;
    private int qtdPesos;

    public Neuronio(int qtdPessos) {
        this.net = 0;
        this.saida = 0;
        this.erro = 0;
        this.pesos = new ArrayList();
        this.qtdPesos = qtdPessos;
        setPeso();
    }

    public float getNet() {
        return net;
    }

    public void setNet(float net) {
        
       // net = (float)(Math.floor(net * 1000) / 1000);   
        this.net = net;
    }

    public float getSaida() {
        return saida;
    }

    public void setSaida(float saida) {
        
         //saida = (float)(Math.floor(saida * 1000) / 1000);     
        this.saida = saida;
    }

    public float getErro() {
        
     
        return erro;
    }

    public void setErro(float erro) {
        
         erro = (float)(Math.floor(erro * 1000) / 1000);     
        this.erro = erro;
    }
    
    public void resetaPesos(List<Float> novospesos){
          
        this.pesos = null;
        this.pesos = novospesos;    
    }
    
    public void setPeso(int pos, float valor){
        
       // valor = (float)(Math.floor(valor * 1000) / 1000);           
        
        this.pesos.add(pos, valor);
    }
    
    private void setPeso(){
        
        float rand, rand2;
        float max = 4;
        
        for (int i = 0; i < qtdPesos; i++) {
            
            rand = new Random().nextFloat()* max;
            rand2 =  Math.round(new Random().nextInt());
            
            if(rand2 <= 0.5)
                rand = -rand;            
           
            rand = (float)(Math.floor(rand * 1000) / 1000);           
          
            this.pesos.add(i, rand);
        }
        
    }
    
    public float getPeso(int pos){
        
        return this.pesos.get(pos);
    }
}

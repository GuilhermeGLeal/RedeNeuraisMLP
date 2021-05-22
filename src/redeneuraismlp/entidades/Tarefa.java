package redeneuraismlp.entidades;

import java.util.ArrayList;
import java.util.List;


public class Tarefa extends Thread {
       
  
    private double valorErro;  
    private List<Neuronio> camadaOcultaMLP;
    private List<Neuronio> camadaSaidaMLP;
    private double taxaaprend;
    private List<Double> novosValores;
    private int i;
    
    public Tarefa( double valorErro, List<Neuronio> camadaOcultaMLP, List<Neuronio> camadaSaidaMLP, double taxaaprend, int i) {

        this.valorErro = valorErro;
     
        this.camadaOcultaMLP = camadaOcultaMLP;
        this.camadaSaidaMLP = camadaSaidaMLP;
        this.taxaaprend = taxaaprend;
        this.i = i;
        novosValores = new ArrayList();
    }

    public List<Double> getNovosValores() {
        return novosValores;
    }
     
    
     @Override
    public void run() {
      
        double saidaOculta;
        double novoPeso, valorPeso;

        for (int j = 0; j < camadaOcultaMLP.size(); j++) {

            saidaOculta = this.camadaOcultaMLP.get(j).getSaida();
            valorPeso = this.camadaSaidaMLP.get(i).getPeso(j);
            novoPeso = valorPeso + taxaaprend * valorErro * saidaOculta;

            this.novosValores.add(novoPeso);
        }
        
             

    }
}

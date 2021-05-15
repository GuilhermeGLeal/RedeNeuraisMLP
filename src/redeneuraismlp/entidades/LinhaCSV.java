
package redeneuraismlp.entidades;

import java.util.ArrayList;
import java.util.List;

public class LinhaCSV {
  
    private String valorclasse;
    private List<Atributo> atributos;

    public LinhaCSV(String valorclasse) {
        this.valorclasse = valorclasse; 
        this.atributos = new ArrayList();
    }

    public String getValorclasse() {
        return valorclasse;
    }

    public void setValorclasse(String valorclasse) {
        this.valorclasse = valorclasse;
    }

    public List<Atributo> getAtributos() {
        return atributos;
    }
    
    public void setAtributo(String nome, double valor){
        
        this.atributos.add(new Atributo(nome, valor));
    }



}

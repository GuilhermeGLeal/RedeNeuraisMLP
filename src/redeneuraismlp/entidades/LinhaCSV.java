
package redeneuraismlp.entidades;

import java.util.List;


public class LinhaCSV {
  
    private String valorclasse;
    private List<Atributo> atributos;

    public LinhaCSV(String valorclasse, List<Atributo> atributos) {
        this.valorclasse = valorclasse;
        this.atributos = atributos;
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

    public void setAtributos(List<Atributo> atributos) {
        this.atributos = atributos;
    }



}

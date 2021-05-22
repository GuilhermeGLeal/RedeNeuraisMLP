
package redeneuraismlp.entidades;


public class Normalizacao {
    
    private String atributo;
    private float menorValor;
    private float maiorValor;
    private float intervalo;

    public Normalizacao(String atributo, float menorValor, float maiorValor) {
        this.atributo = atributo;
        this.menorValor = menorValor;
        this.maiorValor = maiorValor;
        this.intervalo = maiorValor - menorValor;
    }

    public String getAtributo() {
        return atributo;
    }

    public void setAtributo(String atributo) {
        this.atributo = atributo;
    }

    public double getMenorValor() {
        return menorValor;
    }

    public void setMenorValor(float menorValor) {
        this.menorValor = menorValor;
    }

    public float getMaiorValor() {
        return maiorValor;
    }

    public void setMaiorValor(float maiorValor) {
        this.maiorValor = maiorValor;
    }

    public float getIntervalo() {
        return intervalo;
    }

    public void setIntervalo() {
        this.intervalo = maiorValor - menorValor;
    }
    
    

}

package redeneuraismlp.controladora;

import redeneuraismlp.entidades.Arquivo;

public class ControladoraArquivo {

    private Arquivo arq;
    private int camadaOculta;
    private double erroMinimo;
    private int maximaInteract;
    private int taxaAprend;
    private String funcaoTrans;

    public ControladoraArquivo() {

        this.camadaOculta = 0;
        this.erroMinimo = 0;
        this.maximaInteract = 0;
        this.taxaAprend = 0;
        this.funcaoTrans = "";
    }

    public void AbrirArquivo(String path) {
        arq = new Arquivo(path);

        arq.lerArquivo();       

    }

    public Arquivo getArq() {
        return this.arq;
    }

    public void resetaDados() {
        arq = null;
    }

}

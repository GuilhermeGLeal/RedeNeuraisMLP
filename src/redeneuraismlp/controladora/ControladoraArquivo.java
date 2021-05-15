package redeneuraismlp.controladora;

import redeneuraismlp.entidades.Arquivo;

public class ControladoraArquivo {

    private Arquivo arq;

    public ControladoraArquivo() {

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

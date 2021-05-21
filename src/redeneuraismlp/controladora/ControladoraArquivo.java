package redeneuraismlp.controladora;

import redeneuraismlp.entidades.Arquivo;
import redeneuraismlp.entidades.CalculoRedeNeural;

public class ControladoraArquivo {

    private Arquivo arq;
    private CalculoRedeNeural redeNeural;
   
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
    
    public void chamarAlgoritmo(int camadaoculta, double errominimo, int maximainteract, double taxaaprend, int funcaoTrans,
    boolean isTest){
        
        redeNeural = new CalculoRedeNeural(camadaoculta, errominimo, maximainteract, taxaaprend, funcaoTrans, 
                arq.getOutputLayer(), arq.getInputLayer(), arq.getClasses());
        
       // System.out.println(isTest);
       
       if(isTest)
           redeNeural.testar(arq.getLinhas());
       else
            redeNeural.treinar(arq.getLinhas());
    }

}

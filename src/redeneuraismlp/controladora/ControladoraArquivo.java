package redeneuraismlp.controladora;

import java.util.List;
import redeneuraismlp.entidades.Arquivo;
import redeneuraismlp.entidades.CalculoRedeNeural;

public class ControladoraArquivo {

    private Arquivo arq;
    private CalculoRedeNeural redeNeural;
    private List<Double> listaErros;
   
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
    boolean isTest, boolean isMSLINT){
    
    List<Double> lis;    
        
        redeNeural = new CalculoRedeNeural(camadaoculta, errominimo, maximainteract, taxaaprend, funcaoTrans, 
                arq.getOutputLayer(), arq.getInputLayer(), arq.getClasses());
        
     
        if(!isMSLINT){
                
             if(isTest)
                redeNeural.testar(arq.getLinhas());
            else
             {
                 listaErros=redeNeural.treinar(arq.getLinhas());             
             }
                
        }   
        else
            chamaBaseMSLINT();
       
      
    }

    public List<Double> getListaErros() 
    {
        return listaErros;
    }
    
    public void chamaBaseMSLINT(){
        
        
    }

}

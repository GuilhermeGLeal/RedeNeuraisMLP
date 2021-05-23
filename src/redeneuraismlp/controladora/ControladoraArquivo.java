package redeneuraismlp.controladora;

import java.util.ArrayList;
import java.util.List;
import redeneuraismlp.entidades.Arquivo;
import redeneuraismlp.entidades.CalculoRedeNeural;
import redeneuraismlp.entidades.LinhaCSV;

public class ControladoraArquivo {

    private Arquivo arq;
    private CalculoRedeNeural redeNeural;
    private List<Double> listaErros;
    private int matrizConfusao[][];
    private List<LinhaCSV> testeMNIST;
      
    public ControladoraArquivo() {
     
    }

    public void resetaDados(){
     
        testeMNIST = null;
    }
    
    public void AbrirArquivo(String path, String nome_arquivo) {
        
        if(arq == null)
             arq = new Arquivo(path, nome_arquivo);
        else{
            
            arq.setNome_arquivo(nome_arquivo);
            arq.setPath(path);
            
         
        }
        
        arq.openArq();

        arq.lerArquivo();       

    }

    public Arquivo getArq() {
        return this.arq;
    }

   
    public void chamarAlgoritmo(int camadaoculta, double errominimo, int maximainteract, double taxaaprend, int funcaoTrans,
            boolean isTest, boolean isMSLINT) {

        if (!isMSLINT) {

            if (isTest) {
                redeNeural.testar(arq.getLinhas());
                matrizConfusao = redeNeural.getMatrizConfusão();
            } else {
                redeNeural = new CalculoRedeNeural(camadaoculta, errominimo, maximainteract, taxaaprend, funcaoTrans,
                        arq.getOutputLayer(), arq.getInputLayer(), arq.getClasses());
                listaErros = redeNeural.treinar(arq.getLinhas());
            }

        } else {
            chamaBaseMSLINT(camadaoculta, errominimo, maximainteract, taxaaprend, funcaoTrans );
        }

    }

    public List<Double> getListaErros() 
    {
        return listaErros;
    }

    public int[][] getMatrizConfusao() {
        return matrizConfusao;
    }
    
    
    public void chamaBaseMSLINT(int camadaoculta, double errominimo, int maximainteract, double taxaaprend, int funcaoTrans){
        
        List<LinhaCSV> MNISTtreino = new ArrayList();
        
        
        if (testeMNIST == null) {

            testeMNIST = new ArrayList();
            
            for (int i = 0; i < arq.getLinhas().size(); i++) {
                  
                if(i < 700)
                    MNISTtreino.add(arq.getLinhas().get(i));
                else{
                     testeMNIST.add(arq.getLinhas().get(i));
                }
               
            }
            
            redeNeural = new CalculoRedeNeural(camadaoculta, errominimo, maximainteract, taxaaprend, funcaoTrans,
                    arq.getOutputLayer(), arq.getInputLayer(), arq.getClasses());
            
            
            listaErros = redeNeural.treinar(MNISTtreino);
  
        }else{
            
              redeNeural.testar(testeMNIST);
                matrizConfusao = redeNeural.getMatrizConfusão();
        }
        
    }

}

package redeneuraismlp.entidades;

import java.util.ArrayList;
import java.util.List;


public class CalculoRedeNeural {
    
    private  int camadaOculta;
    private double erroMinimo;
    private int maximaInteract;
    private double taxaAprend;
    private int funcaoTrans;
    private int camadaSaida;
    private int camadaEntrada;
    private List<Neuronio> camadaOcultaMLP;
    private List<Neuronio> camadaSaidaMLP;
    private List<Double> errosdaRede;
    private int saidaEsperada[];
    private List<String> classes;

    public CalculoRedeNeural(int camadaOculta, double erroMinimo, int maximaInteract, double taxaAprend, int funcaoTrans, int camadaSaida, int camadaEntrada,
            List<String> classes) {
        this.camadaOculta = camadaOculta;
        this.erroMinimo = erroMinimo;
        this.maximaInteract = maximaInteract;
        this.taxaAprend = taxaAprend;
        this.funcaoTrans = funcaoTrans;
        this.camadaSaida = camadaSaida;
        this.camadaEntrada = camadaEntrada;
        this.saidaEsperada = new int[camadaSaida];     
        this.classes = classes;
      
      
    }
  
    private void setSaidaEsperada(String classeAtual) {

        int posClasse = classes.indexOf(classeAtual);
        this.saidaEsperada = new int[camadaSaida];
        
        this.saidaEsperada[posClasse] = 1;
     
    }
    
    private void calculaNeuronios() {
    
       this.camadaOcultaMLP = new ArrayList();
       this.camadaSaidaMLP = new ArrayList();
       
        // criando os neuronios da camada oculta
        for (int i = 0; i < camadaOculta; i++) {

            this.camadaOcultaMLP.add(new Neuronio(camadaEntrada));
        }

        // criando os neuronios da camada de saida
        for (int i = 0; i < camadaSaida; i++) {

            this.camadaSaidaMLP.add(new Neuronio(camadaOculta));
        }

    }

    private float linear(float net){
        
        return  net / 10;
    }
    
    private float linearDerivada(){
        
        return  (float) 0.1;
    }
    
    private float logistica(float net){
        
        double numeroEuler = 2.7182818284;
        double pow = Math.pow(numeroEuler, -net);
        
        return (float) (1 / ( 1 + pow));
    }
    
    private float logisticaDerivada(float saida){
        
        return  (float) (saida * (1 - saida));
    }
    
    private float Hiperbolica(float net){
        
        double numeroEuler = 2.7182818284;
        double pow = Math.pow(numeroEuler, -(2 * net));
        
        return (float) ((1 - pow) / (1 + pow)) ;
    }
    
    private float HiperbolicaDerivada(float saida){
        
        return  (float) (1 - Math.pow(saida, 2));
    }
    
   
    
    private double calcularErroRede(){
     
        double soma = 0;
        double pow;
        
                
        for (int i = 0; i < camadaSaida; i++) {
            
            pow = camadaSaidaMLP.get(i).getErro() * camadaSaidaMLP.get(i).getErro();   
            
            soma += pow;
        }
        
        System.out.println(soma);
        soma += 0.5 * soma;
        
        return soma;
    }
    
    private float retornaSaida(float net){
        
        float valor = 0;
        
        if(funcaoTrans == 1){
            
            valor = linear(net);
        }
        else if(funcaoTrans == 2){
         
            valor = logistica(net);
        }
        else{
            
            valor = Hiperbolica(net);
        }
        
        return valor;
    }
    
    private float retornaGradiente(float saida){
        
        float valor = 0;
        
        if(funcaoTrans == 1){
            
            valor = linearDerivada();
        }
        else if(funcaoTrans == 2){
         
            valor = logisticaDerivada(saida);
        }
        else{
            
            valor = HiperbolicaDerivada(saida);
        }
        
        return valor;
    }
    
    private void calcularCamadaOculta(List<Atributo> linha){
        
        float netNeuronio = 0;
        float valorLinha;
        float saida;
        
        for (int i = 0; i < camadaOculta; i++) {

            netNeuronio = 0;
            saida = 0;
            
            for (int j = 0; j < camadaEntrada; j++) {

                valorLinha = linha.get(j).getValor();
                netNeuronio += valorLinha * this.camadaOcultaMLP.get(i).getPeso(j);
            }
            
            saida = retornaSaida(netNeuronio);     
            
        
            
            this.camadaOcultaMLP.get(i).setNet(netNeuronio);
            this.camadaOcultaMLP.get(i).setSaida(saida);
            
        }


    }
    
    private void calcularCamadaSaida(){
        
        float netNeuronio = 0;
        float valorLinha;
        float saida;
        float erro;
        float retorno;
                
        for (int i = 0; i < camadaSaida; i++) {
            
            for (int j = 0; j < camadaOculta; j++) {

                valorLinha = this.camadaOcultaMLP.get(j).getSaida();
                netNeuronio += valorLinha * this.camadaSaidaMLP.get(i).getPeso(j);
            }
            
            saida = retornaSaida(netNeuronio);
            retorno =  retornaGradiente(saida);
            erro = (saidaEsperada[i] - saida) * retorno;
            
            this.camadaSaidaMLP.get(i).setNet(netNeuronio);
            this.camadaSaidaMLP.get(i).setSaida(saida);
            this.camadaSaidaMLP.get(i).setErro(erro);            
        }
       
    }    
    
    private void calculaErroCamadaOculta() {

        float retorno;
        float erro = 0, erroGrad;
        float valorLinha;
        
        for (int i = 0; i < camadaOculta; i++) {
            
            for (int j = 0; j < camadaSaida; j++) {
                
                valorLinha = this.camadaSaidaMLP.get(j).getErro();
                erro += valorLinha * this.camadaSaidaMLP.get(j).getPeso(i);
            }
            
            retorno = retornaGradiente(camadaOcultaMLP.get(i).getSaida());
            erroGrad = erro * retorno;
            
            this.camadaOcultaMLP.get(i).setErro(erroGrad);
        }
    }
    
    public void calcularNovosPesosCamadaSaida(){
        
        float saidaOculta;
        float valorErro, novoPeso, valorPeso;
        List<Float> novosPesos;
        
        for (int i = 0; i < camadaSaida; i++) {
            
            valorErro = this.camadaSaidaMLP.get(i).getErro();         
            novosPesos = new ArrayList();
            
            for (int j = 0; j < camadaOculta; j++) {
                
                saidaOculta = this.camadaOcultaMLP.get(j).getSaida();
                valorPeso = this.camadaSaidaMLP.get(i).getPeso(j);
                
                novoPeso = (float) (valorPeso + taxaAprend * valorErro * saidaOculta);
                
                 novosPesos.add(j, novoPeso);
            }
            
             this.camadaSaidaMLP.get(i).resetaPesos(novosPesos);
        }
    }
              
    public void calcularNovosPesosCamadaOculta(List<Atributo> atributos) {
        
        float entrada;
        float valorErro, novoPeso, valorPeso;
        List<Float> novosPesos;

        for (int i = 0; i < camadaOculta; i++) {
            
            valorErro = this.camadaOcultaMLP.get(i).getErro();
            novosPesos = new ArrayList();
            
            for (int j = 0; j < camadaEntrada; j++) {
                
                entrada = atributos.get(j).getValor();
                valorPeso = this.camadaOcultaMLP.get(i).getPeso(j);
                
                novoPeso = (float) (valorPeso + taxaAprend * valorErro * entrada);
                
                 novosPesos.add(j, novoPeso);
            }
            
             this.camadaOcultaMLP.get(i).resetaPesos(novosPesos);
        }
    }
   
    public void treinar(List<LinhaCSV> linhasCSV) {

        LinhaCSV linha;
        double soma;
        boolean erroMin = false;

        this.errosdaRede = new ArrayList();
          calculaNeuronios();
          
        for (int i = 0; i < maximaInteract && !erroMin; i++) {

            for (int j = 0; j < linhasCSV.size() && !erroMin; j++) {

                linha = linhasCSV.get(j);
                setSaidaEsperada(linha.getValorclasse()); // seta matriz de resultado esperado

                calcularCamadaOculta(linha.getAtributos()); // calcula o net e a saida dos neuronios da camada oculta
                calcularCamadaSaida(); // calcular net, saida e erro da camada de saida
                soma = calcularErroRede(); // erro da rede

                this.errosdaRede.add((double) soma);

              

                if (soma <= erroMinimo) { // se atingir o erro minimo para o algortimo

                    System.out.printf("erro final: %.10f", soma);
                    System.out.println("Terminou na epoca: " + i);

                    erroMin = true;
                }

                if (!erroMin) {

                    calculaErroCamadaOculta(); // calcula o erro da camada oculta
                    calcularNovosPesosCamadaSaida();// atualizar pesos da camada de saida
                    calcularNovosPesosCamadaOculta(linha.getAtributos());// atualizar pessos da  camada oculta
                }

            }

            System.out.println("Epoca: " + i);
        }

        if (!erroMin) {

            System.out.println("Não foi possivel chegar no erro minimo, menor erro encontrado: " + errosdaRede.get(errosdaRede.size() - 1));
        }
      
    }
    
     public void testar(List<LinhaCSV> linhasCSV){
        
    }

    public List<Double> getErrosdaRede() {
        return errosdaRede;
    }
     
     
}



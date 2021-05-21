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
    private int cont = 0;

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
        this.camadaOcultaMLP = new ArrayList();
        this.camadaSaidaMLP = new ArrayList();
        this.errosdaRede = new ArrayList();       
        this.classes = classes;
        
        System.out.println(camadaOculta);
        System.out.println(erroMinimo);
        System.out.println(maximaInteract);
        System.out.println(taxaAprend);
        System.out.println(funcaoTrans);
        System.out.println(camadaSaida);
        System.out.println(camadaEntrada);
                    
        calculaNeuronios();
    }
  
    private void setSaidaEsperada(String classeAtual) {

        int posClasse = classes.indexOf(classeAtual);
        this.saidaEsperada = new int[camadaSaida];
        
        this.saidaEsperada[posClasse] = 1;

        /*
        for (int i = 0; i < saidaEsperada.length; i++) {
            
            System.out.println(saidaEsperada[i]);
        }
        
        System.out.println(classeAtual);
        System.out.println(posClasse);
        */
    }
    
    private void calculaNeuronios() {

        // criando os neuronios da camada oculta
        for (int i = 0; i < camadaOculta; i++) {

            this.camadaOcultaMLP.add(new Neuronio(camadaEntrada));
        }

        // criando os neuronios da camada de saida
        for (int i = 0; i < camadaSaida; i++) {

            this.camadaSaidaMLP.add(new Neuronio(camadaOculta));
        }

    }

    private double linear(double net){
        
        return  net / 10;
    }
    
    private double linearDerivada(){
        
        return  0.1;
    }
    
    private double logistica(double net){
        
        double numeroEuler = 2.7182818284;
        double pow = Math.pow(numeroEuler, -net);
        
        return 1 / ( 1 + pow);
    }
    
    private double logisticaDerivada(double net){
        
        return  net * (1 - net);
    }
    
    private double Hiperbolica(double net){
        
        double numeroEuler = 2.7182818284;
        double pow = Math.pow(numeroEuler, -(2 * net));
        
        return (1 - pow) / (1 + pow) ;
    }
    
    private double HiperbolicaDerivada(double net){
        
        return  1 - Math.pow(net, 2);
    }
    
   
    
    private double calcularErroRede(){
     
        double soma = 0;
        
        for (int i = 0; i < camadaSaida; i++) {
            
            soma =+ Math.pow(camadaSaidaMLP.get(i).getErro(), 2);
        }
        
        soma += 0.5 * soma;
        
        return soma;
    }
    
    private double retornaSaida(double net){
        
        double valor = 0;
        
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
    
    private double retornaGradiente(double net){
        
        double valor = 0;
        
        if(funcaoTrans == 1){
            
            valor = linearDerivada();
        }
        else if(funcaoTrans == 2){
         
            valor = logisticaDerivada(net);
        }
        else{
            
            valor = HiperbolicaDerivada(net);
        }
        
        return valor;
    }
    
    private void calcularCamadaOculta(List<Atributo> linha){
        
        double netNeuronio = 0;
        double valorLinha;
        double saida;
        
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
        
        double netNeuronio = 0;
        double valorLinha;
        double saida;
        double erro;
        double retorno;
                
        for (int i = 0; i < camadaSaida; i++) {
            
            for (int j = 0; j < camadaOculta; j++) {

                valorLinha = this.camadaOcultaMLP.get(j).getSaida();
                netNeuronio += valorLinha * this.camadaSaidaMLP.get(i).getPeso(j);
            }
            
            saida = retornaSaida(netNeuronio);
            retorno = retornaGradiente(netNeuronio);
            erro = (saidaEsperada[i] - saida) * retorno;
            
            this.camadaSaidaMLP.get(i).setNet(netNeuronio);
            this.camadaSaidaMLP.get(i).setSaida(saida);
            this.camadaSaidaMLP.get(i).setErro(erro);            
        }
       
    }    
    
    private void calculaErroCamadaOculta() {

        double retorno;
        double erro = 0, erroGrad;
        double valorLinha;
        
        for (int i = 0; i < camadaOculta; i++) {
            
            for (int j = 0; j < camadaSaida; j++) {
                
                valorLinha = this.camadaSaidaMLP.get(j).getErro();
                erro += valorLinha * this.camadaSaidaMLP.get(j).getPeso(i);
            }
            
            retorno = retornaGradiente(camadaOcultaMLP.get(i).getNet());
            erroGrad = erro * retorno;
            
            this.camadaOcultaMLP.get(i).setErro(erroGrad);
        }
    }
    
    public void calcularNovosPesosCamadaSaida(){
        
        double saidaOculta;
        double valorErro, novoPeso, valorPeso;
        
        for (int i = 0; i < camadaSaida; i++) {
            
            valorErro = this.camadaSaidaMLP.get(i).getErro();
            
            for (int j = 0; j < camadaOculta; j++) {
                
                saidaOculta = this.camadaOcultaMLP.get(j).getSaida();
                valorPeso = this.camadaSaidaMLP.get(i).getPeso(j);
                
                novoPeso = valorPeso + taxaAprend * valorErro * saidaOculta;
                
                this.camadaSaidaMLP.get(i).setPeso(j, novoPeso);
            }
        }
    }
    
    public void calcularNovosPesosCamadaSaidaCopia(){
        
        List<Tarefa> tarefas = new ArrayList();
        double valorErro;
        
        for (int i = 0; i < camadaSaida; i++) {
            
            valorErro = this.camadaSaidaMLP.get(i).getErro();
            Tarefa tar = new Tarefa(erroMinimo, camadaOcultaMLP, camadaSaidaMLP, taxaAprend, i);
            tar.setName("Neuronio: "+i+" começou as contas!");
            tarefas.add(tar);
        }
        
        for (int i = 0; i < tarefas.size(); i++) {
            
            tarefas.get(i).start();
        }
        
        try {
            for (int i = 0; i < tarefas.size(); i++) {
                
                tarefas.get(i).join();
            }
            
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        
        
    }
    
    
    public void calcularNovosPesosCamadaOculta(List<Atributo> atributos) {
        
        double entrada;
        double valorErro, novoPeso, valorPeso;
        
        for (int i = 0; i < camadaOculta; i++) {
            
            valorErro = this.camadaOcultaMLP.get(i).getErro();
            
            for (int j = 0; j < camadaEntrada; j++) {
                
                entrada = atributos.get(j).getValor();
                valorPeso = this.camadaOcultaMLP.get(i).getPeso(j);
                
                novoPeso = valorPeso + taxaAprend * valorErro * entrada;
                
                this.camadaOcultaMLP.get(i).setPeso(j, novoPeso);
            }
        }
    }
    
    public void treinar(List<LinhaCSV> linhasCSV) {

        LinhaCSV linha;
        double soma;

        for (int i = 0; i < maximaInteract; i++) {

            for (int j = 0; j < linhasCSV.size(); j++) {

                linha = linhasCSV.get(j);
                setSaidaEsperada(linha.getValorclasse()); // seta matriz de resultado esperado

                calcularCamadaOculta(linha.getAtributos()); // calcula o net e a saida dos neuronios da camada oculta
                calcularCamadaSaida(); // calcular net, saida e erro da camada de saida
                soma = calcularErroRede(); // erro da rede
                this.errosdaRede.add(soma);                
                if (soma == erroMinimo) { // se atingir o erro minimo para o algortimo
                    i = maximaInteract + 1;
                }
               
                calculaErroCamadaOculta(); // calcula o erro da camada oculta
                calcularNovosPesosCamadaSaidaCopia();// atualizar pesos da camada de saida
                //calcularNovosPesosCamadaOculta(linha.getAtributos());// atualizar pessos da  camada oculta

            }           
            
            System.out.println("acabou epoca: "+i);

        }
    }
    
     public void testar(List<LinhaCSV> linhasCSV){
        
    }

    public List<Double> getErrosdaRede() {
        return errosdaRede;
    }
     
     
}



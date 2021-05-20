package redeneuraismlp.entidades;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Arquivo {

    private BufferedReader csvReader;
    private int outputLayer;
    private int inputLayer;
    private int hiddenLayer;
    private List<LinhaCSV> linhas;
    private List<Normalizacao> normalizacaos;
    private List<String> colunas;

    public Arquivo(String path) {

        System.out.println(path);
        try {
            csvReader = new BufferedReader(new FileReader(path));
        } catch (IOException ex) {
            System.out.println(ex);
        }

        this.outputLayer = this.inputLayer = this.hiddenLayer = 0;
        this.linhas = new ArrayList<LinhaCSV>();
        this.normalizacaos = new ArrayList<Normalizacao>();
        this.colunas = new ArrayList<String>();

    }

    public int getOutputLayer() {
        return outputLayer;
    }

    public void setOutputLayer(int outputLayer) {
        this.outputLayer = outputLayer;
    }

    public int getInputLayer() {
        return inputLayer;
    }

    public void setInputLayer(int inputLayer) {
        this.inputLayer = inputLayer;
    }

    public int getHiddenLayer() {
        return hiddenLayer;
    }

    public List<LinhaCSV> getLinhas() {
        return linhas;
    }

    
    private void normalizar() {
       
       double valorAntigo, novoValor;
        List<Atributo> listAux;
        
        for (int i = 0; i < normalizacaos.size(); i++) {
            
            normalizacaos.get(i).setIntervalo();
        }
        
        for (int i = 0; i < linhas.size(); i++) {
            
            listAux = linhas.get(i).getAtributos();
            
            for (int j = 0; j < listAux.size(); j++) {
                        
                // novo valor = (valor antigo - menor valor)/ intervalo
                valorAntigo = listAux.get(j).getValor();                
                novoValor = (valorAntigo - normalizacaos.get(j).getMenorValor()) / normalizacaos.get(j).getIntervalo();
                
                //System.out.println(novoValor + listAux.get(j).getNome());
                listAux.get(j).setValor(novoValor);
            }
        }
    }

    public void lerArquivo() {
        
        String row;
        String[][] data = new String[1500][1500];
        String [] atributos = new String[790];
        String classe = "";
        LinhaCSV linha;
        double valor;
        int j = 0;
        int qtdClasses = 0;
        String classeAnt = "";
        
        try {

            row = csvReader.readLine();
            atributos = row.split(",");
            normalizacaos = new ArrayList();            
            
            for (int i = 0; i < atributos.length; i++) {
                
                if(atributos[i] != "" && !atributos[i].equals("classe")){
                    
                    normalizacaos.add(new Normalizacao(atributos[i], 0, 0));
                }
            }
            
            inputLayer = normalizacaos.size();
            /*
            for (int i = 0; i < normalizacaos.size(); i++) {
                
                System.out.println(normalizacaos.get(i).getAtributo());
            }*/
            
            
            while ((row = csvReader.readLine()) != null) {
               
                data[j] = row.split(",");
                classe = data[j][data[j].length-1];
               
                linha = new LinhaCSV(classe);
                
                for (int i = 0; i < data[j].length; i++) {

                    if (i != data[j].length-1) {
                        valor = Double.parseDouble(data[j][i]);
                        linha.setAtributo(atributos[i], valor);

                        if (valor < normalizacaos.get(i).getMenorValor()) {
                            normalizacaos.get(i).setMenorValor(valor);
                        } else if (valor > normalizacaos.get(i).getMaiorValor()) {
                            normalizacaos.get(i).setMaiorValor(valor);
                        }
                    }
                    else{
                        
                        if(classeAnt.equals("") || !classeAnt.equals(data[j][i])){
                            
                            qtdClasses++;
                            classeAnt = data[j][i];
                        }
                    }

                }
                                
                linhas.add(linha);
                
                j++;
            }

            /*
            for (int i = 0; i < linhas.size(); i++) {
                
                System.out.println(linhas.get(i).getValorclasse());
                
                for (int k = 0; k < linhas.get(i).getAtributos().size(); k++) {
                    
                     System.out.println(linhas.get(i).getAtributos().get(k).getValor());
                    System.out.println(linhas.get(i).getAtributos().get(k).getNome());
                }
            }*/
            
            csvReader.close();

        } catch (IOException ex) {
        }
        
        outputLayer = qtdClasses;
        this.hiddenLayer = (inputLayer + outputLayer) / 2;
        normalizar();

    }

}

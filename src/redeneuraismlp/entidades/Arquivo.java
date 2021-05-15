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

    public void setHiddenLayer(int hiddenLayer) {
        this.hiddenLayer = hiddenLayer;
    }

    private void normalizar() {
        // após ler normalizar
    }

    public void lerArquivo() {
        
        String row;
        String[][] data = new String[1500][1500];
        String [] atributos = new String[790];
        String classe = "";
        LinhaCSV linha;
        double valor;
        int j = 0;
        try {

            row = csvReader.readLine();
            atributos = row.split(",");
                    
            while ((row = csvReader.readLine()) != null) {
               
                data[j] = row.split(",");
                classe = data[j][data[j].length-1];
               
                linha = new LinhaCSV(classe);
                
                for (int i = 0; i < data[j].length-1; i++) {
                    
                    valor = Double.parseDouble(data[j][i]);
                   linha.setAtributo(atributos[i], valor);
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

    }

}

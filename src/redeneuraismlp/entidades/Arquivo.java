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

        try {

            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
            }

            csvReader.close();

        } catch (IOException ex) {
        }

    }

}

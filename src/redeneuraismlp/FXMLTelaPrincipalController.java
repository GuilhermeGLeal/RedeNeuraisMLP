package redeneuraismlp;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import redeneuraismlp.controladora.ControladoraArquivo;
import redeneuraismlp.entidades.Atributo;
import redeneuraismlp.entidades.LinhaCSV;
import redeneuraismlp.entidades.LinhaTabela;

public class FXMLTelaPrincipalController implements Initializable {
    
    @FXML
    private TableView<LinhaTabela> tableCsv;
    @FXML
    private JFXTextField txfCamadaEntrada;
    @FXML
    private JFXTextField txfCamadaSaida;
    @FXML
    private JFXTextField txfCamadaOculta;
    @FXML
    private JFXTextField txfValorErro;
    @FXML
    private JFXTextField txfNumeroMaximo;
    @FXML
    private JFXTextField txfTaxaAprend;
    @FXML
    private JFXCheckBox ckbLinear;
    @FXML
    private JFXCheckBox ckbLogis;
    @FXML
    private JFXCheckBox ckbHiper;
    @FXML
    private JFXButton btnFecharArquivo;
    @FXML
    private JFXButton btnArquivo;
    @FXML
    private JFXCheckBox ckbisTeste;
    @FXML
    private JFXButton btnExecutar;
    @FXML
    private JFXCheckBox ckbisMSLINT;
    File projeto = null;
    
    private ControladoraArquivo control;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        control = new ControladoraArquivo();
        valoresBases();
    }    

    private void valoresBases(){
            
        txfValorErro.setText(""+0.001);
        txfCamadaEntrada.setText(""+0);
        txfCamadaSaida.setText(""+0);
        txfCamadaOculta.setText(""+0);
        txfTaxaAprend.setText(""+0.1);
        txfNumeroMaximo.setText(""+2000);   
    }
    
    @FXML
    private void fechar_arquivo(ActionEvent event){
        
        valoresBases();
        projeto = null;
        
        control.resetaDados();
    }

    @FXML
    private void abrir_arquivo(ActionEvent event) {
         
        if(!ckbisTeste.isSelected()){
            
            projeto = new File("D:\\Downloads\\base_treinamento.csv");
        }
        else{
             projeto = new File("D:\\Downloads\\base_teste.csv");
        }
   
        control.AbrirArquivo(projeto.getPath(), projeto.getName());
        txfCamadaEntrada.setText("" + control.getArq().getInputLayer());
        txfCamadaOculta.setText("" + control.getArq().getHiddenLayer());
        txfCamadaSaida.setText("" + control.getArq().getOutputLayer());
       /* 
        JFileChooser file = new JFileChooser();
        file.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV FILES", "csv", ".csv");
        file.setFileFilter(filter);
        boolean correto = false;
    
        while (!correto) {

            if (file.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

                projeto = file.getSelectedFile();
                if (!projeto.getAbsolutePath().endsWith("csv")) {
                    JOptionPane.showMessageDialog(null, "Opção inválida, selecione novamente um arquivo");
                } else {
                    correto = true;
                      control.AbrirArquivo(projeto.getPath(), projeto.getName());
                    txfCamadaEntrada.setText("" + control.getArq().getInputLayer());
                    txfCamadaOculta.setText("" + control.getArq().getHiddenLayer());
                    txfCamadaSaida.setText("" + control.getArq().getOutputLayer());

                }
            } else {
                break;
            }
        }*/
       
    }

    private boolean validaDouble(String valor, String campo){
        
        double valorInt = 0;
        boolean erro = false;
        Alert al = null;
        
        try{
            valorInt = Double.parseDouble(valor);
        }catch(NumberFormatException num){
            
            erro = true;
            al = new Alert(Alert.AlertType.ERROR, "Digite um valor válido para o campo: "+campo,ButtonType.CLOSE);

        }
        
        if (!erro) {

            if (valorInt <= 0) {
                al = new Alert(Alert.AlertType.ERROR, "Digite um valor maior que 0 para o campo: "+campo, ButtonType.CLOSE);
            }
        }

        
        if(al != null)
            al.showAndWait();
        
        return erro;
        
    }
    
    private boolean validaInt(String valor, String campo){
        
        int valorInt = 0;
        boolean erro = false;
        Alert al = null;
        
        try{
            valorInt = Integer.parseInt(valor);
        }catch(NumberFormatException num){
            
            erro = true;
            al = new Alert(Alert.AlertType.ERROR, "Digite um valor válido para o campo: "+campo,ButtonType.CLOSE);

        }
        
        if (!erro) {

            if (valorInt <= 0) {
                al = new Alert(Alert.AlertType.ERROR, "Digite um valor maior que 0 para o campo: "+campo, ButtonType.CLOSE);
            }
        }

        
        if(al != null)
            al.showAndWait();
        
        return erro;
        
    }
    
    @FXML
    private void aplicar_algoritmo(ActionEvent event) {
        
        int camadaOculta;     
        double valorErro; 
        int numeroMax;
        double taxaAprend; 
        int funcao;        
      
        
        if(!validaInt(txfCamadaOculta.getText(), "Camada Oculta")){
            
            if(!validaDouble(txfValorErro.getText(), "Campo Erro")){
                
                if(!validaInt(txfNumeroMaximo.getText(), "Número máximo de interações")){
                    
                    if(!validaDouble(txfTaxaAprend.getText(), "Taxa de aprendizagem")){
                        
                    camadaOculta = Integer.parseInt(txfCamadaOculta.getText());                       
                        numeroMax = Integer.parseInt(txfNumeroMaximo.getText());
                        funcao = ckbLinear.isSelected() ? 1 : ckbLogis.isSelected() ? 2 : 3;
                        
                        valorErro = Double.parseDouble(txfValorErro.getText());
                        taxaAprend = Double.parseDouble(txfTaxaAprend.getText());
                        boolean isMSLINT = ckbisMSLINT.isSelected();
                        
                        control.chamarAlgoritmo(camadaOculta, valorErro, numeroMax,taxaAprend, funcao, ckbisTeste.isSelected(),
                                isMSLINT);
                        
                        if(!ckbisTeste.isSelected())
                        {                              
                            exibeErro();
                        }
                        else
                        {
                            List<String> lista=control.getArq().getClasses();
                            /*
                            
                            TableView tv = new TableView();
                            TableColumn tc;
                            tc = new TableColumn("Classes");
                            tv.getColumns().add(tc); 
                            
                            for (int i = 0; i < lista.size(); i++)
                            {
                                tc = new TableColumn(lista.get(i));
                                tc.setCellValueFactory(new PropertyValueFactory<>(lista.get(i)));
                                tv.getColumns().add(tc);                              
                            }
                                
                            //ObservableList<List<String>> listaObs;
                            List <String> modelo = new ArrayList();
                            List <List<String>> listaObs = new ArrayList();
                            for (int i = 1; i < control.getMatrizConfusao().length; i++)
                            {
                                modelo.clear();
                                for (int j = 0; j < control.getMatrizConfusao()[i].length; j++)
                                {
                                   modelo.add(control.getMatrizConfusao()[i][j]+"");
                                }
                                listaObs.add(modelo);
                            }
                            
                            tv.setItems(FXCollections.observableArrayList(listaObs));
                            
                            ScrollPane root = new ScrollPane(tv);
                            root.setMinSize(1300,800);
                            tv.setMinSize(root.getMinWidth(),root.getMinHeight()-20);
                            Stage stage = new Stage();
                            Scene scene  = new Scene(root,1215,768);
                            stage.setScene(scene);
                            stage.show();*/
                            System.out.println("\n");
                            System.out.print("       ");
                            for (int i = 0; i < lista.size(); i++)
                            {
                                System.out.print(lista.get(i)+"    ");                           
                            }
                            System.out.println("");
                            for(int i = 0; i < control.getMatrizConfusao().length; i++)
                            {
                                System.out.print(lista.get(i)+"     ");
                                for (int j = 0; j < control.getMatrizConfusao()[i].length; j++)
                                {
                                    if((control.getMatrizConfusao()[i][j]+"").length()==1)
                                       System.out.print(control.getMatrizConfusao()[i][j]+"     "); 
                                    else if((control.getMatrizConfusao()[i][j]+"").length()==2)
                                       System.out.print(control.getMatrizConfusao()[i][j]+"    "); 
                                    else if((control.getMatrizConfusao()[i][j]+"").length()==3)
                                       System.out.print(control.getMatrizConfusao()[i][j]+"   ");
                                    else if((control.getMatrizConfusao()[i][j]+"").length()==4)
                                       System.out.print(control.getMatrizConfusao()[i][j]+"  ");
                                    else if((control.getMatrizConfusao()[i][j]+"").length()==5)
                                       System.out.print(control.getMatrizConfusao()[i][j]+" ");
                                }
                                System.out.println("");
                            }
                        }
                    
                    }
                }
            }
        }
    }
    
    private void exibeErro()
    {
        
        List<Double> erros=control.getListaErros();
        double media;
        double tamanho=erros.get(erros.size()-1);
        erros.remove(erros.get(erros.size()-1));
        int tamEpoca=(int)((erros.size()-1)/tamanho);  
        int contEpoca=(int)tamEpoca;
        int i;
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Épocas");
        yAxis.setLabel("Erro");
        LineChart grafico = new LineChart(xAxis, yAxis);
        XYChart.Series series = new XYChart.Series();
        for (i = 0; i < tamanho-1; i++)
        {
            media=0;
            for (int j=(int)tamEpoca; j < (tamEpoca+contEpoca); j++)
            {
                media+=erros.get(j);
            }
            media=media/contEpoca;
            tamEpoca+=contEpoca;
            series.getData().add(new XYChart.Data((int)i,media));
        }      
        grafico.getData().addAll(series);
        
        ScrollPane root = new ScrollPane(grafico);
        root.setMinSize(1300,800);
        grafico.setMinSize(root.getMinWidth(),root.getMinHeight()-20);
        Stage stage = new Stage();
        Scene scene  = new Scene(root,1215,768);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void evtCkb(ActionEvent event) {

        ckbHiper.setSelected(false);
        ckbLogis.setSelected(false);
    }

    @FXML
    private void evtCkb1(ActionEvent event) {

        ckbHiper.setSelected(false);
        ckbLinear.setSelected(false);
    }

    @FXML
    private void evtCkb2(ActionEvent event) {

        ckbLinear.setSelected(false);
        ckbLogis.setSelected(false);
    }
    
}

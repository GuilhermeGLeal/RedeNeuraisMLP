package redeneuraismlp;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
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
        ckbLinear.setSelected(true);
    }
    
    @FXML
    private void fechar_arquivo(ActionEvent event){
        
        valoresBases();
        projeto = null;
    }

    @FXML
    private void abrir_arquivo(ActionEvent event) {
        
        JFileChooser file = new JFileChooser();
        file.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV FILES", "csv", ".csv");
        file.setFileFilter(filter);
        boolean correto = false;
                 
        /*
        while (!correto) {

            if (file.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            
                projeto = file.getSelectedFile();
                if (!projeto.getAbsolutePath().endsWith("csv")) {
                    JOptionPane.showMessageDialog(null, "Opção inválida, selecione novamente um arquivo");
                } else {
                    correto = true;
                    System.out.println(projeto.getPath());
                    System.out.println(projeto.getAbsoluteFile());
                    control.AbrirArquivo(projeto.getPath());
                txfCamadaEntrada.setText(""+control.getArq().getInputLayer());
               txfCamadaOculta.setText(""+control.getArq().getHiddenLayer());
               txfCamadaSaida.setText(""+control.getArq().getOutputLayer());

               for (int i = 0; i < control.getArq().getLinhas().get(i).getAtributos().size(); i++) {

                   TableColumn<LinhaCSV, String> col = new TableColumn<>("Column "+(i+1));
                   col.setMinWidth(80);
                   final int colIndex = i ;

                   /*
                   col.setCellValueFactory(data -> {
                       List<String> rowValues = data.getValue();
                       String cellValue ;
                       if (colIndex < rowValues.size()) {
                           cellValue = rowValues.get(colIndex);
                       } else {
                            cellValue = "" ;
                       }
                       return new ReadOnlyStringWrapper(cellValue);
                   });

                   tableCsv.getColumns().add(col);
               }   

               ObservableList<LinhaCSV> modelo = FXCollections.observableArrayList(control.getArq().getLinhas());
               tableCsv.setItems(modelo);

                    
                }
            } else {
                break;
            }
        }*/
        projeto = new File("F:\\Users\\Desktop\\Pasta Auxiliar\\eozuckersbergson\\base_treinamento.csv");
        control.AbrirArquivo(projeto.getPath());
        txfCamadaEntrada.setText("" + control.getArq().getInputLayer());
        txfCamadaOculta.setText("" + control.getArq().getHiddenLayer());
        txfCamadaSaida.setText("" + control.getArq().getOutputLayer());
       
             
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
                        
                        exibeErro();
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

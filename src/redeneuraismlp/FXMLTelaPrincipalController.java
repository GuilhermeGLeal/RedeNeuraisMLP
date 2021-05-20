package redeneuraismlp;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import redeneuraismlp.controladora.ControladoraArquivo;
import redeneuraismlp.entidades.LinhaCSV;

public class FXMLTelaPrincipalController implements Initializable {
    
    @FXML
    private TableView<LinhaCSV> tableCsv;
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
            
        txfValorErro.setText(""+0);
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
                }
            } else {
                break;
            }
        }*/
        projeto = new File("D:\\Downloads\\base_treinamento.csv");
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
            */
            tableCsv.getColumns().add(col);
        }   
        
        ObservableList<LinhaCSV> modelo = FXCollections.observableArrayList(control.getArq().getLinhas());
        tableCsv.setItems(modelo);
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
        
        if(!validaInt(txfCamadaOculta.getText(), "Camada Oculta")){
            
            if(!validaDouble(txfValorErro.getText(), "Campo Erro")){
                
                if(!validaInt(txfNumeroMaximo.getText(), "Número máximo de interações")){
                    
                    if(!validaDouble(txfTaxaAprend.getText(), "Taxa de aprendizagem")){
                        
                    }
                }
            }
        }
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

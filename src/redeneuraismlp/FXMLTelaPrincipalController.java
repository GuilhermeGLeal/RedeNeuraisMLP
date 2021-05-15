package redeneuraismlp;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public class FXMLTelaPrincipalController implements Initializable {
    
    @FXML
    private TableView<?> tableCsv;
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void fechar_arquivo(ActionEvent event) {
    }

    @FXML
    private void abrir_arquivo(ActionEvent event) {
    }

    @FXML
    private void aplicar_algoritmo(ActionEvent event) {
    }
    
}

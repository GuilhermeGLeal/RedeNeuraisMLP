package redeneuraismlp;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import redeneuraismlp.controladora.ControladoraArquivo;

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
    
    private ControladoraArquivo control;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        control = new ControladoraArquivo();
    }    

    @FXML
    private void fechar_arquivo(ActionEvent event){
        
        
    }

    @FXML
    private void abrir_arquivo(ActionEvent event) {
        
        JFileChooser file = new JFileChooser();
        file.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV FILES", "csv", ".csv");
        file.setFileFilter(filter);
        boolean correto = false;
        File projeto = null;
          
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
        }
    }

    @FXML
    private void aplicar_algoritmo(ActionEvent event) {
    }
    
}

package redeneuraismlp;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import redeneuraismlp.controladora.ControladoraArquivo;

/**
 * FXML Controller class
 *
 * @author joao
 */
public class HomeController implements Initializable {

    @FXML
    private JFXButton btnArquivo;
    @FXML
    private JFXButton btnFecharArquivo;
    @FXML
    private JFXCheckBox ckbisTeste;
    @FXML
    private JFXCheckBox ckbisMSLINT;
    @FXML
    private JFXButton btnExecutar;
    @FXML
    private TextField txfCamadaEntrada;
    @FXML
    private TextField txfCamadaSaida;
    @FXML
    private TextField txfCamadaOculta;
    @FXML
    private TextField txfValorErro;
    @FXML
    private TextField txfNumeroMaximo;
    @FXML
    private TextField txfTaxaAprend;
    @FXML
    private JFXCheckBox ckbLinear;
    @FXML
    private JFXCheckBox ckbLogis;
    @FXML
    private JFXCheckBox ckbHiper;
    @FXML
    private TableView<?> tableCsv;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        control = new ControladoraArquivo();
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
//                    control.AbrirArquivo(projeto.getPath());
                }
            } else {
                break;
            }
        }
    }
    
}

package redeneuraismlp;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
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

    private ControladoraArquivo control;
    File projeto = null;
    
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
    private VBox vboxTabela;

    private void valoresBases() {
        txfValorErro.setText("" + 0.001);
        txfCamadaEntrada.setText("" + 0);
        txfCamadaSaida.setText("" + 0);
        txfCamadaOculta.setText("" + 0);
        txfTaxaAprend.setText("" + 0.1);
        txfNumeroMaximo.setText("" + 2000);
        ckbLinear.setSelected(true);
    }

    private boolean validaDouble(String valor, String campo) {
        double valorInt = 0;
        boolean erro = false;
        Alert al = null;
        try {
            valorInt = Double.parseDouble(valor);
        } catch (NumberFormatException num) {
            erro = true;
            al = new Alert(Alert.AlertType.ERROR, "Digite um valor válido para o campo: " + campo, ButtonType.CLOSE);
        }
        if (!erro) {
            if (valorInt <= 0) {
                al = new Alert(Alert.AlertType.ERROR, "Digite um valor maior que 0 para o campo: " + campo, ButtonType.CLOSE);
            }
        }
        if (al != null) {
            al.showAndWait();
        }
        return erro;
    }

    private boolean validaInt(String valor, String campo) {
        int valorInt = 0;
        boolean erro = false;
        Alert al = null;
        try {
            valorInt = Integer.parseInt(valor);
        } catch (NumberFormatException num) {
            erro = true;
            al = new Alert(Alert.AlertType.ERROR, "Digite um valor válido para o campo: " + campo, ButtonType.CLOSE);
        }
        if (!erro) {
            if (valorInt <= 0) {
                al = new Alert(Alert.AlertType.ERROR, "Digite um valor maior que 0 para o campo: " + campo, ButtonType.CLOSE);
            }
        }
        if (al != null) {
            al.showAndWait();
        }
        return erro;
    }

    private void exibeErro() {
        List<Double> erros = control.getListaErros();
        double media;
        double tamanho = erros.get(erros.size() - 1);
        erros.remove(erros.get(erros.size() - 1));
        int tamEpoca = (int) ((erros.size() - 1) / tamanho);
        int contEpoca = (int) tamEpoca;
        int i;
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Épocas");
        yAxis.setLabel("Erro");
        LineChart grafico = new LineChart(xAxis, yAxis);
        XYChart.Series series = new XYChart.Series();
        for (i = 0; i < tamanho - 1; i++) {
            media = 0;
            for (int j = (int) tamEpoca; j < (tamEpoca + contEpoca); j++) {
                media += erros.get(j);
            }
            media = media / contEpoca;
            tamEpoca += contEpoca;
            series.getData().add(new XYChart.Data((int) i, media));
        }
        grafico.getData().addAll(series);

        ScrollPane root = new ScrollPane(grafico);
        root.setMinSize(1300, 800);
        grafico.setMinSize(root.getMinWidth(), root.getMinHeight() - 20);
        Stage stage = new Stage();
        Scene scene = new Scene(root, 1215, 768);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        control = new ControladoraArquivo();
        valoresBases();
    }

    @FXML
    private void abrir_arquivo(ActionEvent event) {
        
         /*
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
        */ 
       
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
        }

       
    }

    @FXML
    private void btExecutar(ActionEvent event) {
        int camadaOculta;
        double valorErro;
        int numeroMax;
        double taxaAprend;
        int funcao;

        if (!validaInt(txfCamadaOculta.getText(), "Camada Oculta")) {

            if (!validaDouble(txfValorErro.getText(), "Campo Erro")) {

                if (!validaInt(txfNumeroMaximo.getText(), "Número máximo de interações")) {

                    if (!validaDouble(txfTaxaAprend.getText(), "Taxa de aprendizagem")) {

                        camadaOculta = Integer.parseInt(txfCamadaOculta.getText());
                        numeroMax = Integer.parseInt(txfNumeroMaximo.getText());
                        funcao = ckbLinear.isSelected() ? 1 : ckbLogis.isSelected() ? 2 : 3;

                        valorErro = Double.parseDouble(txfValorErro.getText());
                        taxaAprend = Double.parseDouble(txfTaxaAprend.getText());
                        boolean isMSLINT = ckbisMSLINT.isSelected();

                        control.chamarAlgoritmo(camadaOculta, valorErro, numeroMax, taxaAprend, funcao, ckbisTeste.isSelected(),
                                isMSLINT);

                        if (!ckbisTeste.isSelected()) {
                            exibeErro();
                        } else {
                            List<String> lista = control.getArq().getClasses();
                            System.out.println("\n");
                            System.out.print("       ");
                            
                            vboxTabela.getChildren().clear();
                            HBox hb1 = new HBox();
                            hb1.setSpacing(5);
                            
                            TextField tx3 = new TextField();
                            tx3.setPrefWidth(45);
                            tx3.setStyle("-fx-text-fill: white;");
                            tx3.setStyle("-fx-background-color: #62676a;");
                            tx3.setId("Classe");
                            tx3.setAlignment(Pos.CENTER);
                            tx3.setDisable(true);
                            tx3.setOpacity(1);
                            tx3.setText("-");
                            hb1.getChildren().add(tx3);
                            
                            for (int i = 0; i < lista.size(); i++) {
                                System.out.print(lista.get(i) + "    ");
                                TextField tx1 = new TextField();
                                tx1.setPrefWidth(45);
                                tx1.setStyle("-fx-text-fill: white;");
                                tx1.setStyle("-fx-background-color: #62676a;");
                                tx1.setId("Classe");
                                tx1.setAlignment(Pos.CENTER);
                                tx1.setDisable(true);
                                tx1.setOpacity(1);
                                tx1.setText("" + lista.get(i));
                                hb1.getChildren().add(tx1);
                            }
                            vboxTabela.getChildren().add(hb1);
                            System.out.println("");
                            for (int i = 0; i < control.getMatrizConfusao().length; i++) {
                                System.out.print(lista.get(i) + "     ");
                                hb1 = new HBox();
                                hb1.setSpacing(5);
                                TextField tx1 = new TextField();
                                tx1.setPrefWidth(45);
                                tx1.setStyle("-fx-text-fill: white;");
                                tx1.setStyle("-fx-background-color: #62676a;");
                                tx1.setId("Classe");
                                tx1.setAlignment(Pos.CENTER);
                                tx1.setDisable(true);
                                tx1.setOpacity(1);
                                tx1.setText("" + lista.get(i));
                                hb1.getChildren().add(tx1);
                                for (int j = 0; j < control.getMatrizConfusao()[i].length; j++) {
                                    TextField tx2 = new TextField();
                                    tx2.setPrefWidth(45);
                                    tx2.setStyle("-fx-text-fill: white;");
                                    tx2.setStyle("-fx-background-color: #62676a;");
                                    tx2.setId("D"+i+""+j);
                                    tx2.setAlignment(Pos.CENTER);
                                    tx2.setDisable(true);
                                    tx2.setOpacity(1);
                                    tx2.setText(""+control.getMatrizConfusao()[i][j]);
                                    
                                    if ((control.getMatrizConfusao()[i][j] + "").length() == 1) {
                                        System.out.print(control.getMatrizConfusao()[i][j] + "     ");
                                    } else if ((control.getMatrizConfusao()[i][j] + "").length() == 2) {
                                        System.out.print(control.getMatrizConfusao()[i][j] + "    ");
                                    } else if ((control.getMatrizConfusao()[i][j] + "").length() == 3) {
                                        System.out.print(control.getMatrizConfusao()[i][j] + "   ");
                                    } else if ((control.getMatrizConfusao()[i][j] + "").length() == 4) {
                                        System.out.print(control.getMatrizConfusao()[i][j] + "  ");
                                    } else if ((control.getMatrizConfusao()[i][j] + "").length() == 5) {
                                        System.out.print(control.getMatrizConfusao()[i][j] + " ");
                                    }
                                    
                                    hb1.getChildren().add(tx2);
                                }
                                System.out.println("");
                                vboxTabela.getChildren().add(hb1);
                            }
                            List<Double> acu = control.getRedeNeural().getListAcuracia();
                            double acutot = control.getRedeNeural().getAcuraciaTotal();
                            ComboBox cbacu = new ComboBox();
                            ComboBox cberro = new ComboBox();
                            HBox hboxacu = new HBox();
                            hboxacu.setSpacing(5);
                            for (int i = 0; i < acu.size(); i++) {
                                String stracu = "Classe: " + control.getArq().getClasses().get(i) + " Acurácia: " + acu.get(i);
                                String strerro = "Classe: " + control.getArq().getClasses().get(i) + " Erro: " + (100 - acu.get(i));
                                cberro.getItems().add(strerro);
                                cbacu.getItems().add(stracu);
                            }
                            hboxacu.getChildren().add(cbacu);
                            hboxacu.getChildren().add(cberro);
                            vboxTabela.getChildren().add(hboxacu);
                            
                            // Erro total e acurácia total
                            TextField txtAcutot = new TextField("Acurácia total: " + control.getRedeNeural().getAcuraciaTotal());
                            txtAcutot.setDisable(true);
                            txtAcutot.setOpacity(1);
                            TextField txtErrotot = new TextField("Erro total: " + (100 - control.getRedeNeural().getAcuraciaTotal()));
                            txtErrotot.setDisable(true);
                            txtErrotot.setOpacity(1);
                            vboxTabela.getChildren().add(txtAcutot);
                            vboxTabela.getChildren().add(txtErrotot);
                        }
                    }
                }
            }
        }
    }

    @FXML
    private void fechar_arquivo(ActionEvent event) {
        valoresBases();
        projeto = null;
        control.resetaDados();
    }

    @FXML
    private void evtLinear(ActionEvent event) {
        ckbHiper.setSelected(false);
        ckbLogis.setSelected(false);
    }

    @FXML
    private void evtLogistica(ActionEvent event) {
        ckbHiper.setSelected(false);
        ckbLinear.setSelected(false);
    }

    @FXML
    private void evtHiperbolica(ActionEvent event) {
        ckbLinear.setSelected(false);
        ckbLogis.setSelected(false);
    }
}

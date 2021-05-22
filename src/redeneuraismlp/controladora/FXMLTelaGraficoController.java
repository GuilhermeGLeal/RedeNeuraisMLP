package redeneuraismlp.controladora;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.TableColumn;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FXMLTelaGraficoController implements Initializable 
{

    @FXML
    private LineChart<?,?> grafico;
    @FXML
    private NumberAxis eixoY;
    @FXML
    private CategoryAxis eixoX;
    @FXML
    private TableColumn<?, ?> tbC1;
    @FXML
    private AnchorPane pane;

    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        
    }
}

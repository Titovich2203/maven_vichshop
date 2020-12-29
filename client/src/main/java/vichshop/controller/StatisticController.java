package vichshop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import vichshop.model.Produit;
import vichshop.utils.Fabrique;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class StatisticController implements Initializable {

    @FXML
    private BarChart<String, Integer> barChart;

    @FXML
    private CategoryAxis xAxis;
    @FXML
    private BarChart<String, Integer> barChart1;

    @FXML
    private CategoryAxis xAxis1;

    private ObservableList<String> months = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*String[] months = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();
        // Convert it to a list and add it to our ObservableList of months.
        monthNames.addAll(Arrays.asList(months));

        // Assign the month names as categories for the horizontal axis.
        xAxis.setCategories(monthNames); */

        try {
            List<Produit> produitList = Fabrique.getiProduit().getAllProduit();

            int[] monthCounter = new int[12];

            for (int i = 1, j = 0; i < 25; i += 2, j++) {
                months.add(i + "");
                monthCounter[j] = 1;
            }
            xAxis.setCategories(months);

            ObservableList<String> libelles = FXCollections.observableArrayList();

            for(Produit p : produitList)
            {
                libelles.add(p.getLibelle());
            }
            xAxis1.setCategories(libelles);


            for (Produit p : produitList) {
                for (int i = 0; i < monthCounter.length; i++) {
                    if (Integer.parseInt(months.get(i)) == p.getQteStock()) {
                        monthCounter[i] += 5;
                    }
                }
            }

            XYChart.Series<String, Integer> series = new XYChart.Series<>();

            // Create a XYChart.Data object for each month. Add it to the series.
            for (int i = 0; i < monthCounter.length; i++) {
                series.getData().add(new XYChart.Data<>(months.get(i), monthCounter[i]));
            }

            barChart.getData().add(series);

            XYChart.Series<String, Integer> series1 = new XYChart.Series<>();

            // Create a XYChart.Data object for each month. Add it to the series.
            for (int i = 0; i < produitList.size(); i++) {
                series1.getData().add(new XYChart.Data<>(produitList.get(i).getLibelle(), produitList.get(i).getQteStock()));
            }

            barChart1.getData().add(series1);
        } catch (Exception e) {

        }
/*
        int[] monthCounter = new int[12];
        for (Person p : persons) {
            int month = p.getBirthday().getMonthValue() - 1;
            monthCounter[month]++;
        }

        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        // Create a XYChart.Data object for each month. Add it to the series.
        for (int i = 0; i < monthCounter.length; i++) {
            series.getData().add(new XYChart.Data<>(monthNames.get(i), monthCounter[i]));
        }

        barChart.getData().add(series);


 */
    }
}

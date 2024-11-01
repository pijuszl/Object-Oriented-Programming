package software.gui;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.ChoiceBox;
import software.calculator.LoanData;
import software.calculator.MonthData;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class TabScene extends ProgramScene {
    private Tab calcTab;
    private Tab graphTab;

    private AnchorPane anchor1;
    private AnchorPane anchor2;

    private GridPane grid1;
    private GridPane grid2;

    private TableView<MonthData> tableView;
    private LineChart<Number, Number> lineChart;

    LoanData loanData;

    public TabScene(TabPane tabPane) {
        super(tabPane);

        loanData = new LoanData();

        ColumnConstraints col1 = new ColumnConstraints(10, 64, 362);
        ColumnConstraints col2 = new ColumnConstraints(10, 195, 362);
        ColumnConstraints col3 = new ColumnConstraints(10, 100, 721);
        ColumnConstraints col4 = new ColumnConstraints(10, 193, 670);
        ColumnConstraints col5 = new ColumnConstraints(10, 273, 670);

        RowConstraints row1 = new RowConstraints();
        row1.setMinHeight(10);
        row1.setPrefHeight(30);
        RowConstraints row2 = new RowConstraints();
        row2.setMinHeight(10);
        row2.setPrefHeight(30);
        RowConstraints row3 = new RowConstraints();
        row3.setMinHeight(10);
        row3.setPrefHeight(30);
        RowConstraints row4 = new RowConstraints();
        row4.setMinHeight(10);
        row4.setPrefHeight(30);
        RowConstraints row5 = new RowConstraints();
        row5.setMinHeight(10);
        row5.setPrefHeight(30);
        RowConstraints row6 = new RowConstraints();
        row6.setMinHeight(10);
        row6.setPrefHeight(30);
        RowConstraints row7 = new RowConstraints();
        row7.setMinHeight(10);
        row7.setPrefHeight(30);
        RowConstraints row8 = new RowConstraints();
        row8.setMinHeight(10);
        row8.setPrefHeight(30);
        RowConstraints row9 = new RowConstraints();
        row9.setMinHeight(10);
        row9.setPrefHeight(30);

        grid1 = new GridPane();
        grid1.setHgap(20);
        grid1.setVgap(15);
        grid1.prefHeight(800);
        grid1.prefWidth(1200);
        grid1.getColumnConstraints().addAll(col1, col2, col3, col4, col5);
        grid1.getRowConstraints().addAll(row1, row2, row3, row4, row5, row6, row7, row8, row9);

        TextField fieldLoanAmount = new TextField("1000");
        grid1.add(fieldLoanAmount, 2, 0, 1, 1);

        Label textLoanAmount = new Label("Loan amount (Eur)");
        textLoanAmount.setFont(Font.font("System", FontWeight.BOLD,15));
        grid1.add(textLoanAmount, 1, 0, 1, 1);

        TextField fieldInterestRate = new TextField("5");
        grid1.add(fieldInterestRate, 2, 1, 1, 1);

        Label textInterestRate = new Label("Yearly interest rate (%)");
        textInterestRate.setFont(Font.font("System", FontWeight.BOLD,15));
        grid1.add(textInterestRate, 1, 1, 1, 1);

        TextField fieldTermYears = new TextField("2");
        grid1.add(fieldTermYears, 2, 2, 1, 1);

        Label textTermYears = new Label("Loan term (years)");
        textTermYears.setFont(Font.font("System", FontWeight.BOLD,15));
        grid1.add(textTermYears, 1, 2, 1, 1);

        TextField fieldTermMonths = new TextField("6");
        grid1.add(fieldTermMonths, 2, 3, 1, 1);

        Label textTermMonths = new Label("Loan term (months)");
        textTermMonths.setFont(Font.font("System", FontWeight.BOLD,15));
        grid1.add(textTermMonths, 1, 3, 1, 1);

        TextField fieldDefermentStart = new TextField("1");
        grid1.add(fieldDefermentStart, 2, 4, 1, 1);

        Label textDefermentStart = new Label("Deferment start month");
        textDefermentStart.setFont(Font.font("System", FontWeight.BOLD,15));
        grid1.add(textDefermentStart, 1, 4, 1, 1);

        TextField fieldDefermentTerm = new TextField("3");
        grid1.add(fieldDefermentTerm, 2, 5, 1, 1);

        Label textDefermentTerm = new Label("Deferment term (months)");
        textDefermentTerm.setFont(Font.font("System", FontWeight.BOLD,15));
        grid1.add(textDefermentTerm, 1, 5, 1, 1);

        TextField fieldDefermentInterestRate = new TextField("1");
        grid1.add(fieldDefermentInterestRate, 2, 6, 1, 1);

        Label textDefermentInterestRate = new Label("Deferment interest rate (%)");
        textDefermentInterestRate.setFont(Font.font("System", FontWeight.BOLD,15));
        grid1.add(textDefermentInterestRate, 1, 6, 1, 1);

        ChoiceBox<String> boxCalculationMethod = new ChoiceBox<>();
        boxCalculationMethod.getItems().addAll( "Annuity", "Line");
        grid1.add(boxCalculationMethod, 2, 7, 1, 1);

        Label textCalculationMethod = new Label("Calculation method");
        textCalculationMethod.setFont(Font.font("System", FontWeight.BOLD,15));
        grid1.add(textCalculationMethod, 1, 7, 1, 1);

        ColumnConstraints col6 = new ColumnConstraints(10, 27, 593);
        ColumnConstraints col7 = new ColumnConstraints(10, 520, 624);
        ColumnConstraints col8 = new ColumnConstraints(10, 577, 733);
        ColumnConstraints col9 = new ColumnConstraints(0, 40, 361);

        RowConstraints row10 = new RowConstraints(0, 31, 229);
        RowConstraints row11 = new RowConstraints(10, 423, 586);
        RowConstraints row12 = new RowConstraints(10, 35, 284);
        RowConstraints row13 = new RowConstraints(0, 35, 284);
        RowConstraints row14 = new RowConstraints(4, 30, 284);
        RowConstraints row15 = new RowConstraints(0, 29, 284);
        RowConstraints row16 = new RowConstraints(0, 33, 284);
        RowConstraints row17 = new RowConstraints(0, 28, 284);

        grid2 = new GridPane();
        grid2.setHgap(20);
        grid2.setVgap(15);
        grid2.prefHeight(800);
        grid2.prefWidth(1200);
        grid2.getColumnConstraints().addAll(col6, col7, col8, col9);
        grid2.getRowConstraints().addAll(row10, row11, row12, row13, row14, row15, row16, row17);

        TextField textFilename = new TextField();
        textFilename.setPromptText("Enter fIle name");
        textFilename.setFont(Font.font("System", FontWeight.LIGHT,13));
        grid2.add(textFilename, 1, 2, 1, 1);

        Button buttonSave = new Button("Save to file");
        buttonSave.setFont(Font.font("System", FontWeight.BOLD,15));

        TextField fieldFilterStart = new TextField("0");
        grid2.add(fieldFilterStart, 2, 3, 1, 1);

        Label textFilterStart = new Label("Filter from (month):");
        textFilterStart.setFont(Font.font("System",15));
        grid2.add(textFilterStart, 2, 2, 1, 1);

        TextField fieldFilterEnd = new TextField("0");
        grid2.add(fieldFilterEnd, 2, 5, 1, 1);

        Label textFilterEnd = new Label("Filter to (month):");
        textFilterEnd.setFont(Font.font("System",15));
        grid2.add(textFilterEnd, 2, 4, 1, 1);

        buttonSave.setOnAction(event -> {
            printToFile(textFilename.getText(), fieldFilterStart.getText(), fieldFilterEnd.getText());
        });
        grid2.add(buttonSave, 1, 3, 1, 1);

        Button buttonUpdate = new Button("Update");
        buttonUpdate.setFont(Font.font("System", FontWeight.BOLD,15));
        buttonUpdate.setOnAction(event -> {
            grid2.getChildren().remove(tableView);
            grid2.getChildren().remove(lineChart);
            updateTable(fieldFilterStart.getText(), fieldFilterEnd.getText());
            updateLineChart(fieldFilterStart.getText(), fieldFilterEnd.getText());
        });
        grid2.add(buttonUpdate, 2, 6, 1, 1);

        Button buttonCalculate = new Button("Calculate");
        buttonCalculate.setFont(Font.font("System", FontWeight.BOLD,15));
        buttonCalculate.setOnAction(event -> {
            loanData.changeLoanAmount(fieldLoanAmount.getText());
            loanData.changeInterestRate(fieldInterestRate.getText());
            loanData.changeLoanTerm(fieldTermYears.getText(), fieldTermMonths.getText());
            loanData.changeDefermentInterestRate(fieldDefermentInterestRate.getText());
            loanData.changeDefermentStart(fieldDefermentStart.getText());
            loanData.changeDefermentTerm(fieldDefermentTerm.getText());
            loanData.changeCalcMethod(boxCalculationMethod.getValue());
            loanData.calculateData();

            grid2.getChildren().remove(tableView);
            grid2.getChildren().remove(lineChart);
            updateTable(fieldFilterStart.getText(), fieldFilterEnd.getText());
            updateLineChart(fieldFilterStart.getText(), fieldFilterEnd.getText());
        });
        grid1.add(buttonCalculate, 3, 7, 1, 1);

        loanData.changeLoanAmount(fieldLoanAmount.getText());
        loanData.changeInterestRate(fieldInterestRate.getText());
        loanData.changeLoanTerm(fieldTermYears.getText(), fieldTermMonths.getText());
        loanData.changeDefermentInterestRate(fieldDefermentInterestRate.getText());
        loanData.changeDefermentStart(fieldDefermentStart.getText());
        loanData.changeDefermentTerm(fieldDefermentTerm.getText());
        loanData.changeCalcMethod(boxCalculationMethod.getValue());
        loanData.calculateData();
        updateTable("", "");
        updateLineChart("", "");

        anchor1 = new AnchorPane(grid1);
        anchor1.prefHeight(800);
        anchor1.prefWidth(1200);
        anchor1.setTopAnchor(grid1, 50.0);
        anchor1.setBottomAnchor(grid1, 50.0);
        anchor1.setLeftAnchor(grid1, 50.0);
        anchor1.setRightAnchor(grid1, 50.0);

        anchor2 = new AnchorPane(grid2);
        anchor2.prefHeight(800);
        anchor2.prefWidth(1200);
        anchor2.setTopAnchor(grid1, 50.0);
        anchor2.setBottomAnchor(grid1, 50.0);
        anchor2.setLeftAnchor(grid1, 50.0);
        anchor2.setRightAnchor(grid1, 50.0);

        calcTab = new Tab("Calculator", anchor1);
        graphTab = new Tab("Graphs", anchor2);
        tabPane.getTabs().add(calcTab);
        tabPane.getTabs().add(graphTab);

    }

    private void updateTable(String filterFromText, String filterToText) {
        int filterFrom = Integer.parseInt("0" + filterFromText);
        int filterTo = Integer.parseInt("0" + filterToText);

        double lowerBound = 0;
        double upperBound = loanData.getLoanTerm() + loanData.getDefermentTerm() + 1;

        if (filterFrom != 0) {
            lowerBound = filterFrom - 1;
        }

        if (filterTo != 0) {
            upperBound = filterTo + 1;
        }

        tableView = new TableView<>();

        TableColumn<MonthData, int[]> columnMonth = new TableColumn<>("Month");
        columnMonth.setPrefWidth(62);
        TableColumn<MonthData, double[]> columnPayment = new TableColumn<>("Payment");
        columnPayment.setPrefWidth(107);
        TableColumn<MonthData, double[]> columnPrincipal = new TableColumn<>("Principal");
        columnPrincipal.setPrefWidth(122);
        TableColumn<MonthData, double[]> columnInterest = new TableColumn<>("Interest");
        columnInterest.setPrefWidth(99);
        TableColumn<MonthData, double[]> columnBalance = new TableColumn<>("Remaining Balance");
        columnBalance.setPrefWidth(133);

        columnMonth.setCellValueFactory(new PropertyValueFactory<>("month"));
        columnPayment.setCellValueFactory(new PropertyValueFactory<>("payment"));
        columnPrincipal.setCellValueFactory(new PropertyValueFactory<>("principal"));
        columnInterest.setCellValueFactory(new PropertyValueFactory<>("interest"));
        columnBalance.setCellValueFactory(new PropertyValueFactory<>("loanAmount"));

        tableView.getColumns().add(columnMonth);
        tableView.getColumns().add(columnPayment);
        tableView.getColumns().add(columnPrincipal);
        tableView.getColumns().add(columnInterest);
        tableView.getColumns().add(columnBalance);

        for(MonthData monthData : loanData.dataArray) {
            if(monthData.getMonth() >= lowerBound && monthData.getMonth() <= upperBound) {
                tableView.getItems().add(monthData);
            }
        }

        grid2.add(tableView, 1, 1, 1, 1);
    }

    private void updateLineChart(String filterFromText, String filterToText) {
        int filterFrom = Integer.parseInt("0" + filterFromText);
        int filterTo = Integer.parseInt("0" + filterToText);

        double lowerBound = 0;
        double upperBound = loanData.getLoanTerm() + loanData.getDefermentTerm() + 1;

        if (filterFrom != 0) {
            lowerBound = filterFrom - 1;
        }

        if (filterTo != 0) {
            upperBound = filterTo + 1;
        }

        NumberAxis xAxis = new NumberAxis(lowerBound, upperBound, loanData.getLoanTerm() / 5);
        NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("Months");
        xAxis.setTickLabelFont(Font.font("System",12));
        yAxis.setLabel("Payment, Eur)");
        yAxis.setTickLabelFont(Font.font("System",12));

        lineChart = new LineChart<Number, Number>(xAxis, yAxis);
        lineChart.setTitle("Loan line chart");
        XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
        series.setName("Payment, Eur");

        for(MonthData monthData : loanData.dataArray) {
            series.getData().add(new XYChart.Data<Number, Number>(monthData.getMonth(), monthData.getPayment()));
        }

        lineChart.getData().add(series);

        grid2.add(lineChart, 2, 1, 1 , 1);
    }

    public void printToFile(String fileName, String filterFromText, String filterToText) {
        int filterFrom = Integer.parseInt("0" + filterFromText);
        int filterTo = Integer.parseInt("0" + filterToText);

        double lowerBound = 0;
        double upperBound = loanData.getLoanTerm() + loanData.getDefermentTerm() + 1;

        if (filterFrom != 0) {
            lowerBound = filterFrom - 1;
        }

        if (filterTo != 0) {
            upperBound = filterTo + 1;
        }

        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        PrintWriter printWriter = new PrintWriter(fileWriter);

        printWriter.print("Loan Calculator \n\n");
        printWriter.print("Loan Amount: " + loanData.getLoanAmount() + "\n");
        printWriter.print("Loan Interest Rate: " + loanData.getLoanInterestRate() + "\n");
        printWriter.print("Loan Term (months): " + loanData.getLoanTerm() + "\n");
        printWriter.print("Deferment Interest Rate: " + loanData.getDefermentInterestRate() + "\n");
        printWriter.print("Deferment Start Month: " + loanData.getDefermentStart() + "\n");
        printWriter.print("Deferment Term (months): " + loanData.getDefermentTerm() +"\n\n");
        printWriter.print("Payment table (" + loanData.getCalcMethod() + ")\n");

        printWriter.print(" ________________________________________________________________________________\n");
        printWriter.print("| Month |    Payment    |    Principal    |    Interest    |  Remaining Balance  |\n");

        for(MonthData monthData : loanData.dataArray) {
            if(monthData.getMonth() >= lowerBound && monthData.getMonth() <= upperBound) {
                printWriter.print(monthData.toString());
            }
        }

        printWriter.print("|________________________________________________________________________________|\n");
        printWriter.close();
    }
}


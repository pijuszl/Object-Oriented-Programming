package software.gui;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Function;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import software.export.*;
import software.students.DataSet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import software.students.DateEntry;
import software.students.Student;

public class MenuController implements Initializable {
    @FXML
    private Spinner<Integer> groupSpinner, yearSpinner;

    @FXML
    private ChoiceBox<String> studyChoiceBox;

    @FXML
    private TextField nameTextField, surnameTextField;

    @FXML
    private TextField fileNameTextField1, fileNameTextField2;

    @FXML
    private ChoiceBox<String> fileTypeChoiceBox1, fileTypeChoiceBox2;

    @FXML
    private DatePicker newDate, filter1Date, filter2Date;

    @FXML
    private TableView<Student> table;

    @FXML
    private TableColumn<Student, String> nameCol, surnameCol, programmeCol;

    @FXML
    private TableColumn<Student, Integer> idCol, yearCol, groupCol;

    @FXML
    private RadioButton rbStudent, rbGroup;

    @FXML
    private ChoiceBox<LocalDate> dateChoiceBox;

    @FXML
    private ChoiceBox<Boolean> valueChoiceBox;

    @FXML
    private TextField idTextField1, idTextField2, groupIdTextField;

    @FXML
    private ChoiceBox<String> fieldChoiceBox;

    @FXML
    private TextField newValueTextField;

    @FXML
    public void submitInput(ActionEvent event) throws IOException {
        String name = nameTextField.getText();
        nameTextField.clear();
        String surname = surnameTextField.getText();
        surnameTextField.clear();
        String studies = studyChoiceBox.getValue();
        studyChoiceBox.setValue("");
        int group = groupSpinner.getValue();
        groupSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5, 1));
        int year = yearSpinner.getValue();
        yearSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 4, 1));

        Student s = new Student(name, surname, studies, year, group);
        DataSet.getInstance().addStudent(s);
        showAlert("Success","A student named "+ name + " " +  surname + " was added.");
        switchScenes(event);
        updateTable();
        table.refresh();
    }

    @FXML
    public void submitInputFile(ActionEvent event) throws IOException {
        String fileType = fileTypeChoiceBox1.getValue();
        String fileName = fileNameTextField1.getText();
        FileImporter imp = null;

        switch(fileType) {
            case ".csv":
            {
                imp = new ImporterCSV(fileName + fileType);
                break;
            }
            case ".xlsx":
            {
                imp = new ImporterXLSX(fileName + fileType);
                break;
            }
        }

        imp.fillData();

        fileTypeChoiceBox1.setValue(".csv");
        fileNameTextField1.clear();
        switchScenes(event);
        updateTable();
        table.refresh();
    }

    @FXML
    public void addDate(ActionEvent event) throws IOException {
        LocalDate date = newDate.getValue();
        if(date == null)
            return;

        for(Student student : DataSet.getInstance().getList()) {
            student.setAttendanceValues(date, false);
        }
        table.refresh();
        ObservableList<Student> dataList = FXCollections.observableArrayList(DataSet.getInstance().getList());
        table.setItems(dataList);
        showAlert("Information","The date has been successfully added.");
        dateChoiceBox.getItems().addAll(date);
        switchScenes(event);
        updateTable();
        table.refresh();
    }

    @FXML
    public void submitChange(ActionEvent event) throws IOException {
        try {
            if(rbStudent.isSelected()) {
                int id = Integer.parseInt("0"+idTextField1.getText());
                LocalDate date = dateChoiceBox.getValue();
                Boolean newVal = valueChoiceBox.getValue();
                DataSet.getInstance().findInstanceByID(id).findDate(date).setAttendance(newVal);
            }
            if(rbGroup.isSelected()) {
                int id = Integer.parseInt("0"+groupIdTextField.getText());
                LocalDate date = dateChoiceBox.getValue();
                Boolean newVal = valueChoiceBox.getValue();
                for(Student st : DataSet.getInstance().getList()) {
                    if(st.getGroup() == id) {
                        st.findDate(date).setAttendance(newVal);
                    }
                }
            }
            showAlert("Information","The value has been changed.");
        } catch(Exception e) {
            showAlert("Information","There was an error. Make sure you have entered all the necessary data and try again.");
        }

        table.refresh();
    }

    @FXML
    public void addFilter(ActionEvent event) throws IOException {
        LocalDate date1 = filter1Date.getValue();
        LocalDate date2 = filter2Date.getValue();

        showAlert("Information","The filter has been applied.");
        DataSet.getInstance().createFilter(date1, date2);
        switchScenes(event);
        updateTable();
        table.refresh();

    }

    @FXML
    public void removeFilter(ActionEvent event) throws IOException {
        DataSet.getInstance().removeFilter();
        showAlert("Information","The filter has been removed.");
        switchScenes(event);
        updateTable();
        table.refresh();
    }

    @FXML
    public void submitChangeStudent(ActionEvent event) throws IOException {
        int idToChange = Integer.parseInt("0" + idTextField2.getText());
        String fieldToChange = fieldChoiceBox.getValue();
        Object newValue;

        if(DataSet.getInstance().findInstanceByID(idToChange) != null) {
            switch(fieldToChange) {
                case "Name":
                {
                    newValue = newValueTextField.getText();
                    DataSet.getInstance().findInstanceByID(idToChange).setName((String)newValue);
                    break;
                }
                case "Surname":
                {
                    newValue = newValueTextField.getText();
                    DataSet.getInstance().findInstanceByID(idToChange).setSurname((String)newValue);
                    break;
                }
                case "Programme":
                {
                    newValue = newValueTextField.getText();
                    DataSet.getInstance().findInstanceByID(idToChange).setStudyProgramme((String)newValue);
                    break;
                }
                case "Year":
                {
                    newValue = Integer.parseInt("0" + newValueTextField.getText());
                    DataSet.getInstance().findInstanceByID(idToChange).setYear((Integer)newValue);
                    break;
                }
                case "Group":
                {
                    newValue = Integer.parseInt("0" + newValueTextField.getText());
                    DataSet.getInstance().findInstanceByID(idToChange).setGroup((Integer)newValue);
                    break;
                }
            }
            showAlert("Success","The selected value on student (ID: " + idToChange + ") was successfully edited.");
        }
        else {
            showAlert("Information","There is no such student (ID: " + idToChange + ").");
        }
        newValueTextField.clear();
        idTextField2.clear();
        fieldChoiceBox.setValue("");
        table.refresh();
    }

    public void updateTable() {
        ObservableList<Student> dataList = FXCollections.observableArrayList(DataSet.getInstance().getList());
        table.setItems(dataList);
    }

    @FXML
    public void deleteStudentEntry(ActionEvent event) throws IOException {
        int idToChange = Integer.parseInt("0" + idTextField2.getText());
        if(DataSet.getInstance().findInstanceByID(idToChange) != null) {
            DataSet.getInstance().removeStudent(idToChange);
            updateTable();
            showAlert("Success","The student (ID: " + idToChange + ") was successfully deleted from the list.");
        }
        else {
            showAlert("Information","There is no such student (ID: " + idToChange + ").");
        }
        idTextField2.clear();
        table.refresh();
    }

    @FXML
    public void submitExport(ActionEvent event) throws IOException {
        String fileType = fileTypeChoiceBox2.getValue();
        String fileName = fileNameTextField2.getText();
        if(fileName == "")
            fileName = "output";
        Boolean append = false;

        FileExporter exp = null;
        switch(fileType) {
            case ".csv":
            {
                exp = new ExporterCSV(fileName + fileType, append);
                break;
            }
            case ".xlsx":
            {
                exp = new ExporterXLSX(fileName + fileType);
                break;
            }
            case ".pdf":
            {
                if(DataSet.getInstance().isDataFiltered()) {
                    exp = new ExporterPDF(fileName + fileType, DataSet.getInstance().getFilter1(), DataSet.getInstance().getFilter2());
                } else {
                    exp = new ExporterPDF(fileName + fileType);
                }
                break;
            }
        }
        exp.export(DataSet.getInstance().getList());
        fileTypeChoiceBox2.setValue(".csv");
        fileNameTextField2.clear();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        SpinnerValueFactory<Integer> groupSpinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5, 1);
        groupSpinner.setValueFactory(groupSpinnerValueFactory);
        SpinnerValueFactory<Integer> yearSpinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 4, 1);
        yearSpinner.setValueFactory(yearSpinnerValueFactory);
        studyChoiceBox.getItems().addAll("Software Engineering", "Mathematics", "Chemistry", "Philosophy", "Informatics");

        fileTypeChoiceBox1.getItems().addAll(".csv", ".xlsx");
        fileTypeChoiceBox1.setValue(".csv");

        ToggleGroup selection = new ToggleGroup();

        rbStudent.setToggleGroup(selection);
        rbGroup.setToggleGroup(selection);

        ArrayList<TableColumn<Student, Boolean>> dateColumns = new ArrayList<TableColumn<Student, Boolean>>();
        if(DataSet.getInstance().getList().size() != 0) {
            for(DateEntry dt : DataSet.getInstance().getList().get(0).getAttendanceValues()) {
                if(DataSet.getInstance().isDataFiltered()) {
                    if(dt.isFiltered(DataSet.getInstance().getFilter1(), DataSet.getInstance().getFilter2()))
                        continue;
                }
                TableColumn<Student, Boolean> newEntry = new TableColumn<Student, Boolean>(dt.getDate().toString());
                dateColumns.add(newEntry);
                table.getColumns().add(newEntry);
                newEntry.setCellValueFactory(createArrayValueFactory(Student::getAttendanceValues, dt.getDate()));
                dateChoiceBox.getItems().add(dt.getDate());
            }
        }

        idCol.setCellValueFactory(new PropertyValueFactory<Student, Integer>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
        surnameCol.setCellValueFactory(new PropertyValueFactory<Student, String>("surname"));
        programmeCol.setCellValueFactory(new PropertyValueFactory<Student, String>("studyProgramme"));
        yearCol.setCellValueFactory(new PropertyValueFactory<Student, Integer>("year"));
        groupCol.setCellValueFactory(new PropertyValueFactory<Student, Integer>("group"));

        valueChoiceBox.getItems().addAll(true, false);

        ObservableList<Student> dataList = FXCollections.observableArrayList(DataSet.getInstance().getList());
        table.setItems(dataList);

        fieldChoiceBox.getItems().addAll("Name", "Surname", "Programme", "Year", "Group");

        fileTypeChoiceBox2.getItems().addAll(".csv", ".xlsx", ".pdf");
        fileTypeChoiceBox2.setValue(".csv");
    }

    static <S, T> Callback<TableColumn.CellDataFeatures<S, T>, ObservableValue<Boolean>> createArrayValueFactory(Function<Student, ArrayList<DateEntry>> arrayExtractor, LocalDate date) {
        return cd -> {
            ArrayList<DateEntry> array = arrayExtractor.apply((Student) cd.getValue());
            for(DateEntry de : array) {
                if(de.getDate() == date) {
                    return new SimpleObjectProperty<>(de.getAttendance());
                }
            }
            return null;
        };
    }

    protected void switchScenes(ActionEvent event) throws IOException {
        String resourceName = "software/gui/Menu.fxml";
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(resourceName));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void showAlert(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }
}

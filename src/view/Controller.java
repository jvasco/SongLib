package view;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import songlib.Song;

public class Controller {

	@FXML
	private Button add;

	@FXML
	private Button edit;

	@FXML
	private Button delete;

	@FXML
	private TextField name;

	@FXML
	private TextField artist;

	@FXML
	private TextField album;

	@FXML
	private TextField year;

	@FXML
	private Label nameLabel;

	@FXML
	private Label artistLabel;

	@FXML
	private Label albumLabel;

	@FXML
	private Label yearLabel;

	@FXML
	private Button save;

	@FXML
	private Button cancel;

	@FXML
	private TableView<Song> songTable;

	private ObservableList<Song> obsList = FXCollections.observableArrayList();
	@FXML
	TableColumn<Song, String> songName = new TableColumn<>("songName");
	@FXML
	TableColumn<Song, String> songArtist = new TableColumn<>("songArtist");

	static ArrayList<Song> list = new ArrayList<Song>();

	Song selectedSong;

	boolean editMode = false;

	Song editSong;

	public void start() throws IOException {

		// beginning of nicks stuff

		/*
		 * public static void main(String[]args) throws IOException{ init();
		 * save(); }
		 */

		// end of nicks stuff

		// obsList = FXCollections.observableArrayList(items)
		// songTable.setIte
		songTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Song>() {

			@Override
			public void changed(ObservableValue<? extends Song> observable, Song oldValue, Song newValue) {
				// TODO Auto-generated method stub
				setFieldsDisable(true);
				setSelected(newValue);
			}
		});
		setFieldsDisable(true);
		save.setDisable(true);
		cancel.setDisable(true);
		songName.setCellValueFactory(new PropertyValueFactory<>("name"));
		songArtist.setCellValueFactory(new PropertyValueFactory<>("artist"));
		// songTable.getColumns().addAll(song, artistColumn);
		BufferedReader in = new BufferedReader(new FileReader("src/view/songs.txt"));
		System.out.println("Test");
		String line;
		// System.out.println("Line: " + in.readLine());
		while ((line = in.readLine()) != null) {
			String[] splits = line.split(",", -1);
			obsList.add(new Song(splits[0], splits[1], splits[2], splits[3]));
		}
		in.close();
		// not comparable
		FXCollections.sort(obsList);
		songTable.setItems(obsList);
		if (!(obsList.isEmpty())) {
			songTable.getSelectionModel().select(obsList.get(0));
		}
		System.out.println("DEOESIN THISRN");

	}

	protected void setSelected(Song song) {
		// TODO Auto-generated method stub
		if (song == null) {
			return;
		}
		selectedSong = song;
		name.setText(selectedSong.getName());
		artist.setText(selectedSong.getArtist());
		album.setText(selectedSong.getAlbum());
		year.setText(selectedSong.getYear());
		setFieldsDisable(true);
	}

	public void addSong(ActionEvent e) {
		songTable.setDisable(true);
		save.setDisable(false);
		cancel.setDisable(false);
		clearFields();
		System.out.println("YAWK");
		// make text fields active
		setFieldsDisable(false);
		add.setDisable(true);
		edit.setDisable(true);
		delete.setDisable(true);
	}

	private void clearFields() {
		// TODO Auto-generated method stub
		name.clear();
		artist.clear();
		album.clear();
		year.clear();
	}

	public void editSong(ActionEvent e) {
		//
		if(obsList.isEmpty())
		{
			selectedSong = null;
			return;
		}
		editMode = true;
		Song temp = selectedSong;
		editSong = temp;
		//obsList.remove(selectedSong);
		setSelected(temp);
		//songTable.setItems(obsList);
		
		songTable.setDisable(true);
		setFieldsDisable(false);
		save.setDisable(false);
		cancel.setDisable(false);
		add.setDisable(true);
		edit.setDisable(true);
		delete.setDisable(true);

		

	}

	public void deleteSong(ActionEvent e) throws IOException {
		int index = obsList.indexOf(selectedSong);
		obsList.remove(selectedSong);

		if (obsList.size() == 0) {
			clearFields();
		} else if (index - 1 == (obsList.size() - 1)) {
			setSelected(obsList.get(index - 1));
			songTable.getSelectionModel().select(obsList.get(index - 1));
		} else {
			setSelected(obsList.get(index));
			songTable.getSelectionModel().select(obsList.get(index));
		}
		songTable.setItems(obsList);
		save();
	}

	public void saveSong(ActionEvent e) throws IOException {
		if(editMode)
		{
			editMode=false;
			obsList.remove(editSong);
		}
		String songName = name.getText();
		String songArtist = artist.getText();
		String songAlbum = album.getText();
		String songYear = year.getText();
		Song song = new Song(songName, songArtist, songAlbum, songYear);
		if (songName.isEmpty() || songArtist.isEmpty()) {
			// System.out.println("You need to have a song name and artist");
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("You need to have a song name and artist");
			alert.showAndWait();
			return;
		}
		if (obsList.contains(song)) {
			// System.out.println("List already contains that song");
			// JOptionPane.showMessageDialog(frame, "Eggs are not supposed to be
			// green.");
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("List already contains that song");
			alert.showAndWait();
			return;
		}
		obsList.add(song);
		FXCollections.sort(obsList);
		// songTable.getItems().addAll(obsList);
		System.out.println("KJ " + songTable.toString());
		songTable.setItems(obsList);
		save();
		setFieldsDisable(true);
		songTable.setDisable(false);
		save.setDisable(true);
		cancel.setDisable(true);
		add.setDisable(false);
		edit.setDisable(false);
		delete.setDisable(false);
		songTable.getSelectionModel().select(song);
	}

	private void setFieldsDisable(boolean b) {
		// TODO Auto-generated method stub
		name.setEditable(!b);
		artist.setEditable(!b);
		album.setEditable(!b);
		year.setEditable(!b);

	}

	public void cancel(ActionEvent e) throws IOException {

		songTable.setDisable(false);
		setFieldsDisable(true);
		if (!(selectedSong == null))
			setSelected(selectedSong);
		// name.setEditable(false);
		// clearFields();
		save.setDisable(true);
		cancel.setDisable(true);
		add.setDisable(false);
		edit.setDisable(false);
		delete.setDisable(false);

		if (editMode) {
			//obsList.add(editSong);
			FXCollections.sort(obsList);
			songTable.setItems(obsList);
			save();
			editMode = false;
			songTable.getSelectionModel().select(obsList.get(obsList.indexOf(editSong)));
		}

	}

	public void save() throws IOException {
		System.out.println("Test");

		BufferedWriter fw = new BufferedWriter(new FileWriter("src/view/songs.txt"));
		String album, year;
		for (Song s : obsList) {
			album = s.getAlbum();
			year = s.getYear();
			if (album == null) {
				album = "";
			}
			if (year == null) {
				year = "";
			}
			fw.write(s.getName() + "," + s.getArtist() + "," + album + "," + year + "\n");
		}
		fw.close();
	}

}

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package sample;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javazoom.jl.player.Player;



public class Controller {
    public ListView listView;
    public Button playButton;
    public Text label;
    public boolean paused;
    public boolean played = false;
    public Thread tt;
    public int index;
    private List<String> SongList = new ArrayList<>();
    private List<String> SongLocation = new ArrayList<>();


    public Controller() {
    }

    public void Play() {
        index = SongList.indexOf(listView.getSelectionModel().getSelectedItem());
        label.setText(listView.getItems().get(index).toString());
        label.setVisible(true);
        PlaySong();


    }

    public void PlayButtonDoubleClicked(MouseEvent click) {
        if (click.getClickCount() == 2) {
            if (!played) {
                playButton.setText("Pause");
                tt = new Thread(this::Play);
                tt.start();

            } else {
                tt.stop();
                tt = new Thread(this::Play);
                tt.start();
            }
            label.setText(listView.getItems().get(index).toString());
            label.setVisible(true);
        }
    }


    public void PlayButtonClicked() {
        System.out.println("Play music");

        if (!played) {
            if (!paused) {
                tt = new Thread(this::Play);
                tt.start();
                playButton.setText("Pause");
            } else {
                tt.resume();
                paused = false;
                played = true;
                playButton.setText("Pause");

            }
        } else {
            if (playButton.getText().equals("Pause")) {
                tt.suspend();
                paused = true;
                played = false;
                playButton.setText("Play");
            } else {
                tt.stop();
                tt = new Thread(this::Play);
                tt.start();
            }
        }


    }

    public void RemoveButtonClicked() {
        System.out.println("Remove music");
        index = SongList.indexOf(listView.getSelectionModel().getSelectedItem());
        SongList.remove(index);
        SongLocation.remove(index);
        listView.getItems().remove(index);
        try {
            Files.write(Paths.get("text"), this.SongList);
            Files.write(Paths.get("location"), this.SongLocation);
        } catch (Exception e) {

        }
    }


    public void PlaySong() {
        String songlocation = SongLocation.get(index);
        try {
            FileInputStream file = new FileInputStream(songlocation); //initialize the FileInputStream
            Player player = new Player(file); //initialize the player
            played = true;
            player.play(); //start the player
            while(true){
                if(player.isComplete()){
                    System.out.println("complete");
                    index++;
                    label.setText(listView.getItems().get(index).toString());
                    label.setVisible(true);
                    if (index == SongList.size()){
                        index = 0;
                        return;
                    }
                    PlaySong();
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void NextButtonClicked() {
        System.out.println("Next Music");
        if (index == SongList.size() - 1) {
            index = 0;
            tt.stop();
            tt = new Thread(this::PlaySong);
            tt.start();
        } else {
            index++;
            tt.stop();
            tt = new Thread(this::PlaySong);
            tt.start();
        }
        label.setText(listView.getItems().get(index).toString());
        label.setVisible(true);
    }

    public void PrevButtonClicked() {
        System.out.println("Previous music");
        if(index == 0){
            index = SongList.size()-1;
            tt.stop();
            tt = new Thread(this::PlaySong);
            tt.start();
        }
        else {
            index--;
            tt.stop();
            tt = new Thread(this::PlaySong);
            tt.start();
        }
        label.setText(listView.getItems().get(index).toString());
        label.setVisible(true);
    }

    public void initialize() throws IOException {
        File file = new File("text");
        if (file.exists()) {
            SongList = Files.readAllLines(Paths.get("text"));
            listView.getItems().addAll(SongList);
            SongLocation = Files.readAllLines(Paths.get("location"));
        }
    }


    public void AddButtonClicked() throws IOException {
        System.out.println("Add music");
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(null);
        if (selectedFile != null) {
            this.listView.getItems().add(selectedFile.getName());
            this.SongList.add(selectedFile.getName());
            this.SongLocation.add(selectedFile.getPath());
        } else {
            System.out.println("FILE IS NOT VALID");
        }
        Files.write(Paths.get("text"), this.SongList);
        Files.write(Paths.get("location"), this.SongLocation);

    }

    public void RepeatButtonClicked(){
        System.out.println("Repeat");
        if (!played) {
                tt = new Thread(this::Play);
                tt.start();
        } else {
                tt.stop();
                tt = new Thread(this::Play);
                tt.start();
            }
        label.setText(listView.getItems().get(index).toString());
        label.setVisible(true);
        }

}

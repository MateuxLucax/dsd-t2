package domain.controller;

import domain.model.World;
import presentation.adapter.Util;
import presentation.view.SimulatorView;
import data.datasource.Database;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javax.swing.JFrame;

public class StartController {
    private File file;
    
    public String handleSelectFile(JFrame view) {
        this.file = Util.loadFileSelector(view).getSelectedFile();
        if (file != null && file.exists()) {
            return file.getAbsolutePath();
        }
        return "";
    }
    
    public void handleConfirm() {
        if (file != null && file.exists()) {
            try {
                String content = Files.readString(file.toPath());
                World world = World.from(content);
                System.out.println(world.toString() + '\n');
                
                Database db = Database.getInstance();
                db.setWorld(world);
                
                Util.init(new SimulatorView());
            } catch (IOException ex) {
                Util.message("Erro na leitura: " + ex.getMessage());
            }
        } else {
            Util.message("Nenhum arquivo selecionado");
        }
    }
}

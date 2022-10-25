package domain.controller;

import data.datasource.Database;
import domain.model.World;
import presentation.adapter.Util;
import presentation.enums.SelectedLockable;
import presentation.view.SimulatorView;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class StartController {
    private File file;
    private SelectedLockable lockable;
    
    public String handleSelectFile(JFrame view) {
        this.file = Util.loadFileSelector(view).getSelectedFile();
        if (file != null && file.exists()) {
            return file.getAbsolutePath();
        }
        return "";
    }

    public void handleSelectedLockable(SelectedLockable lockable) {
        this.lockable = lockable;
    }

    public void handleConfirm() {
        if (lockable == null) {
            Util.message("Nenhum gerenciador de threads selecionado!");
        } else if (file != null && file.exists()) {
            try {
                Database db = Database.getInstance();
                db.setLockable(lockable.toClass());

                String content = Files.readString(file.toPath());
                World world = World.from(content);
                System.out.println(world.toString() + '\n');

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

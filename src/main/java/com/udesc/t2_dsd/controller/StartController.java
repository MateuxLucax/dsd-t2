package com.udesc.t2_dsd.controller;

import com.udesc.t2_dsd.model.World;
import com.udesc.t2_dsd.util.Util;
import com.udesc.t2_dsd.view.SimulatorView;
import com.udesc.t2_dsd.view.StartView;
import com.udesc.t2_dsd.infra.Database;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class StartController {
    private StartView view;
    private File file;

    public StartController(StartView view) {
        this.view = view;
    }
    
    public void handleSelectFile() {
        this.file = Util.loadFileSelector(view).getSelectedFile();
        if (file != null && file.exists()) {
            view.getjTfile().setText(file.getAbsolutePath());
        }
    }
    
    public void handleConfirm() {
        if (file != null && file.exists()) {
            try {
                String content = Files.readString(file.toPath());
                World world = World.from(content).from(content);
                world.verifyEntryPoints();
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

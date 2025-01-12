package org.example.graphic.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import org.example.graphic.BaseHomeController;
import org.example.graphic.model.UserAccount;
import org.example.graphic.model.interfaces.GeneralOperations;

import java.util.ArrayList;

public class BackController implements GeneralOperations
{
    private static BackController backController;
    public static BackController getBackController(ArrayList<Node> allPanes) {
        if (backController == null)
            backController = new BackController(allPanes);
        return backController;
    }
    private ArrayList<Node> allPanes = new ArrayList<>();

    public BackController(ArrayList<Node> allPanes) {
        this.allPanes = allPanes;
    }
    @Override
    public void backTo()
    {
        if (allPanes.size() != 1) {
            for (Node node : allPanes) {
                node.setVisible(false);
            }
            allPanes.get(allPanes.size() - 2).setVisible(true);
            allPanes.removeLast();
        }
    }
}

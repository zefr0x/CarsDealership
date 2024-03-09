package carsdealership;

import javax.swing.*;
import java.awt.*;

public class App {
    public static void main(String[] args) {
        com.formdev.flatlaf.intellijthemes.FlatCarbonIJTheme.setup();

        MainWindow mainWindow = new MainWindow();

        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setMinimumSize(new Dimension(1000, 700));
        mainWindow.setVisible(true);
    }
}

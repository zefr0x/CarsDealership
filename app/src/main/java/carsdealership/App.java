package carsdealership;

import javax.swing.*;
import java.awt.*;

public class App {
    public static void main(String[] args) {
        com.formdev.flatlaf.intellijthemes.FlatCarbonIJTheme.setup();

        MainWindow mainWindow = new MainWindow();

        mainWindow.setVisible(true);
    }
}

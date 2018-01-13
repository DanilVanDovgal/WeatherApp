package openWeatherMap.gui;

import javax.swing.*;
import java.awt.*;

class MyDialogSave extends JDialog {

    MyDialogSave(JFrame owner) {
        super(owner, "Save", true);

        add(new JLabel("<html><hr>Insert usb storage to save or \n " +
                "click Save as to save on local disc<hr></html>"), BorderLayout.CENTER);
        JButton ok = new JButton("Ok");
        ok.addActionListener(event -> setVisible(false));

        JPanel panel = new JPanel();
        panel.add(ok);
        add(panel, BorderLayout.SOUTH);
        pack();
    }
}

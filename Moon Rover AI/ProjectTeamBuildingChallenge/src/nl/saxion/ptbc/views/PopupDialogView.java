package nl.saxion.ptbc.views;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Shows a popup with the given text.
 */
public class PopupDialogView {

    private JDialog popupDialog;
    private JPanel popupPanel;
    private JButton okButton;
    private JLabel messageLabel;


    /**
     * @param message the message that will be shown
     * @param title   the window title
     */
    public PopupDialogView(String message, String title) {
        popupDialog = new JDialog();

        initComponents(message);

        popupDialog.setTitle(title);
        popupDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        popupDialog.setLocationRelativeTo(null);
        popupDialog.setModal(true);
        popupDialog.pack();
        popupDialog.setVisible(true);
    }

    public PopupDialogView(String message) {
        this(message, "Info");
    }

    private void initComponents(String message) {
        popupDialog.setContentPane(popupPanel);

        popupDialog.getRootPane().setDefaultButton(okButton);
        messageLabel.setText(message);

        okButton.addActionListener(new OkButtonListener());
    }


    public JDialog getPopupDialog() {
        return popupDialog;
    }

    public JButton getOkButton() {
        return okButton;
    }


    class OkButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            popupDialog.dispose();
        }
    }

}

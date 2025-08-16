package nl.saxion.ptbc.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Shows an error popup with the given text.
 */
public class ErrorDialogView extends PopupDialogView {

    private boolean fixable;


    /**
     * @param error   the error that will be shown
     * @param fixable will close the entire program if the issue is unfixable
     */
    public ErrorDialogView(String error, boolean fixable) {
        super(error, "Error");
        this.fixable = fixable;

        getOkButton().addActionListener(new OkButtonListener());
    }


    class OkButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (fixable) {
                getPopupDialog().dispose();
            } else {
                System.exit(-1);
            }
        }
    }

}

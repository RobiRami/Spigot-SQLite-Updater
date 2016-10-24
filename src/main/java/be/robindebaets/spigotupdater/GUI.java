package be.robindebaets.spigotupdater;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import be.robindebaets.spigotupdater.buttons.SelectButton;

public class GUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JLabel currentFile;
	private File jarFile;
	private Font roboto;
	private JLabel statusLabel;
	public GUI() throws IOException, FontFormatException {
		roboto = getFont("Roboto.ttf");
		setTitle("Spigot SQLiteUpdater");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(400, 400));
        setBackground(Color.DARK_GRAY);
        setDefaultLookAndFeelDecorated(true);
        setLocationRelativeTo(null);
        JLabel header = new JLabel("Spigot SQLite Updater");
        header.setFont(roboto.deriveFont(24F));
        currentFile = new JLabel("No file selected");
        currentFile.setFont(roboto.deriveFont(12F));
        JButton selectFileButton = new SelectButton("Select File", this);
        statusLabel = new JLabel("");
       	statusLabel.setFont(roboto.deriveFont(12F));
        JButton updateButton = new UpdateButton("Update", this);
        JPanel panel = new JPanel();
        GroupLayout layout = new GroupLayout(panel);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setVerticalGroup(
            layout.createSequentialGroup()
            .addComponent(header)
            .addGroup(layout.createParallelGroup(Alignment.BASELINE)
            		.addComponent(currentFile)
            		.addComponent(selectFileButton))
            .addComponent(statusLabel)
            .addComponent(updateButton)
         );
        layout.setHorizontalGroup(
            layout.createParallelGroup(Alignment.LEADING)
            .addComponent(header)
    		.addGroup(layout.createSequentialGroup().addComponent(currentFile).addComponent(selectFileButton))
    		.addComponent(statusLabel)
    		.addComponent(updateButton)
        );
        panel.setLayout(layout);
        add(panel);
        pack();
        setVisible(true);
        Toolkit.getDefaultToolkit().beep();

	}
	public File getJar() {
		return jarFile;
	}
	public void setStatus(final String status) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				statusLabel.setText(status);
			}
		});
	}
	public void setFile(File file) {
		jarFile = file;
		currentFile.setText(jarFile.getName());
	}
	private Font getFont(String fontName) throws IOException, FontFormatException {
	    InputStream is = GUI.class.getClassLoader().getResourceAsStream(fontName);
	    Font font = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.PLAIN);
		is.close();
		return font;
	}
	public Font getRoboto() {
		return roboto;
	}
	public void displayError(final String error) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Toolkit.getDefaultToolkit().beep();
			    JOptionPane optionPane = new JOptionPane(error, JOptionPane.WARNING_MESSAGE);
				JDialog dialog = optionPane.createDialog("Alert!");
			    dialog.setAlwaysOnTop(true);
			    dialog.setVisible(true);
			}
		});
	}
}
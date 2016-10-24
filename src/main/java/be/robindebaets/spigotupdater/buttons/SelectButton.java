package be.robindebaets.spigotupdater.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import be.robindebaets.spigotupdater.GUI;

public class SelectButton extends JButton {

	private static final long serialVersionUID = -595429893516805422L;
	public SelectButton(String title, final GUI gui) {
		super(title);
        setFont(gui.getRoboto().deriveFont(12F));
        addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		JFileChooser fileChooser = new JFileChooser();
        		fileChooser.setFileFilter(new FileNameExtensionFilter(".jar", "jar"));
        		int returnValue = fileChooser.showOpenDialog(null);
        		if(returnValue == JFileChooser.APPROVE_OPTION) {
        			gui.setFile(fileChooser.getSelectedFile());
    			}
        	}
        });
	}
}

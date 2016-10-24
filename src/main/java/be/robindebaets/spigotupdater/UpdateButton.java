package be.robindebaets.spigotupdater;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;

public class UpdateButton extends JButton {

	private static final long serialVersionUID = -5779289148356696085L;
	public UpdateButton(String title, final GUI gui) {
		super(title);
        setFont(gui.getRoboto().deriveFont(12F));
        addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(gui.getJar() == null) {
        			gui.displayError("Please select your Spigot .jar file first.");
        			return;
        		}
    			new Thread() {
    				@Override
    				public void run() {
    					try {
    					SpigotUpdater.INSTANCE.getUpdater().update(gui.getJar());
	    				} catch (IOException e1) {
	    					e1.printStackTrace();
	    				}
    				}
    			}.start();
        	}
        });
	}
}

package be.robindebaets.spigotupdater;

public enum SpigotUpdater {
	INSTANCE;
	private static GUI gui;
	public static void main(String args[]) {
		try {
			gui = new GUI();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
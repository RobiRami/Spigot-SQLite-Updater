package be.robindebaets.spigotupdater;

public enum SpigotUpdater {
	INSTANCE;
	private static GUI gui;
	private static Updater updater;
	public static void main(String args[]) {
		try {
			gui = new GUI();
			updater = new Updater();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public Updater getUpdater() {
		return updater;
	}
	public GUI getGui() {
		return gui;
	}
}
package be.robindebaets.spigotupdater;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Updater {
	public void update(File input) throws IOException {
		long start = System.currentTimeMillis();
		SpigotUpdater.INSTANCE.getGui().updateStatus("Starting update");
		Document snapshotPage = Jsoup.connect("https://oss.sonatype.org/content/repositories/snapshots/org/xerial/sqlite-jdbc/").get();
		String snapshot = snapshotPage.select("a").get(1).attr("href");
		Document artifacts = Jsoup.connect(snapshot).get();
		String latestJar = null;
		for(Element e: artifacts.select("a")) {
			if(e.text().startsWith("sqlite-jdbc") && e.text().endsWith(".jar")) {
				latestJar = e.attr("href");
				break;
			}
		}
		File outputNew = new File(input.getPath().replace(".jar", "-UPDATED.jar"));
		if(!outputNew.exists()) outputNew.createNewFile();
		FileOutputStream outNewStream = new FileOutputStream(outputNew);
	    ZipOutputStream newZip = new ZipOutputStream(outNewStream);
		SpigotUpdater.INSTANCE.getGui().updateStatus("Downloading sqlite-jdbc library");
		File output = getFile(latestJar, "sqlite-LATEST", ".jar");
		SpigotUpdater.INSTANCE.getGui().updateStatus("Putting new contents in .jar");
		ZipFile inputZip = new ZipFile(input);
	    Enumeration<? extends ZipEntry> oldEntries = inputZip.entries();
	    while(oldEntries.hasMoreElements()) {
	    	ZipEntry e = oldEntries.nextElement();
	    	if(!e.getName().startsWith("org/sqlite")) {
	    		newZip.putNextEntry(e);
				BufferedInputStream bis = new BufferedInputStream(inputZip.getInputStream(e));
				int b;
				byte buffer[] = new byte[1024];
				while((b = bis.read(buffer, 0, 1024)) != -1) {
					newZip.write(buffer, 0, b);
				}
				bis.close();
	    		newZip.closeEntry();
	    	}
	    }
		ZipFile jarZip = new ZipFile(output);
		Enumeration<? extends ZipEntry> newEntries = jarZip.entries();
	    while(newEntries.hasMoreElements()) {
	    	ZipEntry e = newEntries.nextElement();
	    	if(e.getName().startsWith("org/sqlite")) {
	    		newZip.putNextEntry(e);
				BufferedInputStream bis = new BufferedInputStream(jarZip.getInputStream(e));
				int b;
				byte buffer[] = new byte[1024];
				while((b = bis.read(buffer, 0, 1024)) != -1) {
					newZip.write(buffer, 0, b);
				}
				bis.close();
	    		newZip.closeEntry();
	    	}
	    }
	    jarZip.close();
	    inputZip.close();
	    newZip.close();
	    outNewStream.close();
		SpigotUpdater.INSTANCE.getGui().updateStatus("Finished in " + ((System.currentTimeMillis() - start) / 1000)  + " seconds!");
	}
	private File getFile(String url, String name, String extension) throws IOException {
		File file = Files.createTempFile(name, extension).toFile();
		HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
	    FileOutputStream fileOutput = new FileOutputStream(file);
	    InputStream inputStream = urlConnection.getInputStream();
	    byte[] buffer = new byte[1024];
	    int bufferLength = 0;
	    while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
	        fileOutput.write(buffer, 0, bufferLength);
	    }
	    fileOutput.close();
	    return file;
	}
}
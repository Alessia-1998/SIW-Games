package it.uniroma3.siw.config;
import java.io.File;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class UploadDirectoryCleaner {
	
	// cancella tutti i file nella cartella uploads/ all’avvio dell’app //
	
	private final String uploadDir = System.getProperty("user.dir") + File.separator + "uploads";

	@PostConstruct
	public void cleanUploadFolderOnStartup() {
		File dir = new File(uploadDir);
		if (dir.exists() && dir.isDirectory()) {
			File[] files = dir.listFiles();
			if (files != null) {
				for (File f : files) {
					if (f.isFile()) {
						boolean deleted = f.delete();
						System.out.println("🗑️ Rimosso " + f.getName() + ": " + deleted);
					}
				}
			}
		}
	}
}

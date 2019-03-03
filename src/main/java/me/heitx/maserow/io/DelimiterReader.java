package me.heitx.maserow.io;

import me.heitx.maserow.Main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Taken from an old project ..
public final class DelimiterReader {
	private static final Logger LOGGER = LogManager.getLogger(DelimiterReader.class.getName());
	private static final char DEFAULT_DELIMITER = ';';

	public static List<Identifier> getSubclasses(int itemClassId) {
		String idAsTwoDecimals = String.format("%02d", itemClassId);
		String[] folders = new String[] {
				ICSV.CSV_FOLDER_NAME, ItemCSV.ITEM_CSV_FOLDER, "item_subclasses", "item_subclass_"
		};
		String path = String.join(File.separator, folders) + idAsTwoDecimals;

		return DelimiterReader.readColumns(path, true, true);
	}

	public static List<Identifier> readColumns(ICSV csv) {
		return readColumns(csv.getFile(), csv.hasId(), csv.hasValue());
	}

	public static List<Identifier> readColumns(String fileString, boolean hasId, boolean hasValue) {
		List<Identifier> identifiers = new ArrayList<>();
		File file = new File(Main.jarFile.getParent(), fileString);

		try {
			BufferedReader reader = new BufferedReader(new FileReader(file + ".csv"));
			String line;
			try {
				while((line = reader.readLine()) != null) {
					String[] split = line.split(";");
					int id = hasId ? Integer.parseInt(split[0]) : -1;
					long value = hasId ? (hasValue ? Long.parseLong(split[1]) : -1) : (hasValue ? Long.parseLong(split[0]) : -1);
					String name = hasId ? (hasValue ? split[2] : split[1]) : (hasValue ? split[1] : "");

					identifiers.add(new Identifier(id, value, name));
				}
			} catch(IOException e) {
				LOGGER.warn("IOException:" + e.getMessage());
				e.printStackTrace();
			}
		} catch(FileNotFoundException e) {
			LOGGER.warn("FileNotFoundException:" + e.getMessage());
			e.printStackTrace();
		}

		return identifiers;
	}
}
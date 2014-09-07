package com.runescape.tools;

import static java.lang.Integer.parseInt;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.runescape.media.renderable.Model;
import com.runescape.util.Signlink;

/**
 * Static utilities used for assisting with models.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class ModelUtilities {

	/**
	 * The path to where external models are located.
	 */
	private static final Path PATH = Paths.get(Signlink.getRootDirectory() + "models");

	/**
	 * Loads all of the models within the specified {@link #PATH}.
	 *
	 * @throws IOException If some I/O exception occurs.
	 */
	public static void loadModels() throws IOException {
		File file = PATH.toFile();
		for (String name : file.list()) {
			Model.loadModelHeader(FileUtilities.getBytesFromFile(file), parseInt(name));
		}
	}

}
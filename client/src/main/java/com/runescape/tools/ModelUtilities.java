package com.runescape.tools;

import static java.lang.Integer.parseInt;

import java.io.File;
import java.io.IOException;

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
	private static final File ROOT = new File(Signlink.getRootDirectory() + "models");

	/**
	 * Loads all of the models within the specified {@link #PATH}.
	 *
	 * @throws IOException If some I/O exception occurs.
	 */
	public static void loadModels() throws IOException {
		for (File file : ROOT.listFiles()) {
			Model.loadModelHeader(FileUtilities.getBytesFromFile(file), parseInt(file.getName()));
		}
	}

}
package org.apollo.fs;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.CRC32;

/**
 * Represents the file system for the RuneScape 2 game-cache.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 * @author Hadyn Richard
 */
public final class FileSystem {

    /**
     * Represents the config file index.
     */
    public static final int CONFIG_IDX = 0;

    /**
     * Represents the model file index.
     */
    public static final int MODEL_IDX = 1;

    /**
     * Represents the animation file index.
     */
    public static final int ANIMATION_IDX = 2;

    /**
     * Represents the music and sound index.
     */
    public static final int MIDI_IDX = 3;

    /**
     * Represents the map index.
     */
    public static final int MAP_IDX = 4;

    /**
     * Represents the title archive.
     */
    public static final int TITLE_ARCHIVE = 1;

    /**
     * Represents the configuration archive.
     */
    public static final int CONFIG_ARCHIVE = 2;

    /**
     * Represents the interface archive.
     */
    public static final int INTERFACE_ARCHIVE = 3;

    /**
     * Represents the media archive.
     */
    public static final int MEDIA_ARCHIVE = 4;

    /**
     * Represents the manifest archive.
     */
    public static final int MANIFEST_ARCHIVE = 5;

    /**
     * Represents the textures archive.
     */
    public static final int TEXTURES_ARCHIVE = 6;

    /**
     * Represents the word archive.
     */
    public static final int WORD_ARCHIVE = 7;

    /**
     * Represents the sound archive.
     */
    public static final int SOUND_ARCHIVE = 8;

    /**
     * Represents the amount of total archives.
     */
    public static final int ARCHIVE_COUNT = 9;

    /**
     * A map of hashed archive names to archives.
     */
    private final Map<Integer, Archive> archives = new HashMap<>();

    /**
     * The file system manifest.
     */
    private final Manifest manifest = new Manifest();

    /**
     * Represents every file index.
     */
    private final FileIndex[] indexes;

    /**
     * Represents the archive hashes, used for determining if the file system is
     * up to date.
     */
    private byte[] archiveHashes;

    /**
     * Constructs a new {@link FileSystem} with the specified file indexes.
     *
     * @param indexes The file indexes.
     */
    public FileSystem(FileIndex[] indexes) {
	this.indexes = indexes;
    }

    /**
     * Initializes this file system.
     *
     * @throws IOException If some I/O exception occurs.
     */
    private void init() throws IOException {
	Archive archive = getArchive(MANIFEST_ARCHIVE);
	manifest.unpack(archive);
    }

    /**
     * Configures a file index to be cached.
     *
     * @param id The id of the index.
     * @param maximumSize The maximum capacity the cache will contain of the
     *            specified index.
     */
    public void configureCaching(int id, int maximumSize) {
	if (indexes[id] != null) {
	    CachedFileIndex index = (CachedFileIndex) indexes[id];
	    index.configure(maximumSize);
	    return;
	}

	indexes[id] = new CachedFileIndex(indexes[id], maximumSize);
    }

    /**
     * Initializes the default index caches.
     */
    public void defaultCaching() {
	configureCaching(CONFIG_IDX, 8);

	configureCaching(MODEL_IDX, 50);
	configureCaching(ANIMATION_IDX, 50);
	configureCaching(MIDI_IDX, 50);
	configureCaching(MAP_IDX, 50);
    }

    /**
     * Returns an {@link Archive} by the specified hashed name.
     *
     * @param id The hashed name of the archive.
     * @return The archive for the specified hashed name.
     * @throws IOException If some I/O exception occurs.
     */
    public Archive getArchive(int id) throws IOException {
	Archive archive = archives.get(id);
	if (archive == null) {
	    byte[] bytes = getFile(CONFIG_IDX, id);
	    if (bytes == null) {
		return null;
	    }
	    archive = new Archive(bytes);
	    archives.put(id, archive.decode());
	}
	return archive;
    }

    /**
     * Returns the data content within a file for the specified index and file
     * id.
     *
     * @param index The index.
     * @param id The file id.
     * @return A <code>byte[]</code> array of data that the file contains.
     * @throws IOException If some I/O exception occurs.
     */
    public byte[] getFile(int index, int id) throws IOException {
	return indexes[index].get(id);
    }

    /**
     * Creates a new file system for the specified directory.
     *
     * @param directory The directory of the file system.
     * @return The created file system.
     * @throws IOException If some I/O exception occurs.
     */
    public static FileSystem create(String directory) throws IOException {
	Pattern pattern = Pattern.compile("idx([0-9]+)$");

	Path data = Paths.get(directory, "main_file_cache.dat");
	if (!Files.exists(data)) {
	    throw new IOException("Cannot find data file for file system");
	}

	SeekableByteChannel dataChannel = Files.newByteChannel(data, StandardOpenOption.READ, StandardOpenOption.WRITE);

	List<Index> indexes = new ArrayList<>();
	try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(directory))) {
	    for (Path path : directoryStream) {
		File file = path.toFile();

		Matcher matcher = pattern.matcher(file.getName());
		if (!matcher.find()) {
		    continue;
		}

		int id = Integer.parseInt(matcher.group(1));
		indexes.add(new Index(dataChannel, Files.newByteChannel(path, StandardOpenOption.READ, StandardOpenOption.WRITE), id));
	    }
	}

	FileSystem fileSystem = new FileSystem(indexes.toArray(new FileIndex[indexes.size()]));
	fileSystem.init();

	return fileSystem;
    }

    /**
     * Returns the hashes of each archive, used for determining if the file
     * system is up to date.
     *
     * @return A <code>byte[]</code> array of archive hashes.
     * @throws IOException If some I/O exception occurs.
     */
    public byte[] getArchiveHashes() throws IOException {
	if (archiveHashes != null) {
	    return archiveHashes;
	}

	int[] archiveData = new int[ARCHIVE_COUNT];

	CRC32 crc32 = new CRC32();
	for (int index = 1; index < archiveData.length; index++) {
	    crc32.reset();

	    byte[] bytes = getFile(CONFIG_IDX, index);

	    ByteBuffer buffer = ByteBuffer.wrap(bytes);
	    buffer.get(bytes, 0, bytes.length);

	    crc32.update(bytes, 0, bytes.length);
	    archiveData[index] = (int) crc32.getValue();
	}

	int hash = 1234;
	ByteBuffer buffer = ByteBuffer.allocate(archiveData.length * 4 + 4);
	for (int element : archiveData) {
	    hash = (hash << 1) + element;
	    buffer.putInt(element);
	}

	buffer.putInt(hash);
	buffer.flip();

	return archiveHashes = buffer.array();
    }

}
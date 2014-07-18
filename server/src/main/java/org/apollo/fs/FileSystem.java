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
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.CRC32;

public final class FileSystem {

    public static final int CONFIG_IDX = 0;
    public static final int MODEL_IDX = 1;
    public static final int ANIMATION_IDX = 2;
    public static final int MIDI_IDX = 3;
    public static final int MAP_IDX = 4;

    public static final int CONFIG_ARCHIVE = 2;
    public static final int MANIFEST_ARCHIVE = 5;

    public static final int ARCHIVE_COUNT = 9;

    private final Map<Integer, Archive> archives = new HashMap<>();
    private final Manifest manifest = new Manifest();

    private final FileIndex[] indexes;
    private final boolean[] cached;

    private byte[] archiveHashes;

    public FileSystem(FileIndex[] indexes) throws IOException {
	this.indexes = indexes;
	cached = new boolean[indexes.length];
    }

    private void init() throws IOException {
	Archive versionList = getArchive(MANIFEST_ARCHIVE);
	manifest.unpack(versionList);
    }

    public void configureCaching(int id, int maximumSize) {
	if (!cached[id]) {
	    indexes[id] = new CachedFileIndex(indexes[id], maximumSize);
	    cached[id] = true;
	} else {
	    CachedFileIndex index = (CachedFileIndex) indexes[id];
	    index.configure(maximumSize);
	}
    }

    public void defaultCaching() {
	configureCaching(CONFIG_IDX, 8);

	configureCaching(MODEL_IDX, 50);
	configureCaching(ANIMATION_IDX, 50);
	configureCaching(MIDI_IDX, 50);
	configureCaching(MAP_IDX, 50);
    }

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

    public byte[] getFile(int index, int id) throws IOException {
	return indexes[index].get(id);
    }

    public static FileSystem create(String directory) throws IOException {

	Pattern pattern = Pattern.compile("idx([0-9]+)$");

	Path data = Paths.get(directory, "main_file_cache.dat");
	if (!Files.exists(data)) {
	    throw new IOException("Cannot find data file for file system");
	}

	SeekableByteChannel dataChannel = Files.newByteChannel(data, StandardOpenOption.READ, StandardOpenOption.WRITE);

	ArrayList<Index> indexes = new ArrayList<>();

	try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(directory))) {
	    for (Path path : directoryStream) {
		File file = path.toFile();

		Matcher matcher = pattern.matcher(file.getName());
		if (!matcher.find()) {
		    continue;
		}

		int id = Integer.parseInt(matcher.group(1));
		indexes.ensureCapacity(id + 1);

		indexes.add(new Index(dataChannel, Files.newByteChannel(path, StandardOpenOption.READ, StandardOpenOption.WRITE), id));
	    }
	}

	FileSystem fileSystem = new FileSystem(indexes.toArray(new FileIndex[indexes.size()]));
	fileSystem.init();

	return fileSystem;
    }

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
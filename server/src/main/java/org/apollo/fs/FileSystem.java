package org.apollo.fs;

import static java.nio.file.StandardOpenOption.READ;
import static java.nio.file.StandardOpenOption.WRITE;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.CRC32;

import com.google.common.base.Preconditions;

public final class FileSystem {

	public static final int CONFIG_INDEX = 0;
	public static final int MODEL_INDEX = 1;
	public static final int ANIMATION_INDEX = 2;
	public static final int MIDI_INDEX = 3;
	public static final int MAP_INDEX = 4;

	public static final int TITLE_ARCHIVE = 1;
	public static final int CONFIG_ARCHIVE = 2;
	public static final int INTERFACE_ARCHIVE = 3;
	public static final int MEDIA_ARCHIVE = 4;
	public static final int MANIFEST_ARCHIVE = 5;
	public static final int TEXTURES_ARCHIVE = 6;
	public static final int WORD_ARCHIVE = 7;
	public static final int SOUND_ARCHIVE = 8;

	private static final int MAXIMUM_ARCHIVES = 9;
	private static final int MAXIMUM_INDICES = 256;

	/**
	 * Represents the prefix of this {@link FileSystem}s main cache files.
	 */
	private static final String DATA_PREFIX = "main_file_cache.dat";

	/**
	 * Represents the prefix of this {@link FileSystem}s index files.
	 */
	private static final String INDEX_PREFIX = "main_file_cache.idx";

	/**
	 * All of the {@link Archive}s within this {@link FileSystem}.
	 */
	private final Archive[] archives;

	/**
	 * All of the {@link Cache}s within this {@link FileSystem}.
	 */
	private final Cache[] caches;

	/**
	 * The cached archive hashes.
	 */
	private ByteBuffer archiveHashes;

	/**
	 * Constructs a new {@link FileSystem} with the specified {@link Cache}s and
	 * {@link Archive}s
	 *
	 * @param caches All of the {@link Cache}s within this {@link FileSystem}.
	 * @param archives All of the {@link Archive}s within this
	 *            {@link FileSystem}.
	 */
	private FileSystem(Cache[] caches, Archive[] archives) {
		this.caches = caches;
		this.archives = archives;
	}

	/**
	 * Constructs and initializes a {@link FileSystem} from the specified
	 * {@code directory}.
	 *
	 * @param directory The directory of the {@link FileSystem}.
	 * @return The constructed {@link FileSystem} instance.
	 * @throws IOException If some I/O exception occurs.
	 */
	public static FileSystem create(String directory) throws IOException {
		Path root = Paths.get(directory);
		Preconditions.checkArgument(Files.isDirectory(root), "Supplied path must be a directory!");

		Path data = root.resolve(DATA_PREFIX);
		Preconditions.checkArgument(Files.exists(data), "No data file found in the specified path!");

		SeekableByteChannel dataChannel = Files.newByteChannel(data, READ, WRITE);

		Cache[] caches = new Cache[MAXIMUM_INDICES];
		Archive[] archives = new Archive[MAXIMUM_ARCHIVES];

		for (int index = 0; index < caches.length; index++) {
			Path path = root.resolve(INDEX_PREFIX + index);
			if (Files.exists(path)) {
				SeekableByteChannel indexChannel = Files.newByteChannel(path, READ, WRITE);
				caches[index] = new Cache(dataChannel, indexChannel, index);
			}
		}

		// We don't use index 0
		for (int id = 1; id < archives.length; id++) {
			Cache cache = caches[CONFIG_INDEX];
			Preconditions.checkNotNull(cache, "Configuration cache is null - unable to decode archives");
			archives[id] = Archive.decode(cache.get(id));
		}

		FileSystem fileSystem = new FileSystem(caches, archives);

		return fileSystem;
	}

	/**
	 * Gets an {@link Archive} for the specified {@code id}.
	 *
	 * @param id The id of the {@link Archive} to fetch.
	 * @return The {@link Archive} for the specified {@code id} or {@code null}
	 *         if it doesn't exist.
	 */
	public Archive getArchive(int id) {
		Preconditions.checkElementIndex(id, archives.length);
		Preconditions.checkNotNull(archives[id]);
		return archives[id];
	}

	public Cache getCache(int id) {
		Preconditions.checkElementIndex(id, caches.length);
		Preconditions.checkNotNull(caches[id]);
		return caches[id];
	}

	public ByteBuffer getFile(int cacheId, int indexId) throws IOException {
		Cache cache = getCache(cacheId);
		return cache.get(indexId);
	}

	public ByteBuffer getArchiveHashes() throws IOException {
		synchronized (this) {
			if (archiveHashes != null) {
				return archiveHashes.duplicate();
			}
		}

		int[] crcs = new int[MAXIMUM_ARCHIVES];

		CRC32 crc32 = new CRC32();
		for (int file = 1; file < crcs.length; file++) {
			crc32.reset();

			ByteBuffer buffer = getFile(CONFIG_INDEX, file);
			crc32.update(buffer);

			crcs[file] = (int) crc32.getValue();
		}

		ByteBuffer buffer = ByteBuffer.allocate((crcs.length + 1) * Integer.BYTES);

		int hash = 1234;
		for (int crc : crcs) {
			hash = (hash << 1) + crc;
			buffer.putInt(crc);
		}

		buffer.putInt(hash);
		buffer.flip();

		synchronized (this) {
			archiveHashes = buffer.asReadOnlyBuffer();
			return archiveHashes.duplicate();
		}
	}

}
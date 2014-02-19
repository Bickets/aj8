
package org.apollo.fs;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.zip.CRC32;

/**
 * A file system based on top of the operating system's file system. It
 * consists of a data file and index files. Index files point to blocks in the
 * data file, which contains the actual data.
 * @author Graham
 */
public final class IndexedFileSystem implements Closeable
{

	/**
	 * Read only flag.
	 */
	private final boolean readOnly;

	/**
	 * The index files.
	 */
	private RandomAccessFile[] indices = new RandomAccessFile[ 256 ];

	/**
	 * The data file.
	 */
	private RandomAccessFile cacheData;

	/**
	 * The cached CRC table.
	 */
	private ByteBuffer crcTable;


	/**
	 * Creates the file system with the specified base directory.
	 * @param base The base directory.
	 * @param readOnly A flag indicating if the file system will be read only.
	 * @throws FileNotFoundException if the file system is invalid or does not exist
	 */
	public IndexedFileSystem( File base, boolean readOnly ) throws FileNotFoundException
	{
		this.readOnly = readOnly;
		detectLayout( base );
	}


	/**
	 * Checks if this {@link IndexedFileSystem} is read only.
	 * @return {@code true} if so, {@code false} if not.
	 */
	public boolean isReadOnly()
	{
		return readOnly;
	}


	/**
	 * Automatically detect the layout of the specified directory.
	 * @param root The base directory.
	 * @throws FileNotFoundException if the file system is invalid or does not exist.
	 */
	private void detectLayout( File root ) throws FileNotFoundException
	{
		int indexCount = 0;
		for( int index = 0; index < indices.length; index ++ ) {
			File indexFile = new File( root.getAbsolutePath() + "/main_file_cache.idx" + index );
			if( indexFile.exists() && ! indexFile.isDirectory() ) {
				indexCount ++ ;
				indices[ index ] = new RandomAccessFile( indexFile, readOnly ? "r": "rw" );
			}
		}
		if( indexCount <= 0 ) {
			throw new FileNotFoundException( "No index file(s) present" );
		}

		File data = new File( root.getAbsolutePath() + "/main_file_cache.dat" );
		if( data.exists() && ! data.isDirectory() ) {
			cacheData = new RandomAccessFile( data, readOnly ? "r": "rw" );
		} else {
			throw new FileNotFoundException( "No data file present" );
		}
	}


	/**
	 * Gets the index of a file.
	 * @param fd The {@link FileDescriptor} which points to the file.
	 * @return The {@link Index}.
	 * @throws IOException if an I/O error occurs.
	 */
	private Index getIndex( FileDescriptor fd ) throws IOException
	{
		int index = fd.getType();
		if( index < 0 || index >= indices.length ) {
			throw new IndexOutOfBoundsException();
		}

		byte[] buffer = new byte[ FileSystemConstants.INDEX_SIZE ];
		RandomAccessFile indexFile = indices[ index ];
		synchronized( indexFile ) {
			long ptr = ( long )fd.getFile() * ( long )FileSystemConstants.INDEX_SIZE;
			if( ptr >= 0 && indexFile.length() >= ( ptr + FileSystemConstants.INDEX_SIZE ) ) {
				indexFile.seek( ptr );
				indexFile.readFully( buffer );
			} else {
				throw new FileNotFoundException();
			}
		}

		return Index.decode( buffer );
	}


	/**
	 * Gets the number of files with the specified type.
	 * @param type The type.
	 * @return The number of files.
	 * @throws IOException if an I/O error occurs.
	 */
	private int getFileCount( int type ) throws IOException
	{
		if( type < 0 || type >= indices.length ) {
			throw new IndexOutOfBoundsException();
		}

		RandomAccessFile indexFile = indices[ type ];
		synchronized( indexFile ) {
			return ( int )( indexFile.length() / FileSystemConstants.INDEX_SIZE );
		}
	}


	/**
	 * Gets the CRC table.
	 * @return The CRC table.
	 * @throws IOException if an I/O erorr occurs.
	 */
	public ByteBuffer getCrcTable() throws IOException
	{
		if( readOnly ) {
			synchronized( this ) {
				if( crcTable != null ) {
					return crcTable.duplicate();
				}
			}

			// the number of archives
			int archives = getFileCount( 0 );

			// the hash
			int hash = 1234;

			// the CRCs
			int[] crcs = new int[ archives ];

			// calculate the CRCs
			CRC32 crc32 = new CRC32();
			for( int i = 1; i < crcs.length; i ++ ) {
				crc32.reset();

				ByteBuffer bb = getFile( 0, i );
				byte[] bytes = new byte[ bb.remaining() ];
				bb.get( bytes, 0, bytes.length );
				crc32.update( bytes, 0, bytes.length );

				crcs[ i ] = ( int )crc32.getValue();
			}

			// hash the CRCs and place them in the buffer
			ByteBuffer buf = ByteBuffer.allocate( crcs.length * 4 + 4 );
			for( int i = 0; i < crcs.length; i ++ ) {
				hash = ( hash << 1 ) + crcs[ i ];
				buf.putInt( crcs[ i ] );
			}

			// place the hash into the buffer
			buf.putInt( hash );
			buf.flip();

			synchronized( this ) {
				crcTable = buf.asReadOnlyBuffer();
				return crcTable.duplicate();
			}
		} else {
			throw new IOException( "cannot get CRC table from a writable file system" );
		}
	}


	/**
	 * Gets a file.
	 * @param type The file type.
	 * @param file The file id.
	 * @return A {@link ByteBuffer} which contains the contents of the file.
	 * @throws IOException if an I/O error occurs.
	 */
	public ByteBuffer getFile( int type, int file ) throws IOException
	{
		return getFile( new FileDescriptor( type, file ) );
	}


	/**
	 * Gets a file.
	 * @param fd The {@link FileDescriptor} which points to the file.
	 * @return A {@link ByteBuffer} which contains the contents of the file.
	 * @throws IOException if an I/O error occurs.
	 */
	public ByteBuffer getFile( FileDescriptor fd ) throws IOException
	{
		Index index = getIndex( fd );
		ByteBuffer buffer = ByteBuffer.allocate( index.getSize() );

		// calculate some initial values
		long ptr = ( long )index.getBlock() * ( long )FileSystemConstants.BLOCK_SIZE;
		int read = 0;
		int size = index.getSize();
		int blocks = size / FileSystemConstants.CHUNK_SIZE;
		if( size % FileSystemConstants.CHUNK_SIZE != 0 ) {
			blocks ++ ;
		}

		for( int i = 0; i < blocks; i ++ ) {

			// read header
			byte[] header = new byte[ FileSystemConstants.HEADER_SIZE ];
			synchronized( cacheData ) {
				cacheData.seek( ptr );
				cacheData.readFully( header );
			}

			// increment pointers
			ptr += FileSystemConstants.HEADER_SIZE;

			// parse header
			int nextFile = ( ( header[ 0 ] & 0xFF ) << 8 ) | ( header[ 1 ] & 0xFF );
			int curChunk = ( ( header[ 2 ] & 0xFF ) << 8 ) | ( header[ 3 ] & 0xFF );
			int nextBlock = ( ( header[ 4 ] & 0xFF ) << 16 ) | ( ( header[ 5 ] & 0xFF ) << 8 ) | ( header[ 6 ] & 0xFF );
			int nextType = header[ 7 ] & 0xFF;

			// check expected chunk id is correct
			if( i != curChunk ) {
				throw new IOException( "Chunk id mismatch." );
			}

			// calculate how much we can read
			int chunkSize = size - read;
			if( chunkSize > FileSystemConstants.CHUNK_SIZE ) {
				chunkSize = FileSystemConstants.CHUNK_SIZE;
			}

			// read the next chunk and put it in the buffer
			byte[] chunk = new byte[ chunkSize ];
			synchronized( cacheData ) {
				cacheData.seek( ptr );
				cacheData.readFully( chunk );
			}
			buffer.put( chunk );

			// increment pointers
			read += chunkSize;
			ptr = ( long )nextBlock * ( long )FileSystemConstants.BLOCK_SIZE;

			// if we still have more data to read, check the validity of the
			// header
			if( size > read ) {
				if( nextType != ( fd.getType() + 1 ) ) {
					throw new IOException( "File type mismatch." );
				}

				if( nextFile != fd.getFile() ) {
					throw new IOException( "File id mismatch." );
				}
			}
		}

		buffer.flip();
		return buffer;
	}


	@Override
	public void close() throws IOException
	{
		if( cacheData != null ) {
			synchronized( cacheData ) {
				cacheData.close();
			}
		}

		for( RandomAccessFile index: indices ) {
			if( index != null ) {
				synchronized( index ) {
					index.close();
				}
			}
		}
	}

}

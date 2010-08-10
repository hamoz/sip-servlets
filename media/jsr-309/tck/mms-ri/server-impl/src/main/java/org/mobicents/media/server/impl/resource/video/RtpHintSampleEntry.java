package org.mobicents.media.server.impl.resource.video;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author amit bhayani
 * 
 */
public class RtpHintSampleEntry extends SampleEntry {

	// File Type = rtp
	static byte[] TYPE = new byte[] { AsciiTable.ALPHA_r, AsciiTable.ALPHA_t, AsciiTable.ALPHA_p, AsciiTable.SPACE };
	static String TYPE_S = "rtp ";
	static {
		bytetoTypeMap.put(TYPE, TYPE_S);
	}

	private int hintTrackVersion;
	private int highestCompatibleVersion;
	private long maxPacketSize;
	private List<Box> additionaldata = new ArrayList<Box>();

	public RtpHintSampleEntry(long size) {
		super(size, TYPE_S);
	}

	@Override
	protected int load(DataInputStream fin) throws IOException {
		int count = 8;
		count += super.load(fin);

		hintTrackVersion = this.read16(fin);
		highestCompatibleVersion = this.read16(fin);
		maxPacketSize = this.readU32(fin);

		count += 8;

		while (count < this.getSize()) {
			long length = readU32(fin);
			byte[] type = read(fin);

			Box box = null;
			if (comparebytes(type, TimeScaleEntry.TYPE)) {
				box = new TimeScaleEntry(length);
				count += box.load(fin);
			} else if (comparebytes(type, TimeOffSet.TYPE)) {
				box = new TimeOffSet(length);
				count += box.load(fin);
			} else if (comparebytes(type, SequenceOffSet.TYPE)) {
				box = new SequenceOffSet(length);
				count += box.load(fin);
			} else {
				throw new IOException("Unknown box=" + new String(type) + " From parent RtpHintSampleEntry");
			}

			additionaldata.add(box);
		}

		return (int) this.getSize();
	}

	public int getHintTrackVersion() {
		return hintTrackVersion;
	}

	public int getHighestCompatibleVersion() {
		return highestCompatibleVersion;
	}

	public long getMaxPacketSize() {
		return maxPacketSize;
	}

	public List<Box> getAdditionaldata() {
		return additionaldata;
	}

}
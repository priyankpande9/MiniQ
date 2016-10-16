package com.pp.miniq.utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

public class InflateDeflateUtility {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    byte[]                buffer       = new byte[8 * 1024];

    public byte[] compress(String str) throws Exception {
        InputStream is = new ByteArrayInputStream(str.getBytes("UTF-8"));
        return compress(is);
    }

    public String deCompress(byte[] bytes) throws Exception {
        InputStream is = new ByteArrayInputStream(bytes);
        return deCompress(is);
    }

    public String deCompress(String str) throws Exception {
        InputStream is = new ByteArrayInputStream(str.getBytes("UTF-8"));
        return deCompress(is);
    }

    public ByteArrayOutputStream readSourceContent(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            int length = 0;
            outputStream.reset();
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
        } catch (IOException e) {
            throw new IOException("Exception occurred while reading content", e);
        } finally {
            outputStream.close();
        }

        return outputStream;
    }

    public String deCompress(InputStream stream) throws IOException {
        InputStream inflatedStrm = null;

        try {
            inflatedStrm = new InflaterInputStream(stream, new Inflater(true));
            outputStream = readSourceContent(inflatedStrm);
            return outputStream.toString();
        } finally {
            if (inflatedStrm != null) {
                try {
                    inflatedStrm.close();
                } catch (Exception e) {
                }
                inflatedStrm = null;
            }
        }

    }

    public byte[] compress(InputStream stream) throws IOException {
        DeflaterInputStream deflateStream = null;
        try {
            deflateStream = new DeflaterInputStream(stream, new Deflater(Deflater.BEST_COMPRESSION, true));
            outputStream = readSourceContent(deflateStream);
            return outputStream.toByteArray();
        } finally {
            if (deflateStream != null) {
                try {
                    deflateStream.close();
                } catch (Exception e) {
                }
                deflateStream = null;
            }
        }
    }

    public void reset() {
        outputStream.reset();
    }
}

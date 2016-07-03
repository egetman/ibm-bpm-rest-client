package ru.bpmink.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.List;

public class Utils {

    public static String inputStreamToString(InputStream inputStream) throws IOException {
        return inputStreamToString(inputStream, Charset.forName("UTF-8"));
    }

    public static String inputStreamToString(InputStream inputStream, String charset) throws IOException {
        return inputStreamToString(inputStream, Charset.forName(charset));
    }

    /**
     * Retrieves all data for given {@link java.io.InputStream} as String.
     *
     * @param inputStream {@link java.io.InputStream} to process.
     * @param charset {@link java.nio.charset.Charset} to be used during byte to string conversion.
     * @return string representation of {@link java.io.InputStream} data.
     * @throws IOException  If the first byte cannot be read for any reason
     *      other than the end of the file, if the input stream has been closed, or
     *      if some other I/O error occurs.
     */
    public static String inputStreamToString(InputStream inputStream, Charset charset) throws IOException {
        if (inputStream == null) {
            return null;
        }
        try (ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                arrayOutputStream.write(buffer, 0, length);
            }
            return arrayOutputStream.toString(charset.name());
        }
    }

    /**
     * Writes a list of string in to given {@link java.io.Writer}.
     *
     * @param writer {@link java.io.Writer} to write.
     * @param source {@link java.util.List} of strings to be written.
     * @throws IOException If an I/O error occurs.
     */
    public static void writeLines(Writer writer, List<String> source) throws IOException {
        try {
            for (String line : source) {
                writer.write(line);
                writer.write(Constants.LINE_SEPARATOR);
            }
            writer.flush();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

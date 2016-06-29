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

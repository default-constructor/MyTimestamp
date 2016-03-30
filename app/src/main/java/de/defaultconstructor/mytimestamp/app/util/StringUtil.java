package de.defaultconstructor.mytimestamp.app.util;

/**
 * Created by thre on 30.03.2016.
 */
public class StringUtil {

    private StringUtil() {
        // util class
    }

    public static String getStringedArray(String[] stringArray, String separator) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < stringArray.length - 1; i++) {
            buffer.append(stringArray[i]).append(separator);
        }
        buffer.append(stringArray[stringArray.length - 1]);
        return buffer.toString();
    }
}

package com.brz.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by macro on 16/7/20.
 */
public class DebugUtil {

    public static String FormatStackTrace(Throwable throwable) {
        if(throwable == null) return "";

        String rtn = "";

        try {
            Writer writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            throwable.printStackTrace(printWriter);
            printWriter.flush();
            writer.flush();
            rtn = writer.toString();
            printWriter.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception ex) {

        }

        return rtn;
    }
}

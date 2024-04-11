// Copyright (C) 2022 Sergei Semenkov
// All Rights Reserved.

package mondrian.util;

import java.io.IOException;
import java.io.PrintWriter;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.FileAppender;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse
        ;
public class LogResource extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException  {
        String pathInfo = request.getPathInfo();
        String[] parts = pathInfo.split("/");
        String appenderRef = "";
        String result = "";
        if(parts.length > 0) {
            appenderRef = parts[parts.length - 1];
            FileAppender fileAppender = (FileAppender)LoggerContext.getContext().getConfiguration().getAppender(appenderRef);

            if(fileAppender != null) {
                String logFileName = fileAppender.getFileName();

                byte[] encoded = Files.readAllBytes(Paths.get(logFileName));
                result = new String(encoded, StandardCharsets.UTF_8);
            }
            else {
                result = "There is no such log.";
            }
        }

        response.setContentType("text/plain charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print(result);
    }
}

package com.example.jambe.util;

import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResponseOuterUtil {

    private HttpServletRequest request;
    private HttpServletResponse response;

    public ResponseOuterUtil(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public void send(String alertText) {
        System.out.println("util class called");
        try {
            response.setContentType("text/html; charset=UTF-8");
            response.setStatus(404);
            PrintWriter out = response.getWriter();
            out.println("<script>alert('" + alertText + "'); location.href='/auth/login';</script> ");
            out.flush();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String alertText,int statusCode) {
        System.out.println("util class called");
        try {
            response.setContentType("text/html; charset=UTF-8");
            response.setStatus(statusCode);
            PrintWriter out = response.getWriter();
            out.println("<script>alert('" + alertText + "'); location.href='/auth/login';</script> ");
            out.flush();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}

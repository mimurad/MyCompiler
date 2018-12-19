/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mycompiler;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DELL-XPS
 */
public class Cmd_prac implements Runnable {

    Thread thread = null;
    private DataInputStream streamIn = null, conSole = null;
    private DataOutputStream streamOut = null;
    static Process process = null;

    public Cmd_prac() throws IOException {

        open();
        start();
        while (true) {
            System.out.println("input: ");
            int line2 = conSole.read();
            System.out.println(line2);
            streamOut.write(line2);
            streamOut.flush();
        }
    }

    public void run() {
        while (thread != null) {
            boolean done = false;
            while (!done) {
                try {
                    String line;
                    while ((line = streamIn.readLine()) != null) {
                        System.out.println("recived=" + line);
                    }
                } catch (IOException ioe) {
                    done = true;
                }
            }
            stop();
        }
    }

    public void stop() {
        try {
            if (conSole != null) {
                conSole.close();
            }
            if (streamOut != null) {
                streamOut.close();
            }

        } catch (IOException ioe) {
            System.out.println("Error closing ...");
        }
    }

    public void start() {
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
            System.out.println("New Thread!!!");
        }
    }

    public void open() throws IOException {
        streamIn = new DataInputStream(new BufferedInputStream(process.getInputStream()));
        conSole = new DataInputStream(System.in);
        streamOut = new DataOutputStream(process.getOutputStream());
    }

    public static void main(String[] args) throws IOException {
        String[] command = {"CMD", "/C", "java Hh"};
        ProcessBuilder probuilder = new ProcessBuilder(command);
        //You can set up your work directory
        probuilder.directory(new File("C:\\Users\\DELL-XPS\\Desktop"));
        process = probuilder.start();
        System.out.println("ok");
        Cmd_prac any = new Cmd_prac();

    }

}

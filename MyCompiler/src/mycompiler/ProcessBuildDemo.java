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

/**
 *
 * @author DELL-XPS
 */
public class ProcessBuildDemo implements Runnable {

    Thread thread = null;
    private DataInputStream streamIn = null, conSole = null;
    private DataOutputStream streamOut = null;
    static Process process = null;

    public ProcessBuildDemo() throws IOException {

        open();
        start();
        while (true) {
            //System.out.println("input: ");
            int line2 = conSole.read();
            streamOut.write(line2);
            streamOut.flush();
        }
    }

    public void run() {
        while (thread != null) {
            try {

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
                open();
            } catch (IOException ie) {
                System.out.println("Acceptance Error: " + ie);
            }
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
        String[] command = {"CMD", "/C", "java hh"};
        ProcessBuilder probuilder = new ProcessBuilder(command);
        //You can set up your work directory
        probuilder.directory(new File("C:\\Users\\DELL-XPS\\Desktop"));
        process = probuilder.start();
        System.out.println("ok");
        Cmd_prac any = new Cmd_prac();

    }

}

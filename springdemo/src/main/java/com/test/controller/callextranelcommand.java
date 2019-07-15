package com.test.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@RestController
@CrossOrigin(origins = "*")
public class callextranelcommand {

    @RequestMapping(path = "/testsh")
    public String testsh() {
        String[] command = {"/bin/bash", "-c","sh /usr/local/test.sh"};
        ProcessBuilder pb = new ProcessBuilder(command);
        try {
            pb.redirectErrorStream(true);
            Process process = pb.start();
            process.waitFor();
            int ret = process.exitValue();
            InputStream is = process.getInputStream();	//標準出力
            printInputStream(is);
            InputStream es = process.getErrorStream();	//標準エラー
            printInputStream(es);
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "test Hello world";
    }


    public static void printInputStream(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        try {
            for (;;) {
                String line = br.readLine();
                if (line == null) break;
                System.out.println(line);
            }
        } finally {
            br.close();
        }
    }

}

package com.puk.compiler.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class CompileService {
    public void GetOutput(String filePath,String programmingLanguage) {

        Map<String, String> commands = new HashMap<>();
        commands.put("python", "cmd /c python " + filePath);
        commands.put("java", "cmd /c java " + filePath);
        commands.put("c", "cmd /c gcc " + filePath + " -o " + filePath.substring(0, filePath.lastIndexOf(".")) + " && " + filePath.substring(0, filePath.lastIndexOf(".")));
        commands.put("c++", "cmd /c g++ " + filePath + " -o " + filePath.substring(0, filePath.lastIndexOf(".")) + " && " + filePath.substring(0, filePath.lastIndexOf(".")));

        String command = commands.get(programmingLanguage);
        if (command == null) {
            System.out.println("Unsupported programming language: " + programmingLanguage);
            return;
        }

        try {
            ProcessBuilder pb = new ProcessBuilder(command.split(" "));
            pb.directory(new File(filePath).getParentFile());
            pb.redirectErrorStream(true);

            Process p = pb.start();
            int exitCode = p.waitFor();


            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "";
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();


            if (exitCode == 0) {
                System.out.println("Program executed successfully.");
            } else {
                System.err.println("Program exited with error code " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

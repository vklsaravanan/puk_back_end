package com.puk.compiler.service;

import com.puk.compiler.pojo.CompilerRequest;
import com.puk.compiler.pojo.CompilerResponse;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;
import java.io.*;
@ApplicationScoped
public class CompileService {

    private static final Logger logger = Logger.getLogger(CompileService.class);

    public CompilerResponse compileCodeAndGetOutput(final CompilerRequest compilerRequest) {
        final String code = compilerRequest.getCode();
        final String programmingLanguage = compilerRequest.getLanguage();
        final String[] input = compilerRequest.getInput();
        final String filePath = convertStringToFile(code,programmingLanguage);
        String command = "";
        if (".py".equals(programmingLanguage)) {
            command = "cmd /c python " + filePath;
        } else if (".java".equals(programmingLanguage)) {
            command = "cmd /c javac " + filePath + " && java -cp " +
                    filePath.substring(0, filePath.lastIndexOf("\\")) + " " +
                    new File(filePath).getName().split(".java")[0];
        } else if (".c".equals(programmingLanguage)) {
            command = "cmd /c gcc " + filePath + " -o " +
                    filePath.substring(0, filePath.lastIndexOf(".")) +
                    " && " + filePath.substring(0, filePath.lastIndexOf(".")) + ".exe";
        } else if (".cpp".equals(programmingLanguage)) {
            command = "cmd /c g++ " + filePath + " -o " +
                    filePath.substring(0, filePath.lastIndexOf(".")) +
                    " && " + filePath.substring(0, filePath.lastIndexOf(".")) + ".exe";
        } else {
            System.out.println("Unsupported programming language: " + programmingLanguage);
        }

        final CompilerResponse response = new CompilerResponse();

        try {
            // Create ProcessBuilder object and set the working directory to the directory containing the source file
            ProcessBuilder pb = new ProcessBuilder(command.split(" "));
            pb.directory(new File(filePath).getParentFile());


            // Start the process
            Process p = pb.start();

            // Write the input lines to the process's input stream only the program required some inputs
            try (BufferedWriter inputWriter = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()))) {
                for (String inputLine : input) {
                    inputWriter.write(inputLine);
                    inputWriter.newLine();
                }
            }

            // Catch the output like print statements
            try (BufferedReader outputReader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                String output = "/n";
                String outputLineReader = "";
                while ((outputLineReader = outputReader.readLine()) != null) {
                    output+=outputLineReader;
                    output +="/n";
                 }
                response.setOutput(output);
            }

            //  Catch the error messages
            try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(p.getErrorStream()))) {
                String error = "/n";
                String errorLineReader = "";
                while ((errorLineReader = errorReader.readLine()) != null) {
                    if(errorLineReader.contains(filePath)){
                        errorLineReader = errorLineReader.replace(filePath.substring(0,filePath.lastIndexOf("\\"))+"\\","");
                    }
                    error += errorLineReader;
                    error += "/n";
                 }
                response.setError(error);
            }


            // Check if the process exited successfully
            final int exitCode = p.waitFor();
            response.setExitCode(String.valueOf(exitCode));
            if (exitCode == 0) {
                logger.info("Program executed successfully.");

            } else {
                logger.info("Program exited with error code " + exitCode);
            }

            // Delete the .class or .exe file
            deleteCacheFiles(programmingLanguage,filePath);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return response;


    }

    private String convertStringToFile(final String code,final String lang){
        final File programmingFile = new File("C:\\Do\\puk_back_end\\Code\\Main"+lang);
        try {
            FileWriter fileWriter = new FileWriter(programmingFile);
            fileWriter.append(code);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
         return programmingFile.getAbsolutePath();

    }

    private void deleteCacheFiles(final String programmingLanguage,final String filePath){
        String extension = "";
        if(programmingLanguage.equalsIgnoreCase(".java")){
            extension = ".class";
        } else{
            extension = ".exe";
        }
        final String exeFilePath = filePath.substring(0, filePath.lastIndexOf(".")) + extension;
        final File exeFile = new File(exeFilePath);
        if (exeFile.exists()) {
            if (exeFile.delete()) {
                System.out.println("Deleted " + exeFilePath);
            } else {
                System.err.println("Failed to delete " + exeFilePath);
            }
        }
    }

}

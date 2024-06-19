package com.kariutech.apacheCamelPatients.Controller;


import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


public class FileImporterApp {
    private static JTextField filePathField;
    private static CamelContext camelContext;

    public static void main(String[] args) {
        JFrame frame = new JFrame("File Importer");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JButton browseButton = new JButton("Browse");
        browseButton.setBounds(20, 20, 100, 30);
        frame.add(browseButton);

        filePathField = new JTextField();
        filePathField.setBounds(130, 20, 200, 30);
        frame.add(filePathField);

        JButton importButton = new JButton("Import");
        importButton.setBounds(20, 70, 100, 30);
        frame.add(importButton);

        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    filePathField.setText(selectedFile.getAbsolutePath());
                }
            }
        });

        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    startCamelRoute(filePathField.getText());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        frame.setVisible(true);

        // Initialize Camel Context
        camelContext = new DefaultCamelContext();
    }

    private static void startCamelRoute(String filePath) throws Exception {
        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("file://" + filePath)
                        .to("file://output");
            }
        });
        camelContext.start();

        // Run Camel context for 10 seconds for demo purposes
        Thread.sleep(10000);

//        camelContext.stop();
    }
}

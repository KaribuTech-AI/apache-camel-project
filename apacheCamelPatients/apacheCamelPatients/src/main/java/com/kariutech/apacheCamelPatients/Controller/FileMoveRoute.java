package com.kariutech.apacheCamelPatients.Controller;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class FileMoveRoute {
    public static void main(String[] args) throws Exception {
        // Create Camel context
        CamelContext context = new DefaultCamelContext();

        // Add our route to the Camel context
        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("file:data/input?noop=true")
                        .to("file:data/output");
          System.out.println("Camel project started");
            }
        });

        // Start the Camel context
        context.start();

        // Keep the application running
        Thread.sleep(5000);

        // Stop the Camel context
//        context.stop();
    }
}


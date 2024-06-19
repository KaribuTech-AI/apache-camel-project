package com.kariutech.apacheCamelPatients.Controller;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MyRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:start")
                .to("https://eazimed-backend-web-mongo.azurewebsites.net/mongo/documents/PatientsSpreadsheet0612.xlsx?type=patient")
                .to("log:output");
    }
}



//    @Override
//    public void configure() {
//        from("timer://foo?repeatCount=1")
//                .setHeader("CamelHttpMethod", constant("GET"))
//                .to("https://localhost:3001/mongo/documents/PatientsSpreadsheet0612.xlsx?type=patient")
//                .convertBodyTo(byte[].class)
//                .to("file:output?fileName=PatientsSpreadsheet0612.xlsx");
//    }



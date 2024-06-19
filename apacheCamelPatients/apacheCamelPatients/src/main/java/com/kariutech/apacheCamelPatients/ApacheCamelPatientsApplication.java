package com.kariutech.apacheCamelPatients;




import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.poi.ss.usermodel.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.InputStream;
import java.util.Iterator;

@SpringBootApplication
public class ApacheCamelPatientsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApacheCamelPatientsApplication.class, args);

        CamelContext camelContext = new DefaultCamelContext();
        try {
            camelContext.addRoutes(new RouteBuilder() {
                @Override
                public void configure() {
                    from("timer:fetchSpreadsheet?repeatCount=1")
                            .to("https://eazimed-backend-web-mongo.azurewebsites.net/mongo/documents/PatientsSpreadsheet0612.xlsx?type=patient")
                            .process(exchange -> {
                                InputStream body = exchange.getIn().getBody(InputStream.class);
                                Workbook workbook = WorkbookFactory.create(body);
                                Sheet sheet = workbook.getSheetAt(0);
                                StringBuilder output = new StringBuilder();

                                for (Row row : sheet) {
                                    for (Cell cell : row) {
                                        switch (cell.getCellType()) {
                                            case STRING:
                                                output.append(cell.getStringCellValue()).append("\t");
                                                break;
                                            case NUMERIC:
                                                if (DateUtil.isCellDateFormatted(cell)) {
                                                    output.append(cell.getDateCellValue()).append("\t");
                                                } else {
                                                    output.append(cell.getNumericCellValue()).append("\t");
                                                }
                                                break;
                                            case BOOLEAN:
                                                output.append(cell.getBooleanCellValue()).append("\t");
                                                break;
                                            case FORMULA:
                                                output.append(cell.getCellFormula()).append("\t");
                                                break;
                                            default:
                                                output.append("\t");
                                        }
                                    }
                                    output.append("\n");
                                }

                                exchange.getIn().setBody(output.toString());
                            })
                            .to("file://output?fileName=output.txt");
                }
            });

            camelContext.start();
            Thread.sleep(5000);
            camelContext.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}










//package com.kariutech.apacheCamelPatients;
//
//import org.apache.camel.main.Main;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication
//public class ApacheCamelPatientsApplication {
//
//	public static void main(String[] args) throws Exception {
//		Main main = new Main();
//		main.configure().addRoutesBuilder(new MyRouteBuilder());
//		main.run(args);
//	}
//}
//
//

package com.scoutingthestatline;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * A simple Camel route that triggers from a timer and calls a bean and prints to system out.
 * <p/>
 * Use <tt>@Component</tt> to make Camel auto detect this route when starting.
 */
@Component
public class MySpringBootRouter extends RouteBuilder {
    static Logger LOG = LoggerFactory.getLogger(MySpringBootRouter.class);

    @Override
    public void configure() {
        from("file:src/main/resources/input?noop=true")
        .log("body ${body}")
        .log("Converting ${file:name}")
        .process(exchange -> {
                File file = exchange.getIn().getBody(File.class);
                BufferedReader br = new BufferedReader(new FileReader(file));

                ArrayList al = new ArrayList();

                String line = null;
                int counter = 0;
                while ((line = br.readLine()) != null) {
                    if ("".matches(line.trim())) {
                        al.add(new Integer(counter));
                        counter = 0;
                    } else {
                        counter += Integer.parseInt(line);
                    }
                }


                exchange.getMessage().setBody(al);
        }).sort(body().tokenize(","), new IntegerComparator())
        .log("BODY ${body}")
        .process(exchange -> {
            // Get top three elves
            ArrayList al = exchange.getIn().getBody(ArrayList.class);
            int counter = 0;
            for (int i = 1; i<4; i++) {
                counter += Integer.parseInt((String)al.get(al.size()-i));
            }
            exchange.getMessage().setBody(counter);
        })
        .log("BODY ${body}");  

    }
}

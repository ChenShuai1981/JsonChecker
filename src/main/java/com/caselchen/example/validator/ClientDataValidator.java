package com.caselchen.example.validator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.apache.commons.lang.StringUtils;

import com.caselchen.example.avro.ClientDataVO;

import tech.allegro.schema.json2avro.converter.AvroConversionException;
import tech.allegro.schema.json2avro.converter.JsonAvroConverter;

public class ClientDataValidator {

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.err.println(
                    "usage: java -jar JsonChecker-jar-with-dependencies.jar ${yourJsonFilePath}");
        }
        try (Stream<String> stream = Files.lines(Paths.get(args[0]))) {
            stream.forEach(line -> {
                JsonAvroConverter converter = new JsonAvroConverter();
                if (!StringUtils.isEmpty(line)) {
                    try {
                        converter.convertToSpecificRecord(line.getBytes(),
                                ClientDataVO.class, ClientDataVO.getClassSchema());
                        System.out.println("Passed!");
                    } catch (AvroConversionException e) {
                        e.printStackTrace();
                        System.err.println(
                                String.format("Failed!! Your json < %s > is not conform to schema < %s >",
                                        line, ClientDataVO.getClassSchema().toString()));
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

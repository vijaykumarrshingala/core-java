package com.miit.sep22.java.batch.mapper;

import com.miit.sep22.java.batch.model.TransactionDto;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionMapper<T extends TransactionDto, R extends String> implements Mapper<T, R>{

    public TransactionDto map(String record) {

        TransactionDto result = null;
        //System.out.println("map record "+ record );

        validateRecord(record);

        List<String> rList = Arrays.asList(record.split(","))
                .stream().map(String::strip)
                .collect(Collectors.toList());

        try {
            int txnId = Integer.parseInt(rList.get(0));
            result = TransactionDto.builder()
                    .id(Integer.parseInt(rList.get(0)))
                    .description(rList.get(1))
                    .amount( txnId% 2 == 0? Double.parseDouble(rList.get(0)):Double.parseDouble("-"+rList.get(0)))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(String.format("Invalid content in Record %s", record));
        }

        return result;

    }

    private void validateRecord(String record) {
        if(record == null || record.split(",").length != 3) {
            throw new RuntimeException(String.format("Invalid Record %s", record));
        }

    }
}

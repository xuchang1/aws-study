package com.example.dynamodb;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

public class GetItem {

    public static void main(String[] args) {
        System.out.println(getItem(Constant.getEnhancedClient()));
    }

    public static String getItem(DynamoDbEnhancedClient enhancedClient) {
        DynamoDbTable<Customer> table = enhancedClient
                .table(Constant.TABLE_NAME, TableSchema.fromBean(Customer.class));

        Key key = Key.builder().partitionValue("id146").build();
        Customer result = table.getItem(key);
        table.getItem(r -> r.key(key));

        return "The email value is " + result.getEmail();
    }
}

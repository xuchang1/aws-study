package com.example.dynamodb;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.Iterator;

public class ScanTable {

    public static void main(String[] args) {
        scanTable(Constant.getEnhancedClient());
    }

    public static void scanTable(DynamoDbEnhancedClient enhancedClient) {
        DynamoDbTable<Customer> table = enhancedClient.table(Constant.TABLE_NAME, TableSchema.fromBean(Customer.class));
        Iterator<Customer> result = table.scan().items().iterator();
        while (result.hasNext()) {
            System.out.println(result.next());
        }
    }
}

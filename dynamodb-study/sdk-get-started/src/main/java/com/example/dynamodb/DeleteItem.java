package com.example.dynamodb;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

public class DeleteItem {

    public static void main(String[] args) {
        deleteItem(Constant.getEnhancedClient(), "id146");
    }

    public static void deleteItem(DynamoDbEnhancedClient enhancedClient, String keyVal) {
        Key key = Key.builder().partitionValue(keyVal).build();
        DynamoDbTable<Customer> table = enhancedClient.table(Constant.TABLE_NAME, TableSchema.fromBean(Customer.class));
        Customer customer = table.deleteItem(key);
        System.out.println(customer);
    }
}

package com.example.dynamodb;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

public class ModifyItem {

    public static void main(String[] args) {
        System.out.println(modifyItem(Constant.getEnhancedClient(), "id146", "sred2@noserver.com"));
    }

    public static String modifyItem(DynamoDbEnhancedClient enhancedClient, String keyVal, String email) {
        DynamoDbTable<Customer> table = enhancedClient.table(Constant.TABLE_NAME, TableSchema.fromBean(Customer.class));

        Key key = Key.builder().partitionValue(keyVal).build();
        Customer customer = table.getItem(key);
        customer.setEmail(email);
        table.updateItem(customer);
        return customer.getEmail();
    }
}

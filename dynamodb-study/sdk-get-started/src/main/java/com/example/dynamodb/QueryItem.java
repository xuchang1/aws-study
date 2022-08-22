package com.example.dynamodb;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

import java.util.Iterator;

public class QueryItem {

    public static void main(String[] args) {
        queryItem(Constant.getEnhancedClient());
    }

    public static void queryItem(DynamoDbEnhancedClient enhancedClient) {
        DynamoDbTable<Customer> table = enhancedClient.table(Constant.TABLE_NAME, TableSchema.fromBean(Customer.class));
        QueryConditional queryConditional = QueryConditional.keyEqualTo(Key.builder().partitionValue("id146").build());

        Iterator<Customer> result = table.query(queryConditional).items().iterator();
        while (result.hasNext()) {
            System.out.println(result.next());
        }

    }
}

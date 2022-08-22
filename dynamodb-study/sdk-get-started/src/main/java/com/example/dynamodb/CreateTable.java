package com.example.dynamodb;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;
import software.amazon.awssdk.services.dynamodb.waiters.DynamoDbWaiter;

import java.net.URI;

public class CreateTable {

    public static void main(String[] args) {
        String tableName = Constant.TABLE_NAME;
        String key = Constant.KEY;
        System.out.println("Creating an Amazon DynamoDB table " + tableName + " with a simple primary key: " + key);

        // 本地连接，连接云服务，需要设置 credentialsProvider
        DynamoDbClient ddb = DynamoDbClient.builder()
                .endpointOverride(URI.create("http://localhost:8000"))
                .region(Region.US_EAST_1)
                .credentialsProvider(StaticCredentialsProvider
                        .create(AwsBasicCredentials.create("dummy-key", "dummy-secret")))
                .build();

        String result = createTable(ddb, tableName, key);
        System.out.println("New table is " + result);
        ddb.close();
    }

    public static String createTable(DynamoDbClient ddb, String tableName, String key) {
        // 等待资源
        DynamoDbWaiter dbWaiter = ddb.waiter();

        // 建表请求
        CreateTableRequest request = CreateTableRequest.builder()
                .attributeDefinitions(AttributeDefinition.builder()
                        .attributeName(key).attributeType(ScalarAttributeType.S).build())
                .keySchema(KeySchemaElement.builder()
                        .attributeName(key).keyType(KeyType.HASH).build())
                .provisionedThroughput(ProvisionedThroughput.builder()
                        .readCapacityUnits(10L).writeCapacityUnits(10L).build())
                .tableName(tableName)
                .build();

        CreateTableResponse response = ddb.createTable(request);

        // 描述表请求
        DescribeTableRequest describeTableRequest = DescribeTableRequest.builder()
                .tableName(tableName).build();
        WaiterResponse<DescribeTableResponse> waiterResponse = dbWaiter.waitUntilTableExists(describeTableRequest);
        waiterResponse.matched().response().ifPresent(System.out::println);

        return response.tableDescription().tableName();
    }
}

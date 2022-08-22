package com.example.dynamodb;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.net.URI;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

public class PutItem {

    public static void main(String[] args) {
        DynamoDbClient ddb = DynamoDbClient.builder()
                .endpointOverride(URI.create("http://localhost:8000"))
                .region(Region.US_EAST_1)
                .credentialsProvider(StaticCredentialsProvider
                        .create(AwsBasicCredentials.create("dummy-key", "dummy-secret")))
                .build();

        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(ddb)
                .build();
        putRecord(enhancedClient);
    }

    public static void putRecord(DynamoDbEnhancedClient enhancedClient) {
        DynamoDbTable<Customer> custTable = enhancedClient
                .table(Constant.TABLE_NAME, TableSchema.fromBean(Customer.class));

        // Create an Instant
        LocalDate localDate = LocalDate.parse("2020-04-07");
        LocalDateTime localDateTime = localDate.atStartOfDay();
        Instant instant = localDateTime.toInstant(ZoneOffset.UTC);

        // Populate the Table
        Customer custRecord = new Customer();
        custRecord.setName("Susan red");
        custRecord.setId("id149");
        custRecord.setEmail("");
        custRecord.setRegDate(instant);

        Map<String, String> properties = new HashMap<>();
        properties.put("1", "1");
        properties.put("2", "");
        custRecord.setProperties(properties);

        // Put the customer data into a DynamoDB table
        custTable.putItem(custRecord);
    }
}

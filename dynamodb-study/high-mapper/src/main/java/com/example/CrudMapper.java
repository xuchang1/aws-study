package com.example;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import software.amazon.awssdk.regions.Region;

import java.util.*;

public class CrudMapper {

    public static void main(String[] args) {
        AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration("http://localhost:8000/", Region.US_EAST_1.id()))
                .withCredentials(
                        new AWSStaticCredentialsProvider(new BasicAWSCredentials("dummy-key", "dummy-secret")))
                .build();
        DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
//        deleteTable(amazonDynamoDB);
        saveAndException(dynamoDBMapper);
//        load(dynamoDBMapper);
//        update(dynamoDBMapper);
//        delete(dynamoDBMapper);
//        batchSave(dynamoDBMapper);
//        batchDelete(dynamoDBMapper);
//        query(dynamoDBMapper);
    }

    public static void deleteTable(AmazonDynamoDB amazonDynamoDB) {
        amazonDynamoDB.deleteTable("ProductCatalog");
    }

    public static void save(DynamoDBMapper mapper) {
        CatalogItem catalogItem = new CatalogItem();
        catalogItem.setId(4);
        catalogItem.setTitle("cs");
        catalogItem.setIsbn("lsbn");
        catalogItem.setBookAuthors(new HashSet<>(Arrays.asList("xc", "ravi")));
        catalogItem.setSomeProp("ignore");

        Map<String, String> properties = new HashMap<>();
        properties.put("1", "1");
        properties.put("2", "");
        catalogItem.setProperties(properties);
        mapper.save(catalogItem, DynamoDBMapperConfig.builder()
                .withTypeConverterFactory(new EmptyStringTypeConverterFactory())
                .build());
    }

    public static void load(DynamoDBMapper mapper) {
        CatalogItem catalogItem = mapper.load(CatalogItem.class, 4);
        System.out.println(catalogItem);
    }

    public static void update(DynamoDBMapper mapper) {
        CatalogItem catalogItem = new CatalogItem();
        catalogItem.setId(2);
        catalogItem.setTitle("cs2");
        catalogItem.setBookAuthors(new HashSet<>(Arrays.asList("xc", "ravi", "xu")));
        catalogItem.setSomeProp("ignore2");
        // SaveBehavior 的类型，会判断是否更新未设置的值
        mapper.save(catalogItem, DynamoDBMapperConfig.builder()
                .withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.UPDATE_SKIP_NULL_ATTRIBUTES).build());
    }

    public static void delete(DynamoDBMapper mapper) {
        CatalogItem catalogItem = new CatalogItem();
        catalogItem.setId(1);
        mapper.delete(catalogItem);
    }

    public static void batchSave(DynamoDBMapper mapper) {
        List<CatalogItem> catalogItems = new ArrayList<>();
        for (int i = 1000; i < 10000; i++) {
            CatalogItem catalogItem = new CatalogItem();
            catalogItem.setId(i);
            catalogItem.setTitle("cs");
            catalogItem.setIsbn("123");
            catalogItem.setBookAuthors(new HashSet<>(Arrays.asList("xc", "ravi")));
            catalogItem.setSomeProp("ignore");
            catalogItems.add(catalogItem);
        }

        List<DynamoDBMapper.FailedBatch> failedBatches = mapper.batchSave(catalogItems);
        failedBatches.forEach(System.out::println);
    }

    public static void batchDelete(DynamoDBMapper mapper) {
        CatalogItem catalogItem1 = new CatalogItem();
        catalogItem1.setId(1);
        CatalogItem catalogItem2 = new CatalogItem();
        catalogItem2.setId(2);
        mapper.batchDelete(catalogItem1, catalogItem2);
    }

    public static void query(DynamoDBMapper mapper) {
        Map<String, AttributeValue> map = new HashMap<>();
        map.put(":val1", new AttributeValue().withS("cs"));
        DynamoDBQueryExpression<CatalogItem> queryExpression = new DynamoDBQueryExpression<CatalogItem>()
                .withIndexName("indexTitle")
                .withConsistentRead(false)
                .withKeyConditionExpression("Title = :val1")
                .withExpressionAttributeValues(map);
//        PaginatedQueryList<CatalogItem> catalogItems = mapper.query(CatalogItem.class, queryExpression);
//        for (CatalogItem catalogItem : catalogItems) {
//            System.out.println(catalogItem);
//        }

//        QueryResultPage<CatalogItem> catalogItems = mapper.queryPage(CatalogItem.class, queryExpression);
//        for (CatalogItem catalogItem : catalogItems.getResults()) {
//            System.out.println(catalogItem);
//        }

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withIndexName("indexTitle")
                .withFilterExpression("Title = :val1")
                .withExpressionAttributeValues(map);
//        PaginatedScanList<CatalogItem> catalogItems = mapper.scan(CatalogItem.class, scanExpression);
//        catalogItems.forEach(System.out::println);

        ScanResultPage<CatalogItem> scanResultPage = mapper.scanPage(CatalogItem.class, scanExpression);
        scanResultPage.getResults().forEach(System.out::println);
    }

    public static void saveAndException(DynamoDBMapper mapper) {
        CatalogItem catalogItem = new CatalogItem();
        catalogItem.setId(400001);
        catalogItem.setTitle("cs");
        catalogItem.setIsbn("lsbn");
        catalogItem.setBookAuthors(new HashSet<>(Arrays.asList("xc", "ravi")));
        catalogItem.setSomeProp("ignore");

        Map<String, String> properties = new HashMap<>();
        properties.put("1", "1");
        properties.put("2", "");
        catalogItem.setProperties(properties);
        mapper.save(catalogItem, DynamoDBMapperConfig.builder()
                .withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.UPDATE).build());
    }
}

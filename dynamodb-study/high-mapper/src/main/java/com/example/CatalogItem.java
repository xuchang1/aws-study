package com.example;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

@DynamoDBTable(tableName = "ProductCatalog4")
public class CatalogItem {

    private Integer id;
    private String title;
    private String isbn;
    private Set<String> bookAuthors;
    private String someProp;

    @DynamoDBRangeKey(attributeName = "date2")
    private Date date = new Date();

    private Map<String, String> properties;

    @DynamoDBHashKey(attributeName = "Id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @DynamoDBIndexHashKey(globalSecondaryIndexName = "indexTitle", attributeName = "Title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @DynamoDBAttribute(attributeName = "ISBN")
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @DynamoDBAttribute(attributeName = "Authors")
    public Set<String> getBookAuthors() {
        return bookAuthors;
    }

    public void setBookAuthors(Set<String> bookAuthors) {
        this.bookAuthors = bookAuthors;
    }

    @DynamoDBIgnore
    public String getSomeProp() {
        return someProp;
    }

    public void setSomeProp(String someProp) {
        this.someProp = someProp;
    }

    @Override
    public String toString() {
        return "CatalogItem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", isbn='" + isbn + '\'' +
                ", bookAuthors=" + bookAuthors +
                ", someProp='" + someProp + '\'' +
                ", properties=" + properties +
                ", date=" + date +
                '}';
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
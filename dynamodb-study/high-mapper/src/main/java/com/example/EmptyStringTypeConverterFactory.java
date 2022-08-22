package com.example;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverterFactory;

public class EmptyStringTypeConverterFactory extends DynamoDBTypeConverterFactory {

    private final DynamoDBTypeConverterFactory delegate = DynamoDBTypeConverterFactory.standard();

    @Override
    public <S, T> DynamoDBTypeConverter<S, T> getConverter(Class<S> sourceType, Class<T> targetType) {
        DynamoDBTypeConverter<S, T> delegateConverter = delegate.getConverter(sourceType, targetType);
        if (sourceType.equals(String.class)) {
            return (DynamoDBTypeConverter<S, T>) new EmptyStringDynamoDBTypeConverter<T>((DynamoDBTypeConverter<String,T>)delegateConverter);
        }
        return delegateConverter;
    }

    private static final class EmptyStringDynamoDBTypeConverter<T>
            implements DynamoDBTypeConverter<String, T> {

        private static final String DUMMY_VALUE_FOR_EMPTY_STRINGS = " ";

        private final DynamoDBTypeConverter<String, T> delegate;

        private EmptyStringDynamoDBTypeConverter(
                DynamoDBTypeConverter<String, T> delegate) {
            this.delegate = delegate;
        }

        @Override
        public String convert(T object) {
            String convert = delegate.convert(object);
            if (convert.equals("")) {
                return DUMMY_VALUE_FOR_EMPTY_STRINGS;
            }
            return convert;
        }

        @Override
        public T unconvert(String object) {
            if (object.equals(DUMMY_VALUE_FOR_EMPTY_STRINGS)) {
                return delegate.unconvert("");
            }
            return delegate.unconvert(object);
        }
    }
}
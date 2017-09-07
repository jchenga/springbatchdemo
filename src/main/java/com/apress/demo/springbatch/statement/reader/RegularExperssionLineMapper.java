package com.apress.demo.springbatch.statement.reader;

import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.beans.factory.InitializingBean;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by j1cheng on 2017-08-15.
 */
public class RegularExperssionLineMapper implements LineMapper<Object>, InitializingBean {

    private Map<String, LineTokenizer> tokenizers;
    private Map<String, FieldSetMapper<Object>> mappers;
    private Map<Pattern, LineTokenizer> patternTokenizers;
    private Map<LineTokenizer, FieldSetMapper<Object>> patternMappers;

    @Override
    public Object mapLine(String input, int rowCount) throws Exception {
        LineTokenizer tokenizer = findTokenizer(input);
        FieldSet fields = tokenizer.tokenize(input);
        FieldSetMapper<Object> mapper = patternMappers.get(tokenizer);

        if (mapper != null) {
            return mapper.mapFieldSet(fields);
        }

        throw new ParseException("Unable to parse the input " + input);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        patternTokenizers = new HashMap<Pattern, LineTokenizer>();
        patternMappers = new HashMap<>();

        for(Map.Entry<String, LineTokenizer> entry : tokenizers.entrySet()) {
            Pattern pattern = Pattern.compile(entry.getKey());
            patternTokenizers.put(pattern, entry.getValue());
            patternMappers.put(entry.getValue(), mappers.get(entry.getKey()));
        }
    }

    public void setLineTokenizers(Map<String,LineTokenizer> lineTokenizers) {
        this.tokenizers = lineTokenizers;
    }

    public void setFieldSetMappers(Map<String,FieldSetMapper<Object>> fieldSetMappers) {
        this.mappers = fieldSetMappers;
    }

    private LineTokenizer findTokenizer(String input) {
        LineTokenizer tokenizer = null;

        for(Map.Entry<Pattern, LineTokenizer> entry : patternTokenizers.entrySet()) {
            Matcher matcher = entry.getKey().matcher(input);
            if (matcher.find()) {
                tokenizer = entry.getValue();
                break;
            }
        }

        if (tokenizer != null) {
            return tokenizer;
        } else {
            throw new ParseException("Unable to locate a tokenizer for " + input);
        }
    }
}

package com.apress.demo.springbatch.statement.reader;

import com.apress.demo.springbatch.statement.dao.TickerDao;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.batch.item.*;


import java.net.URI;
import java.util.List;

/**
 * Created by j1cheng on 2017-08-17.
 */
public class UrlReader implements ItemStreamReader<String> {

    private String host;
    private String path;
    private int curPage = -1;
    private int pageSize = 200;
    private TickerDao tickerDao;

    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        HttpClient client = HttpClientBuilder.create().build();
        String buildQueryString = buildQueryString();

        if (buildQueryString == null)
            return null;

        URI uri = new URI("http", host, path, buildQueryString, null);

        HttpGet get = new HttpGet(uri);

        HttpResponse response = client.execute(get);

        HttpEntity entity = response.getEntity();

        String stockPrices = IOUtils.toString(entity.getContent());
        stockPrices = StringUtils.strip(stockPrices);

        if (stockPrices != null && stockPrices.length() > 0)
            return stockPrices;
        else
            return null;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        if (executionContext.containsKey("stepp2.tickers.page"))
            curPage = (Integer) executionContext.get("step2.tickers.page");
        else
            executionContext.put("step2.tickers.page", curPage);
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        executionContext.put("step2.tickers.page", curPage);
        curPage++;
    }

    @Override
    public void close() throws ItemStreamException {

    }

    public void setTickerDao(TickerDao tickerDao) {
        this.tickerDao = tickerDao;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    private String buildQueryString() {
        List<String> tickers = tickerDao.getTickersPaged(curPage, pageSize);

        if (tickers == null || tickers.size() == 0)
            return null;

        StringBuilder tickerList = new StringBuilder("s=");

        for(String ticker : tickers) {
            tickerList.append(ticker).append("+");
        }

        tickerList = new StringBuilder(tickerList.substring(0, tickerList.length() - 1));
        return tickerList.append("&f=sl1").toString();
    }
}

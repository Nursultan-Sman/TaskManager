package com.bestprofi.services;

import com.bestprofi.models.Log;
import com.bestprofi.models.Task;
import com.bestprofi.repositories.LogRepository;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ClientService {
    @Autowired
    private LogRepository logRepository;

    private HttpClient httpClient = HttpClientBuilder.create().build();

    public void sendPost(Task task) {
        try {
            ResponseHandler<String> handler = new BasicResponseHandler();
            HttpPost request = new HttpPost(task.getUrl());
            StringEntity params = new StringEntity(task.getParameter(), ContentType.APPLICATION_JSON);
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            Date startDate = new Date();
            HttpResponse response = httpClient.execute(request);
            String responseCode = Integer.toString(response.getStatusLine().getStatusCode());
            String responseMessage = EntityUtils.toString(response.getEntity());
            Date endDate = new Date();
            System.out.println(response.getEntity());
            logRepository.save(new Log(startDate, endDate, responseCode, responseMessage, task));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

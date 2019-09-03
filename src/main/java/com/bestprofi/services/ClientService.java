package com.bestprofi.services;

import com.bestprofi.models.Log;
import com.bestprofi.models.Task;
import com.bestprofi.repositories.LogRepository;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ClientService {
    @Autowired
    private LogRepository logRepository;

    HttpClient httpClient = HttpClientBuilder.create().build();

    public void sendPost(Task task) {
        try {
            HttpPost request = new HttpPost(task.getUrl());
            StringEntity params = new StringEntity(task.getParameter(), ContentType.APPLICATION_JSON);
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            Date startDate = new Date();
            HttpResponse response = httpClient.execute(request);
            Date endDate = new Date();
            logRepository.save(new Log(startDate, endDate, Integer.toString(response.getStatusLine().getStatusCode()), task));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

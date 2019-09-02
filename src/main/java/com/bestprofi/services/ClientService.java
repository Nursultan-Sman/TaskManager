package com.bestprofi.services;

import com.bestprofi.models.Task;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    HttpClient httpClient = HttpClientBuilder.create().build();

    public void sendPost(Task task) {
        try {
            HttpPost request = new HttpPost(task.getUrl());
            StringEntity params = new StringEntity(task.getParameter(), ContentType.APPLICATION_JSON);
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

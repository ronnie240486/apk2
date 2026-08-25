package org.androidannotations.api.rest;

import org.springframework.web.client.RestTemplate;

/* JADX INFO: loaded from: classes3.dex */
public interface RestClientSupport {
    RestTemplate getRestTemplate();

    void setRestTemplate(RestTemplate restTemplate);
}

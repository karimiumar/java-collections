package com.umar.apps.webcrawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

public class BoilerplateWebCrawlerV2 {

    public static void main(String[] args) {
        var urls = List.of("https://masterdevskills.com");
        var webcrawler = new BoilerplateWebCrawlerV2();
        webcrawler.crawl(urls);
    }

    private void crawl(List<String> urls) {
        urls.stream().map(this::createURL).forEach(this::saveURL);
    }

    private void saveURL(URL url) {
        try{
            var uuid = UUID.randomUUID().toString();
            var inputStream = url.openConnection().getInputStream();
            Files.copy(inputStream, Paths.get(uuid + ".txt"), StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private URL createURL(String url) {
        try {
            return new URL(url);
        }catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}

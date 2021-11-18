package com.umar.apps.webcrawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

/**
 * Example taken from https://dzone.com/articles/how-to-handle-checked-exception-in-lambda-expressi
 *
 * How to Handle Checked Exceptions With Lambda Expression
 */
public class BoilerplateWebCrawlerV1 {

    public static void main(String[] args) {
        var urls = List.of("https://masterdevskills.com");
        var webcrawler = new BoilerplateWebCrawlerV1();
        webcrawler.crawl(urls);
    }

    private void crawl(List<String> urls) {
        urls.stream().map(url -> {
                    try {
                        return new URL(url);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .forEach(url -> {
                    try {
                        save(url);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    private void save(URL url) throws IOException {
        String uuid = UUID.randomUUID().toString();
        var inputStream = url.openConnection().getInputStream();
        Files.copy(inputStream, Paths.get(uuid + ".txt"), StandardCopyOption.REPLACE_EXISTING);
    }
}

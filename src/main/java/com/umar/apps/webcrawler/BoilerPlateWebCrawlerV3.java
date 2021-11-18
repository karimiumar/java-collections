package com.umar.apps.webcrawler;

import com.umar.apps.util.ThrowingFunction;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

public class BoilerPlateWebCrawlerV3 {

    public static void main(String[] args) {
        var urls = List.of("https://masterdevskills.com");
        var webcrawler = new BoilerPlateWebCrawlerV3();
        webcrawler.crawl(urls);
    }

    private void crawl(List<String> urls) {
        urls.stream().map(ThrowingFunction.uncheckedFunction(URL::new))
                .forEach(this::save);
    }

    private void save(URL url) {
        try {
            var uuid = UUID.randomUUID().toString();
            var inputStream = url.openConnection().getInputStream();
            Files.copy(inputStream, Paths.get(uuid + ".txt"), StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

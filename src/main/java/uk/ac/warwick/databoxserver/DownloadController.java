package uk.ac.warwick.databoxserver;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class DownloadController {

    private final ResourceLoader resourceLoader;

    public DownloadController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @GetMapping("/resources/{fileName}")
    public ResponseEntity<Resource> downloadCSV(@PathVariable String fileName) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:/data/" + fileName);

        //response entity headers
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "http://localhost:3000");

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

}
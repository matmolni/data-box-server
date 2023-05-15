package uk.ac.warwick.databoxserver;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class FilesController {

    private final ResourceLoader resourceLoader;

    public FilesController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    //returns the list of data files stored in the /resources/data directory
    @GetMapping("/files")
    public ResponseEntity<List<String>> listFiles() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:/data/");
        Path path = Paths.get(resource.getURI());

        List<String> filesList = Files.walk(path, 1)
                .filter(Files::isRegularFile)
                .map(path::relativize)
                .map(Path::toString)
                .collect(Collectors.toList());


        //response entity headers
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "http://localhost:3000");

        return ResponseEntity.ok()
                .headers(headers)
                .body(filesList);
    }
}

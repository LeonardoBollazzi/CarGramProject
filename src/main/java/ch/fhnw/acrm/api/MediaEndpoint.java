package ch.fhnw.acrm.api;

import ch.fhnw.acrm.business.service.MediaService;
import ch.fhnw.acrm.data.domain.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.ConstraintViolationException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class MediaEndpoint {
    @Autowired
    private MediaService mediaService;

    @PostMapping(path = "/mediaHandling", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Media> postUser(@RequestBody Media media) {
        try {
            media = mediaService.saveMedia(media);
        } catch (ConstraintViolationException e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getConstraintViolations().iterator().next().getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{customerId}")
                .buildAndExpand(media.getId()).toUri();

        return ResponseEntity.created(location).body(media);
    }

    @GetMapping(path = "/mediaHandling", produces = "application/json")
    public List<Media> getCustomers() {
        return mediaService.getAllMedia();
    }
}

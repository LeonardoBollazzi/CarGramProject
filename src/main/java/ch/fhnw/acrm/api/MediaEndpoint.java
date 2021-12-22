package ch.fhnw.acrm.api;

import ch.fhnw.acrm.business.service.AgentService;
import ch.fhnw.acrm.data.domain.Agent;
import ch.fhnw.acrm.business.service.MediaService;
import ch.fhnw.acrm.data.domain.Customer;
import ch.fhnw.acrm.data.domain.Media;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
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
    @Autowired
    private AgentService agentService;

    @PostMapping(path = "/mediaHandling", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Media> postMedia(@RequestBody String str) {
        Media media;
        try {
            Agent agent = agentService.getCurrentAgent();
            JSONObject json = new JSONObject(str);
            //json.put("agentID", agent);
            ObjectMapper mapper = new ObjectMapper();
            media = mapper.readValue(json.toString(), Media.class);
            media.setAgent(agent);
            media = mediaService.saveMedia(media);
        } catch (ConstraintViolationException e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getConstraintViolations().iterator().next().getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{mediaID}")
                .buildAndExpand(media.getId()).toUri();

        return ResponseEntity.created(location).body(media);
    }

    @GetMapping(path = "/allMediaHandling", produces = "application/json")
    public List<Media> getMedia() {
        return mediaService.getAllMedia();
    }

    @GetMapping(path = "/mediaHandling", produces = "application/json")
    public List<Media> getAgentMedia() {
        Agent agent = agentService.getCurrentAgent();
        Long agentId = agent.getId();
        return mediaService.getAgentMedia(agentId);
    }
}

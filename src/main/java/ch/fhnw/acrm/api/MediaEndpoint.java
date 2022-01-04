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

    // Create new Media with Json as input
    @PostMapping(path = "/mediaHandling", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Media> postMedia(@RequestBody String str) {
        Media media;
        try {
            Agent agent = agentService.getCurrentAgent();
            JSONObject json = new JSONObject(str);
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

    // Returns all Media in the system / No Input / Json as Output
    @GetMapping(path = "/allMediaHandling", produces = "application/json")
    public List<Media> getMedia() {
        return mediaService.getAllMedia();
    }

    // Returns all Media from current User (Must be logged in) / No Input / Json as Output
    @GetMapping(path = "/mediaHandling", produces = "application/json")
    public List<Media> getAgentMedia() {
        Agent agent = agentService.getCurrentAgent();
        Long agentId = agent.getId();
        return mediaService.getAgentMedia(agentId);
    }

    // Returns all Media from a specific user / User name as input / Json as Output
    @GetMapping(path = "/mediaHandling/{agentName}", produces = "application/json")
    public ResponseEntity<List<Media>> getNameMedia(@PathVariable(value = "agentName") String agentName) {
        List<Media> media = null;
        try {
            media = mediaService.getNameMedia(agentName);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.ok(media);
    }

    // Returns the likes from a specific Media / Media ID as input / long as Output
    @GetMapping(path = "/likeMedia/{mediaID}", produces = "application/json")
    public ResponseEntity<Long> getMediaLikes(@PathVariable(value = "mediaID") String mediaID) {
        long likesCount;
        try {
            Media media = mediaService.getMediaByID(Long.valueOf(mediaID));
            likesCount = media.getLikes().size();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.ok(likesCount);
    }

    @GetMapping(path = "/getMediaFollow", produces = "application/json")
    public List<Media> getMediaFollow() {
        Agent agent = agentService.getCurrentAgent();
        return mediaService.getMediaFollows(agent);
    }

    // likes or unlikes a specific Media (Must be logged in) / media ID as Input / json as Output
    @PutMapping(path = "/likeMedia/{mediaID}", produces = "application/json")
    public ResponseEntity<Media> putLike(@PathVariable(value = "mediaID") String mediaID) {
        Media media;
        try {
            Agent agent = agentService.getCurrentAgent();
            media = mediaService.getMediaByID(Long.valueOf(mediaID));
            mediaService.putMediaLike(media, agent);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        }
        return ResponseEntity.accepted().body(media);
    }




    //////////////////////////////////////////////////////////////
    // TEST!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    @PostMapping(path = "/testPost/{userName}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Media> test(@RequestBody String str, @PathVariable(value = "userName") String userName) {
        Media media;
        try {
            Agent agent = agentService.getSpecificAgent(userName);
            JSONObject json = new JSONObject(str);
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
}

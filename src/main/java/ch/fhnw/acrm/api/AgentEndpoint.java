package ch.fhnw.acrm.api;

import ch.fhnw.acrm.data.domain.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ch.fhnw.acrm.business.service.AgentService;
import ch.fhnw.acrm.data.domain.Agent;

import javax.validation.ConstraintViolationException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class AgentEndpoint {
    @Autowired
    private AgentService agentService;

    @PostMapping(path = "/registration", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Agent> postAgent(@RequestBody Agent user) {
        try {
            agentService.saveAgent(user);
        } catch (ConstraintViolationException e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getConstraintViolations().iterator().next().getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{customerId}")
                .buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(location).body(user);
    }

    @GetMapping(path = "/registration", produces = "application/json")
    public Agent getCurrentAgent() {
        return agentService.getCurrentAgent();
    }

    @GetMapping(path = "/registration/{agentName}", produces = "application/json")
    public ResponseEntity<Agent> getNameMedia(@PathVariable(value = "agentName") String agentName) {
        Agent agent = null;
        try {
            agent = agentService.getSpecificAgent(agentName);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.ok(agent);
    }
}

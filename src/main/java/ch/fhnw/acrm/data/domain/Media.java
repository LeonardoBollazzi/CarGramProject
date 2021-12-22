package ch.fhnw.acrm.data.domain;

import javax.persistence.*;
import java.net.URL;
import java.util.Set;

@Table(name = "media")
@Entity
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne( optional = false)
    @JoinColumn(name = "agentID", nullable = true)
    private Agent agent;
    @Column(name = "url", nullable = false)
    private URL url;

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
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
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private Agent agent;
    @Column(name = "url", nullable = false)
    private URL url;

    public Agent getUser() {
        return agent;
    }

    public void setUser(Agent user) {
        this.agent = user;
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
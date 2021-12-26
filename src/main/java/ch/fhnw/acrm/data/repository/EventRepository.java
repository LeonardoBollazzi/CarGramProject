package ch.fhnw.acrm.data.repository;

import ch.fhnw.acrm.data.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.print.DocFlavor;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    Event findByIdEquals(Long id);




    @Override
    List<Event> findAll();

    List<Event> findByAgent_IdEquals(Long id);

    List<Event> findByAgent_NameEquals(String name);


}
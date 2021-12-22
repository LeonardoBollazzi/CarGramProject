package ch.fhnw.acrm.data.repository;

import ch.fhnw.acrm.data.domain.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.print.DocFlavor;
import java.util.List;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {
    @Override
    List<Media> findAll();

    List<Media> findByAgent_IdEquals(Long id);


}
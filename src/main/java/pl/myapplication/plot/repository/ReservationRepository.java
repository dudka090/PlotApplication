package pl.myapplication.plot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.myapplication.plot.model.Reservation;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Reservation save(Reservation reservation);

    void deleteById(Long id);

    boolean existsById(long id);

    Optional<Reservation> findById(Long id);

    Optional<List<Reservation>>findAllByUser_LoginAndStartDateGreaterThanOrderByStartDateAsc(String login, LocalDate now);

    Optional<List<Reservation>> findAllByEndDateLessThanOrderByEndDateDesc(LocalDate now);

    @Query("select r from Reservation r where (r.startDate<?1 and r.endDate>?2) or (r.startDate<?2 and r.endDate>?2) or (r.startDate<?1 and r.endDate>?1) or (r.startDate>=?1 and r.endDate<=?2)")
    Optional<List<Reservation>> checkAvailability(LocalDate startDate, LocalDate endDate);







}

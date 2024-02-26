package ru.practicum.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.model.entity.Request;
import ru.practicum.utils.enums.StatusRequest;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Long> {

    Optional<List<Request>> findAllByRequester(Long userId);

    Optional<Request> findByRequesterAndEvent(Long userId, Long eventId);

    Optional<Integer> countAllByEventAndStatus(Long eventId, StatusRequest statusRequest);

    Optional<List<Request>> findAllByEvent(Long eventId);

    boolean existsRequestByIdInAndStatus(List<Long> ids, StatusRequest statusRequest);

    Optional<List<Request>> findAllByIdIn(List<Long> ids);

    @Modifying
    @Query("update Request r set r.status = ?2 where r.id in (?1)")
    void updateStatusByIds(List<Long> ids, StatusRequest status);

}

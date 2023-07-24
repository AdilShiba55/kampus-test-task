package com.example.kampustesttask.repository;

import com.example.kampustesttask.dto.ReminderSearchDTO;
import com.example.kampustesttask.entity.Reminder;
import com.example.kampustesttask.util.UtMap;
import com.example.kampustesttask.util.UtPagination;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReminderSearchRepositoryImpl implements ReminderSearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Map<String, Object> search(ReminderSearchDTO filter, Long userId) {
        int pageNum = filter.getPageNum();
        int pageSize = filter.getPageSize();
        int firstResultNum = UtPagination.getPagination(pageNum, pageSize);

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Reminder> criteriaQuery = criteriaBuilder.createQuery(Reminder.class);
        Root<Reminder> reminder = criteriaQuery.from(Reminder.class);

        List<Predicate> predicates = getPredicates(filter, criteriaBuilder, reminder, userId);
        Order order = getOrder(filter, criteriaBuilder, reminder);

        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        criteriaQuery.orderBy(order);

        List<Reminder> reminders = entityManager
                .createQuery(criteriaQuery)
                .setFirstResult(firstResultNum)
                .setMaxResults(pageSize)
                .getResultList();

        Long totalCount = this.getTotalCount(criteriaBuilder, reminder, userId);

        return UtMap.toMap(
                "reminders", reminders,
                "totalCount", totalCount
        );
    }

    private List<Predicate> getPredicates(
            ReminderSearchDTO filter, CriteriaBuilder criteriaBuilder, Root<Reminder> reminder, Long userId
    ) {
        List<Predicate> predicates = new ArrayList<>();
        // условие на пользователя
        predicates.add(criteriaBuilder.equal(reminder.get("user"), userId));
        // условие на заголовок
        if(filter.isTitlePresent()) {
            predicates.add(criteriaBuilder.like(reminder.get("title"), "%" + filter.getTitle() + "%"));
        }
        // условие на описание
        if(filter.isDescriptionPresent()) {
            predicates.add(criteriaBuilder.like(reminder.get("description"), "%" + filter.getDescription() + "%"));
        }
        return predicates;
    }

    private Order getOrder(ReminderSearchDTO filter, CriteriaBuilder criteriaBuilder, Root<Reminder> reminder) {
        if(filter.isTitleAscPagination()) {
            return criteriaBuilder.asc(reminder.get("title"));
        } else if(filter.isTitleDescPagination()) {
            return criteriaBuilder.desc(reminder.get("title"));
        } else if(filter.isDtRemindAscPagination()) {
            return criteriaBuilder.asc(reminder.get("dtRemind"));
        } else {
            return criteriaBuilder.desc(reminder.get("dtRemind"));
        }
    }

    private Long getTotalCount(CriteriaBuilder criteriaBuilder, Root<Reminder> reminder, Long userId) {
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        criteriaQuery = criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(Reminder.class)));
        Predicate predicate = criteriaBuilder.equal(reminder.get("user"), userId);
        criteriaQuery.where(predicate);
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }
}

package com.example.final_project_3.service.user;




import com.example.final_project_3.entity.BaseUser;
import com.example.final_project_3.repository.user.BaseUserRepository;
import com.example.final_project_3.service.dto.UserSearch;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;


@RequiredArgsConstructor
@Transactional(readOnly = true)
public abstract class BaseUserServiceImpl<T extends BaseUser,R extends BaseUserRepository<T>> implements BaseUserService<T> {

    protected final R repository;

    @Override
    public T changePassword(String email, String newPassword) {
        T user = repository.findByEmail(email).
                orElseThrow(() -> new NullPointerException("userName or password is wrong"));
        user.setPassword(newPassword);
        return repository.save(user);
    }

    @Override
    public boolean existByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public T logIn(String email, String password) {
        T user = repository.findByEmail(email).
                orElseThrow(() -> new NullPointerException("userName or password is wrong"));

        if (password.equals(user.getPassword())){
            return user;
        }
        throw new NoResultException("userName or password is wrong");

    }

    @Override
    public T findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(() -> new NullPointerException("this user not found"));
    }

    @Override
    public Collection<T> loadAll() {
        return repository.findAll();
    }
    @Override
    public List<T> searchUsers(UserSearch searchCriteria) {
            return repository.findAll(getUserSpecification(searchCriteria));
        }

        private Specification<T> getUserSpecification(UserSearch searchCriteria) {
            return (root, query, cb) -> {
                Predicate predicate = cb.conjunction();


                if (searchCriteria.getRole() != null) {
                    predicate = cb.and(predicate, cb.equal(root.get("role"), searchCriteria.getRole()));
                }

                if (searchCriteria.getFirstName() != null && !searchCriteria.getFirstName().isEmpty()) {
                    predicate = cb.and(predicate, cb.like(root.get("firstName"), "%" + searchCriteria.getFirstName() + "%"));
                }
                if (searchCriteria.getLastName() != null && !searchCriteria.getLastName().isEmpty()) {
                    predicate = cb.and(predicate, cb.like(root.get("lastName"), "%" + searchCriteria.getLastName() + "%"));
                }

                if (searchCriteria.getEmail() != null && !searchCriteria.getEmail().isEmpty()) {
                    predicate = cb.and(predicate, cb.like(root.get("email"), "%" +searchCriteria.getEmail()+ "%"));
                }

                if (searchCriteria.getExpertiseField() != null && !searchCriteria.getExpertiseField().isEmpty()) {
                    predicate = cb.and(predicate, cb.equal(root.get("subServices").get("subServiceName"), searchCriteria.getExpertiseField()));
                }
//                if (searchCriteria.getExpertiseField() != null) {
//                    Join<T, SubService> expertJoin = root.join("experts", JoinType.INNER);
//                    predicate = cb.and(predicate, cb.equal(expertJoin.get("subServiceName"), searchCriteria.getExpertiseField()));
//                }

                if (searchCriteria.getMinRating() != 0) {
                    predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("score"), searchCriteria.getMinRating()));
                }

                if (searchCriteria.getMaxRating() != 0) {
                    predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("score"), searchCriteria.getMaxRating()));
                    query.orderBy(cb.desc(root.get("score")));
                }


                return predicate;
            };
        }
    @Override
    public T findById(Integer id){
       return repository.findById(id)
               .orElseThrow(() -> new NullPointerException("this id not found"));
    }

    }











//        repository.findAll((root, query, criteriaBuilder) -> {
//            addFirstNamePredicate(predicates, root, criteriaBuilder, searchCriteria.getName());
//            addEmailPredicate(predicates, root, criteriaBuilder, searchCriteria.getEmail());
//            addACDExpertScorePredicate(predicates, root, criteriaBuilder, searchCriteria.getMinRating());
//            expertiseFieldPredicate(predicates, root, criteriaBuilder, searchCriteria.getExpertiseField());
//            addRollPredicate(predicates, root, criteriaBuilder, searchCriteria.getRole());
//            query.where(predicates.toArray(new Predicate[0]));
//
//
//            return criteriaBuilder.createQuery(query)
//        });
//
//    }
//    public abstract Class<T> getEntityClass();
//    public List<Customer> search(CustomerSearch search) {
//
////        select c from Customer c
//
//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//
//        CriteriaQuery<Customer> query = criteriaBuilder.createQuery(
//                getEntityClass()
//        );
//
//        Root<Customer> customerRoot = query.from(getEntityClass());
//
//        List<Predicate> predicates = new ArrayList<>();
//
//        addFirstNamePredicate(predicates, customerRoot, criteriaBuilder, search.getFirstName());
//        addEmailPredicate(predicates, customerRoot, criteriaBuilder, search.getLastName());
//        addMobileNumberPredicate(predicates, customerRoot, criteriaBuilder, search.getMobileNumber());
//        addCreateDatePredicate(predicates, customerRoot, criteriaBuilder, search.getFromDate(), search.getToDate());
//        addIsActivePredicate(predicates, customerRoot, criteriaBuilder, search.getIsActive());
//        addCodePredicate(predicates, customerRoot, criteriaBuilder, search.getCode());
//
//        if (predicates.size() > 0) {
//            query.where(
//                    predicates.toArray(
//                            new Predicate[0]
//                    )
//            );
//        }
//
//        return entityManager.createQuery(
//                query
//        ).getResultList();
//    }
//
//    private void addFirstNamePredicate(List<Predicate> predicates, Root<T> root,
//                                       CriteriaBuilder criteriaBuilder, String firstName) {
//        if (StringUtils.isNotBlank(firstName)) {
////            c.firstName like '%:firstName%'
//            predicates.add(
//                    criteriaBuilder.like(
//                            root.get("firstName"),
//                            "%" + firstName + "%"
//                    )
//            );
//        }
//    }
//
//    private void addEmailPredicate(List<Predicate> predicates, Root<T> root,
//                                      CriteriaBuilder criteriaBuilder, String email) {
//        if (StringUtils.isNotBlank(email)) {
////            c.lastName like '%:lastName%'
//            predicates.add(
//                    criteriaBuilder.like(
//                            root.get("email"),
//                            "%" + email + "%"
//                    )
//            );
//        }
//    }
//
//    private void addACDExpertScorePredicate(List<Predicate> predicates, Root<T> root,
//                                          CriteriaBuilder criteriaBuilder, int score) {
//        if (score != 0) {
////            c.mobileNumber like '%:mobileNumber%'
//            predicates.add(
//                    criteriaBuilder.greaterThanOrEqualTo(root.get("score"),score)
//            );
//        }
//    }
//
////    private void addCreateDatePredicate(List<Predicate> predicates, Root<Customer> root,
////                                        CriteriaBuilder criteriaBuilder, ZonedDateTime fromDate,
////                                        ZonedDateTime toDate) {
////
////        /*predicates.add(
////                criteriaBuilder.between(
////                        root.get("createDate"), fromDate, toDate
////                )
////        );*/
////
////        if (fromDate != null) {
////            predicates.add(
////                    criteriaBuilder.greaterThanOrEqualTo(
////                            root.get("createDate"),
////                            fromDate
////                    )
////            );
////        }
////        if (toDate != null) {
////            predicates.add(
////                    criteriaBuilder.lessThanOrEqualTo(
////                            root.get("createDate"),
////                            toDate
////                    )
////            );
////        }
////    }
//
//    private void addRollPredicate(List<Predicate> predicates, Root<T> root,
//                                      CriteriaBuilder criteriaBuilder, Roll roll) {
//        if (roll != null) {
//            predicates.add(
//                    criteriaBuilder.equal(root.get("roll"),roll)
//
//
//            );
//        }
//    }
//
//    private void expertiseFieldPredicate(List<Predicate> predicates, Root<T> root,
//                                  CriteriaBuilder criteriaBuilder, String expertiseField) {
//        if (StringUtils.isNotBlank(expertiseField)) {
////            c.code like '%:code%'
//            predicates.add(
//                    criteriaBuilder.like(
//                            root.get("expertiseField"),
//                            "%" + expertiseField + "%"
//                    )
//            );
//        }
//    }


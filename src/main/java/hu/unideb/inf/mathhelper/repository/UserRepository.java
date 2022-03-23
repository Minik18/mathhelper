package hu.unideb.inf.mathhelper.repository;

import hu.unideb.inf.mathhelper.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends CrudRepository<User, Long> {
    @Transactional
    @Modifying
    @Query("delete from User ")
    void deleteAll();
}

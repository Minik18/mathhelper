package hu.unideb.inf.mathhelper.repository;

import hu.unideb.inf.mathhelper.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {
}

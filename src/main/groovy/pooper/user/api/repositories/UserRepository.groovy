package pooper.user.api.repositories

import io.micronaut.data.repository.CrudRepository
import pooper.user.api.domain.User

interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmailAddress(String emailAddress)
}

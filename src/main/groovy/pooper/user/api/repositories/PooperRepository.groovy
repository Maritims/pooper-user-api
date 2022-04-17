package pooper.user.api.repositories

import io.micronaut.data.annotation.Repository

@Repository(value = "pooper")
interface PooperRepository extends UserRepository {
}

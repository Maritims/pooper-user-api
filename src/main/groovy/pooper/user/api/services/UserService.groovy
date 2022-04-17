package pooper.user.api.services

import io.micronaut.cache.annotation.CacheConfig
import io.micronaut.cache.annotation.Cacheable
import jakarta.inject.Singleton
import pooper.user.api.dtos.UserCreate
import pooper.user.api.repositories.PooperRepository
import pooper.user.api.domain.User
import pooper.user.api.repositories.UserRepository

@Singleton
@CacheConfig('users')
class UserService {
    Map<String, UserRepository> repositories = [:]

    UserService(PooperRepository pooperRepository) {
        repositories.put('pooper', pooperRepository)
    }

    @Cacheable
    List<User> findAll() {
        repositories.collectMany {it.value.findAll().toList() }
    }

    @Cacheable
    List<User> findByTenantId(String tenantId) {
        []
    }

    @Cacheable
    User findByEmailAddress(String emailAddress) {
        findAll().find { it.emailAddress == emailAddress }
    }

    @Cacheable
    User findById(Long id) {
        findAll().find { it.id == id }
    }

    User create(UserCreate userCreate) {
        repositories.pooper.save(new User(
                firstName: userCreate.firstName,
                lastName: userCreate.lastName,
                emailAddress: userCreate.emailAddress,
                passwordHash: 'foo', isDisabled: false
        ))
    }

    User update(Long id, UserCreate userCreate) {
        def user = findById(id)
        if(!user) return null

        user.emailAddress = userCreate.emailAddress
        user.firstName = userCreate.firstName
        user.lastName = userCreate.lastName
        user.homeLongitude = userCreate.homeLongitude
        user.homeLatitude = userCreate.homeLatitude

        repositories.pooper.update(user)
    }

    void delete(Long id) {
        repositories.pooper.deleteById(id)
    }
}

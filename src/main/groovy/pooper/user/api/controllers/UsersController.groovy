package pooper.user.api.controllers

import io.micronaut.cache.annotation.CacheInvalidate
import io.micronaut.cache.annotation.CachePut
import io.micronaut.cache.annotation.Cacheable
import io.micronaut.http.annotation.*
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import pooper.user.api.dtos.UserCreate
import pooper.user.api.dtos.UserRead
import pooper.user.api.services.UserService

import java.security.Principal

@Controller('/users')
@Tag(name = 'Users')
@Secured(SecurityRule.IS_AUTHENTICATED)
@SecurityRequirement(name = 'bearerAuth')
class UsersController {

    final UserService userService

    UsersController(UserService userService) {
        this.userService = userService
    }

    @Cacheable(parameters = "principal")
    @Get('/me')
    UserRead getMe(Principal principal) {
        def user = userService.findByEmailAddress(principal.name)
        new UserRead(user)
    }

    @Cacheable
    @Get('/')
    List<UserRead> getAll() {
        userService.findAll().collect { new UserRead(it) }
    }

    @CachePut
    @Post('/')
    UserRead create(@Body UserCreate userCreate) {
        def user = userService.create(userCreate)
        new UserRead(user)
    }

    @CachePut
    @Patch('/')
    UserRead update(Long id, @Body UserCreate userCreate) {
        def user = userService.update(id, userCreate)
        new UserRead(user)
    }

    @CachePut
    @Patch('/theme/{theme}')
    def setTheme(Principal principal, String theme) {
        def user = repositories.pooper.findByEmailAddress(principal.name).orElse(null)
        if(!user) return null

        user.colorTheme = theme
        user = repositories.pooper.update(user)
        new UserRead(user)
    }

    @CacheInvalidate
    @Delete('/{id}')
    void delete(Long id) {
        userService.delete(id)
    }
}

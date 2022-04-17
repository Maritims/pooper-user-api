package pooper.user.api.dtos

import io.micronaut.core.annotation.Introspected
import pooper.user.api.domain.User

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import java.time.Instant

abstract class UserBase {
    @NotNull
    @NotBlank
    String firstName

    @NotNull
    @NotBlank
    String lastName

    @NotNull
    @NotBlank
    @Email
    String emailAddress

    Double homeLongitude
    Double homeLatitude
    String colorTheme
}

class UserCreate extends UserBase {
    @NotNull
    @NotBlank
    String password

    @NotNull
    @NotBlank
    String passwordRepeated
}

@Introspected
class UserRead extends UserBase {
    final Long id
    final Boolean isDisabled
    final Instant created
    final Instant updated

    UserRead(User user) {
        id = user.id
        isDisabled = user.isDisabled
        created = user.created
        updated = user.updated
        firstName = user.firstName
        lastName = user.lastName
        emailAddress = user.emailAddress
        homeLongitude = user.homeLongitude
        homeLatitude = user.homeLatitude
        colorTheme = user.colorTheme
    }
}
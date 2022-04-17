package pooper.user.api.domain

import io.micronaut.core.annotation.NonNull
import io.micronaut.data.annotation.DateCreated
import io.micronaut.data.annotation.DateUpdated

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import java.time.Instant

@Entity(name = "user")
class User {
    @Id
    @GeneratedValue
    @NonNull
    Long id

    @NonNull
    @NotBlank
    String firstName

    @NonNull
    @NotBlank
    String lastName

    @NonNull
    @NotBlank
    String emailAddress

    Double homeLongitude
    Double homeLatitude
    String colorTheme

    @NonNull
    @NotBlank
    String passwordHash

    String passwordResetToken
    Boolean isDisabled

    @DateCreated
    @NonNull
    @NotNull
    Instant created

    @DateUpdated
    @NonNull
    @NotNull
    Instant updated
}

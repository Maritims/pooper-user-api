package pooper.user.api

import io.micronaut.core.annotation.Nullable
import io.micronaut.http.HttpRequest
import io.micronaut.security.authentication.AuthenticationProvider
import io.micronaut.security.authentication.AuthenticationRequest
import io.micronaut.security.authentication.AuthenticationResponse
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.reactivestreams.Publisher
import pooper.user.api.services.UserService
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink

@Singleton
class UserAuthenticationProvider implements AuthenticationProvider {

    @Inject
    UserService userService

    @Override
    Publisher<AuthenticationResponse> authenticate(@Nullable HttpRequest<?> httpRequest, AuthenticationRequest<?, ?> authenticationRequest) {
        Flux.create(emitter -> {
            def user = userService.findByEmailAddress((String) authenticationRequest.identity)
            if(user) {
                emitter.next(AuthenticationResponse.success(((String) authenticationRequest.identity)))
                emitter.complete()
            } else {
                emitter.error(AuthenticationResponse.exception('Invalid credentials'))
            }
        }, FluxSink.OverflowStrategy.ERROR)
    }
}

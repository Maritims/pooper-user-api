package pooper.user.api

import groovy.transform.CompileStatic
import io.micronaut.openapi.annotation.OpenAPISecurity
import io.micronaut.runtime.Micronaut
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.security.*
import io.swagger.v3.oas.annotations.tags.Tag

@CompileStatic
@SecurityScheme(name = "bearerAuth",
        type = SecuritySchemeType.OAUTH2,
        scheme = "bearer",
        bearerFormat = "jwt",
        flows = @OAuthFlows(
                password = @OAuthFlow(
                        authorizationUrl = '/login',
                        tokenUrl = '/login',
                        refreshUrl = '',
                        scopes = @OAuthScope(name = 'bearerAuth', description = 'Pooper Auth')
                )
        )
)
@OpenAPIDefinition(
        info = @Info(
                title = "Pooper Auth API",
                version = "0.1",
                contact = @Contact(
                        name = "Pooper Auth API",
                        url = "https://github.com/Maritims",
                        email = "maritim@gmail.com"
                )
        ),
        security = [
            @SecurityRequirement(name = 'bearerAuth')
        ]
)
@OpenAPISecurity(tags = @Tag(name = 'Security'), security = @SecurityRequirement(name = 'bearerAuth'))
class Application {
    static void main(String[] args) {
        Micronaut.run(Application, args)
    }
}

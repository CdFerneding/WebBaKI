package de.thb.webbaki.controller.form;

import de.thb.webbaki.enums.*;
import de.thb.webbaki.security.passwordValidation.PasswordMatches;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@PasswordMatches
public class UserRegisterFormModel {

    //SEKTOREN
    private SectorFinance sectorFinance;
    private SectorGesundheit sectorGesundheit;
    private SectorInfandTel sectorInfandTel;
    private SectorMedandCult sectorMedandCult;
    private SectorNutriton sectorNutriton;
    private SectorTransport sectorTransport;
    private SectorState sectorState;
    private SectorEnergie sectorEnergie;
    private SectorWasser sectorWasser;

    @NotNull(message = "LastName not null")
    @NotEmpty(message = "Nachname darf nicht leer sein")
    private String lastname;

    @NotNull(message = "firstName not null")
    @NotEmpty(message = "Vorname darf nicht leer sein")
    private String firstname;

    @NotNull(message = "branche not null")
    @NotEmpty(message = "Branche darf nicht leer sein")
    private String branche;

    @NotNull(message = "company not null")
    @NotEmpty(message = "Company darf nicht leer sein")
    private String company;

    @NotNull(message = "password not null")
    @NotEmpty(message = "Passwort darf nicht leer sein")
    private String password;
    private String confirmPassword;

    @NotNull(message = "email not null")
    @NotEmpty(message = "Email darf nicht leer sein")
    @Email
    private String email;

    @NotNull(message = "username not null")
    @NotEmpty(message = "Username darf nicht leer sein")
    private String username;


}

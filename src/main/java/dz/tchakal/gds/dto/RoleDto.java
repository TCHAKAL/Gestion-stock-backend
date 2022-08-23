package dz.tchakal.gds.dto;

import dz.tchakal.gds.model.Role;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {

    private Integer id;

    private String roleName;

    private UtilisateurDto utilisateur;

    public static RoleDto fromEntity(Role role) {
        if (role == null) {
            //TODO throw an exception
            return null;
        }
        return RoleDto.builder()
                .id(role.getId())
                .roleName(role.getRoleName())
                .utilisateur(UtilisateurDto.fromEntity(role.getUtilisateur()))
                .build();
    }
    public static Role toEntity(RoleDto roleDto) {
        if (roleDto == null) {
            //TODO throw an exception
            return null;
        }
        return Role.builder()
                .id(roleDto.getId())
                .roleName(roleDto.getRoleName())
                .utilisateur(UtilisateurDto.toEntity(roleDto.getUtilisateur()))
                .build();
    }
}

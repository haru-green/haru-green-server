package harugreen.harugreenserver.dto.user;

import harugreen.harugreenserver.domain.User;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto {

    @NotNull
    private String nickname;
    @NotNull
    private Integer level;
    @NotNull
    private String email;
    private String refreshToken;

    public User toEntity() {
        return User.builder()
                .nickname(this.nickname)
                .level(1)
                .email(this.email)
                .refreshToken(this.refreshToken)
                .build();
    }
}

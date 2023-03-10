package harugreen.harugreenserver.dto.user;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    private String nickname;
    private Integer level;
    private String email;
    private String refreshToken;
    private LocalDateTime answerTime;
}

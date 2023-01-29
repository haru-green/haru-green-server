package harugreen.harugreenserver.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Quiz {

    @Id
    @GeneratedValue
    @Column(name = "quiz_id")
    private Integer id;

    private String title;
    private String content;
    private boolean ox;
}

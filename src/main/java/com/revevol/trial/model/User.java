package com.revevol.trial.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "User", schema = "trial")
@IdClass(UserId.class)
@Data
@NoArgsConstructor
public class User implements Comparable<User>,Serializable{
    @Id
    private String email;
    @Id
    private String fileRef;
    private String name;
    private String surname;

    @Override
    public int compareTo(User o) {
        return this.email.compareTo(o.email);
    }


}

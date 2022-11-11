package de.thb.webbaki.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Entity(name = "branche")
@NoArgsConstructor
public class Branche {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    @ManyToOne
    @JoinColumn(name="sector_id", nullable=false)
    private Sector sectors;

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Branche branche = (Branche) o;
        return Objects.equals(name, branche.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

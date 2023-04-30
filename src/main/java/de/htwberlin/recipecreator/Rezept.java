package de.htwberlin.recipecreator;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Rezept {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 2000)
    private String beschreibung;

    @ElementCollection
    @CollectionTable(
            name = "rezept_zutat",
            joinColumns = @JoinColumn(name = "rezept_id")
    )
    @Column(name = "zutat", nullable = false)
    private List<String> zutaten;

    // Standard-Konstruktor
    public Rezept() {
    }

    // Konstruktor mit Parametern
    public Rezept(String name, String beschreibung, List<String> zutaten) {
        this.name = name;
        this.beschreibung = beschreibung;
        this.zutaten = zutaten;
    }

    // Getter- und Setter-Methoden
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public List<String> getZutaten() {
        return zutaten;
    }

    public void setZutaten(List<String> zutaten) {
        this.zutaten = zutaten;
    }
}
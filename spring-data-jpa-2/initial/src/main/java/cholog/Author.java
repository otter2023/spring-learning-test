package cholog;

import jakarta.persistence.*;


import java.util.Set;

@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Person person;

    @OneToMany(mappedBy="author")
    Set<BookAuthor> books;

    public Author(Person person) {
        this.person = person;
    }

    public Author() {
    }

    public Long getId() {
        return id;
    }

    public Person getPerson() {
        return person;
    }


}

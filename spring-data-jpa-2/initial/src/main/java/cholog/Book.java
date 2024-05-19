package cholog;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    Publisher publisher;

    @OneToMany(mappedBy = "book")
    Set<BookAuthor> authors;

    public Book() {

    }

    public Book(String name, Publisher publisher) {
        this.name = name;
        this.publisher = publisher;
    }
    public Long getId() {
        return id;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public Set<BookAuthor> getAuthors() {
        return authors;
    }
}

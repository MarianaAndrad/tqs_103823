package pt.ua.deti.tqs.TestContainers.Data;

import jakarta.persistence.*;
import lombok.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.Date;
import java.util.List;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "books")
class Book {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private String id;

    private String title;
    private String author;
    private String isbn;
    private String publisher;
    private Date published_date;
    private Double price;
    private String description;
    private String category;


}

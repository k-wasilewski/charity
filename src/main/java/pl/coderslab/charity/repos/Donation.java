package pl.coderslab.charity.repos;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name="donations")
public class Donation {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    @NotNull(message = "Podaj dodatnią ilość")
    private Integer quantity;
    @ManyToMany
    @NotEmpty(message = "Wybierz co chcesz oddać")
    private List<Category> categories;
    @ManyToOne
    @NotNull(message = "Wybierz instytucję")
    private Institution institution;
    @NotBlank(message = "Podaj ulicę")
    private String street;
    @NotBlank(message = "Podaj miasto")
    private String city;
    @NotBlank(message = "Podaj kod pocztowy")
    private String zipCode;
    @NotNull(message = "Podaj datę z przyszłości")
    @Future(message = "Podaj datę z przyszłości")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate pickUpDate;
    @DateTimeFormat(pattern = "H:mm")
    @NotNull(message = "Podaj godzinę")
    private LocalTime pickUpTime;
    private String pickUpComment;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public LocalDate getPickUpDate() {
        return pickUpDate;
    }

    public void setPickUpDate(LocalDate pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    public LocalTime getPickUpTime() {
        return pickUpTime;
    }

    public void setPickUpTime(LocalTime pickUpTime) {
        this.pickUpTime = pickUpTime;
    }

    public String getPickUpComment() {
        return pickUpComment;
    }

    public void setPickUpComment(String pickUpComment) {
        this.pickUpComment = pickUpComment;
    }

    public String toString() {
        return ""+id+quantity+categories+institution+street+city+zipCode+pickUpDate+pickUpTime+pickUpComment;
    }
}

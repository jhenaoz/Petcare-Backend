package com.petcare.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.petcare.domain.util.CustomLocalDateSerializer;
import com.petcare.domain.util.ISO8601LocalDateDeserializer;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A Lost.
 */
@Entity
@Table(name = "LOST")
public class Lost implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    

    @NotNull        
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull        
    @Column(name = "species", nullable = false)
    private String species;

    @NotNull   
    @Min(value = 0)
    @Max(value = 260)    
    @Column(name = "age", nullable = false)
    private Integer age;

    @NotNull      
    @Column(name = "gender", nullable = false)
    private String gender;

    @NotNull        
    @Column(name = "size", nullable = false)
    private String size;
    
    @Column(name = "description")
    private String description;

    @NotNull    
    @Column(name = "phone", nullable = false)
    private Integer phone;

    @NotNull        
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    @Column(name = "lost_date", nullable = false)
    private LocalDate lostDate;

    @NotNull        
    @Lob
    @Column(name = "image", nullable = false, length=100000)
    private byte[] image;

    @ManyToOne
    private User have;

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

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public LocalDate getLostDate() {
        return lostDate;
    }

    public void setLostDate(LocalDate lostDate) {
        this.lostDate = lostDate;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public User getHave() {
        return have;
    }

    public void setHave(User user) {
        this.have = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Lost lost = (Lost) o;

        if ( ! Objects.equals(id, lost.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Lost{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", species='" + species + "'" +
                ", age='" + age + "'" +
                ", gender='" + gender + "'" +
                ", size='" + size + "'" +
                ", description='" + description + "'" +
                ", phone='" + phone + "'" +
                ", lostDate='" + lostDate + "'" +
                ", image='" + image + "'" +
                '}';
    }
}

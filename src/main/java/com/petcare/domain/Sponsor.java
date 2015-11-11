package com.petcare.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A Sponsor.
 */
@Entity
@Table(name = "SPONSOR")
public class Sponsor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    

    @NotNull        
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull        
    @Column(name = "species", nullable = false)
    private String species;

    @Min(value = 0)
    @Max(value = 260)        
    @Column(name = "age")
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

        Sponsor sponsor = (Sponsor) o;

        if ( ! Objects.equals(id, sponsor.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Sponsor{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", species='" + species + "'" +
                ", age='" + age + "'" +
                ", gender='" + gender + "'" +
                ", size='" + size + "'" +
                ", description='" + description + "'" +
                ", image='" + image + "'" +
                '}';
    }
}

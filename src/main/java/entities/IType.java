package entities;
import javax.persistence.*;

@Entity
@Table(name = "i_types")
public class IType {

    @Id
    @Column(name = "i_type_Id", nullable = false)
    private Byte iTypeId;

    @Column(name = "description", length = 10)
    private String description;

    // Constructors (you can have multiple constructors as needed)

    public IType() {
    }

    public IType(Byte iTypeId, String description) {
        this.iTypeId = iTypeId;
        this.description = description;
    }

    // Getter and Setter methods

    public Byte getITypeId() {
        return iTypeId;
    }

    public void setITypeId(byte iTypeId) {
        this.iTypeId = iTypeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

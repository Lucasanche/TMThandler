package entities;
import entities.IType;
import javax.persistence.*;

@Entity
@Table(name = "e_types")
public class EType {

    @Id
    @Column(name = "e_type_id", nullable = false)
    private Byte eTypeId;

    @Column(name = "service_type", nullable = false, length = 80)
    private String serviceType;

    @Column(name = "i_type_id", nullable = false)
    private Byte iTypeId;

    @ManyToOne
    @JoinColumn(name = "i_type_id", referencedColumnName = "i_type_Id", insertable = false, updatable = false)
    private IType iTypeEntity;

    public EType() {
    }

    public EType(Byte eTypeId, String serviceType, Byte iTypeId) {
        this.eTypeId = eTypeId;
        this.serviceType = serviceType;
        this.iTypeId = iTypeId;
    }

    // Getter and Setter methods

    public int getETypeId() {
        return eTypeId;
    }

    public void setETypeId(Byte eTypeId) {
        this.eTypeId = eTypeId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public int getITypeId() {
        return iTypeId;
    }

    public void setITypeId(Byte iTypeId) {
        this.iTypeId = iTypeId;
    }
}
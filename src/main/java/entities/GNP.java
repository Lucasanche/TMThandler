package entities;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "GNP")
public class GNP {

    @Id
    @Column(name = "Id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "start", nullable = false, length = 13)
    private String start;

    @Column(name = "stop", nullable = false, length = 13)
    private String stop;

    @Column(name = "network_id", nullable = false, length = 7)
    private String networkId;

    @Column(name = "e_type_id", nullable = false)
    private Byte eType_id;
    @Column(name = "mcc", nullable = false, length = 3)
    private String mcc;
    @Column(name = "mnc", nullable = false, length = 3)
    private String mnc;
    @Column(name = "insertion_timestamp", nullable = false)
    private LocalDateTime insertionTimestamp;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;
    @Transient
    private String action;
    @ManyToOne
    @JoinColumn(name = "e_type_id", referencedColumnName = "e_type_id", insertable = false, updatable = false)
    private EType eTypeEntity;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "network_id", referencedColumnName = "network_id", insertable = false, updatable = false)
    public Network networkEntity;

    public Network getNetworkEntity() {
        return networkEntity;
    }
    public void setNetworkEntity(Network network){
        this.networkEntity = network;
    }
    public GNP() {
        this.mnc = "370";
        this.mcc = "";
        this.expirationDate = LocalDateTime.of(5000, 12, 31, 0, 0, 0);
    }
    public GNP(int id, String start, String stop, String networkId, String mcc, String mnc, Byte eType_id, LocalDateTime insertionTimestamp, LocalDateTime expirationDate) {
        this.id = id;
        this.start = start;
        this.stop = stop;
        this.networkId = networkId;
        this.eType_id = eType_id;
        this.mcc = mcc;
        this.mnc = mnc;
        this.insertionTimestamp = insertionTimestamp;
        this.expirationDate = expirationDate;
    }
    public GNP(String start, String stop, String networkId, String mcc, String mnc, Byte eType_id) {
        this.id = 0;
        this.start = start;
        this.stop = stop;
        this.networkId = networkId;
        this.mnc = mnc;
        this.mcc = mcc;
        this.eType_id = eType_id;
        this.insertionTimestamp = LocalDateTime.now();
        this.expirationDate = LocalDateTime.of(5000, 12, 31, 0, 0);
    }
    public void setId(int id) {
        this.id = id;
    }
    public Integer getId() {
        return this.id;
    }
    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getStop() {
        return stop;
    }

    public void setStop(String stop) {
        this.stop = stop;
    }

    public String getNetworkId() {
        return networkId;
    }

    public void setNetworkId(String networkId) {
        this.networkId = networkId;
    }

    public void setMnc(String mnc){this.mnc = mnc;}
    public String getMnc(){return mnc;}
    public void setMcc(String mcc){this.mcc = mcc;}
    public String getMcc(){return mcc;}
    public Byte getEType() {
        return eType_id;
    }

    public void setEType(Byte eType) {
        this.eType_id = eType;
    }

    public LocalDateTime getInsertionTimestamp() {
        return insertionTimestamp;
    }

    public void setInsertionTimestamp(LocalDateTime insertionTimestamp) {
        this.insertionTimestamp = insertionTimestamp;
    }
    public String getAction(){
        return this.action;
    }
    public void setAction(String action){
        this.action = action;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }
}


package entities;

import javax.persistence.*;

@Entity(name = "networks")
//@Table(name = "networks")
public class Network {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "network_id", nullable = false, length = 7)
    private String networkId;

    @Column(name = "network", nullable = false, length = 21)
    private String network;

    @Column(name = "full_name", nullable = false, length = 50)
    private String fullName;

    @Column(name = "digitalk_name", nullable = false, length = 100)
    private String digitalKName;

    @Column(name = "mcc", length = 3)
    private String mcc;

    @Column(name = "mnc", length = 2)
    private String mnc;

    // Constructors (you can have multiple constructors as needed)

    public Network() {
    }

    public Network(String networkId, String network, String fullName, String digitalkName, String mcc, String mnc) {
        this.networkId = networkId;
        this.network = network;
        this.fullName = fullName;
        this.digitalKName = digitalkName;
        this.mcc = mcc;
        this.mnc = mnc;
    }

    // Getter and Setter methods

    public String getNetworkId() {
        return networkId;
    }

    public void setNetworkId(String networkId) {
        this.networkId = networkId;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDigitalkName() {
        return digitalKName;
    }

    public void setDigitalkName(String digitalkName) {
        this.digitalKName = digitalkName;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getMnc() {
        return mnc;
    }

    public void setMnc(String mnc) {
        this.mnc = mnc;
    }

    @Override
    public String toString() {
        return "Network{" +
                "networkId='" + networkId + '\'' +
                ", network='" + network + '\'' +
                ", fullName='" + fullName + '\'' +
                ", digitalKName='" + digitalKName + '\'' +
                ", mcc='" + mcc + '\'' +
                ", mnc='" + mnc + '\'' +
                '}';
    }
}


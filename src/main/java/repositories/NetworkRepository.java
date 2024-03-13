package repositories;

import entities.Network;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class NetworkRepository implements Repository<Network> {
    private final Session session;

    public NetworkRepository(Session session) {
        this.session = session;
    }

    @Override
    public void save(Network network) {
        Transaction trx = session.beginTransaction();
        session.save(network);
        System.out.println("NOT IN LOG: Network saved OK");
        trx.commit();
    }

    @Override
    public List<Network> listAll() {
        Transaction trx = session.beginTransaction();
        List<Network> list = session.createQuery("FROM networks", Network.class).getResultList();
        trx.commit();
        return list;
    }

    @Override
    public Network findById(String ID) {
        Transaction trx = session.beginTransaction();
        Network network = session.createQuery("From networks where networkId=:ID", Network.class).setParameter("ID", ID).getSingleResult();
        trx.commit();
        return network;
    }

    @Override
    public void update(Network network) {
        Transaction trx = session.beginTransaction();
        session.update(network);
        System.out.print("Network updated OK with ID: "+ network.getNetworkId());
        trx.commit();
    }

    @Override
    public void delete(Network network) {
        Transaction trx = session.beginTransaction();
        session.delete(network);
        System.out.print("Network deleted OK with ID: "+ network.getNetworkId());
        trx.commit();
    }
}

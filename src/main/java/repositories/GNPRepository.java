package repositories;

import entities.GNP;
import entities.Network;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class GNPRepository implements Repository<GNP>{
    private final Session session;

    public GNPRepository(Session session) {
        this.session = session;
    }
    @Override
    public void save(GNP gnp) {
        Transaction trx = session.beginTransaction();
        session.save(gnp);
        System.out.println("NOT IN LOG: GNP saved OK");
        trx.commit();
    }

    @Override
    public List<GNP> listAll() {
        Transaction trx = session.beginTransaction();
        List<GNP> list = session.createQuery("FROM GNP", GNP.class).getResultList();
        trx.commit();
        return list;
    }

    @Override
    public GNP findById(String ID) {
        Transaction trx = session.beginTransaction();
        GNP gnp = session.createQuery("From GNP where id=:ID", GNP.class).setParameter("ID", ID).getSingleResult();
        trx.commit();
        return gnp;
    }

    @Override
    public void update(GNP gnp) {
        Transaction trx = session.beginTransaction();
        session.update(gnp);
        System.out.print("GNP updated OK with ID: "+ gnp.getId().toString());
        trx.commit();
    }

    @Override
    public void delete(GNP gnp) {
        Transaction trx = session.beginTransaction();
        session.delete(gnp);
        System.out.print("GNP deleted OK with ID: "+ gnp.getId().toString());
        trx.commit();
    }
}

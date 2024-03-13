package repositories;

import entities.EType;
import entities.Network;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;
public class ETypeRepository implements Repository<EType>{
    private final Session session;

    public ETypeRepository(Session session) {
        this.session = session;
    }
    @Override
    public void save(EType eType) {
        Transaction trx = session.beginTransaction();
        session.save(eType);
        System.out.println("NOT IN LOG: EType saved OK");
        trx.commit();
    }

    @Override
    public List<EType> listAll() {
        Transaction trx = session.beginTransaction();
        List<EType> list = session.createQuery("FROM EType", EType.class).getResultList();
        trx.commit();
        return list;
    }

    @Override
    public EType findById(String eTypeId) {
        Transaction trx = session.beginTransaction();
        EType eType = session.createQuery("From EType where eTypeId=:ID", EType.class).setParameter("ID", eTypeId).getSingleResult();
        trx.commit();
        return eType;
    }

    @Override
    public void update(EType eType) {
        Transaction trx = session.beginTransaction();
        session.update(eType);
        System.out.print("EType updated OK with ID: "+ eType.getETypeId());
        trx.commit();
    }

    @Override
    public void delete(EType eType) {
        Transaction trx = session.beginTransaction();
        session.delete(eType);
        System.out.print("EType deleted OK with ID: "+ eType.getETypeId());
        trx.commit();
    }
}

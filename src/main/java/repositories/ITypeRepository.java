package repositories;

import entities.IType;
import entities.Network;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ITypeRepository implements Repository<IType>{
    private final Session session;

    public ITypeRepository(Session session) {
        this.session = session;
    }
    @Override
    public void save(IType iType) {
        Transaction trx = session.beginTransaction();
        session.save(iType);
        System.out.println("NOT IN LOG: IType saved OK");
        trx.commit();
    }

    @Override
    public List<IType> listAll() {
        Transaction trx = session.beginTransaction();
        List<IType> list = session.createQuery("FROM IType ", IType.class).getResultList();
        trx.commit();
        return list;
    }

    @Override
    public IType findById(String ID) {
        Transaction trx = session.beginTransaction();
        IType itype = session.createQuery("From IType where IType=:ID", IType.class).setParameter("ID", ID).getSingleResult();
        trx.commit();
        return itype;
    }

    @Override
    public void update(IType iType) {
        Transaction trx = session.beginTransaction();
        session.update(iType);
        System.out.print("IType updated OK with ID: "+ iType.getITypeId());
        trx.commit();
    }

    @Override
    public void delete(IType iType) {
        Transaction trx = session.beginTransaction();
        session.delete(iType);
        System.out.print("IType deleted OK with ID: "+ iType.getITypeId());
        trx.commit();
    }
}

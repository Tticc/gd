package gd.web.domainImpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import gd.web.domain.InStreamDAO;
import gd.web.entity.InStreamEntity;

@Repository
public class InStreamDAOImpl implements InStreamDAO{
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void addInStream(InStreamEntity inStreamEntity) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().save(inStreamEntity);
	}

	@Override
	public void inactivate(int staId) {
		// TODO Auto-generated method stub
		
		String hql = "from InStreamEntity where active = ? and staId = ? and isValid = ?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger(0, 1);
		query.setInteger(1, staId);
		query.setInteger(2, 1);

		List<InStreamEntity> inStreamEntityList = query.list();
		if(inStreamEntityList.size()>0){
			InStreamEntity inStreamEntity = inStreamEntityList.get(0);
			inStreamEntity.setActive(0);
			updateInStream(inStreamEntity);
		}
	}

	@Override
	public void updateInStream(InStreamEntity inStreamEntity) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().update(inStreamEntity);
	}

	@Override
	public InStreamEntity getActiveRecord(int staId) {
		// TODO Auto-generated method stub
		String hql = "from InStreamEntity where active = ? and staId = ? and isValid = ?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger(0, 1);
		query.setInteger(1, staId);
		query.setInteger(2, 1);

		List<InStreamEntity> inStreamEntityList = query.list();
		if(inStreamEntityList.size()>0){
			return inStreamEntityList.get(0);
		}
		return null;
	}
}

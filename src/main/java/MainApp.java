import model.Person;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

public class MainApp {



    public static void main(String[] args) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            transaction.begin();

            Person person = new Person();
            person.setName("Mike Gilmore");
            session.save(person);
            transaction.commit();
        }catch (Exception e)
        {
            if(transaction != null)
            {
                transaction.rollback();
            }
        }finally {
            if(session != null)
            {
                session.close();
            }

        }

        HibernateUtil.shutdown();
    }

}

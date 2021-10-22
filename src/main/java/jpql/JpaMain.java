package jpql;


import javax.persistence.*;
import java.util.Collection;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            System.out.println("==================");

            Member member = new Member();
            member.setUsername("memberA");
            member.setAge(10);
            member.setType(MemberType.ADMIN);
            member.changeTeam(team);

            em.persist(member);

            em.flush();
            em.clear();

            Member findMember = em.find(Member.class, member.getId());

            Order order = new Order();
            order.setAddress(new Address("iksan","sun","12345"));
            order.setOrderAmount(100);
            order.setMember(findMember);

            em.persist(order);

            em.flush();
            em.clear();
            Member findMember2 = em.find(Member.class, member.getId());
            List<Order> orders = findMember2.getOrders();

            System.out.println("orders = " + orders);
            System.out.println("++++++++");

            for (Order order1 : orders) {
                System.out.println("order1 = " + order1);
            }


            tx.commit();
        } catch (Exception e) {
            System.out.println("e = " + e);
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();

    }

}

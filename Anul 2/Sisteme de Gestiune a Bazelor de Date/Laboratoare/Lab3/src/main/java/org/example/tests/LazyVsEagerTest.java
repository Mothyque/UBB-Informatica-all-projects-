package org.example.tests;


import org.example.domain.Professor;
import org.example.utils.HibernateUtil;
import org.hibernate.Session;

public class LazyVsEagerTest
{
    public static void main(String[] args)
    {
        System.out.println("Test: Lazy vs Eager Loading");

        Integer testProfessorId = 1;
        testLazyLoading(testProfessorId);

        System.out.println("\n -------------------------------------------");

        testEagerLoading(testProfessorId);

        HibernateUtil.shutdown();
    }

    private static void testLazyLoading(int testProfessorId)
    {
        System.out.println("Lazy Loading");

        try (Session session = HibernateUtil.getSessionFactory().openSession())
        {
            System.out.println("Interogarea 1: aducem profesorul pe baza id-ului");
            Professor professor = session.get(Professor.class, testProfessorId);
            System.out.println("Interogarea 2: aducem numarul de materii pe baza id-ului");
            int classesCount = professor.getClasses().size();

            System.out.println("Profesorul " + professor.getName() + " are " + classesCount + " materii");
        }
    }

    private static void testEagerLoading(int testProfessorId)
    {
        System.out.println("Eager Loading");
        try (Session session = HibernateUtil.getSessionFactory().openSession())
        {
            String hql = "SELECT p FROM Professor p LEFT JOIN FETCH p.collegeClasses WHERE p.id = :id";
            System.out.println("Interogarea 1: aducem profesorul pe baza id-ului impreuna cu materiile");
            Professor professor = session.createQuery(hql, Professor.class)
                    .setParameter("id", testProfessorId)
                    .getSingleResult();

            int classesCount = professor.getClasses().size();
            System.out.println("Profesorul " + professor.getName() + " are " + classesCount + " materii");
        }
    }
}
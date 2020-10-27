package no.kristiania.database;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MemberDao {

    private DataSource dataSource;

    public MemberDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public static void main(String[] args) throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL("jdbc:postgresql://localhost:5432/kristianiashop");
        dataSource.setUser("kristianiashopuser");
        dataSource.setPassword("abc");

        MemberDao memberDao = new MemberDao(dataSource);

        System.out.print("Enter member name: ");
        Scanner scanner = new Scanner(System.in);
        String memberName = scanner.next();

        Member member = new Member();
        member.setName(memberName);
        memberDao.insert(member);
        for (Member m : memberDao.list()) {
            System.out.println(m);
        }
    }

    public void insert(Member member) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO members (member_name) VALUES (?)")) {
                statement.setString(1, member.getName());
                statement.executeUpdate();
            }
        }
    }

    public List<Member> list() throws SQLException {
        List<String> members = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM members")) {
                try (ResultSet rs = statement.executeQuery()) {
                    while (rs.next()) {
                        Member member = new Member();
                        members.add(rs.getString("member_name"));
                        members.add(member);
                    }
                }
            }
        }
        return members;
    }
}
